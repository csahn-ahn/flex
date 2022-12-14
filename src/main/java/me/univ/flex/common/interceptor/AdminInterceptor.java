package me.univ.flex.common.interceptor;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.adminGroupMenu.AdminGroupMenuService;
import me.univ.flex.admin.log.access.AdminLogAccessService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AdminInterceptor implements HandlerInterceptor {

	private final AdminGroupMenuService adminGroupMenuService;
	private final AdminLogAccessService adminLogAccessService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		// 관리자 시스템 접근일 경우.
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal == null) {
			return true;
		}

		if(principal instanceof String){
			return true;
		}

		if(principal instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) principal;

			log.debug("admin : " , userDetails);
			request.setAttribute("admin", userDetails);

			// 해당 페이지에 접근권한(isRead)이 있는지 확인
			AdminGroupMenuEntity menuAuthority = adminGroupMenuService.getMenuAuthority(requestURI, userDetails.getGroupId());
			if(menuAuthority == null) {
				response.sendRedirect(BaseConstants.ADMIN_PREFIX + "/main");
				return false;
			}
			// 해당 페이지에 Read 권한이 없을경우.

			if (menuAuthority.isHasRead() != true) {
				response.sendRedirect(BaseConstants.ADMIN_PREFIX + "/main");
				return false;
			}

			// 페이지 권한(Read, Create, Update, Delete, Download)
			request.setAttribute("menuAuthority", menuAuthority);

			// 관리자 권한에 따른 메뉴 조회
			List<AdminGroupMenuEntity> adminGroupMenus = adminGroupMenuService.findMyGroupMenu(
				userDetails.getGroupId());
			request.setAttribute("adminGroupMenus", adminGroupMenus);
			setCurrentMenuInit(requestURI, adminGroupMenus);

			// 관리자 접속 로그 등록
			adminLogAccessService.save(userDetails.getUsername(), (menuAuthority == null ? 0 : menuAuthority.getMenuId()), (menuAuthority == null || menuAuthority.getMenuId() == 0 ? "메인" : menuAuthority.getMenuName()), requestURI);

		}

		return true;
	}

	private void setCurrentMenuInit(String requestURI, List<AdminGroupMenuEntity> adminGroupMenus) {
		String[] urlArray = requestURI.split("/");
		if(urlArray.length <= 4) {
			return;
		}

		String upperMenuPath = '/' + urlArray[1] + '/' + urlArray[2];
		String lowerMenuPath = upperMenuPath + '/' + urlArray[3];

		if (StringUtils.isNotEmpty(upperMenuPath)) {
			List<AdminGroupMenuEntity> upperMenus = adminGroupMenus.stream().filter(
					menu -> menu.getUpperMenuId() == 0
						&& StringUtils.isNotEmpty(menu.getLinkUrl())
						&& menu.getLinkUrl().indexOf(upperMenuPath) > -1)
				.collect(Collectors.toList());

			if (upperMenus != null && !upperMenus.isEmpty()) {
				AdminGroupMenuEntity upperMenu = upperMenus.get(0);
				upperMenu.setActive(true);

				List<AdminGroupMenuEntity> lowerMenus = upperMenu.getLowerMenus().stream()
					.filter(
						menu -> menu.getUpperMenuId() > 0
							&& StringUtils.isNotEmpty(menu.getLinkUrl())
							&& menu.getLinkUrl().indexOf(lowerMenuPath) > -1)
					.collect(Collectors.toList());

				if (lowerMenus != null && !lowerMenus.isEmpty()) {
					AdminGroupMenuEntity lowerMenu = lowerMenus.get(0);
					lowerMenu.setActive(true);
				}
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		log.debug("AdminInterceptor - postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		log.debug("AdminInterceptor - afterCompletion");
	}
}
