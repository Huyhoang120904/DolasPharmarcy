package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.entity.notification.Notification;
import com.hoanghocdev.dolaspharmacy.entity.notification.Notification_;
import com.hoanghocdev.dolaspharmacy.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RequestMapping("/notifications")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService notificationService;

    @GetMapping
    public ApiResponse<Page<Notification>> getMyNofication(@PageableDefault(page = 0, size = 5,
                                                            sort = Notification_.CREATE_AT,
                                                            direction = Sort.Direction.ASC)
                                                           Pageable pageable){
        return  ApiResponse.<Page<Notification>>builder()
                .result(notificationService.findOrderNotificationByPageAndUsername(pageable))
                .build();
    }

}
