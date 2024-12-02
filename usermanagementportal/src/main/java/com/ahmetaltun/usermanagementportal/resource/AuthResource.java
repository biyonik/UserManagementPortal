package com.ahmetaltun.usermanagementportal.resource;

import com.ahmetaltun.usermanagementportal.response.ApiResponse;
import com.ahmetaltun.usermanagementportal.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 01/12/2024
 */


@RestController
@RequestMapping(value = "auth")
public class AuthResource {
    @GetMapping
    public ResponseEntity<ApiResponse> test(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        SuccessResponse.of(
                                request.getRequestURI(),
                                "Test page",
                                Map.of()
                        )
                );
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse> register() {
        return null;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse> login() {
        return null;
    }
}
