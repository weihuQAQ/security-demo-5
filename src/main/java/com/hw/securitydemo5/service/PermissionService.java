package com.hw.securitydemo5.service;

import com.hw.securitydemo5.domain.Permission;
import com.hw.securitydemo5.domain.Role;
import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.repository.PermissionRepository;
import com.hw.securitydemo5.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class PermissionService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PermissionRepository permissionRepository;

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Object> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public List<Permission> findPermissions() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
