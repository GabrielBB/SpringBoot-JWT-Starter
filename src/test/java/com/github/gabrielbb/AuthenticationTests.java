package com.github.gabrielbb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testControllersWithNoAuth() {
        HttpStatus catStatus = restTemplate.exchange("/cats", HttpMethod.GET, null, String.class).getStatusCode();
        HttpStatus dogStatus = restTemplate.exchange("/dogs", HttpMethod.GET, null, String.class).getStatusCode();
        HttpStatus loginStatus = restTemplate.exchange("/login", HttpMethod.POST, null, String.class).getStatusCode();
        assertEquals(catStatus, HttpStatus.FORBIDDEN);
        assertEquals(dogStatus, HttpStatus.FORBIDDEN);
        assertEquals(loginStatus, HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testSuccessfulLogin() {
        ResponseEntity<String> response = login("SnoopDogg", "123");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testFailedLogin() {
        ResponseEntity<String> response = login("SnoopDogg", "Random Password");
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testControllersAfterAuth() {
        ResponseEntity<String> response = login("bigboss", "123");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", response.getBody()); // Setting JSON Web Token to Request Header
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpStatus catStatus = restTemplate.exchange("/cats", HttpMethod.GET, entity, String.class).getStatusCode();
        HttpStatus dogStatus = restTemplate.exchange("/dogs", HttpMethod.GET, entity, String.class).getStatusCode();
        assertEquals(catStatus, HttpStatus.OK);
        assertEquals(dogStatus, HttpStatus.OK);
    }

    private ResponseEntity<String> login(String user, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("name", user);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, null);
        return restTemplate.exchange("/login", HttpMethod.POST, entity, String.class);
    }
}