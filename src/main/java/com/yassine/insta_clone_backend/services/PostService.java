package com.yassine.insta_clone_backend.services;

import com.yassine.insta_clone_backend.Mappers.CommentMapper;
import com.yassine.insta_clone_backend.Mappers.PostMapper;
import com.yassine.insta_clone_backend.Mappers.UserMapper;
import com.yassine.insta_clone_backend.dtos.*;

import com.yassine.insta_clone_backend.entities.Post;
import com.yassine.insta_clone_backend.entities.User;
import com.yassine.insta_clone_backend.exception.ResourceAlreadyExistsException;
import com.yassine.insta_clone_backend.exception.ResourceNotFoundException;
import com.yassine.insta_clone_backend.exception.RetrieveImageFailedException;
import com.yassine.insta_clone_backend.repositories.PostRepository;
import com.yassine.insta_clone_backend.repositories.UserRepository;
import com.yassine.insta_clone_backend.utils.TimeUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);


    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FollowerService followerService;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final FileService fileService;
    private final TimeUtils timeUtils;

    private final EntityManager entityManager;


    public Set<PostDto> getPostsOfFollowingAccounts(String username){
            Set<Post> postOfFollowedAccounts = new HashSet<>();
            Set<FollowerDto> followedAccounts = followerService.getFollowedAccountsByUser(username);
            followedAccounts
                    .forEach(followObj -> postOfFollowedAccounts.addAll(postRepository.findPostsByUser(followObj.getUserFollowedUsername())));

            Set<PostDto> postDtoSet = postOfFollowedAccounts.stream()
                    .map(this::mapAllValuesToPostDto)
                    .collect(Collectors.toSet());
            postDtoSet.forEach(postDto -> {
                try {
                    postDto.setPostImage(fileService.getFile(postDto.getPostImageName()).readAllBytes());
                } catch (Exception e) {
                    throw new RetrieveImageFailedException("Image named " + postDto.getPostImageName() + " not found !");
                }
            });
            return postDtoSet;
    }

    public PostDto getPostById(Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found !"));
        InputStream imageContent = fileService.getFile(post.getPostImageName());
        PostDto postDto = mapAllValuesToPostDto(post);
        postDto.setPostImage(imageContent.readAllBytes());
        return postDto;
    }

    public UserDto getPostPublisher(String username){
        return userService.getUserByUsername(username);
    }



    public PostRequest addPost(PostRequest postRequest, MultipartFile imageFile) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        postRequest.setUserPostingUsername(username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String uniqueFileName = fileService.uploadImage(imageFile);
        postRequest.setPostImageName(uniqueFileName);

        Post post = postMapper.postRequestToPost(postRequest);
        post.setUserPosting(user);

        if (postRepository.findAll().contains(post)) {
            throw new ResourceAlreadyExistsException("Post already exists!");
        }

        postRepository.save(post);
        return postRequest;
    }



    public Set<CommentDto> getCommentsOfPost(Long postId) throws Exception {
        PostDto postDto = getPostById(postId);
        Post post = postMapper.postDtoToPost(postDto);
        return post.getComments().stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toSet());
    }

    public PostDto mapAllValuesToPostDto(Post post){
        PostDto postDto = postMapper.postToPostDto(post);
        postDto.setTimePassedBetweenNowAndDatePublication(timeUtils.getTimeBetweenDates(post.getCreatedAt(), LocalDateTime.now()));
        logger.info("The postDto object contains {}", postDto.getTimePassedBetweenNowAndDatePublication());
        return postDto;
    }






}
