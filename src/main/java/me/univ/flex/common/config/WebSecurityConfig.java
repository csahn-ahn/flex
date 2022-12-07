package me.univ.flex.common.config;

import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.ManagerService;
import me.univ.flex.admin.manager.handler.AuthFailureHandler;
import me.univ.flex.admin.manager.handler.AuthSuccessHandler;
import me.univ.flex.common.constants.BaseConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ManagerService managerService;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;

    @Bean
    public BCryptPasswordEncoder encryptPassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(managerService).passwordEncoder(encryptPassword());
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