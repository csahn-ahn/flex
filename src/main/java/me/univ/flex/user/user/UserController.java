package me.univ.flex.user.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.entity.user.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(name = "사용자", value = BaseConstants.USER_API_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(name = "사용자 상세조회 API", value = "/users/{userId}")
    public ResponseEntity<UserEntity> userDetail(@PathVariable int userId) {
        Optional<UserEntity> userEntityOptional = this.userService.findById(userId);
        UserEntity userEntity = userEntityOptional.isPresent() ? userEntityOptional.get() : UserEntity.builder().build();
        return ResponseEntity.ok(userEntity);
    }
}
