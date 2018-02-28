package com.claro.moose;

import com.claro.moose.util.authentication.JWTUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationTests {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void decodeToken() {

    }
}