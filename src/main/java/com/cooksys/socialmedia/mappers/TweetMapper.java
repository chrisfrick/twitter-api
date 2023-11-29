package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.SimpleTweetResponseDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.entities.Tweet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { CredentialsMapper.class, UserMapper.class })
public interface TweetMapper {

    Tweet tweetRequestDtoToEntity(TweetRequestDto tweetRequestDto);

    SimpleTweetResponseDto entityToSimpleTweetResponseDto(Tweet tweet);
}
