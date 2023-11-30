package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserRepository userRepository;

    @Override
    public boolean validateUsernameExists(String username) {
        return userRepository.findByCredentials_UsernameAndDeletedFalse(username).isPresent();
    }
}
