package com.yassine.insta_clone_backend.Mappers;

import com.yassine.insta_clone_backend.dtos.PostDto;
import com.yassine.insta_clone_backend.dtos.PostRequest;
import com.yassine.insta_clone_backend.entities.Comment;
import com.yassine.insta_clone_backend.entities.Post;
import com.yassine.insta_clone_backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source="userPosting", target="userPostingUsername", qualifiedByName = "userToUsernamePosting")
    PostDto postToPostDto(Post post);

    @Mapping(source="userPostingUsername", target="userPosting", qualifiedByName = "usernamePostingToUser")
    Post postDtoToPost(PostDto postDto);

    @Mapping(source="userPostingUsername", target="userPosting", qualifiedByName = "usernamePostingToUser")
    Post postRequestToPost(PostRequest postRequest);


    /*
    @Named("commentSetToCommentIds")
    default Set<Long> commentSetToCommentIds(Set<Comment> commentSet){
        if (commentSet.isEmpty()){
            return null;
        }
        return commentSet.stream()
                .map(Comment::getId)
                .collect(Collectors.toSet());
    }


    @Named("commentIdsToCommentSet")
    default Set<Comment> commentIdsToCommentSet(Set<Long> commentIds){
        if (commentIds.isEmpty()){
            return null;
        }
        return commentIds.stream()
                .map(commentId -> {
                    Comment comment = new Comment();
                    comment.setId(comment.getId());
                    return comment;
                })
                .collect(Collectors.toSet());
    }

     */


    @Named("usernamePostingToUser")
    default User usernamePostingToUser(String username){
        if (username == null){
            return null;
        }
        return User.builder()
                .username(username)
                .build();
    }


    @Named("userToUsernamePosting")
    default String userToUsernamePosting(User user){
        if (user == null){
            return null;
        }
        return user.getUsername();
    }
}
