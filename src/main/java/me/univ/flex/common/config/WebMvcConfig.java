package me.univ.flex.common.config;

import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.adminGroupMenu.AdminGroupMenuService;
import me.univ.flex.admin.log.access.AdminLogAccessService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.interceptor.AdminInterceptor;
import me.univ.flex.common.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final AdminGroupMenuService adminGroupMenuService;
	private final AdminLogAccessService adminLogAccessService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AdminInterceptor(adminGroupMenuService, adminLogAccessService))
				.addPathPatterns(BaseConstants.ADMIN_PREFIX + "/**")
				.excludePathPatterns(BaseConstants.ADMIN_API_PREFIX + "/**")
				.excludePathPatterns("/static/**");

		registry.addInterceptor(new UserInterceptor())
			.addPathPatterns(BaseConstants.USER_PREFIX)
			.excludePathPatterns(BaseConstants.ADMIN_PREFIX + "/**")
			.excludePathPatterns("/static/**");
	}
}
