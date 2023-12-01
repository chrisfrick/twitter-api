package com.cooksys.socialmedia.services;

import java.util.List;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;

import java.util.List;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getTweetReposts(Long id);
    
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto deleteTweet(Long id);

    TweetResponseDto getTweetById(Long id);

    List<HashtagDto> getTweetTags(Long id);

    TweetResponseDto createReplyTweet(Long id, TweetRequestDto tweetRequestDto);

    ContextDto getTweetContext(Long id);

}
