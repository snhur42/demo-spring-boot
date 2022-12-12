package com.example.demospringboot.security.jwt.provider;

import com.example.demospringboot.model.Person;
import com.example.demospringboot.model.Role;
import com.example.demospringboot.service.PersonDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private PersonDetailsServiceImpl personDetailsService;
    private String secretKey = "key";
    private final long validityInMilliseconds = 6000L;

    private final String authorizationHeader = "header";
    private final String authorizationHeaderPrefix = "header-prefix";

    @Autowired
    public JwtTokenProvider(PersonDetailsServiceImpl personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UUID userId, Role role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("role", role);

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            logger.error(ex.getMessage());
            return false;
        }

    }

    public Authentication getAuthentication(String token) {
        Person person = this.personDetailsService.loadUserById(UUID.fromString(getSubject(token)));
        return new UsernamePasswordAuthenticationToken(person, person.getAuthorities(), person.getAuthorities());
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpiredDate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody().getExpiration().after(new Date());
    }

    public String resolveToken(HttpServletRequest request) {
        String headerValue = request.getHeader(authorizationHeader);
        if (headerValue != null && headerValue.startsWith(authorizationHeaderPrefix)) {
            return headerValue.substring(authorizationHeaderPrefix.length());
        }
        return null;
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}