package me.univ.flex.admin.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
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
@RequestMapping(name = "운영자 관리", value = BaseConstants.ADMIN_API_PREFIX + "/managers")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping(name = "운영자 목록 조회")
    public ResponseEntity<Page<ManagerEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, ManagerEntity.PageRequest request){
        return ResponseEntity.ok(managerService.findAll(request));
    }

    @GetMapping(name = "운영자 조회", value = "/{username}")
    public ResponseEntity<ManagerEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String username){
        return ResponseEntity.ok(managerService.detail(admin, username));
    }

    @PostMapping(name = "운영자 등록/수정")
    public ResponseEntity<ManagerEntity> save(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody ManagerEntity.SaveRequest request) {
        return ResponseEntity.ok(managerService.save(admin, request));
    }

    @DeleteMapping(name = "운영자 삭제", value = "/{username}")
    public ResponseEntity<ManagerEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String username) {
        return ResponseEntity.ok(managerService.delete(admin, username));
    }

    @PutMapping(name = "운영자 비밀번호 변경", value = "/password")
    public ResponseEntity<ManagerEntity.Response> password(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody ManagerEntity.UpdatePasswordRequest request) {
        return ResponseEntity.ok(managerService.updatePassword(admin, request));
    }
}
