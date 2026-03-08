package com.yassine.insta_clone_backend.services;

import com.yassine.insta_clone_backend.Mappers.LikeMapper;
import com.yassine.insta_clone_backend.Mappers.PostMapper;
import com.yassine.insta_clone_backend.Mappers.UserMapper;
import com.yassine.insta_clone_backend.dtos.PostDto;
import com.yassine.insta_clone_backend.dtos.SaveDto;
import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.entities.Save;
import com.yassine.insta_clone_backend.exception.ResourceAlreadyExistsException;
import com.yassine.insta_clone_backend.repositories.SaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final SaveRepository saveRepository;
    private final PostService postService;
    private final UserService userService;

    public void savePost(SaveDto saveDto) throws Exception {
        PostDto postDto = postService.getPostById(saveDto.getPostSavedId());
        UserDto userDto = userService.getUserByUsername(saveDto.getUserSavingUsername());
        Save save = Save.builder()
                .userSaving(userMapper.userDtoToUser(userDto))
                .postSaved(postMapper.postDtoToPost(postDto))
                .build();
        if (saveRepository.findAll().contains(save)){
            throw new ResourceAlreadyExistsException("You already liked the post");
        }
        saveRepository.save(save);
    }
}
