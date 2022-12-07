package me.univ.flex.admin.adminGroup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.entity.adminGroup.AdminGroupEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminGroupController {

    private final AdminGroupService adminGroupService;

    @GetMapping(name = "메뉴 목록 조회", value = BaseConstants.ADMIN_API_PREFIX + "/adminGroups")
    public ResponseEntity<List<AdminGroupEntity>> findAll(@AuthenticationPrincipal ManagerEntity managerEntity){
        return ResponseEntity.ok(adminGroupService.findAll());
    }
}
