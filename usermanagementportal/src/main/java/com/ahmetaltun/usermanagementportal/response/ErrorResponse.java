package com.ahmetaltun.usermanagementportal.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 01/12/2024
 */

public record ErrorResponse(
        String time,
        int code,
        String path,
        HttpStatus status,
        String message,
        String exception,
        Map<String, Object> errors
) implements ApiResponse {

    public static ErrorResponse of(
            String path,
            HttpStatus status,
            String message,
            String exception,
            Map<String, Object> errors) {
        return new ErrorResponse(
                LocalDateTime.now().toString(),
                status.value(),
                path,
                status,
                message,
                exception,
                errors
        );
    }

    public static ErrorResponse validationError(
            String path,
            Map<String, List<String>> validationErrors) {
        Map<String, Object> errors = Map.of(
                "validationErrors", validationErrors,
                "errorCount", validationErrors.values()
                        .stream()
                        .mapToInt(List::size)
                        .sum()
        );

        return of(
                path,
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "ValidationException",
                errors
        );
    }
}