package com.doantotnghiep.DoAnTotNghiep.service.sos;

import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SosAlertRequest;
import com.doantotnghiep.DoAnTotNghiep.repository.SosAlertRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SosAlertService implements ISosAlertService {

    private final SosAlertRepository sosAlertRepository;
    private final UserRepository userRepository;

    @Override
    public SosAlert createSosAlert(SosAlertRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        SosAlert sosAlert = SosAlert.builder()
                .user(user)
                .message(request.getMessage())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .timeAnnouncement(LocalDateTime.now())
                .isActive(true)
                .numberAlert(1)
                .build();

        return sosAlertRepository.save(sosAlert);
    }

    @Override
    public void disableSosAlert(int id) {
        SosAlert sosAlert = sosAlertRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        sosAlert.setActive(false);
        sosAlertRepository.save(sosAlert);
    }

    @Override
    public List<SosAlert> getSosAlertsByUserId(int userId) {
        return sosAlertRepository.findAllByUserId(userId);
    }
}
