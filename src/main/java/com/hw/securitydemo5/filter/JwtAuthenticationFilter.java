package com.hw.securitydemo5.filter;

import com.hw.securitydemo5.domain.UserDetailsImpl;
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
            throw new RuntimeException("Token is invalid");
        }

        // get redis user
        UserDetailsImpl user = redisCache.getCacheObject("login:" + uid);

        if (Objects.isNull(user)) {
            throw new RuntimeException("Please log in again");
        }

        // save context holder
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                )
        );

        filterChain.doFilter(request, response);
    }
}
