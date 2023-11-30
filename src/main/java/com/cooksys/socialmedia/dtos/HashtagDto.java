package com.cooksys.socialmedia.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagDto {
    private Long id;

    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

    public HashtagDto(Long id, String label, Timestamp firstUsed, Timestamp lastUsed) {
        this.id = id;
        this.label = label;
        this.firstUsed = firstUsed;
        this.lastUsed = lastUsed;
    }
}
