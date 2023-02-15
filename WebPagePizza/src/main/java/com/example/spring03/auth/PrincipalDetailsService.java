package com.example.spring03.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring03.domain.User;
import com.example.spring03.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	log.info("loadUserByUsername(username = {})", username);
    	
        User byUsername = userRepository.findByUsername(username);
        log.info("byUsername = {}", byUsername);
        if(byUsername != null){
            return new PrincipalDetails(byUsername);
        }
        return null;
    }
}
