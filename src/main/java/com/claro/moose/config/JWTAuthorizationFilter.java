package com.claro.moose.config;

import com.claro.moose.models.User;
import com.claro.moose.util.authentication.JWTUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.claro.moose.models.Permission;
import com.claro.moose.models.Role;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private final String jwtHeaderName;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTUtil jwtUtil, String jwtHeaderName) {
        super(authManager);
        this.jwtUtil = jwtUtil;
        this.jwtHeaderName = jwtHeaderName;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String token = req.getHeader(jwtHeaderName);

        if (token != null) {
            User user = jwtUtil.getUser(token);

            if (user != null) {
               List<GrantedAuthority> authorities = getUserAuthorities(user.getRole());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getName(), null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        chain.doFilter(req, res);
    }

    private List<GrantedAuthority> getUserAuthorities(Role role) {

        List<GrantedAuthority> authorities = new ArrayList();

        for (Permission permission : role.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        return authorities;
    }
}