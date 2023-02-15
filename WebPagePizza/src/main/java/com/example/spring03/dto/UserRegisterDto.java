package com.example.spring03.dto;

import com.example.spring03.domain.Role;
import com.example.spring03.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRegisterDto {
    
    private String username;
    private String password;
    private String email;
    private String name;
    private String address;
    private String phone;
 

    public User toEntity() {
        return User.builder()
                .username(username).password(password).email(email).role(Role.ROLE_USER)
                .name(name).address(address).phone(phone).build();
    }
    
}