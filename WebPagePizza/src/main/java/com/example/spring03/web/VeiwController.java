package com.example.spring03.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/view")
public class VeiwController {
    
    @GetMapping("/cart")
    public String cartPage() {
        log.info("cart()");
        
        return "/view/cart";
        
    }
    
    @GetMapping("/login")
    public String login() {
        log.info("login()");
        
        return "/view/login";  
    }

    @GetMapping("/signup")
    public String singup() {
        log.info("singup()");
        
        return "/view/signup";  
    }
    
    @GetMapping("/main")
    public String mainPage() {
        log.info("main()");
        
        return "/view/main";
        
    }
    
    @GetMapping("/address")
    public String address() {
        System.out.println("카카오 API 테스트");
        
        return "/view/address";
    }
}
