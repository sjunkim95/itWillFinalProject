package com.example.spring03.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "USERS")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "USERS_SEQ_GEN", sequenceName = "USERS_SEQ", allocationSize = 1)
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ_GEN")
    private Long id;

    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    private String email;
    
    private String name;
    private String address;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    @CreationTimestamp  //자동으로 만들어준다
    private Timestamp createTime;

//    @Setter // auth2 추가 코드
    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
    private String providerId;  // oauth2를 이용할 경우 아이디값


    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public User(Long id, String username, String password, String email, Role role) {
    	this.id = id; // auth2 추가
        this.username = username;
        this.password = password;
        this.email = email;
//        this.provider = provider; // auth2 추가
        this.role = role;
    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public User(String username, String password, String email, Role role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
    
//    // auth2 추가
//    public User update(String username) {
//    	this.username = username;
//    	return this;
//    }
//    
//    // auth2 추가
//    public String getRoleKey() {
//    	return this.role.getKey();
//    }
}
