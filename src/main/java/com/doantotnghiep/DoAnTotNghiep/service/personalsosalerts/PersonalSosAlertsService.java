package com.doantotnghiep.DoAnTotNghiep.service.personalsosalerts;

import com.doantotnghiep.DoAnTotNghiep.entity.PersonalSosAlerts;
import com.doantotnghiep.DoAnTotNghiep.entity.SosAlert;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.PersonalSosAlertsRequest;
import com.doantotnghiep.DoAnTotNghiep.repository.PersonalSosAlertsRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.SosAlertRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.utils.BaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalSosAlertsService implements IPersonalSosAlertsService {
    private final PersonalSosAlertsRepository personalSosAlertsRepository;
    private final SosAlertRepository sosAlertRepository;
    private final UserRepository userRepository;
    private final BaseUserService baseUserService;

    @Override
    public List<PersonalSosAlerts> createPersonalSosAlert(PersonalSosAlertsRequest request) {
        SosAlert sosAlert = sosAlertRepository.findById(request.getSosAlertId()).orElseThrow();

        List<PersonalSosAlerts> alerts = request.getFriendIds().stream().map(friendId -> {
            User friend = userRepository.findById(friendId).orElseThrow();
            return PersonalSosAlerts.builder()
                    .sosAlert(sosAlert)
                    .friend(friend)
                    .build();
        }).toList();

        return personalSosAlertsRepository.saveAll(alerts);
    }



    @Override
    public List<PersonalSosAlerts> getAlertsByUserId() {
        User currentUser = baseUserService.getCurrentUser();

        // Lấy danh sách tất cả các cảnh báo SOS của user hiện tại
        List<SosAlert> sosAlerts = sosAlertRepository.findByUserId(currentUser.getId());

        // Kiểm tra nếu không có alert nào
        if (sosAlerts.isEmpty()) {
            return Collections.emptyList();
        }

        // Lấy danh sách friend từ tất cả các sos_alert_id
        return personalSosAlertsRepository.findBySosAlertIdIn(
                sosAlerts.stream().map(SosAlert::getId).toList()
        );
    }

}
