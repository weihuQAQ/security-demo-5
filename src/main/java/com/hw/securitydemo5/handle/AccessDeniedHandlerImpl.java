package com.hw.securitydemo5.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.securitydemo5.entry.ResponseResult;
import com.hw.securitydemo5.utils.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Map<String, String> map = Map.of("message", accessDeniedException.getMessage());
        String value = objectMapper.writeValueAsString(new ResponseResult<>(HttpStatus.FORBIDDEN.value(), "Access Denied", map));
        WebUtils.renderString(response, value);
    }
}
