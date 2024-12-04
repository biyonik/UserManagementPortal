package com.ahmetaltun.usermanagementportal.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 04/12/2024
 */


@Slf4j
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
        log.error("Token expired: {}", message);
    }
}