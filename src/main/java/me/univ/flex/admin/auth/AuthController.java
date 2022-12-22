package me.univ.flex.admin.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "인증 관리", value = BaseConstants.ADMIN_API_PREFIX + "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(name = "임시 비밀번호 발급", value = "/findPassword")
    public ResponseEntity<AuthDTO.Response> findPassword(@RequestBody AuthDTO.FindPasswordRequest request) {
        return ResponseEntity.ok(authService.tempPassword(request));
    }

    @PutMapping(name = "임시 비밀번호 변경", value = "/changeTempPassword")
    public ResponseEntity<AuthDTO.Response> changeTempPassword(@RequestBody AuthDTO.ChangeTempPasswordRequest request) {
        return ResponseEntity.ok(authService.changeTempPassword(request));
    }
}
