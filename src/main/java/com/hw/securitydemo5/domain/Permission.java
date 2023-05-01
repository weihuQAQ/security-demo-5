package com.hw.securitydemo5.domain;

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
@ToString(exclude = "roles")
@Entity
public class Permission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String permission;

    @Column(unique = true)
    private String permissionName;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(
            joinColumns = {@JoinColumn(referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<>();
}