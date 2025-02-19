package com.doantotnghiep.DoAnTotNghiep.service.friends;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;

import java.util.List;

public interface IFriendsService {
    Friends addFriend(FriendRequest request); // Thêm bạn
    void removeFriend(int userId, int friendId); // Xóa bạn
    List<Friends> getFriendsByUserId(int userId); // Lấy danh sách bạn bè
}
