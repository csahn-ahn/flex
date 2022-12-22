package me.univ.flex.admin.stats;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "통계 메뉴", value = BaseConstants.ADMIN_API_PREFIX + "/userStats")
public class UserStatsController {

    private final UserStatsService userStatsService;

    @GetMapping(name = "사용자 통계 조회")
    public ResponseEntity<List<UserStatsEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin){
        return ResponseEntity.ok(userStatsService.findAll());
    }
}
