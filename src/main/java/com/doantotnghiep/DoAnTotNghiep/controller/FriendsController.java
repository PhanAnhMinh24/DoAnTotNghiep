package com.doantotnghiep.DoAnTotNghiep.controller;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
import com.doantotnghiep.DoAnTotNghiep.service.friends.IFriendsService;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointConstants.FRIENDS)
@RequiredArgsConstructor
public class FriendsController {

    private final IFriendsService friendsService;

    // 🔹 Thêm bạn bè
    @PostMapping("/add")
    public ResponseEntity<Friends> addFriend(@RequestBody FriendRequest request) {
        Friends newFriend = friendsService.addFriend(request);
        return ResponseEntity.ok(newFriend);
    }

    // 🔹 Xóa bạn bè
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFriend(@RequestParam int userId, @RequestParam int friendId) {
        friendsService.removeFriend(userId, friendId);
        return ResponseEntity.ok("Bạn bè đã được xóa thành công");
    }

    // 🔹 Lấy danh sách bạn bè của người dùng
    @GetMapping("/{userId}")
    public ResponseEntity<List<Friends>> getFriendsByUserId(@PathVariable int userId) {
        List<Friends> friendsList = friendsService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friendsList);
    }
}
