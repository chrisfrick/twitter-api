package com.cooksys.socialmedia.model;

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
