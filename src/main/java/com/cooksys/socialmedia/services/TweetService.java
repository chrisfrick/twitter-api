package com.cooksys.socialmedia.services;


import com.cooksys.socialmedia.dtos.*;
import java.util.List;

public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getTweetReposts(Long id);
    
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto deleteTweet(Long id);

    TweetResponseDto getTweetById(Long id);

    List<HashtagDto> getTweetTags(Long id);

    ContextDto getTweetContext(Long id);

    void likeTweet(Long id, CredentialsDto credentialsDto);

    List<UserResponseDto> getTweetLikes(Long id);

    List<UserResponseDto> getMentionedUsers(Long id);

    TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto);

}
