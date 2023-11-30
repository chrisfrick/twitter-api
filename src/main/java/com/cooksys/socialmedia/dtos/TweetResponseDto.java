package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import com.cooksys.socialmedia.entities.User;

@NoArgsConstructor
@Data
public class TweetResponseDto {

    private Long id;
    
    private UserResponseDto author;
    
    private Timestamp posted;
    
    private String content;

    private TweetResponseDto inReplyTo;

    private TweetResponseDto repostOf;
}
