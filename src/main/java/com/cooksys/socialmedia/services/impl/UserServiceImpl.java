package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.Profile;
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

        if (credentialsDto == null) {
            throw new NotFoundException("No Credentials given. Please provide credentials");

        }
        Optional<Credentials> optionalCredentials = Optional.of(credentialsMapper.dtoToEntity(credentialsDto));
        Credentials credentials = optionalCredentials.get();
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);
        if (optionalUser.isEmpty()) {
            System.out.println(userRepository.findByCredentials(credentials));
            throw new NotFoundException("No User found with Username: " + credentials.getUsername());

        }
        if (optionalUser.isEmpty()) {
            System.out.println(userRepository.findByCredentials(credentials));
            throw new NotFoundException("No User found with Username: " + credentials.getUsername());

        }
        User userToUpdate = optionalUser.get();
        userToUpdate.setProfile(profileMapper.dtoToEntity(profileDto));
        userToUpdate.setCredentials(credentials);
        
        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToUpdate));
        
        
    }

    @Override
    public UserResponseDto deleteUser(
        String username,
        CredentialsDto credentialsDto) {

        if (credentialsDto == null) {
            throw new NotFoundException("No Credentials given. Please provide credentials");

        }
        Optional<Credentials> optionalCredentials = Optional.of(credentialsMapper.dtoToEntity(credentialsDto));
        Credentials credentials = optionalCredentials.get();
        
        Optional<User> optionalUser = userRepository.findByCredentials_UsernameAndDeletedFalse(credentials.getUsername());
        if (optionalUser.isEmpty()) {
            System.out.println(userRepository.findByCredentials(credentials));
            throw new NotFoundException("No User found with Username: " + credentials.getUsername());

        }
        
        User userToDelete = optionalUser.get();
        userToDelete.setDeleted(true);
        
        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToDelete));
    }

    @Override
    public UserResponseDto getUser(String username) {

        Optional<User> optionalUser = userRepository.findByCredentials_UsernameAndDeletedFalse(username);
        
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No User found with Username: " + username);

        }
        
        return userMapper.entityToResponseDto(optionalUser.get());
    }
}
