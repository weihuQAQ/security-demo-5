package com.hw.securitydemo5.repository;

import com.hw.securitydemo5.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role>, CrudRepository<Role, String> {
    //
}
