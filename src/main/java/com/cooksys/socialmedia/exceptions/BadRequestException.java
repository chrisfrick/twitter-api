package com.cooksys.socialmedia.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    private String message;

}
