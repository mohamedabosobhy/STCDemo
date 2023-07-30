package com.stc.demo.repository;

import com.stc.demo.entity.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
    
}