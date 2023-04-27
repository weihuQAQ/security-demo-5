package com.hw.securitydemo5.service;

import com.hw.securitydemo5.domain.ResponseResult;
import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.utils.JwtUtil;
import com.hw.securitydemo5.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

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
//        redisCache.setCacheObject("login:" + userid, loginUser);

        return new ResponseResult<>(200, "登录成功", map);
    }
}
