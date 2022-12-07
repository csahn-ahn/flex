package me.univ.flex.user.web;

import me.univ.flex.common.constants.BaseConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class WebController {

    private final String templatePrefix = "user/";

    @GetMapping(name = "기본 레이아웃", value = {"", "/"})
    public String index() {
        return this.templatePrefix + "main";
    }

    @GetMapping(name = "2Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}")
    public String layout(@PathVariable String depth1) {
        return this.templatePrefix + depth1;
    }

    @GetMapping(name = "2Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}/{depth2}")
    public String layout(@PathVariable String depth1, @PathVariable String depth2) {
        return this.templatePrefix + depth1 + "/" + depth2;
    }

    @GetMapping(name = "3Depth 레이아웃", value = BaseConstants.USER_PREFIX + "/{depth1}/{depth2}/{depth3}")
    public String layout(@PathVariable String depth1, @PathVariable String depth2, @PathVariable String depth3) {
        return this.templatePrefix + depth1 + "/" + depth2 + "/" + depth3;
    }
}
