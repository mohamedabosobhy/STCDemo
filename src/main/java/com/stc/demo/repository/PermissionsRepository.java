package com.stc.demo.repository;

import java.util.List;

import com.stc.demo.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stc.demo.entity.PermissionGroup;


public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
	@Query("SELECT u FROM Permissions u WHERE u.email = :email and u.group = :group")
	List<Permissions> getP(@Param("email") String email,@Param("group")PermissionGroup group);
}