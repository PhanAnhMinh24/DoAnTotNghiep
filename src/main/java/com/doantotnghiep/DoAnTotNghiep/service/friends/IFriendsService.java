package com.doantotnghiep.DoAnTotNghiep.service.friends;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;

import java.util.List;

public interface IFriendsService {
    void addFriend(FriendRequest request);
    List<Friends> getFriendsList();
    void removeFriend(int friendId);
}
