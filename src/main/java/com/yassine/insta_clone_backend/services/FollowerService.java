package com.yassine.insta_clone_backend.services;

import com.yassine.insta_clone_backend.Mappers.FollowerMapper;
import com.yassine.insta_clone_backend.Mappers.UserMapper;
import com.yassine.insta_clone_backend.dtos.FollowerDto;
import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.entities.Follower;
import com.yassine.insta_clone_backend.entities.User;
import com.yassine.insta_clone_backend.exception.FollowAlreadyExistsException;
import com.yassine.insta_clone_backend.exception.NoResourceExistsException;
import com.yassine.insta_clone_backend.exception.ResourceNotFoundException;
import com.yassine.insta_clone_backend.repositories.FollowerRepository;
import com.yassine.insta_clone_backend.repositories.UserRepository;
import com.yassine.insta_clone_backend.utils.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;
    private final FollowerMapper followerMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final NumberUtils numberUtils;

    public Set<FollowerDto> getFollowedAccountsByUser(String followerUsername){
        User user = userRepository.findByUsername(followerUsername).orElseThrow(
                () -> new UsernameNotFoundException("User not found !"));


        Set<Follower> followedAccounts = followerRepository.findFollowerByFollowerId(user.getId());
        if (followedAccounts.isEmpty()){
            return null;
        }
        return followedAccounts.stream()
                .map(followerMapper::followerToFollowDto)
                .collect(Collectors.toSet());
    }


    public FollowerDto followAccount(FollowerDto followerDto){
        Follower follower = followerMapper.followerDtoToFollower(followerDto);
        List<Follower> listFollows = followerRepository.findAll();
        if (listFollows.contains(follower)){
            throw new FollowAlreadyExistsException("You already follow this account");
        }
        followerRepository.save(follower);
        return followerMapper.followerToFollowDto(follower);
    }

    public Set<UserDto> getSuggestionsOfAccounts(String username){
        Set<FollowerDto> suggestionsAccounts = new HashSet<>();
        Set<FollowerDto> followedAccounts = getFollowedAccountsByUser(username);

        followedAccounts
                .forEach(followObj -> suggestionsAccounts.addAll(getFollowedAccountsByUser(followObj.getUserFollowedUsername())));

        if (suggestionsAccounts.isEmpty()){
            throw new ResourceNotFoundException("No suggestions exist");
        }

        Set<UserDto> userDtoSet = suggestionsAccounts.stream()
                .map(suggestion -> userService.getUserByUsername(suggestion.getUserFollowingUsername()))
                .collect(Collectors.toSet());

        if (userDtoSet.isEmpty()){
            throw new NoResourceExistsException("No suggestion for you");
        }

        return userDtoSet;
    }

}
