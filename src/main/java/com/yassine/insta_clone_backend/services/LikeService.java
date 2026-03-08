package com.yassine.insta_clone_backend.services;

import com.yassine.insta_clone_backend.Mappers.LikeMapper;
import com.yassine.insta_clone_backend.Mappers.PostMapper;
import com.yassine.insta_clone_backend.Mappers.UserMapper;
import com.yassine.insta_clone_backend.dtos.FollowerDto;
import com.yassine.insta_clone_backend.dtos.LikeDto;
import com.yassine.insta_clone_backend.dtos.PostDto;
import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.entities.Like;
import com.yassine.insta_clone_backend.entities.Post;
import com.yassine.insta_clone_backend.entities.User;
import com.yassine.insta_clone_backend.exception.NoResourceExistsException;
import com.yassine.insta_clone_backend.exception.ResourceAlreadyExistsException;
import com.yassine.insta_clone_backend.exception.ResourceNotFoundException;
import com.yassine.insta_clone_backend.repositories.LikeRepository;
import com.yassine.insta_clone_backend.repositories.PostRepository;
import com.yassine.insta_clone_backend.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    private final LikeMapper likeMapper;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostService postService;
    private final UserService userService;
    private final FollowerService followerService;
    private final EntityManager entityManager;

    public void likePost(LikeDto likeDto) throws Exception {
        Post post = postRepository.findById(likeDto.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Post not found !"));
        User user = userRepository.findByUsername(likeDto.getUserLikingUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        Like like = Like.builder()
                .post(post)
                .userLiking(user)
                .build();
        if (likeRepository.findAll().contains(like)){
            throw new ResourceAlreadyExistsException("You already liked the post");
        }
        likeRepository.save(like);
    }

    public Set<UserDto> getAccountsWhoLikedPost(Long postId){
        Set<UserDto> userDtoSet = new HashSet<>();
        Set<User> usersLikingPost = likeRepository.findAccountsLikingPost(postId);
        if (usersLikingPost.isEmpty()){
            throw new NoResourceExistsException("No Like yet");
        }
        usersLikingPost.forEach(userDto -> userDtoSet.add(userService.getUserByUsername(userDto.getUsername())));
        return userDtoSet;
    }

    public Set<UserDto> getFollowedAccountsLikingPost(Long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<FollowerDto> followedAccountsObj = followerService.getFollowedAccountsByUser(username);
        Set<UserDto> followedAccounts = followedAccountsObj.stream()
                .map(followedObj -> userService.getUserByUsername(followedObj.getUserFollowedUsername()))
                .collect(Collectors.toSet());
        Set<UserDto> accountsLikingPost = getAccountsWhoLikedPost(postId);
        if (accountsLikingPost.isEmpty()){
            throw new NoResourceExistsException("No followed account liked the post " + postId);
        }
        return accountsLikingPost.stream()
                .filter(followedAccounts::contains)
                .limit(3)
                .collect(Collectors.toSet());
    }

    public int countLikesPost(Long postId){
        Set<UserDto> users = getAccountsWhoLikedPost(postId);
        return users.size();
    }

}
