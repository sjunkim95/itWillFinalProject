package com.example.spring03.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
    
    @GetMapping("/") // 요청 URL/방식 매핑.
    public String home() {
        log.info("home()");
        
        
        return "/view/main";

    }

}
