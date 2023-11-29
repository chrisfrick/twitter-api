package com.cooksys.socialmedia.dtos;

import com.cooksys.socialmedia.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleTweetRequestDto {

    private Long id;
    
    private User author;
    
    private String content;
}
