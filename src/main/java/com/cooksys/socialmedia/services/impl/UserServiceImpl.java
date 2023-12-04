package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.ProfileMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final ProfileMapper profileMapper;
    private final TweetMapper tweetMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponseDtos(userRepository
            .findAllByDeletedFalse());
    }


    @Override
    public UserResponseDto updateUser(
        String username,
        CredentialsDto credentialsDto,
        ProfileDto profileDto) {

        if (credentialsDto == null) {
            throw new NotFoundException(
                "No Credentials given. Please provide credentials");

        }
        Optional<Credentials> optionalCredentials = Optional.of(
            credentialsMapper.dtoToEntity(credentialsDto));
        Credentials credentials = optionalCredentials.get();
        Optional<User> optionalUser = userRepository.findByCredentials(
            credentials);
        if (optionalUser.isEmpty()) {
            System.out.println(userRepository.findByCredentials(credentials));
            throw new NotFoundException("No User found with Username: "
                + credentials.getUsername());

        }
        if (optionalUser.isEmpty()) {
            System.out.println(userRepository.findByCredentials(credentials));
            throw new NotFoundException("No User found with Username: "
                + credentials.getUsername());

        }
        User userToUpdate = optionalUser.get();
        userToUpdate.setProfile(profileMapper.dtoToEntity(profileDto));
        userToUpdate.setCredentials(credentials);

        return userMapper.entityToResponseDto(userRepository.saveAndFlush(
            userToUpdate));

        
    }

    @Override
    public UserResponseDto deleteUser(
        String username,
        CredentialsDto credentialsDto) {

        if (credentialsDto == null) {
            throw new NotFoundException(
                "No Credentials given. Please provide credentials");

        }
        Optional<Credentials> optionalCredentials = Optional.of(
            credentialsMapper.dtoToEntity(credentialsDto));
        Credentials credentials = optionalCredentials.get();

        Optional<User> optionalUser = userRepository
            .findByCredentials_UsernameAndDeletedFalse(credentials
                .getUsername());
        if (optionalUser.isEmpty()) {
            System.out.println(userRepository.findByCredentials(credentials));
            throw new NotFoundException("No User found with Username: "
                + credentials.getUsername());

        }

        User userToDelete = optionalUser.get();
        userToDelete.setDeleted(true);

        return userMapper.entityToResponseDto(userRepository.saveAndFlush(
            userToDelete));
    }

    @Override
    public UserResponseDto getUser(String username) {

        Optional<User> optionalUser = userRepository
            .findByCredentials_UsernameAndDeletedFalse(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No User found with Username: "
                + username);

        }

        return userMapper.entityToResponseDto(optionalUser.get());
    }

    @Override
    public List<UserResponseDto> getFollowers(String username) {

        Optional<User> optionalUser = userRepository
            .findByCredentials_UsernameAndDeletedFalse(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No User found with Username: "
                + username);

        }
        User followedUser = optionalUser.get();

        List<User> followers = new ArrayList<User>();

        for (User user : followedUser.getFollowers()) {

            if (!(user.isDeleted())) {
                followers.add(user);
            }
        }

        return userMapper.entitiesToResponseDtos(followers);
    }


    @Override
    public List<UserResponseDto> getFollowing(String username) {

        Optional<User> optionalUser = userRepository
            .findByCredentials_UsernameAndDeletedFalse(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No User found with Username: "
                + username);

        }
        User userWithFollowingList = optionalUser.get();
        
        List<User> following = new ArrayList<User>();
        
        for (User user : userWithFollowingList.getFollowing()) {
            
            if (!(user.isDeleted())) {
                following.add(user);
            }
        }
        
        return userMapper.entitiesToResponseDtos(following);
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

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {


        Optional<User> optionalUser = userRepository
                .findByCredentials_UsernameAndDeletedFalse(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No User found with Username: "
                    + username);

        }
        User userWithTweets = optionalUser.get();

        List<Tweet> tweets = tweetRepository.findByAuthorAndDeletedOrderByPostedDesc(userWithTweets, false);

        return tweetMapper.entitiesToResponseDtos(tweets);
    }

    @Override
    public void unfollowUser(String username, CredentialsDto credentialsDto) {
        User follower = getUserByCredentials(credentialsDto);
        User following = getUserByUsername(username);

        if (!follower.getFollowing().contains(following)) {
            throw new NotFoundException("No following relationship found");
        }

        follower.getFollowing().remove(following);
        userRepository.saveAndFlush(follower);
    }

    private User getUserByCredentials(CredentialsDto credentialsDto) {
        return userRepository.findByCredentials(credentialsMapper.dtoToEntity(credentialsDto))
                .orElseThrow(() -> new NotFoundException("No user found with the provided credentials"));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByDeletedFalseAndCredentials_UsernameIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("No followable user found with username: " + username));

    }
}
