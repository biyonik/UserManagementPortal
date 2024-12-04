package com.ahmetaltun.usermanagementportal.utility.token;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 04/12/2024
 */


@Data
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}