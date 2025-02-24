package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendConfirmRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.FriendResponse;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.ProfileResponse;
import com.doantotnghiep.DoAnTotNghiep.service.friends.IFriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendsController {

    private final IFriendsService friendsService;

    @PostMapping()
    public ResponseEntity<String> addFriend(@RequestBody FriendRequest request) {
        friendsService.addFriend(request);
        return ResponseEntity.ok("Friend request sent.");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmFriend(@RequestBody FriendConfirmRequest request) {
        friendsService.confirmFriendRequest(request);
        return ResponseEntity.ok("Friend request updated.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<FriendResponse>> getFriendsList() {
        return ResponseEntity.ok(friendsService.getFriendsList());
    }

    @DeleteMapping("/remove/{friendId}")
    public ResponseEntity<String> removeFriend(@PathVariable Long friendId) {
        friendsService.removeFriend(friendId);
        return ResponseEntity.ok("Friend removed successfully.");
    }
}
