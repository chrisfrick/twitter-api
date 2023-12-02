package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;

import java.util.List;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getTweetReposts(Long id);
    
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto deleteTweet(Long id);

    TweetResponseDto getTweetById(Long id);

    ContextDto getTweetContext(Long id);

    void likeTweet(Long id, CredentialsDto credentialsDto);
}
