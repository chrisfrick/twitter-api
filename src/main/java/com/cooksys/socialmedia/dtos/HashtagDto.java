package com.cooksys.socialmedia.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagDto {
    private Long id;

    private Timestamp firstUsed;

    private Timestamp lastUsed;
}
