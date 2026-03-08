package com.yassine.insta_clone_backend.controllers;

import com.yassine.insta_clone_backend.dtos.CommentDto;
import com.yassine.insta_clone_backend.services.CommentService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> addCommentOfPost(@RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.addCommentOfPost(commentDto);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Integer> countCommentsOfPost(@PathVariable("postId") Long postId){
        return new ResponseEntity<>(commentService.countCommentsOfPost(postId), HttpStatus.OK);
    }

    @GetMapping("/find/{postId}")
    public ResponseEntity<Set<CommentDto>> getCommentsByPostId(@PathVariable("postId") Long postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

}
