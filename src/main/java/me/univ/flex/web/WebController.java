package me.univ.flex.web;

import lombok.RequiredArgsConstructor;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.properties.FlexProperties;
import me.univ.flex.common.security.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final String userTemplatePrefix = "user/";
    private final String adminTemplatePrefix = "admin/";
    private final UserDetailsService userDetailsService;
    private final FlexProperties flexProperties;

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // 관리자
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(name = "관리자 로그인 페이지", value = BaseConstants.ADMIN_PREFIX + "/login")
    public String login(Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="exception", required = false) String exception){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "admin/auth/login";
    }

    @GetMapping(name = "관리자 비밀번호 찾기", value = BaseConstants.ADMIN_PREFIX + "/findPassword")
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
        return this.adminTemplatePrefix + depth1;
    }

    @GetMapping(name = "2Depth 레이아웃", value = BaseConstants.ADMIN_PREFIX + "/{depth1}/{depth2}")
    public String layout(@PathVariable String depth1, @PathVariable String depth2) {
        return this.adminTemplatePrefix + depth1 + "/" + depth2;
    }

    @GetMapping(name = "3Depth 레이아웃", value = BaseConstants.ADMIN_PREFIX + "/{depth1}/{depth2}/{depth3}")
    public String layout(@PathVariable String depth1, @PathVariable String depth2, @PathVariable String depth3) {
        return this.adminTemplatePrefix + depth1 + "/" + depth2 + "/" + depth3;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    // 사용자
    /////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping(name = "사용자 로그인", value = BaseConstants.USER_PREFIX + "/loginProcess")
    public String loginProcess() {
        String username = "user01";
        //String password = "1234";
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        return "redirect:/";
    }

    @GetMapping(name = "사용자 로그아웃", value = BaseConstants.USER_PREFIX + "/logout")
    public String logout() {
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }

    @GetMapping(name = "기본 레이아웃", value = {"", "/", BaseConstants.USER_PREFIX, BaseConstants.USER_PREFIX + "/"})
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("userDetails", userDetails);
        return "redirect:" + BaseConstants.USER_PREFIX + "/main";
    }

    @GetMapping(name = "1Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}")
    public String layout(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model, @PathVariable String depth1) {
        model.addAttribute("userDetails", userDetails);
        return this.userTemplatePrefix + depth1;
    }

    @GetMapping(name = "2Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}/{depth2}")
    public String layout(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model, @PathVariable String depth1, @PathVariable String depth2) {
        model.addAttribute("userDetails", userDetails);
        return this.userTemplatePrefix + depth1 + "/" + depth2;
    }

    @GetMapping(name = "3Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}/{depth2}/{depth3}")
    public String layout(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model, @PathVariable String depth1, @PathVariable String depth2, @PathVariable String depth3) {
        model.addAttribute("userDetails", userDetails);
        return this.userTemplatePrefix + depth1 + "/" + depth2 + "/" + depth3;
    }
}
