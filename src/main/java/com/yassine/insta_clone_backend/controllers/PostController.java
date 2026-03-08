package com.yassine.insta_clone_backend.controllers;

import com.yassine.insta_clone_backend.dtos.PostDto;
import com.yassine.insta_clone_backend.dtos.PostRequest;
import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.services.FileService;
import com.yassine.insta_clone_backend.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @GetMapping("/feed")
    public ResponseEntity<Set<PostDto>> getPostsOfFollowingAccounts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<PostDto> postDtoSet = postService.getPostsOfFollowingAccounts(username);
        return new ResponseEntity<>(postDtoSet, HttpStatus.OK);
    }

    @GetMapping("/find/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId) throws Exception {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/publisher")
    public ResponseEntity<UserDto> getPostPublisher(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDto userDto = postService.getPostPublisher(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<PostRequest> addPost(@RequestPart("data") PostRequest postRequest,
                                               @RequestPart(name = "file") MultipartFile image) throws Exception {

        return new ResponseEntity<>(postService.addPost(postRequest, image), HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile image) throws Exception {
        fileService.uploadImage(image);
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.CREATED);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName){
        try (InputStream fileStream = fileService.getFile(fileName)) {
            if (fileStream == null) {
                return ResponseEntity.status(404).body(null); // File not found
            }

            byte[] content = fileStream.readAllBytes();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(content);
        } catch (Exception e) {
            System.err.println("Error occurred while downloading the file: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }





}
