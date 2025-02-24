package com.doantotnghiep.DoAnTotNghiep.pojo.request;

import com.doantotnghiep.DoAnTotNghiep.pojo.data.FriendStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendConfirmRequest {
    Long friendId; // ID của người cần xác nhận
    FriendStatus status; // "accepted" hoặc "rejected"
}
