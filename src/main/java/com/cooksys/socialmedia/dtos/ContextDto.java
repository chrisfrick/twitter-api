package com.cooksys.socialmedia.dtos;

import java.util.List;
import com.cooksys.socialmedia.entities.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContextDto {

    private Tweet target;
    
    private List<Tweet> before;
    
    private List<Tweet> after;
}
