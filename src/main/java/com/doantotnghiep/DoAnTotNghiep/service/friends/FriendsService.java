package com.doantotnghiep.DoAnTotNghiep.service.friends;

import com.doantotnghiep.DoAnTotNghiep.entity.Friends;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.data.FriendStatus;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendConfirmRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.FriendRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.FriendResponse;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.ProfileResponse;
import com.doantotnghiep.DoAnTotNghiep.repository.FriendsRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.ProfileRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.utils.BaseUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FriendsService implements IFriendsService {

    private final FriendsRepository friendsRepository;
    private final UserRepository userRepository;
    private final BaseUserService baseUserService;
    private final ProfileRepository profileRepository;


    @Override
    @Transactional
    public void addFriend(FriendRequest request) {
        User currentUser = baseUserService.getCurrentUser();
        User friend = userRepository.findById(request.getFriendId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Optional<Friends> existingFriendRequest = friendsRepository.findByUserAndFriendId(currentUser, friend.getId());
        if (existingFriendRequest.isPresent()) {
            throw new AppException(ErrorCode.ALREADY_FRIENDS);
        }

        Friends friends = Friends.builder()
                .user(currentUser)
                .friendId(friend.getId())
                .status(FriendStatus.pending)
                .build();

        friendsRepository.save(friends);
    }

    @Override
    @Transactional
    public void confirmFriendRequest(FriendConfirmRequest request) {
        User currentUser = baseUserService.getCurrentUser();
        Friends friendRequest = friendsRepository.findByUserAndFriendId(
                        userRepository.findById(request.getFriendId())
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)),
                        currentUser.getId())
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        if (!FriendStatus.pending.equals(friendRequest.getStatus())) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        if (FriendStatus.accepted.equals(request.getStatus())) {
            friendRequest.setStatus(FriendStatus.accepted);
            friendsRepository.save(friendRequest);
        } else if (FriendStatus.rejected.equals(request.getStatus())) {
            friendsRepository.delete(friendRequest);
        }
    }

    @Override
    public List<FriendResponse> getFriendsList() {
        User currentUser = baseUserService.getCurrentUser();
        List<Friends> friends = friendsRepository.findByUserIdOrFriendId(currentUser.getId(), currentUser.getId());
        List<Long> friendIds = friends.stream()
                .filter(tmp -> FriendStatus.accepted.equals(tmp.getStatus()))
                .flatMap(f -> Stream.of(f.getUser().getId(), f.getFriendId()))
                .filter(id -> !id.equals(currentUser.getId()))
                .distinct()
                .toList();
        List<ProfileResponse> profileResponses = baseUserService.getAllProfile(friendIds);
        return friends.stream().map(friend -> {
                    long profileId = currentUser.getId().equals(friend.getUser().getId()) ? friend.getFriendId() : friend.getUser().getId();
                    ProfileResponse profileResponse = profileResponses.stream()
                            .filter(item -> item.getId().equals(profileId))
                            .findFirst().orElse(null);
                    if (ObjectUtils.isNotEmpty(profileResponse)) {
                        return FriendResponse.builder()
                                .friendRelationshipId(friend.getId())
                                .profileResponse(profileResponse)
                                .build();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    @Transactional
    public void removeFriend(Long friendId) {
        User currentUser = baseUserService.getCurrentUser();
        Friends friendship = friendsRepository.findByUserAndFriendId(currentUser, friendId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FRIENDS));

        friendsRepository.delete(friendship);
    }
}
