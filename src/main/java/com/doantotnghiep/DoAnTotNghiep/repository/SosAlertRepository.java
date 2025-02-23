package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SosAlertRepository extends JpaRepository<SosAlert, Integer> {
    List<SosAlert> findAllByUserId(int userId);
}
