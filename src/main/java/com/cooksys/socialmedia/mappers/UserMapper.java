package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { CredentialsMapper.class, ProfileMapper.class })
public interface UserMapper {

    User requestDtoToEntity(UserRequestDto userRequestDto);

    UserResponseDto entityToResponseDto(User user);
}
