package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service

public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username).orElseThrow();
        }
        throw new IllegalStateException();
    }
}
