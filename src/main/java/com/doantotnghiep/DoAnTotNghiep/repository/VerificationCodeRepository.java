package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.VerificationCode;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByUser(User user);
}
