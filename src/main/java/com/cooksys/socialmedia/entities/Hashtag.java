package com.cooksys.socialmedia.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp firstUsed;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp lastUsed;
    
    @ManyToMany(mappedBy = "hashtags")
    private List<Tweet> tweets;

    public Hashtag(String label) {
        this.label = label;
    }
}
