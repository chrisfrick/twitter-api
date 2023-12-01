package com.cooksys.socialmedia.services;

public interface ValidateService {
    boolean validateUsernameExists(String username);

    boolean validateUsernameAvailable(String username);
}
