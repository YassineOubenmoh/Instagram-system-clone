package com.yassine.insta_clone_backend.controllers;

import com.yassine.insta_clone_backend.dtos.LikeDto;
import com.yassine.insta_clone_backend.dtos.SaveDto;
import com.yassine.insta_clone_backend.services.LikeService;
import com.yassine.insta_clone_backend.services.SaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@RequestMapping("/save")
public class SaveController {

    private final SaveService saveService;

    @PostMapping
    public ResponseEntity<String> savePost(@RequestBody SaveDto saveDto) throws Exception {
        saveService.savePost(saveDto);
        return new ResponseEntity<>("Like done successfully", HttpStatus.OK);
    }
}
