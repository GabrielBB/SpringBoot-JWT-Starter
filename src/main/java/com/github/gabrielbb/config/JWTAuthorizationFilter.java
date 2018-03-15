package com.github.gabrielbb.config;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.SignatureException;

import com.github.gabrielbb.models.User;
import com.github.gabrielbb.util.JWTUtility;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtility jwtUtil;
    private static final String JWT_HEADER_NAME = "Authorization";

    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTUtility jwtUtil) {
        super(authManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String token = req.getHeader(JWT_HEADER_NAME);

        if (token != null) {
            try {
                User user = jwtUtil.getUser(token);

                GrantedAuthority roleAuthority = new SimpleGrantedAuthority(user.getRole());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user.getId(), null, Arrays.asList(roleAuthority));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (SignatureException ex) {
                // User provided an invalid Token
            }
        }

        chain.doFilter(req, res);
    }
}