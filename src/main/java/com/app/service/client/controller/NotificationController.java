package com.app.service.client.controller;

import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.comment.CommentDTO;
import com.app.service.client.domain.comment.CommentResponseDTO;
import com.app.service.client.model.Notification;
import com.app.service.client.service.*;
import com.app.service.client.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v2/customer/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<ApiResponseData<List<Notification>>> getNotificationCustomer() {
        return ResponseEntity.ok(ApiResponseData.<List<Notification>>builder()
                .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
                .messages(Arrays.asList("success"))
                .data(notificationService.listNotificationCustomer())
                .build());
    }
}