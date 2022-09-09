package org.miu.cs590.springbatchdemo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateAccessToken(Authentication authenticate) {

        return Jwts.builder()
                .setIssuer("org.miu.cs590")
                .setSubject(authenticate.getName())
                .claim("authorities",authenticate.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(36000)))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).compact();
    }
}
