package me.univ.flex.admin.code;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.adminGroup.AdminGroupService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.entity.adminGroup.AdminGroupEntity;
import me.univ.flex.entity.code.CodeEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @GetMapping(name = "코드 목록 조회", value = BaseConstants.ADMIN_API_PREFIX + "/codes")
    public ResponseEntity<List<CodeEntity>> findAll(@AuthenticationPrincipal ManagerEntity managerEntity){
        return ResponseEntity.ok(codeService.findAll());
    }
}