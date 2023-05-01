package com.hw.securitydemo5.service;

import com.hw.securitydemo5.domain.Permission;
import com.hw.securitydemo5.domain.Role;
import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.domain.UserDetailsImpl;
import com.hw.securitydemo5.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> permissions = user.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(Collection::stream)
                .map(Permission::getPermission)
                .distinct()
                .toList();

        return UserDetailsImpl.builder()
                .user(user)
                .permissions(permissions)
                .build();
    }
}
