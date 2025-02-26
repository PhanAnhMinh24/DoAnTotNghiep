package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.PersonalSosAlerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalSosAlertsRepository extends JpaRepository<PersonalSosAlerts, Long> {
    List<PersonalSosAlerts> findBySosAlertId(Long sosAlertId);

    List<PersonalSosAlerts> findBySosAlertIdIn(List<Long> sosAlertIds);

}