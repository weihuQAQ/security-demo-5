package com.hw.securitydemo5.controller;

import com.hw.securitydemo5.domain.ResponseResult;
import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    private ResponseResult<?> login(@RequestBody User user) {
        ResponseResult<Map<String, String>> res = authService.login(user);
        System.out.println(res);
        return res;
    }
}
