package me.univ.flex.admin;

import me.univ.flex.common.constants.BaseConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AdminController {

    private final String templatePrefix = "admin/";

    @GetMapping(name = "관리자 로그인 페이지", value = BaseConstants.ADMIN_PREFIX + "/login")
    public String login(Model model, @RequestParam(value="error", required=false) String error, @RequestParam(value="exception", required = false) String exception){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "admin/login";
    }

    @GetMapping(name = "관리자 메인 페이지", value = BaseConstants.ADMIN_PREFIX + "/main")
    public String main(Model model){
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
