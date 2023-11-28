package com.cooksys.socialmedia.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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

}
