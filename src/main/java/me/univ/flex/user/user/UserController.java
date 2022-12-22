package me.univ.flex.user.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(name = "사용자", value = BaseConstants.USER_API_PREFIX + "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(name = "사용자 상세조회", value = "/me")
    public ResponseEntity<UserEntity> myInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails == null) {
            return ResponseEntity.ok(null);
        }

        Optional<UserEntity> userEntityOptional = this.userService.findById(userDetails.getUsername());
        UserEntity userEntity = userEntityOptional.isPresent() ? userEntityOptional.get()
            : UserEntity.builder().build();
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping(name = "사용자 로그인", value = "/login")
    public ResponseEntity<UserEntity.Response> login(@RequestBody UserEntity.LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping(name = "사용자 sns로그인", value = "/loginSns")
    public ResponseEntity<UserEntity.Response> loginSns(UserEntity.LoginSnsRequest request) {
        userService.loginSns(request);
        return ResponseEntity.ok(userService.loginSns(request));
    }

    @GetMapping(name = "사용자 로그아웃", value = "/logout")
    public ResponseEntity<UserEntity.Response> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.logout());
    }

    @PostMapping(name = "사용자 회원가입", value = "/join")
    public ResponseEntity<UserEntity.Response> join(@RequestBody UserEntity.JoinRequest request) {
        return ResponseEntity.ok(userService.join(request));
    }

    @PostMapping(name = "사용자 회원탈퇴", value = "/leave")
    public ResponseEntity<UserEntity.Response> leave(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.leave(userDetails));
    }

    @PostMapping(name = "사용자 정보변경", value = "/updateUser")
    public ResponseEntity<UserEntity.Response> updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserEntity.UpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(userDetails, request));
    }
}
