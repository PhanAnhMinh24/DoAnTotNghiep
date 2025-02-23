package com.doantotnghiep.DoAnTotNghiep.pojo.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendRequest {
    Long friendId; // ID của người bạn muốn thêm
    String relation; // Mối quan hệ (Bạn thân, Gia đình, ...)
}
