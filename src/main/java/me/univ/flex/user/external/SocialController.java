package me.univ.flex.user.external;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface SocialController {

	String KEY_SOCIAL_STATE = "social_state"; // state 저장 키
	String KEY_SOCIAL_EMAIL = "social_email"; // 로그인 완료 시 이메일 저장 키
	String KEY_SOCIAL_TYPE = "social_type"; // sns_type 저장 키
	String KEY_SOCIAL_ID = "social_id"; // sns_id 저장 키

	/**
	 * SNS 로그인 페이지로 리다이렉트
	 *
	 * @return SNS 로그인 페이지 URL
	 */
	String login(HttpServletRequest req);

	/**
	 * SNS 로그인 후 호출 콜백
	 */
	String callback(HttpServletRequest req, RedirectAttributes redirectAttributes);

	/**
	 * 로그인 처리 완료 페이지로 리다이렉트
	 */
	void complete(HttpServletRequest req);

	/**
	 * 접근 토큰 반환
	 *
	 * @param state 스테이트
	 * @param code 코드
	 * @return 접근 토큰
	 */
	String accessToken(String state, String code);

	/**
	 * 사용자 프로필 정보 반환
	 *
	 * @param accessToken 접근 토큰
	 * @return 프로필 정보
	 */
	Map<String, Object> profile(String accessToken);

}
