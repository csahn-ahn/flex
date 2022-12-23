package me.univ.flex.common.interceptor;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.content.ContentItemEntity;
import me.univ.flex.content.ContentService;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class UserInterceptor implements HandlerInterceptor {

	private final ContentService contentService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("UserInterceptor - preHandle");

		String requestURI = request.getRequestURI();
		log.debug("requestURI : " + requestURI);

		setPageContent(request);
		// 관리자 시스템 접근일 경우.

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if(authentication == null){
			return true;
		}

		Object principal = authentication.getPrincipal();
		if(principal == null) {
			return true;
		}

		if(principal instanceof String){
			return true;
		}

		if(principal instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) principal;
			log.debug("user : ", userDetails);

			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			if(
			authorities.stream().filter(auth ->
				auth.getAuthority().equals(BaseConstants.ROLE_MANAGER)
			).collect(Collectors.toList()).size() > 0){
				SecurityContextHolder.clearContext();
				response.sendRedirect("/");
				return false;
			}
			request.setAttribute("user", userDetails);
		}

		return true;
	}

	private void setPageContent(HttpServletRequest request) {
		String url = request.getRequestURI();
		boolean preview = request.getParameter("preview") != null ? true : false;
		ContentItemEntity contentItem = contentService.findByUrl(2, url, preview);
		request.setAttribute("contentItem", contentItem);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		log.debug("UserInterceptor - postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		log.debug("UserInterceptor - afterCompletion");
	}
}
