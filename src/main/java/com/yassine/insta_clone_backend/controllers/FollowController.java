package com.yassine.insta_clone_backend.controllers;

import com.yassine.insta_clone_backend.dtos.FollowerDto;
import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.services.CommentService;
import com.yassine.insta_clone_backend.services.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowerService followerService;
    private final CommentService commentService;

    @GetMapping("followed")
    public ResponseEntity<Set<FollowerDto>> getFollowedAccountsByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<FollowerDto> followerDtoSet = followerService.getFollowedAccountsByUser(username);
        return new ResponseEntity<>(followerDtoSet, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FollowerDto> followAccount(@RequestBody FollowerDto follower){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        follower.setUserFollowingUsername(authentication.getName());
        FollowerDto followerDto = followerService.followAccount(follower);
        return new ResponseEntity<>(followerDto, HttpStatus.CREATED);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<Set<UserDto>> getSuggestionsOfAccounts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(followerService.getSuggestionsOfAccounts(username), HttpStatus.OK);
    }
}
