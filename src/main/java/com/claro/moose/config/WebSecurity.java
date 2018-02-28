package com.claro.moose.config;

import com.claro.moose.util.authentication.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTUtil jwtUtil;

    @Value("${jwt.header-string}")
    private  String jwtHeaderName = "x-auth";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/extraccion").hasAuthority("Extraccion")
                .antMatchers("/api/mapping").hasAuthority("Mappings")
                .and()
                .addFilter(getAuthorizationFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private JWTAuthorizationFilter getAuthorizationFilter() throws Exception {
        return new JWTAuthorizationFilter(authenticationManager(), jwtUtil, jwtHeaderName);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}