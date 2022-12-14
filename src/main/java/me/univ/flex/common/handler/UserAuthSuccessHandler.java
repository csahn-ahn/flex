package me.univ.flex.common.handler;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.user.user.UserEntity;
import me.univ.flex.user.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        UserEntity userEntity = userRepository.findById(authentication.getName()).orElseThrow();
        userEntity.setLastLoginTime(Timestamp.from(Instant.now()));
        setDefaultTargetUrl(BaseConstants.USER_PREFIX + "/main");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
