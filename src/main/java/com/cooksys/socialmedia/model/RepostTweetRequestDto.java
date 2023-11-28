package com.cooksys.socialmedia.model;

import com.cooksys.socialmedia.entities.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepostTweetRequestDto {

    private Long id;
    
    private User author;
    
    private Tweet repostOf;
}
