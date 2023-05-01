package com.hw.securitydemo5.service;

import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.domain.UserDetailsImpl;
import com.hw.securitydemo5.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public User getCurrentUserInfo() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository
                .findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found Exception"));
    }
}
