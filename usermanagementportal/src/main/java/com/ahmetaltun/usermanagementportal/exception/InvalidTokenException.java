package com.ahmetaltun.usermanagementportal.exception;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 04/12/2024
 */


@Slf4j
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
        log.error("Invalid token: {}", message);
    }
}