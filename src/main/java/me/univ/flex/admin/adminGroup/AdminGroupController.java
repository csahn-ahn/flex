package me.univ.flex.admin.adminGroup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
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
@RequestMapping(name = "관리자 그룹", value = BaseConstants.ADMIN_API_PREFIX + "/adminGroups")
public class AdminGroupController {

    private final AdminGroupService adminGroupService;

    @GetMapping(name = "그룹 목록 조회")
    public ResponseEntity<List<AdminGroupEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin){
        return ResponseEntity.ok(adminGroupService.findAll());
    }

    @GetMapping(name = "그룹 조회", value = "/{groupId}")
    public ResponseEntity<AdminGroupEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int groupId){
        return ResponseEntity.ok(adminGroupService.detail(admin, groupId));
    }

    @PostMapping(name = "그룹 등록/수정")
    public ResponseEntity<AdminGroupEntity> save(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody AdminGroupEntity.SaveRequest request) {
        return ResponseEntity.ok(adminGroupService.save(admin, request));
    }

    @DeleteMapping(name = "그룹 삭제", value = "/{groupId}")
    public ResponseEntity<AdminGroupEntity.DeleteResponse> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int groupId) {
        return ResponseEntity.ok(adminGroupService.delete(admin, groupId));
    }
}
