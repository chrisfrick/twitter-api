package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
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

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponseDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        if (userRequestDto.getCredentials() == null
                || userRequestDto.getProfile() == null
                || userRequestDto.getCredentials().getUsername() == null
                || userRequestDto.getCredentials().getPassword() == null
                || userRequestDto.getProfile().getEmail() == null) {
            throw new BadRequestException("Username, password, and email are required");
        }

        String username = userRequestDto.getCredentials().getUsername();
        Optional<User> optionalExistingUser = userRepository.findByCredentials_UsernameIgnoreCase(username);

        if(optionalExistingUser.isPresent()) {
            User existingUser = optionalExistingUser.get();

            if (existingUser.isDeleted()) {
                existingUser.setDeleted(false);     // Re-activate existing user
//                 Should this path also update the re-activated user's profile info
//                 if the request body differs from what's in the db?
                return userMapper.entityToResponseDto(userRepository.saveAndFlush(existingUser));
            } else {
                throw new BadRequestException("Username " + username + " is unavailable");
            }
        }

        User userToSave = userMapper.requestDtoToEntity(userRequestDto);
        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToSave));
    }
}
