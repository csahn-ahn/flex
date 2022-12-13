package me.univ.flex.admin;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.stats.UserStatsService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.crypto.AES256Crypto;
import me.univ.flex.common.properties.FlexProperties;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.service.email.EmailParameterKey;
import me.univ.flex.common.service.email.EmailTemplateEnum;
import me.univ.flex.common.service.email.MailService;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserStatsService userStatsService;
    private final PasswordEncoder passwordEncoder;
    private final FlexProperties flexProperties;
    private final MailService mailService;


    @GetMapping(name = "관리자 로그인 페이지", value = BaseConstants.ADMIN_PREFIX + "/login")
    public String login(Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="exception", required = false) String exception){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "admin/auth/login";
    }

    @GetMapping(name = "비밀번호 찾기", value = BaseConstants.ADMIN_PREFIX + "/findPassword")
    public String forgotPassword(Model model){
        return "admin/auth/findPassword";
    }

    @GetMapping(name = "관리자 OTP 인증페이지", value = BaseConstants.ADMIN_PREFIX + "/otp")
    public String otp(@AuthenticationPrincipal UserDetailsImpl admin, Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="exception", required = false) String exception){

        // OTP 인증을 사용할 경우.
        if(flexProperties.getSecurityProps().isUseOtp()) {
            return "admin/auth/otp";
        }
        return "redirect:/admin/main";
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
