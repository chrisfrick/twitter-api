package com.cooksys.socialmedia.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;

    @Override
    public List<TweetResponseDto> getAllTweets() {

        return tweetMapper.entitiesToDtos(tweetRepository
            .findAllByDeletedFalseOrderByPostedDesc());
    }

}
