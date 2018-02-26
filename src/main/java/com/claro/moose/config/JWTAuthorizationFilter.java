package com.claro.moose.config;

import com.claro.moose.models.User;
import com.claro.moose.util.authentication.JWTUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.claro.moose.models.Role;
import org.springframework.stereotype.Component;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Value("${jwt.header-string}")
    private final String jwtHeaderName = "x-auth";

    @Autowired
    private JWTUtil jwtUtil = new JWTUtil();

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String token = req.getHeader(jwtHeaderName);

        if (token != null) {
            User user = jwtUtil.getUser(token);

            if (user != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getName(), null, getUserAuthorities(user.getRole()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        chain.doFilter(req, res);
    }

    private List<GrantedAuthority> getUserAuthorities(Role role) {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        // Build user's authorities
        for (String permission : role.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        return authorities;
    }
}