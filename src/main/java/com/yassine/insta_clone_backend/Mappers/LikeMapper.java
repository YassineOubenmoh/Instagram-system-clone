package com.yassine.insta_clone_backend.Mappers;

import com.yassine.insta_clone_backend.dtos.LikeDto;
import com.yassine.insta_clone_backend.entities.Like;
import com.yassine.insta_clone_backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    LikeMapper INSTANCE = Mappers.getMapper(LikeMapper.class);

    @Mapping(source="userLiking", target = "userLikingUsername", qualifiedByName = "userLikingToUserLikingUsername")
    @Mapping(source="post.id", target = "postId")
    LikeDto likeToLikeDto(Like like);

    @Mapping(source="userLikingUsername", target = "userLiking", qualifiedByName = "userLikingUsernameToUserLiking")
    @Mapping(source="postId", target = "post.id")
    Like likeDtoToLike(LikeDto likeDto);

    @Named("userLikingToUserLikingUsername")
    default String userLikingToUserLikingUsername(User userLiking){
        return userLiking.getUsername();
    }

    @Named("userLikingUsernameToUserLiking")
    default User userLikingUsernameToUserLiking(String username){
        if (username == null){
            return null;
        }
        return User.builder()
                .username(username)
                .build();
    }
}
