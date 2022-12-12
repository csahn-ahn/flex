package me.univ.flex.common.config;

import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.adminGroupMenu.AdminGroupMenuService;
import me.univ.flex.common.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final AdminGroupMenuService adminGroupMenuService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AdminInterceptor(adminGroupMenuService))
			.excludePathPatterns("/static/**");
	}



}
