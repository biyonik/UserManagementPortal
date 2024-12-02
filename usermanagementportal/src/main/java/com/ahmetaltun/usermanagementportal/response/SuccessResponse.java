package com.ahmetaltun.usermanagementportal.response;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 01/12/2024
 */

public record SuccessResponse(
        String time,
        int code,
        String path,
        HttpStatus status,
        String message,
        Map<String, Object> data
) implements ApiResponse {

    public static SuccessResponse of(String path, String message, Map<String, Object> data) {
        return new SuccessResponse(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                path,
                HttpStatus.OK,
                message,
                data
        );
    }

    public static SuccessResponse created(String path, String message, Map<String, Object> data) {
        return new SuccessResponse(
                LocalDateTime.now().toString(),
                HttpStatus.CREATED.value(),
                path,
                HttpStatus.CREATED,
                message,
                data
        );
    }
}