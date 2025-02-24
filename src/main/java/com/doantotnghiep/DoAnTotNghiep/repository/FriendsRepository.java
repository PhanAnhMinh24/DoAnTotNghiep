package com.doantotnghiep.DoAnTotNghiep.repository;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.data.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, Integer> {

    Optional<Friends> findByUserAndFriendId(User user, Long friendId);

    List<Friends> findByUserIdOrFriendId(Long userId, Long friendId);

    List<Friends> findByUserIdAndFriendIdAndStatus(Long id, Long friendId, FriendStatus status);
}