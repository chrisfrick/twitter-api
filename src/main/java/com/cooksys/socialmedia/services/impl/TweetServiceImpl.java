package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
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
    private final HashtagRepository hashtagRepository;

    private List<String> parseMentions(String content) {

        Pattern regex = Pattern.compile("(?<=@)\\w+");
        Matcher m = regex.matcher(content);

        List<String> mentionedUsernames = new ArrayList<>();
        while (m.find()) {
            mentionedUsernames.add(m.group());
        }

        System.out.println(mentionedUsernames);
        return mentionedUsernames;
    }

    private List<String> parseHashtags(String content) {

        Pattern regex = Pattern.compile("#\\w+");
        Matcher m = regex.matcher(content);

        List<String> hashtags = new ArrayList<>();
        while (m.find()) {
            hashtags.add(m.group().toLowerCase());
        }
        return hashtags;
    }

    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {

        Tweet tweetToSave = tweetMapper.tweetRequestDtoToEntity(tweetRequestDto);
        Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());

        Optional<User> optionalUser = userRepository.findByCredentials(credentials);

        if (optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new NotAuthorizedException("User with given credentials does not exist");
        }

        if (tweetToSave.getContent() == null) {
            throw new BadRequestException("New tweet must have content");
        }

        tweetToSave.setAuthor(optionalUser.get());

        List<String> mentionedUsernames = parseMentions(tweetToSave.getContent());
        List<User> mentionedUsers = new ArrayList<>();

        for (String username : mentionedUsernames) {
            Optional<User> optionalMentionedUser = userRepository.findByCredentials_UsernameAndDeletedFalse(username);

            if (optionalMentionedUser.isPresent()) {
                mentionedUsers.add(optionalMentionedUser.get());
            }
        }

        tweetToSave.setMentionedUsers(mentionedUsers);

        List<String> hashtagsFound = parseHashtags(tweetToSave.getContent());
        List<Hashtag> hashtags = new ArrayList<>();

        for (String label : hashtagsFound) {
            Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabel(label);
            Hashtag hashtag;

            if (optionalHashtag.isEmpty()) {
                hashtag = new Hashtag(label);
            } else {
                hashtag = optionalHashtag.get();
                hashtag.setLastUsed(Timestamp.from(Instant.now()));
            }
            hashtagRepository.saveAndFlush(hashtag);
            hashtags.add(hashtag);
        }

        tweetToSave.setHashtags(hashtags);

        return tweetMapper.entityToTweetResponseDto(tweetRepository.saveAndFlush(tweetToSave));
    }
}
