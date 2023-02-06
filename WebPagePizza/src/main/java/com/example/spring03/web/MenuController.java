package com.example.spring03.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @GetMapping("/pepperoni_pizza")
    public String peperoniPage() {
        log.info("pepperoni_pizza()");
        
        return "/menu/pepperoni_pizza";   
    }
    
    @GetMapping("/cheese_Pizza")
    public String cheesePage() {
        log.info("cheese_Pizza()");
        
        return "/menu/cheese_Pizza";   
    }
    
    @GetMapping("/italy-margherita")
    public String margheritaPage() {
        log.info("italy-margherita()");
        
        return "/menu/italy-margherita";   
    }
    
    
    
}
