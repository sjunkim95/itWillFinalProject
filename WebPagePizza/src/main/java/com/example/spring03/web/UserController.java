package com.example.spring03.web;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring03.auth.PrincipalDetails;
import com.example.spring03.domain.User;
import com.example.spring03.dto.MailDto;
import com.example.spring03.dto.UserRegisterDto;
import com.example.spring03.service.MailService;
import com.example.spring03.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    
	
    private final UserService userService;
    private final MailService mailService;
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
//    @PostMapping("/join")
//    public String join(@ModelAttribute User user){
//        user.setRole(Role.ROLE_USER);
//        
//        user.setEmail(user.getEmail());
//        user.setUsername(user.getUsername());
//
//        String encodePwd = bCryptPasswordEncoder.encode(user.getPassword());
//        user.setPassword(encodePwd);
//
//        userRepository.save(user);  //반드시 패스워드 암호화해야함
//        
//        return "redirect:/loginForm";
//    }

    @PostMapping("/view/signup")
    public String join(UserRegisterDto dto, MailDto mdto){
    	log.info("join(dto={}, mdto={})", dto, mdto); 
    	
    	userService.join(dto);
    	
    	mdto.setTitle("회원가입을 축하합니다.");
		mdto.setMessage(dto.getUsername() + "님의 회원가입을 축하드립니다.");
		mailService.justSend(mdto);
        
        return "redirect:/view/login";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(){
        return "user";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }
    
    
    // !!!! OAuth로 로그인 시 이 방식대로 하면 CastException 발생함
    @GetMapping("/form/loginInfo")
    @ResponseBody
    public String formLoginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        System.out.println(user);
        //User(id=2, username=11, password=$2a$10$m/1Alpm180jjsBpYReeml.AzvGlx/Djg4Z9/JDZYz8TJF1qUKd1fW, email=11@11, role=ROLE_USER, createTime=2022-01-30 19:07:43.213, provider=null, providerId=null)

        User user1 = principalDetails.getUser();
        System.out.println(user1);
        //User(id=2, username=11, password=$2a$10$m/1Alpm180jjsBpYReeml.AzvGlx/Djg4Z9/JDZYz8TJF1qUKd1fW, email=11@11, role=ROLE_USER, createTime=2022-01-30 19:07:43.213, provider=null, providerId=null)
        //user == user1

        return user.toString();
   }
    
    
    @GetMapping("/oauth/loginInfo")
    @ResponseBody
    public String oauthLoginInfo(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);
        // PrincipalOauth2UserService의 getAttributes내용과 같음

        Map<String, Object> attributes1 = oAuth2UserPrincipal.getAttributes();
        // attributes == attributes1

       return attributes.toString();     //세션에 담긴 user가져올 수 있음음
    }
    
    
    @GetMapping("/loginInfo")
    @ResponseBody
    public String loginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String result = "";

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if(principal.getUser().getProvider() == null) {
            result = result + "Form 로그인 : " + principal;
        }else{
            result = result + "OAuth2 로그인 : " + principal;
        }
        return result; 
    }
    
//    @RequestMapping(value ="/admin/logout", method = RequestMethod.GET)
//    public String loout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//    	log.info("logout");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }

}
