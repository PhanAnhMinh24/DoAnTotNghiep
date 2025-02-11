package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserName(String username);  // Sử dụng phương thức này vì email đã được thêm vào User

    Optional<User> findByUserName(String email);
}
