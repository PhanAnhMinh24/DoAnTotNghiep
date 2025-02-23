package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
