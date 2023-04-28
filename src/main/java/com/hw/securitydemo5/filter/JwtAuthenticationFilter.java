package com.hw.securitydemo5.filter;

import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.utils.JwtUtil;
import com.hw.securitydemo5.utils.RedisCache;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        String authPrefix = "Bearer ";

        if (auth == null || !auth.startsWith(authPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = auth.substring(authPrefix.length());
        String uid;
        try {
            uid = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // get redis user
        User user = redisCache.getCacheObject("login:" + uid);

        if (Objects.isNull(user)) {
            throw new RuntimeException("用户未登录");
        }

        // save context holder
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        null
                )
        );

        filterChain.doFilter(request, response);
    }
}