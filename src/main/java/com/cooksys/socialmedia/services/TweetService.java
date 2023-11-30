package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);
}
