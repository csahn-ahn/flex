package me.univ.flex.admin.log.access;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.logs.AdminLogAccessEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "관리자 접속 로그", value = BaseConstants.ADMIN_API_PREFIX + "/adminLogAccess")
public class AdminLogAccessController {

    private final AdminLogAccessService adminLogAccessService;

    @GetMapping(name = "로그 목록 조회")
    public ResponseEntity<Page<AdminLogAccessEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, AdminLogAccessEntity.PageRequest request){
        return ResponseEntity.ok(adminLogAccessService.findAll(request));
    }
}
