package me.univ.flex.common.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.handler.AdminAuthFailureHandler;
import me.univ.flex.common.handler.AdminAuthSuccessHandler;
import me.univ.flex.common.handler.UserAuthFailureHandler;
import me.univ.flex.common.handler.UserAuthSuccessHandler;
import me.univ.flex.common.properties.FlexProperties;
import me.univ.flex.common.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsService;
	private final AdminAuthSuccessHandler authSuccessHandler;
	private final AdminAuthFailureHandler authFailureHandler;
	private final FlexProperties flexProperties;

	public static final String[] EXCLUDE_PATTERN = {
		"/h2-console/**",

		//BaseConstants.USER_PREFIX + "/main",
		BaseConstants.USER_PREFIX + "/auth/login",
		//BaseConstants.USER_API_PREFIX + "/**",

		BaseConstants.ADMIN_PREFIX + "/login",
		BaseConstants.ADMIN_PREFIX + "/otp",
		BaseConstants.ADMIN_PREFIX + "/auth/**",
		BaseConstants.ADMIN_API_PREFIX + "/auth/**"
	};

	@Bean
	public PasswordEncoder encryptPassword() {
		//String encodingId = "SHA-256";		// ???????????? ????????? ???????????? ??????.
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

	@Order(1)
	@RequiredArgsConstructor
	@Configuration
	public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

		private final AdminAuthSuccessHandler adminAuthSuccessHandler;
		private final AdminAuthFailureHandler adminAuthFailureHandler;

		@Override
		public void configure(WebSecurity web) {
			web.ignoring().antMatchers(EXCLUDE_PATTERN);  // ?????? ??????
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http
				.authorizeRequests()
				.antMatchers(EXCLUDE_PATTERN)
				.permitAll()
				.antMatchers(BaseConstants.ADMIN_PREFIX + "/**")
				.authenticated()
				.and()
					.formLogin()
						.loginPage(BaseConstants.ADMIN_PREFIX + "/login")
						.loginProcessingUrl(BaseConstants.ADMIN_PREFIX + "/login/action")
						.successHandler(adminAuthSuccessHandler)
						.failureHandler(adminAuthFailureHandler)
						//.defaultSuccessUrl(BaseConstants.ADMIN_PREFIX + "/main")
						.defaultSuccessUrl(BaseConstants.ADMIN_PREFIX + "/otp")

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


	@Order(2)
	@RequiredArgsConstructor
	@Configuration
	public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {
		private final UserDetailsServiceImpl userDetailsService;
		private final UserAuthSuccessHandler userAuthSuccessHandler;
		private final UserAuthFailureHandler userAuthFailureHandler;

		@Override
		public void configure(WebSecurity web) {
			web.ignoring().antMatchers(EXCLUDE_PATTERN);  // ?????? ??????
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception { // 5
			http
				.authorizeRequests()
				.antMatchers(EXCLUDE_PATTERN).permitAll()
				.antMatchers(BaseConstants.USER_PREFIX + "/**").authenticated()

			// ?????? ????????? ????????? ???????????? ????????? ????????? ??????????????? ?????? ??????
			.and()
				.formLogin()
				.loginPage(BaseConstants.USER_PREFIX + "/auth/login")
				.defaultSuccessUrl("/", false)
				.loginProcessingUrl(BaseConstants.USER_PREFIX + "/loginProcess")
				.defaultSuccessUrl(BaseConstants.USER_PREFIX + "/main", true)

			.and()
				.logout()
				.logoutUrl(BaseConstants.USER_PREFIX + "/logout")
				.logoutSuccessUrl("/");

			http.userDetailsService(userDetailsService);
		}
	}
}