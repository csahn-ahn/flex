package me.univ.flex.admin;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.crypto.AES256Crypto;
import me.univ.flex.common.properties.ApplicationProperties;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.service.RedisService;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
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

    private final String templatePrefix = "admin/";
    private final MessageSourceAccessor messageSourceAccessor;
    private final RedisTemplate redisTemplate;


    @GetMapping(name = "관리자 로그인 페이지", value = BaseConstants.ADMIN_PREFIX + "/login")
    public String login(Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="exception", required = false) String exception){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "admin/login";
    }

    @GetMapping(name = "관리자 메인 페이지로 이동", value = BaseConstants.ADMIN_PREFIX)
    public String admin(){
        return "redirect:/admin/main";
    }

    @GetMapping(name = "관리자 메인 페이지", value = BaseConstants.ADMIN_PREFIX + "/main")
    public String main(@AuthenticationPrincipal UserDetailsImpl admin, Model model){
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
