package com.cooksys.socialmedia.model;

import java.sql.Timestamp;
import com.cooksys.socialmedia.entities.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cooksys.socialmedia.entities.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepostTweetResponseDto {

    private Long id;
    
    private User author;
    
    private Timestamp posted;
    
    private Tweet repostOf;
}
