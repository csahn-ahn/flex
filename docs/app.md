# 애플리케이션 구성

## 기본 구성
| 구분                       |     설명      |  비고 |
|--------------------------|:-----------:|----:|
| `Language`               | Open Jdk 11 |     |
| `Framework`              | Spring Boot |     |     |
| `ORM`                    |     JPA     | |
| `Mapper`                 |   MyBatis   |     |
| `View Template` |  Thymeleaf  |     |

## 설정
### 스프링 시큐리티
- 인증 및 접근제어
```java
@Override
protected void configure(HttpSecurity http) throws Exception { // 5
	http
	.authorizeRequests()
	.antMatchers(
	    BaseConstants.ADMIN_PREFIX + "/login",
	    "/h2-console/**"
	)
	.permitAll()
	.antMatchers(BaseConstants.ADMIN_PREFIX + "/**")
	.authenticated()
	//.hasRole("ADMIN")
	.anyRequest()
	.permitAll()

	.and()
	.formLogin()
	.loginPage(BaseConstants.ADMIN_PREFIX + "/login")
	.loginProcessingUrl(BaseConstants.ADMIN_PREFIX + "/login/action")
	.successHandler(authSuccessHandler)
	.failureHandler(authFailureHandler)
	.defaultSuccessUrl(BaseConstants.ADMIN_PREFIX + "/main")

	.and()
	.logout()
	.logoutRequestMatcher(new AntPathRequestMatcher(BaseConstants.ADMIN_PREFIX + "/logout"))
	.logoutSuccessUrl(BaseConstants.ADMIN_PREFIX + "/login")
	.invalidateHttpSession(true)
	.deleteCookies("JSESSIONID", "remember-me")
	.and()
	.csrf()
	.ignoringAntMatchers(
	    "/h2-console/**"
	).disable()
	;
}
```

- 비밀번호 암호화 설정
```java
@Bean
public PasswordEncoder encryptPassword() {
	//String encodingId = "SHA-256";		// 패스워드 암호화 알고리즘 선택.
	String encodingId = flexProperties.getSecurityProps().getPasswordEncoder();

	Map<String, PasswordEncoder> encoders = new HashMap<>();
	encoders.put("bcrypt", new BCryptPasswordEncoder());
	encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
	encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
	encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
	return new DelegatingPasswordEncoder(encodingId, encoders);
} 
```




[< 뒤로가기](../README.md)