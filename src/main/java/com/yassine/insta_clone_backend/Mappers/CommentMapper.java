package com.yassine.insta_clone_backend.Mappers;

import com.yassine.insta_clone_backend.dtos.CommentDto;
import com.yassine.insta_clone_backend.entities.Comment;
import com.yassine.insta_clone_backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source="user", target="commenterUsername", qualifiedByName = "commenterUsernameToUser")
    @Mapping(source="post.id", target="postId")
    CommentDto commentToCommentDto(Comment comment);

    @Mapping(source="commenterUsername", target="user", qualifiedByName = "userToCommenterUsername")
    @Mapping(source="postId", target="post.id")
    Comment commentDtoToComment(CommentDto commentDto);

    @Named("commenterUsernameToUser")
    default String commenterUsernameToUser(User user){
        return user.getUsername();
    }

    @Named("userToCommenterUsername")
    default User userToCommenterUsername(String username){
        if (username == null){
            return null;
        }
        return User.builder()
                .username(username)
                .build();
    }
}
