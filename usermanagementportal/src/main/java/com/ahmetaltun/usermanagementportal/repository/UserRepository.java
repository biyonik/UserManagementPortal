package com.ahmetaltun.usermanagementportal.repository;

import com.ahmetaltun.usermanagementportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 02/12/2024
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
