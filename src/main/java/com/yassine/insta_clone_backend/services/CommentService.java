package com.yassine.insta_clone_backend.services;

import com.yassine.insta_clone_backend.Mappers.CommentMapper;
import com.yassine.insta_clone_backend.dtos.CommentDto;
import com.yassine.insta_clone_backend.entities.Comment;
import com.yassine.insta_clone_backend.exception.NoResourceExistsException;
import com.yassine.insta_clone_backend.exception.ResourceAlreadyExistsException;
import com.yassine.insta_clone_backend.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public Set<CommentDto> getCommentsByPostId(Long postId){
        Set<Comment> commentSet = commentRepository.findCommentsByPost(postId);
        if (commentSet.isEmpty()){
            throw new NoResourceExistsException("No Comment yet");
        }
        return commentSet.stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toSet());
    }

    public int countCommentsOfPost(Long postId){
        Set<CommentDto> commentDtoSet = getCommentsByPostId(postId);
        return commentDtoSet.size();
    }

    public CommentDto addCommentOfPost(CommentDto commentDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameCommenting = authentication.getName();
        commentDto.setCommenterUsername(usernameCommenting);
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        List<Comment> commentList = commentRepository.findAll();
        if (commentList.contains(comment)){
            throw new ResourceAlreadyExistsException("Comment already exists");
        }
        commentRepository.save(comment);
        return commentDto;
    }
}