package com.hw.securitydemo5;

import com.hw.securitydemo5.domain.User;
import com.hw.securitydemo5.repository.UserRepository;
import com.hw.securitydemo5.utils.JwtUtil;
import com.hw.securitydemo5.utils.RedisCache;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityDemo5ApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRepository userRepository;
    @Resource
    private RedisCache redisCache;

    @Test
    void testUserRepository() {
        System.out.println(userRepository.findByUsername("hw"));
    }

    @Test
    void testEncodePassword() {
        System.out.println(passwordEncoder.encode("1234"));
//        System.out.println(userRepository.findByUsername("hw"));
    }

    @Test
    void testSaveUser() {
        String password = passwordEncoder.encode("1234");
        userRepository.save(User.builder()
                .username("hw3")
                .email("hw3@qq.com")
                .password(password)
                .phone("17781579730")
                .build()
        );
//        System.out.println(userRepository.findByUsername("hw"));
    }

    @Test
    void testJwtUtil() {
        System.out.println(JwtUtil.createJWT("123"));
    }

    @Test
    void testParseJwtUtil() throws Exception {
        String jwt = JwtUtil.createJWT("123");
        System.out.println(JwtUtil.parseJWT(jwt).getSubject());
    }

    @Test
    void findOne() throws Exception {
        System.out.println(userRepository.findByUsername(""));
    }

    @Test
    void redisTest() {
        System.out.println((Object) redisCache.getCacheObject("login:245f1b7d-ef5a-4f1e-ab17-68e1e84b6ab6"));
    }
}
