package com.hw.securitydemo5.service;

import com.hw.securitydemo5.domain.Permission;
import com.hw.securitydemo5.domain.Role;
import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.domain.UserDetailsImpl;
import com.hw.securitydemo5.repository.PermissionRepository;
import com.hw.securitydemo5.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.hw.securitydemo5.function.Helper.distinctByKey;

@Service
public class PermissionService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PermissionRepository permissionRepository;

    public List<Permission> findPermissions() {
        UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository
                .findByUsername(currentUser.getUsername())
                .orElseThrow();

        return user.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(Collection::stream)
                .filter(distinctByKey(Permission::getId))
                .toList();
    }

    public void save(Permission permission) {
        permissionRepository.save(permission);
    }
}
