package com.cooksys.socialmedia.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "hashtag_table")
@NoArgsConstructor
@Data
public class Hashtag {

    @Id
    private Long id;

    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

    @OneToMany(mappedBy = "hashtag")
    private List<Hashtag> inReplyTo;

}
