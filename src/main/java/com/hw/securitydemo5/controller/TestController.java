package com.hw.securitydemo5.controller;

import com.hw.securitydemo5.entry.ResponseResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    private ResponseResult<?> getTest(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("content", "authenticated");
        return new ResponseResult<>(200, "", map);
    }

}
