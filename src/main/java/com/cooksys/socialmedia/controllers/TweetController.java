package com.cooksys.socialmedia.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TweetResponseDto createTweet(
        @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }


    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getTweetReposts(@PathVariable Long id) {
        return tweetService.getTweetReposts(id);
    }


    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }
    
    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@PathVariable(name = "id") Long id) {
        return tweetService.deleteTweet(id);
    }
    

}
