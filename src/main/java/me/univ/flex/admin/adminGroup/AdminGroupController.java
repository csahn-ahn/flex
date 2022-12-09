package me.univ.flex.admin.adminGroup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminGroup.AdminGroupEntity;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "관리자 그룹", value = BaseConstants.ADMIN_API_PREFIX + "/adminGroups")
public class AdminGroupController {

    private final AdminGroupService adminGroupService;

    @GetMapping(name = "그룹 목록 조회")
    public ResponseEntity<List<AdminGroupEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin){
        return ResponseEntity.ok(adminGroupService.findAll());
    }
}
