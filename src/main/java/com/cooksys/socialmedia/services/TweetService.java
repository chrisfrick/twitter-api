package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.SimpleTweetResponseDto;

public interface TweetService {

    SimpleTweetResponseDto createTweet(TweetRequestDto tweetRequestDto);
}
