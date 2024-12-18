package com.ahmetaltun.usermanagementportal.constant;


/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 03/12/2024
 */


public class SecurityConstant {
    public static final Long EXPIRATION_TIME = 432_000_000L; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String TOKEN_ISSUER = "Ltn Dev, LLC";
    public static final String TOKEN_AUDIENCE = "User Management Portal Application";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page!";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page!";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {
            "/auth/login",
            "/auth/register",
            "/auth/reset-password/**",
            "user/image/**"
    };
}
