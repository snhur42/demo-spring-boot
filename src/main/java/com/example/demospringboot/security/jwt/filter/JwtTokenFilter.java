package com.example.demospringboot.security.jwt.filter;

import com.example.demospringboot.security.jwt.provider.JwtTokenProvider;
import com.example.demospringboot.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component("jwtTokenFilter")
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveToken(request);

        try {
            if (jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new JwtAuthenticationException("token is NOT Authentication");
                }

            } else {
                throw new JwtAuthenticationException("token is NOT VALIDATE");
            }
            logger.info("doFilterInternal success");
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            logger.error(e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
