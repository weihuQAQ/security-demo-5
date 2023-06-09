package com.hw.securitydemo5.controller;

import com.hw.securitydemo5.entry.ResponseResult;
import com.hw.securitydemo5.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/info")
    public ResponseResult<?> getCurrentUserInfo() {
        return new ResponseResult<>(200, "", userService.getCurrentUserInfo());
    }

    @GetMapping("/testAuthModify")
    @PreAuthorize("hasAuthority('system:book:modify')")
    public ResponseResult<?> testAuthModify() {
        return new ResponseResult<>(200, "has auth system:book:modify");
    }

    @GetMapping("/testAuthGet")
    @PreAuthorize("hasAuthority('system:book:get')")
    public ResponseResult<?> testAuthGet() {
        return new ResponseResult<>(200, "has auth system:book:get");
    }

}
