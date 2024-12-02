package com.ahmetaltun.usermanagementportal.service.impl;


import com.ahmetaltun.usermanagementportal.repository.UserRepository;
import com.ahmetaltun.usermanagementportal.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 02/12/2024
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
}
