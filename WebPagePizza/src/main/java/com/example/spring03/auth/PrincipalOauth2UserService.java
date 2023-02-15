package com.example.spring03.auth;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.spring03.domain.Role;
import com.example.spring03.domain.User;
import com.example.spring03.dto.MailDto;
import com.example.spring03.dto.SessionUser;
import com.example.spring03.repository.UserRepository;
import com.example.spring03.service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private final MailService mailService;
    
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();    
        
        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(provider.equals("kakao")){	//추가
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }
     
        String providerId = oAuth2UserInfo.getProviderId();	
        String username = provider+"_"+providerId;  			

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("패스워드"+uuid); 

        String email = oAuth2UserInfo.getEmail();
        Role role = Role.ROLE_USER;

        User byUsername = userRepository.findByUsername(username);
        
        //DB에 없는 사용자라면 회원가입처리
        if(byUsername == null){
            byUsername = User.oauth2Register()
                    .username(username).password(password).email(email).role(role)
                    .provider(provider).providerId(providerId)
                    .build();
            userRepository.save(byUsername);
            
            MailDto mdto = new MailDto();
            
            mdto.setEmail(email);
            mdto.setTitle("회원가입을 축하합니다.");
    		mdto.setMessage(oAuth2UserInfo.getName() + "님의 회원가입을 축하드립니다.");
    		log.info("mdto={}", mdto);
    		mailService.justSend(mdto);
        }
        
        httpSession.setAttribute("user", new SessionUser(byUsername));
        log.info(httpSession.getAttribute("user").toString());
        
        return new PrincipalDetails(byUsername, oAuth2UserInfo);	
    }
}