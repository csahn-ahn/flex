package me.univ.flex.admin.dashboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.event.EventApplyEntity;
import me.univ.flex.event.EventEntity;
import me.univ.flex.event.EventService;
import me.univ.flex.user.user.UserEntity;
import me.univ.flex.user.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "대시보드 관리", value = BaseConstants.ADMIN_API_PREFIX + "/dashboard")
public class DashboardController {

    private final UserService userService;

    @GetMapping(name = "회원 집계 조회", value = "/todayUserStats")
    public ResponseEntity<UserEntity.TodayStatsResponse> todayUserStats(@AuthenticationPrincipal UserDetailsImpl admin){
        return ResponseEntity.ok(userService.todayStats());
    }
}
