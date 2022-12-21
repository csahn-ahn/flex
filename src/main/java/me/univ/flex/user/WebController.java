package me.univ.flex.user;

import lombok.RequiredArgsConstructor;
import me.univ.flex.common.constants.BaseConstants;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final String templatePrefix = "user/";
    private final UserDetailsService userDetailsService;

    @PostMapping(name = "로그인", value = BaseConstants.USER_PREFIX + "/loginProcess")
    public String loginProcess() {
        String username = "user01";
        //String password = "1234";
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        return "redirect:/";
    }

    @GetMapping(name = "로그아웃", value = BaseConstants.USER_PREFIX + "/logout")
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
        return this.templatePrefix + depth1;
    }

    @GetMapping(name = "2Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}/{depth2}")
    public String layout(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model, @PathVariable String depth1, @PathVariable String depth2) {
        model.addAttribute("userDetails", userDetails);
        return this.templatePrefix + depth1 + "/" + depth2;
    }

    @GetMapping(name = "3Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}/{depth2}/{depth3}")
    public String layout(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model, @PathVariable String depth1, @PathVariable String depth2, @PathVariable String depth3) {
        model.addAttribute("userDetails", userDetails);
        return this.templatePrefix + depth1 + "/" + depth2 + "/" + depth3;
    }
}
