package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


    Boolean existsByEmail(String email);

    Optional<User> findById(int id);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :keyword OR u.email = :keyword")
    List<User> findByPhoneNumberOrEmail(@Param("keyword") String keyword);


}
