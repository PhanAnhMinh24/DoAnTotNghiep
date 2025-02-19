package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    List<Friends> findAllByUserId(int userId);
    Optional<Friends> findByUserIdAndFriendId(int userId, int friendId);
}
