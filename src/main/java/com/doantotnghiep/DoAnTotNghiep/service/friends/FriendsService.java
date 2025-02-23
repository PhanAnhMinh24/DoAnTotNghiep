package com.doantotnghiep.DoAnTotNghiep.service.friends;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
import com.doantotnghiep.DoAnTotNghiep.repository.FriendsRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.utils.BaseUserService;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsService implements IFriendsService {

    private final FriendsRepository friendsRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BaseUserService baseUserService;

    // Thêm bạn bè
    @Override
    public void addFriend(FriendRequest request) {
        User user = baseUserService.getCurrentUser();

        // Kiểm tra xem có tự thêm chính mình không
        if (user.getId().equals(request.getFriendId())) {
            throw new AppException(ErrorCode.SUPPLEMENT_YOURSELF);
        }

        // Kiểm tra xem bạn có tồn tại không
        User friend = userRepository.findById(request.getFriendId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra xem đã là bạn bè chưa
        if (friendsRepository.findByUserAndFriendId(user, request.getFriendId().intValue()).isPresent()) {
            throw new AppException(ErrorCode.ALREADY_FRIENDS);
        }

        // Tạo quan hệ bạn bè mới
        Friends newFriend = Friends.builder()
                .user(user)
                .friendId(Math.toIntExact(request.getFriendId()))
                .relation(request.getRelation())
                .build();

        friendsRepository.save(newFriend);
    }

    // Lấy danh sách bạn bè
    @Override
    public List<Friends> getFriendsList() {
        User user = baseUserService.getCurrentUser();
        return friendsRepository.findByUser(user);
    }

    // Xóa bạn bè
    @Override
    public void removeFriend(int friendId) {
        User user = baseUserService.getCurrentUser();

        // Tìm mối quan hệ bạn bè giữa user hiện tại và friendId
        Friends friendship = friendsRepository.findByUserAndFriendId(user, friendId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FRIENDS));

        // Nếu tồn tại, tiến hành xóa
        friendsRepository.delete(friendship);
    }
}
