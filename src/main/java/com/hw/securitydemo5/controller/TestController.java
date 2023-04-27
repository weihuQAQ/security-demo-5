package com.hw.securitydemo5.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    private String getTest(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "hello";
    }
}
