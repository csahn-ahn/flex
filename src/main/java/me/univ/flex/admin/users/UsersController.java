package me.univ.flex.admin.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.manager.ManagerEntity;
import me.univ.flex.admin.manager.ManagerService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.user.user.UserEntity;
import me.univ.flex.user.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "회원 관리", value = BaseConstants.ADMIN_API_PREFIX + "/users")
public class UsersController {

    private final UserService userService;

    @GetMapping(name = "회원 목록 조회")
    public ResponseEntity<Page<UserEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, UserEntity.PageRequest request){
        return ResponseEntity.ok(userService.findAll(request));
    }

    @GetMapping(name = "회원 조회", value = "/{username}")
    public ResponseEntity<UserEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String username){
        return ResponseEntity.ok(userService.detail(admin, username));
    }
}
