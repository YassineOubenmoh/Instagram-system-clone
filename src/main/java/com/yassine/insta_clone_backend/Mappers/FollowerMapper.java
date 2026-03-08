package com.yassine.insta_clone_backend.Mappers;

import com.yassine.insta_clone_backend.dtos.FollowerDto;
import com.yassine.insta_clone_backend.entities.Follower;
import com.yassine.insta_clone_backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FollowerMapper {

    FollowerMapper INSTANCE = Mappers.getMapper(FollowerMapper.class);

    @Mapping(source = "userFollowing", target = "userFollowingUsername", qualifiedByName = "userFollowingToUserFollowingUsername")
    @Mapping(source = "userFollowed", target = "userFollowedUsername", qualifiedByName = "userFollowedToUserFollowedUsername")
    FollowerDto followerToFollowDto(Follower follower);

    @Mapping(source = "userFollowingUsername", target = "userFollowing", qualifiedByName = "userFollowingUsernameToUserFollowing")
    @Mapping(source = "userFollowedUsername", target = "userFollowed", qualifiedByName = "userFollowedUsernameToUserFollowed")
    Follower followerDtoToFollower(FollowerDto followerDto);


    @Named("userFollowingToUserFollowingUsername")
    default String userFollowingToUserFollowingUsername(User userFollowing){
        return userFollowing.getUsername();
    }

    @Named("userFollowingUsernameToUserFollowing")
    default User userFollowingUsernameToUserFollowing(String username){
        if (username == null){
            return null;
        }
        return User.builder()
                .username(username)
                .build();
    }

    @Named("userFollowedToUserFollowedUsername")
    default String userFollowedToUserFollowedUsername(User userFollowed){
        return userFollowed.getUsername();
    }

    @Named("userFollowedUsernameToUserFollowed")
    default User userFollowedUsernameToUserFollowed(String username){
        if (username == null){
            return null;
        }
        return User.builder()
                .username(username)
                .build();
    }


}
