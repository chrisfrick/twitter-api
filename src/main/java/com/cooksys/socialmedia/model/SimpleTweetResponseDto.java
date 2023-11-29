package com.cooksys.socialmedia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import com.cooksys.socialmedia.entities.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleTweetResponseDto {

    private Long id;
    
    private User author;
    
    private Timestamp posted;
    
    private String content;
}
