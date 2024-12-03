package com.ahmetaltun.usermanagementportal.utility;

import com.ahmetaltun.usermanagementportal.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.ahmetaltun.usermanagementportal.constant.SecurityConstant.*;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 03/12/2024
 */

public class JwtTokenProvider {
    @Value(value = "${jwt.secret}")
    private String secret;

    public String generateJwtToken(User userPrincipal) {
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withIssuer(LTN_DEV)
                .withAudience(APPLICATION_NAME)
                .withIssuedAt(new Date())
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));

    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(Objects.requireNonNull(claims))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = HMAC512(secret.getBytes());
            verifier = JWT.require(algorithm)
                    .withIssuer(LTN_DEV)
                    .build();
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String[] getClaimsFromUser(User userPrincipal) {
        List<String> authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return authorities.toArray(new String[0]);
    }
}
