package me.univ.flex.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Configuration
public class CommonInterceptor implements HandlerInterceptor {



	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("CommonInterceptor - preHandle: " + request.getRequestURI());

		String requestURI = request.getRequestURI();
		if(requestURI.toLowerCase().indexOf(BaseConstants.ADMIN_PREFIX.toLowerCase()) > -1) {
			// 관리자 시스템 접근일 경우.



		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		log.debug("CommonInterceptor - postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		log.debug("CommonInterceptor - afterCompletion");
	}
}
