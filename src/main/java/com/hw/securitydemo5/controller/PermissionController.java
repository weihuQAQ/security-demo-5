package com.hw.securitydemo5.controller;

import com.hw.securitydemo5.domain.Permission;
import com.hw.securitydemo5.entry.ResponseResult;
import com.hw.securitydemo5.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @GetMapping("/all")
    public ResponseResult<?> getPermissions(){
        List<Permission> permissions = permissionService.findPermissions();
        return new ResponseResult<>(200, "", Map.of("permissions", permissions));
    }

    @PostMapping("/save")
    public ResponseResult<?> getPermissions(@RequestBody Permission permission){
        permissionService.save(permission);
        return new ResponseResult<>(200, "ok");
    }
}
