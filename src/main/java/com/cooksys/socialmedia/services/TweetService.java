package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.*;

import java.util.List;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getTweetReposts(Long id);
    
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto deleteTweet(Long id);

    TweetResponseDto getTweetById(Long id);

    ContextDto getTweetContext(Long id);

    void likeTweet(Long id, CredentialsDto credentialsDto);

    List<UserResponseDto> getTweetLikes(Long id);
}
