package com.cooksys.socialmedia.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Credentials credentials;

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp joined;

    private boolean deleted = false;

    @Embedded
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "followers_following",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id")
    )
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<User> following;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    // TODO: mentionedUsers or similar property to Tweet entity, something like:
    /**
     * @ManyToMany
 *     @JoinTable(
 *         name = "tweet_mentions",
 *         joinColumns = @JoinColumn(name = "tweet_id"),
 *         inverseJoinColumns = @JoinColumn(name = "user_id")
 *     )
 *     private List<User> mentionedUsers;
     */

    @ManyToMany(mappedBy = "mentionedUsers")
    private List<Tweet> mentions;

    // TODO: add @ManyToMany relationship in Tweet entity, something like:
    /**
     * @ManyToMany(mappedBy = "likes")
     * private Set<User> likedBy = new HashSet<>();
     */
    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private Set<Tweet> likes;
}
