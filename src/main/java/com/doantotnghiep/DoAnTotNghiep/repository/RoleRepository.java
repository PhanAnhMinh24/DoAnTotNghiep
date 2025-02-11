package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    Role findByIsDefaultTrue();
}
