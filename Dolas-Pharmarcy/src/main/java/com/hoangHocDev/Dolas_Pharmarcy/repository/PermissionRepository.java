package com.hoangHocDev.Dolas_Pharmarcy.repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Permission;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Page<Permission> findAllByRolesIs(Role role, Pageable pageable);
}
