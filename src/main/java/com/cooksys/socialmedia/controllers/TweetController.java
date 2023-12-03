package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dtos.*;
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
    

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentionedUsers(@PathVariable Long id) {
        return tweetService.getMentionedUsers(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTweetTags(@PathVariable(name = "id") Long id) {
        return tweetService.getTweetTags(id);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/repost")
    public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.repostTweet(id, credentialsDto);

    }

}
