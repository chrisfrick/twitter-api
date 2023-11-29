package com.cooksys.socialmedia.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleTweetResponseDto {

    private Long id;
    
    private UserResponseDto author;
    
    private Timestamp posted;
    
    private String content;
}
