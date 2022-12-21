package me.univ.flex.common.handler;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ManagerRepository managerRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        //managerRepository.updateLastLoginTime(authentication.getName(), LocalDateTime.now());

        ManagerEntity managerEntity = managerRepository.findById(authentication.getName()).orElseThrow();
        managerEntity.setLastLoginTime(Timestamp.from(Instant.now()));

        setDefaultTargetUrl(BaseConstants.ADMIN_PREFIX + "/main");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
