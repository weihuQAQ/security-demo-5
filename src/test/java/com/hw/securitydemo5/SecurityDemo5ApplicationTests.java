package com.hw.securitydemo5;

import com.hw.securitydemo5.domain.Permission;
import com.hw.securitydemo5.domain.Role;
import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.repository.PermissionRepository;
import com.hw.securitydemo5.repository.RoleRepository;
import com.hw.securitydemo5.repository.UserRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Stream;

@SpringBootTest
class SecurityDemo5ApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRepository userRepository;
    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void clearData() {
        userRepository.deleteAll();
        permissionRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void saveSystem() {
        List<Role> roles = roleRepository.saveAll(
                Stream.of("superAdmin", "admin", "user").map(
                                p -> Role.builder().role(p).roleName(p).build()
                        )
                        .toList()
        );

        List<Permission> permissions = Stream.of(
                        "system:book:add", "system:book:remove", "system:book:get", "system:book:modify",
                        "system:user:add", "system:user:remove", "system:user:get", "system:user:modify",
                        "system:role:add", "system:role:remove", "system:role:get", "system:role:modify",
                        "system:permission:add", "system:permission:remove", "system:permission:get", "system:permission:modify"
                )
                .map(p -> {
                    Permission permission = Permission
                            .builder()
                            .permission(p)
                            .permissionName(p.replaceAll(":", "-"))
                            .roles(new HashSet<>())
                            .build();

                    if (p.contains("book:get")) permission.getRoles().add(roles.get(2));
                    if (p.contains("book")) permission.getRoles().add(roles.get(1));
                    permission.getRoles().add(roles.get(0));

                    return permission;
                })
                .toList();

        List<User> users = Stream.of("hw-admin&user", "hw-superAdmin&admin&user", "hw-user", "hw2-user")
                .map(u -> {
                            List<String> strRoles = Arrays.asList(u.split("-")[1].split("&"));

                            User user = User.builder()
                                    .username(u)
                                    .password(passwordEncoder.encode("1234"))
                                    .phone("1880000000" + u)
                                    .email(u + "@qq.com")
                                    .roles(new HashSet<>())
                                    .build();

                            List<Role> roleList = roles.stream().filter(i -> strRoles.contains(i.getRole())).toList();

                            user.getRoles().addAll(roleList);

                            return user;
                        }
                ).toList();

        roleRepository.saveAll(roles);
        userRepository.saveAll(users);
        permissionRepository.saveAll(permissions);
    }

    @Test
    @Transactional
    void queryItems() throws Exception {
        User user = userRepository.findByUsername("hw-admin&user").orElseThrow(() -> new Exception(""));

        System.out.println(user);

        Set<Role> roles = user.getRoles();

        System.out.println(roles);
    }

    @Test
    void queryRedis() {
        System.out.println(redisTemplate.opsForValue().get("login:29b61e2f-9a10-4ad3-834d-5a8ccaf5d756"));
    }

}
