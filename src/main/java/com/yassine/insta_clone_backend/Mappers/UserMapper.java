package com.yassine.insta_clone_backend.Mappers;

import com.yassine.insta_clone_backend.dtos.UserDto;
import com.yassine.insta_clone_backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
