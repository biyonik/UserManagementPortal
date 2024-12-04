package com.ahmetaltun.usermanagementportal.utility.token;

import com.ahmetaltun.usermanagementportal.constant.SecurityConstant;
import com.ahmetaltun.usermanagementportal.domain.User;
import com.ahmetaltun.usermanagementportal.exception.InvalidTokenException;
import com.ahmetaltun.usermanagementportal.exception.InvalidUserException;
import com.ahmetaltun.usermanagementportal.exception.TokenExpiredException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

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

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value(value = "${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationTime;

    private final RateLimiter rateLimiter;

    public TokenResponse generateTokenPair(User user) {
        validateUser(user);
        rateLimiter.checkRateLimit(user.getId().toString());

        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        log.info("Generated new token pair for user: {}", user.getUsername());

        return new TokenResponse(accessToken, refreshToken);
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        validateToken(token);
        String[] claims = getClaimsFromToken(token);

        return Arrays.stream(claims)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        if (!StringUtils.isNotBlank(token)) {
            throw new InvalidTokenException("Token cannot be empty");
        }

        try {
            JWTVerifier verifier = getJwtVerifier();
            var decodedJWT = verifier.verify(token);

            Date expirationDate = decodedJWT.getExpiresAt();
            if (expirationDate.before(new Date())) {
                throw new TokenExpiredException("Token has expired");
            }
        } catch (JWTVerificationException ex) {
            log.error("Token validation failed: {}", ex.getMessage());
            throw new InvalidTokenException("Token verification failed: " + ex.getMessage());
        }

        return true;
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
                    .withIssuer(TOKEN_ISSUER)
                    .build();
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String generateAccessToken(User user) {
        return JWT.create()
                .withIssuer(SecurityConstant.TOKEN_ISSUER)
                .withAudience(SecurityConstant.TOKEN_AUDIENCE)
                .withIssuedAt(new Date())
                .withSubject(user.getUsername())
                .withArrayClaim(SecurityConstant.AUTHORITIES, getClaimsFromUser(user))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withJWTId(UUID.randomUUID().toString())
                .sign(Algorithm.HMAC512(getRotatingSecret()));
    }

    private String generateRefreshToken(User user) {
        return JWT.create()
                .withIssuer(SecurityConstant.TOKEN_ISSUER)
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .withJWTId(UUID.randomUUID().toString())
                .sign(Algorithm.HMAC512(getRotatingSecret()));
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getSubject();
    }

    public Authentication getAuthentication(String userName, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    private String getRotatingSecret() {
        return secret + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private void validateUser(User user) {
        if (user == null || user.getUsername() == null || user.getAuthorities() == null) {
            throw new InvalidUserException("Invalid user details provided");
        }
    }

    private String[] getClaimsFromUser(User user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }
}
