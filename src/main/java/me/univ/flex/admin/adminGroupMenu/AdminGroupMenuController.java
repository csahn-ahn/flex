package me.univ.flex.admin.adminGroupMenu;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "관리자 그룹 메뉴", value = BaseConstants.ADMIN_API_PREFIX + "/adminGroupMenus")
public class AdminGroupMenuController {

    private final AdminGroupMenuService adminGroupMenuService;

    @GetMapping(name = "그룹 메뉴 조회")
    public ResponseEntity<List<AdminGroupMenuEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, @RequestParam int groupId){
        return ResponseEntity.ok(adminGroupMenuService.findByGroupId(groupId));
    }

    @PostMapping(name = "그룹 메뉴 저장")
    public ResponseEntity<AdminGroupMenuEntity.AdminGroupMenuResponse> saveGroupMenu(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody
        AdminGroupMenuEntity.AdminGroupMenuRequest request){
        return ResponseEntity.ok(adminGroupMenuService.save(admin, request));
    }

    @GetMapping(name = "관리자 메뉴 조회", value = "/myMenu")
    public ResponseEntity<List<AdminGroupMenuEntity>> findMyGroupMenu(@AuthenticationPrincipal UserDetailsImpl admin){
        return ResponseEntity.ok(adminGroupMenuService.findMyGroupMenu(admin.getGroupId()));
    }
}
