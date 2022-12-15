package me.univ.flex.admin.adminMenu;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
import me.univ.flex.entity.manager.ManagerEntity;
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
@RequestMapping(name = "관리자 메뉴", value = BaseConstants.ADMIN_API_PREFIX + "/adminMenus")
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @GetMapping(name = "메뉴 목록 조회")
    public ResponseEntity<List<AdminMenuEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin){
        return ResponseEntity.ok(adminMenuService.findAll());
    }

    @GetMapping(name = "상위메뉴 목록 조회", value = "/upper")
    public ResponseEntity<List<AdminMenuEntity>> findUpperMenus(@AuthenticationPrincipal UserDetailsImpl admin){
        return ResponseEntity.ok(adminMenuService.findUpperMenus());
    }

    @GetMapping(name = "메뉴 조회", value = "/{menuId}")
    public ResponseEntity<AdminMenuEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int menuId){
        return ResponseEntity.ok(adminMenuService.detail(admin, menuId));
    }

    @PostMapping(name = "메뉴 저장")
    public ResponseEntity<AdminMenuEntity> save(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody AdminMenuEntity.SaveRequest request) {
        return ResponseEntity.ok(adminMenuService.save(admin, request));
    }

    @DeleteMapping(name = "메뉴 삭제", value = "/{menuId}")
    public ResponseEntity<AdminMenuEntity.DeleteResponse> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int menuId) {
        return ResponseEntity.ok(adminMenuService.delete(admin, menuId));
    }
}
