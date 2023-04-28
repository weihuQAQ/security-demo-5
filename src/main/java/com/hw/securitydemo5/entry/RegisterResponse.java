package com.hw.securitydemo5.entry;

import com.hw.securitydemo5.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String id;
    private String username;
    private String email;
    private String phone;
    private Set<Role> roles = new HashSet<>();
}
