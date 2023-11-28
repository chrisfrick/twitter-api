package com.cooksys.socialmedia.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

    private String message;

}
