package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final CredentialsMapper credentialsMapper;

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {

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
        
        Pattern mentionsRegex = Pattern.compile("(?<=@)\\w+");
        Matcher m = mentionsRegex.matcher(tweetToSave.getContent());
        List<String> mentionedUsernames = new ArrayList<>();
        if (m.find()) {
            mentionedUsernames.add(m.group());
        }

        List<User> mentionedUsers = new ArrayList<>();

        for (String username : mentionedUsernames) {
            Optional<User> optionalMentionedUser = userRepository.findByCredentials_UsernameAndDeletedFalse(username);

            if (optionalMentionedUser.isPresent()) {
                mentionedUsers.add(optionalMentionedUser.get());
            }
        }
        tweetToSave.setMentionedUsers(mentionedUsers);

        // TODO: Hashtag matching

        return tweetMapper.entityToTweetResponseDto(tweetRepository.saveAndFlush(tweetToSave));
    }
}
