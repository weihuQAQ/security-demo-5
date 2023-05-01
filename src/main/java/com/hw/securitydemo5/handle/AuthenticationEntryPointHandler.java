package com.hw.securitydemo5.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.securitydemo5.entry.ResponseResult;
import com.hw.securitydemo5.utils.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String value = objectMapper.writeValueAsString(new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "认证失败"));
        WebUtils.renderString(response, value);
    }
}
