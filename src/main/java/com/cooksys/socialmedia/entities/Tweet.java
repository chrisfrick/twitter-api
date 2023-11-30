package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cooksys.socialmedia.entities.User;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    private Timestamp posted;

    private boolean deleted;

    private String content;

    @ManyToOne
    private Tweet inReplyTo;

    @OneToMany(mappedBy = "inReplyTo")
    private List<Tweet> replies = new ArrayList<>();

    @ManyToOne
    private Tweet repostOf;

    @OneToMany(mappedBy = "repostOf")
    private List<Tweet> reposts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_mentions",
        joinColumns = @JoinColumn(name = "tweet_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> mentionedUsers;

    @ManyToMany(mappedBy = "userLikes")
    private Set<User> likedBy;
    
    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;

}
