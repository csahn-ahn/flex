package me.univ.flex.admin;

import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.crypto.AES256Crypto;
import me.univ.flex.common.properties.ApplicationProperties;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final HttpSession session;
    private final String templatePrefix = "admin/";
    private final MessageSourceAccessor messageSourceAccessor;


    @GetMapping(name = "관리자 로그인 페이지", value = BaseConstants.ADMIN_PREFIX + "/login")
    public String login(Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="exception", required = false) String exception){
        model.addAttribute("title", messageSourceAccessor.getMessage("admin.login.title"));
        model.addAttribute("welcome", messageSourceAccessor.getMessage("admin.login.welcome"));
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "admin/login";
    }

    @GetMapping(name = "관리자 로그인 페이지", value = BaseConstants.ADMIN_PREFIX)
    public String admin(){
        return "redirect:/admin/main";
    }

    @GetMapping(name = "관리자 메인 페이지", value = BaseConstants.ADMIN_PREFIX + "/main")
    public String main(@AuthenticationPrincipal UserDetailsImpl admin, Model model){
        model.addAttribute("admin", admin);

        String key = "test111";
        String value = "안치성112";

        session.setAttribute(key, value);
        String result = (String) session.getAttribute(key);

        String returnValue = LocalDateTime.now().toString() + " \nsession get id : " + session.getId() + " \nsession get value " + result;

        log.debug("key : " + key);
        log.debug("value : " + result);
        log.debug("returnValue : " + returnValue);

        Object[] messageParams = new Object[]{
            admin.getName()
        };
        try {
            model.addAttribute("welcomeText", messageSourceAccessor.getMessage("admin.main.welcome.text",new Object[]{admin.getName()}));
        }catch(NoSuchMessageException e){
            e.printStackTrace();
        }

        model.addAttribute("logoutText", messageSourceAccessor.getMessage("admin.main.logout.text"));
        return "admin/main";
    }

    @GetMapping(name = "2Depth 레이아웃", value = BaseConstants.ADMIN_PREFIX + "/{depth1}")
    public String layout(@PathVariable String depth1) {
        return this.templatePrefix + depth1;
    }

    @GetMapping(name = "2Depth 레이아웃", value = BaseConstants.ADMIN_PREFIX + "/{depth1}/{depth2}")
    public String layout(@PathVariable String depth1, @PathVariable String depth2) {
        return this.templatePrefix + depth1 + "/" + depth2;
    }

    @GetMapping(name = "3Depth 레이아웃", value = BaseConstants.ADMIN_PREFIX + "/{depth1}/{depth2}/{depth3}")
    public String layout(@PathVariable String depth1, @PathVariable String depth2, @PathVariable String depth3) {
        return this.templatePrefix + depth1 + "/" + depth2 + "/" + depth3;
    }
}
