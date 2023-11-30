package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.ProfileMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final ProfileMapper profileMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponseDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto updateUser(String username, CredentialsDto credentialsDto, ProfileDto profileDto) {

        Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
        Optional<User> optionalUser = userRepository.findByCredentials_UsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No User found with Username: " + username);

        }
        User userToUpdate = optionalUser.get();
        userToUpdate.setProfile(profileMapper.dtoToEntity(profileDto));
        userToUpdate.setCredentials(credentials);
        
        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToUpdate));
        
        
    }
}
