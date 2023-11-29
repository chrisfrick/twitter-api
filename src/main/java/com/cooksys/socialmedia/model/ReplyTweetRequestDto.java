package com.cooksys.socialmedia.model;

import com.cooksys.socialmedia.entities.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyTweetRequestDto {

    private Long id;
    
    private User author;
    
    private String content;
    
    private Tweet inReplyTo;
}
