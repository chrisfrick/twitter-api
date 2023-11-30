package com.cooksys.socialmedia.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cooksys.socialmedia.entities.Tweet;

import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {


    List<Tweet> findAllByDeletedFalseOrderByPostedDesc();

    Optional<Tweet> findByIdAndDeletedFalse(Long id);

}
