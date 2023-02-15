package com.example.spring03.dto;

import java.io.Serializable;

import com.example.spring03.domain.User;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable{

	private String username;
	private String email;
	
	public SessionUser(User user) {
		this.username = username;
		this.email = email;
	}
	
	
}
