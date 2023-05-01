package com.hw.securitydemo5.repository;

import com.hw.securitydemo5.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission>, CrudRepository<Permission, String> {
    //
}
