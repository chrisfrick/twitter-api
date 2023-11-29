package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.SimpleTweetResponseDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final CredentialsMapper credentialsMapper;

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleTweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {

        Tweet tweetToSave = tweetMapper.tweetRequestDtoToEntity(tweetRequestDto);

        Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());

        Optional<User> optionalUser = userRepository.findByCredentials(credentials);

        if (optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new NotAuthorizedException("User with given credentials does not exist");
        }

        tweetToSave.setAuthor(optionalUser.get());

        if (tweetToSave.getContent() == null) {
            throw new BadRequestException("New tweet must have content");
        }

        return tweetMapper.entityToSimpleTweetResponseDto(tweetRepository.saveAndFlush(tweetToSave));
    }
}
