package com.github.gabrielbb;

import com.github.gabrielbb.util.JWTUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationTests {

    @Autowired
    private JWTUtility jwtUtil;

    @Test
    public void decodeToken() {

    }
}