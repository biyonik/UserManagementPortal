package com.ahmetaltun.usermanagementportal.response;

import org.springframework.http.HttpStatus;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 01/12/2024
 */


public sealed interface ApiResponse permits ErrorResponse, SuccessResponse {
    String time();
    int code();
    String path();
    HttpStatus status();
    String message();
}