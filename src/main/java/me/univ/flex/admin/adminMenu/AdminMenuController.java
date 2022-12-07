package me.univ.flex.admin.adminMenu;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.manager.ManagerService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @GetMapping(name = "메뉴 목록 조회", value = BaseConstants.ADMIN_API_PREFIX + "/adminMenus")
    public ResponseEntity<List<AdminMenuEntity>> findAll(@AuthenticationPrincipal ManagerEntity managerEntity){
        return ResponseEntity.ok(adminMenuService.findAll());
    }
}
