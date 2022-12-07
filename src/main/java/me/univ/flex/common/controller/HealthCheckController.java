package me.univ.flex.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health1")
    public HttpStatus check() {
        return HttpStatus.OK;
    }
}
