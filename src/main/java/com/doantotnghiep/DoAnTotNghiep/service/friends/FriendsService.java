package com.doantotnghiep.DoAnTotNghiep.service.friends;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
import com.doantotnghiep.DoAnTotNghiep.repository.FriendsRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsService implements IFriendsService {

    private final FriendsRepository friendsRepository;
    private final UserRepository userRepository;

    @Override
    public Friends addFriend(FriendRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Friends friend = Friends.builder()
                .user(user)
                .friendId(request.getFriendId())
                .relation(request.getRelation())
                .build();

        return friendsRepository.save(friend);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        Friends friend = friendsRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        friendsRepository.delete(friend);
    }

    @Override
    public List<Friends> getFriendsByUserId(int userId) {
        return friendsRepository.findAllByUserId(userId);
    }
}
