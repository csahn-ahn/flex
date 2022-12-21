package me.univ.flex.common.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.univ.flex.common.constants.BaseConstants;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class UserAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String msg = "Invalid username or password";

        if(exception instanceof DisabledException) {
            msg = "DisabledException account";

        } else if (exception instanceof CredentialsExpiredException) {
            msg = "CredentialsExpiredException account";

        } else if (exception instanceof UsernameNotFoundException) {
            msg = "UsernameNotFoundException account";

        } else if (exception instanceof BadCredentialsException) {
            msg = "BadCredentialsException account";
        }

        setDefaultFailureUrl(BaseConstants.USER_PREFIX + "/auth/login?error=true&exception=" + msg);
        super.onAuthenticationFailure(request, response, exception);
    }

}
