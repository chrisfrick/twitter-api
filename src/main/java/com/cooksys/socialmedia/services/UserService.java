package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(String username, CredentialsDto credentialsDto, ProfileDto profileDto);

    UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

    UserResponseDto updateUser(String username, CredentialsDto credentialsDto, ProfileDto profileDto);

    UserResponseDto createUser(UserRequestDto userRequestDto);

}
