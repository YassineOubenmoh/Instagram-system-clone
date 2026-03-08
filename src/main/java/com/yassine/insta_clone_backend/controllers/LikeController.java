package com.yassine.insta_clone_backend.controllers;

import com.yassine.insta_clone_backend.dtos.LikeDto;
import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<String> likePost(@PathVariable("postId") Long postId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLiking = authentication.getName();
        LikeDto likeDto = LikeDto.builder()
                .userLikingUsername(usernameLiking)
                .postId(postId)
                .build();
        likeService.likePost(likeDto);
        return new ResponseEntity<>("Like done successfully", HttpStatus.OK);
    }


    @GetMapping("/count/{postId}")
    public ResponseEntity<Integer> countLikePost(@PathVariable("postId") Long postId){
        int countLikes = likeService.countLikesPost(postId);
        return new ResponseEntity<>(countLikes, HttpStatus.OK);
    }


    @GetMapping("/followed-who-liked/{postId}")
    public ResponseEntity<Set<UserDto>> getFollowedAccountsLikingPost(@PathVariable("postId") Long postId){
        Set<UserDto> users = likeService.getFollowedAccountsLikingPost(postId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
