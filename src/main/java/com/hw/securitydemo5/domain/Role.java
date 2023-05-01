package com.hw.securitydemo5.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users", "permissions"})
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String role;

    @Column(unique = true)
    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<Permission> permissions = new HashSet<>();
}
