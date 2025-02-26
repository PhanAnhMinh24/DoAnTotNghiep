package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendConfirmRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SearchFriendRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.FriendResponse;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.ProfileResponse;
import com.doantotnghiep.DoAnTotNghiep.service.friends.IFriendsService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.FRIENDS)
@RequiredArgsConstructor
public class FriendsController {

    private final IFriendsService friendsService;

    @PostMapping(value = EndpointConstants.FRIENDS_ADD)
    public ResponseEntity<String> addFriend(@RequestBody FriendRequest request) {
        friendsService.addFriend(request);
        return ResponseEntity.ok("Friend request sent.");
    }

    @PostMapping(value = EndpointConstants.FRIENDS_CONFIRM)
    public ResponseEntity<String> confirmFriend(@RequestBody FriendConfirmRequest request) {
        friendsService.confirmFriendRequest(request);
        return ResponseEntity.ok("Friend request updated.");
    }

    @GetMapping(value = EndpointConstants.FRIENDS_LIST)
    public ResponseEntity<List<FriendResponse>> getFriendsList() {
        return ResponseEntity.ok(friendsService.getFriendsList());
    }

    @DeleteMapping(value = EndpointConstants.FRIENDS_REMOVE)
    public ResponseEntity<String> removeFriend(@RequestBody FriendRequest request) {
        friendsService.removeFriend(request);
        return ResponseEntity.ok("Friend removed successfully.");
    }

    @PostMapping(value = EndpointConstants.FRIENDS_SEARCH)
    public ResponseEntity<List<ProfileResponse>> searchFriends(@RequestBody SearchFriendRequest request) {
        return ResponseEntity.ok(friendsService.searchFriends(request));
    }


}
