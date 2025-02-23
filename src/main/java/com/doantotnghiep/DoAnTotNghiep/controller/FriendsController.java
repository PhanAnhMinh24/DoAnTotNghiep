package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
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

    // Thêm bạn bè
    @PostMapping()
    public ResponseEntity<String> addFriend(
            @RequestBody FriendRequest request) {
        friendsService.addFriend(request);
        return ResponseEntity.ok("Thêm bạn bè thành công!");
    }

    // Lấy danh sách bạn bè
    @GetMapping("/list")
    public ResponseEntity<List<Friends>> getFriendsList() {
        return ResponseEntity.ok(friendsService.getFriendsList());
    }

    // Xóa bạn bè
    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable int friendId) {
        friendsService.removeFriend(friendId);
        return ResponseEntity.ok("Friend removed successfully!");
    }
}
