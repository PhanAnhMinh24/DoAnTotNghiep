package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    List<Friends> findByUser(User user);
    Optional<Friends> findByUserAndFriendId(User user, int friendId);
}
