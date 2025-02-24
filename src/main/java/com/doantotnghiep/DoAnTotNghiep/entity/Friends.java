package com.doantotnghiep.DoAnTotNghiep.entity;

import com.doantotnghiep.DoAnTotNghiep.pojo.data.FriendStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "friends")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "friend_id", nullable = false)
    Long friendId;

    @Column(name = "relation", length = 50)
    String relation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    FriendStatus status;
}
