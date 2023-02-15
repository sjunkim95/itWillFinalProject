package com.example.spring03.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring03.domain.User;
import com.example.spring03.dto.UserRegisterDto;
import com.example.spring03.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public User join(UserRegisterDto userDto) {
    	log.info("join(userDto={})", userDto);
    	
        User optional = userRepository.findByUsername(userDto.getUsername());
        
        if (optional != null) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        
        String encodePW = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePW);
        User user = userDto.toEntity();
        log.info(userDto.getUsername() + ", " + userDto.getPassword() + ", " + userDto.getEmail());
        
        return userRepository.save(user);
    }

}
