package com.yassine.insta_clone_backend.services;

import com.yassine.insta_clone_backend.Mappers.UserMapper;
import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.entities.User;
import com.yassine.insta_clone_backend.exception.PasswordIncorrectException;
import com.yassine.insta_clone_backend.exception.ResourceNotFoundException;
import com.yassine.insta_clone_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    public UserDto updateUser(Long id, UserDto userDto, MultipartFile imageFile) throws IOException {

        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found !"));
        if (userDto.getFirstName() != null){
            existingUser.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null){
            existingUser.setLastName(userDto.getLastName());
        }
        if (userDto.getBio() != null){
            existingUser.setBio(userDto.getBio());
        }
        if (imageFile != null){
            existingUser.setImageName(imageFile.getOriginalFilename());
            existingUser.setImageType(imageFile.getContentType());
            existingUser.setImageContent(imageFile.getBytes());
        }
        userRepository.save(existingUser);
        return userMapper.userToUserDto(existingUser);
    }

     */


    public void updatePassword(Long id, String oldPassword, String newPassword){
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found !"));

        if (passwordEncoder.matches(oldPassword, existingUser.getPassword())){
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        } else {
            throw new PasswordIncorrectException("Password incorrect !");
        }
    }

    public UserDto getUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found !"));
        return userMapper.userToUserDto(user);
    }





}
