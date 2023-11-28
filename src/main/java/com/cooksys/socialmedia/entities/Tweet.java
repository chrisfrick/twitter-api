package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cooksys.socialmedia.entities.User;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn
    private User author;
    
    private Timestamp posted;
    
    private boolean deleted;
    
    private String content;
    
    @OneToMany(mappedBy = "tweet")
    private Tweet inReplyTo;
    
    @OneToMany(mappedBy = "tweet")
    private Tweet repostOf;
    
    
    
    
    
}
