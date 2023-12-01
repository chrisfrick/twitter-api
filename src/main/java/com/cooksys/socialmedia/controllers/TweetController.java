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
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    
    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getTweetContext(@PathVariable Long id) {
        return tweetService.getTweetContext(id);
    }
    
    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTweetTags(@PathVariable(name = "id") Long id) {
        return tweetService.getTweetTags(id);
    }
    
    @PostMapping("/{id}/reply")
    public TweetResponseDto createReplyTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createReplyTweet(id, tweetRequestDto);
    }
    

}
