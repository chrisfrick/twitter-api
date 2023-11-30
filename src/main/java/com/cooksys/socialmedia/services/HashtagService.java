package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.TweetResponseDto;

import java.util.List;

public interface HashtagService {
    List<TweetResponseDto> getTweetsByTag(String label);
}
