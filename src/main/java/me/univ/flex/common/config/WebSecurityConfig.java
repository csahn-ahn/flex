package me.univ.flex.common.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.ManagerService;
import me.univ.flex.common.handler.AuthFailureHandler;
import me.univ.flex.common.handler.AuthSuccessHandler;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.properties.FlexProperties;
import me.univ.flex.common.properties.SecurityProperties;
import me.univ.flex.common.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsService;
	private final AuthSuccessHandler authSuccessHandler;
	private final AuthFailureHandler authFailureHandler;
	private final FlexProperties flexProperties;

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

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encryptPassword());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
			"/h2-console/**"
		);
	}

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
}