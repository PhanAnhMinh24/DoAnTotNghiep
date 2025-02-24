package com.doantotnghiep.DoAnTotNghiep.service.friends;

import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendConfirmRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.FriendResponse;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.ProfileResponse;

import java.util.List;

public interface IFriendsService {
    void addFriend(FriendRequest request);

    void confirmFriendRequest(FriendConfirmRequest request);

    List<FriendResponse> getFriendsList();

    void removeFriend(Long friendId);
}
