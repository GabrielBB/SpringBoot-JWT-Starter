---


---

<h1 id="springboot-jwt-starter">SpringBoot-JWT-Starter</h1>
<p>Easily editable Spring Boot REST Backend with JSON Web Token Authentication and Authorization</p>
<h2 id="the-user-entity">The User Entity</h2>
<p>We have a model representing an user and his role. Role can also be another model class, but this project tries to keep things simple so is easy for you to make your modifications or additions. This entity will travel in every authenticated request decoded as a token in JSON format. For security, the <strong>password</strong> field will be the only one excluded when the token is generated because it is only used to validate credentials with an in-memory database. Once the user is authenticated, this property is no longer needed.</p>
<pre><code>package  com.github.gabrielbb.models;

@Entity
public class User {
@Id
private Integer id;
private String name;
private String password;
private String role; }
</code></pre>
<h2 id="the-user-repository">The User Repository</h2>
<p>Using <a href="https://spring.io/guides/gs/accessing-data-jpa/">Spring Data JPA</a> to create an User Repository so we can fetch an user from the in-memory database based on username and password provided (we are using <a href="http://www.springboottutorial.com/spring-boot-and-h2-in-memory-database">H2 database</a> but you can switch the project to use a traditional relational database) .</p>
<pre><code>package  com.github.gabrielbb.repos;

public  interface  UserRepository  extends  CrudRepository&lt;User, Integer&gt; {
	User  findByNameAndPassword(String  name, String  password);
}
</code></pre>
<h2 id="token-encodingdecoding">Token Encoding/Decoding</h2>
<p>To generate a JSON Web Token based on the entity and convert it back to a <a href="https://en.wikipedia.org/wiki/Plain_old_Java_object">POJO</a>, this project is using this utility class i made: <a href="https://github.com/GabrielBB/jwt-java-utility">JWTUtility</a>, it’s just an implementation of the library <a href="https://github.com/jwtk/jjwt">JJWT</a></p>
<h2 id="authentication-controller">Authentication Controller</h2>
<p>We have an authentication controller with a single “/login” method that accepts POST requests. Here we are importing the <a href="https://github.com/GabrielBB/jwt-java-utility">JWTUtility</a> and the UserRepository. The logic here is self explanatory: Check if there is a user registered with that username and password, take the returned entity from the database and generate a token from it. If this is successfully done it returns the HTTP code: <a href="https://httpstatuses.com/200">200</a> and the token in the response body, otherwise it returns the code <a href="https://httpstatuses.com/401">401</a>.</p>
<pre><code>package  com.github.gabrielbb.controllers;

@RestController
public  class  AuthController {

@Autowired
private  JWTUtility  jwtUtil;
@Autowired
private  UserRepository  userRepo;

@PostMapping(value =  "/login")
public  ResponseEntity&lt;String&gt; login(String  name, String  password) {
final  User  user;
if ((user =  userRepo.findByNameAndPassword(name, password)) !=  null) {

String  jsonWebToken  =  jwtUtil.getToken(user);
return  ResponseEntity.status(HttpStatus.OK).body(jsonWebToken);
}
return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
}
}
</code></pre>
<h2 id="authorization-filter">Authorization Filter</h2>
<p>Apart from the login, every single request must be authorized, and for that, the token returned in the login must be saved by the requester and sent in the <a href="https://developer.mozilla.org/es/docs/Web/HTTP/Headers">header</a> of every request. We intercept the requests, one by one, and check if there is an <a href="https://developer.mozilla.org/es/docs/Web/HTTP/Headers/Authorization">Authorization</a> header containing the token.</p>
<p>After getting the token from the header we need to decode it back to a POJO with a little more help from our JWTUtility. This is how the filter should look by now (i remove imports and some global variable declarations in these examples, full classes are in the project code):</p>
<pre><code>package  com.github.gabrielbb.config;

public  class  JWTAuthorizationFilter  extends  BasicAuthenticationFilter {

 private JWTUtility  jwtUtil;
 
@Override
protected  void  doFilterInternal(HttpServletRequest  req, HttpServletResponse  res, FilterChain  chain) {

    String  token  =  req.getHeader("Authorization");
    if (token !=  null) {
try {
User  user  =  jwtUtil.getUser(token);

} catch (SignatureException  ex) {
// User provided an invalid Token
}
}
    chain.doFilter(req, res);
    }
    }
</code></pre>
<p>Now we have to tell Spring who is this guy and what role he has but Spring doesn’t know the user entity and what it is allowed to do. What Spring do understand is an <a href="https://docs.spring.io/spring-security/site/docs/4.2.5.BUILD-SNAPSHOT/apidocs/org/springframework/security/core/Authentication.html">Authentication</a> object and <a href="https://docs.spring.io/spring-security/site/docs/5.0.0.RELEASE/reference/htmlsingle/#tech-granted-authority">Authorities</a>. Let’s traduce our User entity and his role to Spring:</p>
<pre><code>try {
User  user  =  jwtUtil.getUser(token);
GrantedAuthority  roleAuthority  =  new  SimpleGrantedAuthority(user.getRole());
Authentication  authentication  =  new UsernamePasswordAuthenticationToken(user.getId(), null, Arrays.asList(roleAuthority));
SecurityContextHolder.getContext().setAuthentication(authentication);

} catch (SignatureException  ex) {
// User provided an invalid Token
}
</code></pre>
<h2 id="security-maintenance">Security Maintenance</h2>
<p>In the configuration class <strong>com.github.gabrielbb.config.WebSecurity</strong> we are specifying which routes are open to the world, such as the login, and which routes are protected and the roles that are permitted to request them.</p>
<pre><code>http.cors().and().csrf().disable().authorizeRequests()
.antMatchers("/login").permitAll()
.antMatchers("/*").hasAuthority("MASTER_ADMIN")
.antMatchers("/cats").hasAuthority("CAT_ADMIN")
.antMatchers("/dogs").hasAuthority("DOG_ADMIN")
.and()
.addFilter(getAuthorizationFilter())
.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
</code></pre>
<h2 id="unit-testing">Unit Testing</h2>
<p>Spring automatically reads an SQL file and executes it on your configured database if you place it in:</p>
<blockquote>
<p>/src/main/resources/data.sql</p>
</blockquote>
<p>The <strong>data.sql</strong> file in this project contains (you can modify it based on your tables or just delete it if you don’t need it, we are using it for initial unit testings):</p>
<pre><code>insert into user values(1, 'bigboss','123', 'MASTER_ADMIN');
insert into user values(2, 'SnoopDogg','123', 'DOG_ADMIN');
insert into user values(3, 'meow','123', 'CAT_ADMIN');
</code></pre>
<p>And finally the unit tests:</p>
<pre><code>public  class  AuthenticationTests {

@Autowired
private  TestRestTemplate  restTemplate;

@Test
public  void  testControllersWithNoAuth() {

HttpStatus  catStatus  =  restTemplate.exchange("/cats", HttpMethod.GET, null, String.class).getStatusCode();

HttpStatus  dogStatus  =  restTemplate.exchange("/dogs", HttpMethod.GET, null, String.class).getStatusCode();

HttpStatus  loginStatus  =  restTemplate.exchange("/login", HttpMethod.POST, null, String.class).getStatusCode();

assertEquals(catStatus, HttpStatus.FORBIDDEN);

assertEquals(dogStatus, HttpStatus.FORBIDDEN);

assertEquals(loginStatus, HttpStatus.UNAUTHORIZED);

}

  

@Test
public  void  testSuccessfulLogin() {

ResponseEntity&lt;String&gt; response  =  login("SnoopDogg", "123");

assertEquals(response.getStatusCode(), HttpStatus.OK);

}

  

@Test
public  void  testFailedLogin() {
ResponseEntity&lt;String&gt; response  =  login("SnoopDogg", "Random Password");
assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);

}

  

@Test
public  void  testControllersAfterLogin() {
ResponseEntity&lt;String&gt; response  =  login("bigboss", "123");
HttpHeaders  headers  =  new  HttpHeaders();
headers.set("Authorization", response.getBody()); // Setting JSON Web Token to Request Header
HttpEntity&lt;?&gt; entity  =  new  HttpEntity&lt;&gt;(headers);
HttpStatus  catStatus  =  restTemplate.exchange("/cats", HttpMethod.GET, entity, String.class).getStatusCode();
HttpStatus  dogStatus  =  restTemplate.exchange("/dogs", HttpMethod.GET, entity, String.class).getStatusCode();
assertEquals(catStatus, HttpStatus.OK);
assertEquals(dogStatus, HttpStatus.OK);
}

private  ResponseEntity&lt;String&gt; login(String  user, String  password) {
MultiValueMap&lt;String, String&gt; map  =  new  LinkedMultiValueMap&lt;String, String&gt;();
map.add("name", user);
map.add("password", password);
HttpEntity&lt;MultiValueMap&lt;String, String&gt;\&gt; entity  =  new  HttpEntity&lt;&gt;(map, null);
return  restTemplate.exchange("/login", HttpMethod.POST, entity, String.class);
}
}
</code></pre>

