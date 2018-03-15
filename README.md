# SpringBoot-JWT-Starter

Easily editable Spring Boot REST Backend with JSON Web Token Authentication and Authorization

## The User Entity

We have a model representing an user and his role. Role can also be another model class, but this project tries to keep things simple so is easy for you to make your modifications or additions. This entity will travel in every authenticated request decoded as a token in JSON format. For security, the **password** field will be the only one excluded when the token is generated because it is only used to validate credentials with an in-memory database. Once the user is authenticated, this property is no longer needed. 

package com.github.gabrielbb.models;

```java
@Entity
public class User {

    @Id
    private Integer id;
    private String name;
    private String password;
    private String role;
}
```

## The User Repository

Using [Spring Data JPA](https://spring.io/guides/gs/accessing-data-jpa/) to create an User Repository so we can fetch an user from the in-memory database based on username and password provided (we are using [H2 database](http://www.springboottutorial.com/spring-boot-and-h2-in-memory-database) but you can switch the project to use a traditional relational database) .

```java
    package  com.github.gabrielbb.repos;
   
    public  interface  UserRepository  extends  CrudRepository<User, Integer> {
    	User  findByNameAndPassword(String  name, String  password);
    }
```

## Token Encoding/Decoding

To generate a JSON Web Token based on the entity and convert it back to a [POJO](https://en.wikipedia.org/wiki/Plain_old_Java_object), this project is using this utility class i made: [JWTUtility](https://github.com/GabrielBB/jwt-java-utility), it's just an implementation of the library [JJWT](https://github.com/jwtk/jjwt) 

## Authentication Controller
We have an authentication controller with a single "/login" method that accepts POST requests. Here we are importing the [JWTUtility](https://github.com/GabrielBB/jwt-java-utility) and the UserRepository. The logic here is self explanatory: Check if there is a user registered with that username and password, take the returned entity from the database and generate a token from it. If this is successfully done it returns the HTTP code: [200](https://httpstatuses.com/200) and the token in the response body, otherwise it returns the code [401](https://httpstatuses.com/401).

```java
package com.github.gabrielbb.controllers;

@RestController
public class AuthController {

    @Autowired
    private JWTUtility jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(String name, String password) {

        final User user;
        if ((user = userRepo.findByNameAndPassword(name, password)) != null) {
            String jsonWebToken = jwtUtil.getToken(user);

            return ResponseEntity.status(HttpStatus.OK).body(jsonWebToken);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
```

## Authorization Filter

Apart from the login, every single request must be authorized, and for that, the token returned in the login must be saved by the requester and sent in the [header](https://developer.mozilla.org/es/docs/Web/HTTP/Headers) of every request. We intercept the requests, one by one, and check if there is an [Authorization](https://developer.mozilla.org/es/docs/Web/HTTP/Headers/Authorization) header containing the token.

After getting the token from the header we need to decode it back to a POJO with a little more help from our JWTUtility. This is how the filter should look by now (i remove imports and some global variable declarations in these examples, full classes are in the project code):

```java
package com.github.gabrielbb.config;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtility jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {

        String token = req.getHeader("Authorization");

        if (token != null) {
            try {
                User user = jwtUtil.getUser(token);
		
            } catch (SignatureException ex) {
                // User provided an invalid Token
            }
        }

        chain.doFilter(req, res);
    }
}
```

Now we have to tell Spring who is this guy and what role he has but Spring doesn't know the user entity and what it is allowed to do. What Spring do understand is an [Authentication](https://docs.spring.io/spring-security/site/docs/4.2.5.BUILD-SNAPSHOT/apidocs/org/springframework/security/core/Authentication.html) object and [Authorities](https://docs.spring.io/spring-security/site/docs/5.0.0.RELEASE/reference/htmlsingle/#tech-granted-authority). Let's traduce our User entity and his role to Spring:

```java
 try {
                User user = jwtUtil.getUser(token);

                GrantedAuthority roleAuthority = new SimpleGrantedAuthority(user.getRole());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user.getId(), null, Arrays.asList(roleAuthority));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (SignatureException ex) {
                // User provided an invalid Token
            }
```

## Security Maintenance

In the configuration class **com.github.gabrielbb.config.WebSecurity** we are specifying which routes are open to the world, such as the login, and which routes are protected and the roles that are permitted to request them.

```javascript
http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/*").hasAuthority("MASTER_ADMIN")
                .antMatchers("/cats").hasAuthority("CAT_ADMIN")
                .antMatchers("/dogs").hasAuthority("DOG_ADMIN")
                .and()
                .addFilter(getAuthorizationFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
```

## Unit Testing

Spring automatically reads an SQL file and executes it on your configured database if you place it in: 

> /src/main/resources/data.sql

The **data.sql** file in this project contains (you can modify it based on your tables or just delete it if you don't need it, we are using it for initial unit testings):

```sql
insert into user values(1, 'bigboss','123', 'MASTER_ADMIN');
insert into user values(2, 'SnoopDogg','123', 'DOG_ADMIN');
insert into user values(3, 'meow','123', 'CAT_ADMIN');
```

And finally the unit tests validating the HTTP Status Codes every route should return before and after authentication:

```java
package com.github.gabrielbb;

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
    public void testControllersAfterLogin() {
        ResponseEntity<String> response = login("bigboss", "123");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", response.getBody()); // Setting JSON Web Token to Request Header
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpStatus catStatus = restTemplate.exchange("/cats", HttpMethod.GET, entity, String.class).getStatusCode();
        HttpStatus dogStatus = restTemplate.exchange("/dogs", HttpMethod.GET, entity, String.class).getStatusCode();
        assertEquals(catStatus, HttpStatus.OK);
        assertEquals(dogStatus, HttpStatus.OK);
    }
}
```
