package com.hw.securitydemo5.service;

import com.hw.securitydemo5.entry.ResponseResult;
import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.entry.RegisterResponse;
import com.hw.securitydemo5.repository.UserRepository;
import com.hw.securitydemo5.utils.JwtUtil;
import com.hw.securitydemo5.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AuthService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRepository userRepository;

    public ResponseResult<Map<String, String>> login(User user) {
        // AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果认证没通过，给出对应的提示
        if (authenticate == null) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证通过了，使用userid生成一个jwt, jwt存入ResponseResult返回
        User loginUser = (User) authenticate.getPrincipal();
        String userid = loginUser.getId();
        Map<String, String> map = new HashMap<>();
        map.put("token", JwtUtil.createJWT(userid));

        // 把完整的用户信息存入redis  userid作为key
        redisCache.setCacheObject("login:" + userid, loginUser);

        return new ResponseResult<>(200, "登录成功", map);
    }

    public ResponseResult<?> register(User registerUser) {
        User user = User.builder()
                .username(registerUser.getUsername())
                .email(registerUser.getEmail())
                .phone(registerUser.getPhone())
                .password(passwordEncoder.encode(registerUser.getPassword()))
                .roles(Set.of())
                .build();

        User savedUser = userRepository.save(user);
        RegisterResponse registerResponse = RegisterResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .roles(savedUser.getRoles())
                .build();

        return new ResponseResult<>(200, "注册成功", registerResponse);
    }

    public ResponseResult<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String id = user.getId();

        redisCache.deleteObject("login:"+id);

        return new ResponseResult<>(200, "ok");
    }
}
