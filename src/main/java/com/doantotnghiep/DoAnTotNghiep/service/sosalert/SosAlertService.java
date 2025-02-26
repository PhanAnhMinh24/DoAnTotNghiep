package com.doantotnghiep.DoAnTotNghiep.service.sosalert;

import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SosAlertRequest;
import com.doantotnghiep.DoAnTotNghiep.repository.SosAlertRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.utils.BaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SosAlertService implements ISosAlertService {

    private final SosAlertRepository sosAlertRepository;
    private final UserRepository userRepository;
    private final BaseUserService baseUserService;

    @Override
    public SosAlert createSosAlert(SosAlertRequest request) {
        User currentUser = baseUserService.getCurrentUser();

        SosAlert sosAlert = SosAlert.builder()
                .user(currentUser) // Sử dụng user hiện tại thay vì từ request
                .message(request.getMessage())
                .markerId(request.getMarkerId())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .timeAnnouncement(request.getTimeAnnouncement())
                .isActive(request.isActive())
                .numberAlert(request.getNumberAlert())
                .build();

        return sosAlertRepository.save(sosAlert);
    }

    @Override
    public List<SosAlert> getAlertsByUserId() {
        User currentUser = baseUserService.getCurrentUser();
        return sosAlertRepository.findByUserId(currentUser.getId());
    }

}
