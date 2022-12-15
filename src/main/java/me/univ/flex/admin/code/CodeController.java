package me.univ.flex.admin.code;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.adminGroup.AdminGroupService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminGroup.AdminGroupEntity;
import me.univ.flex.entity.code.CodeEntity;
import me.univ.flex.entity.code.CodeGroupEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "코드 관리", value = BaseConstants.ADMIN_API_PREFIX + "/codes")
public class CodeController {

    private final CodeService codeService;

    @GetMapping(name = "코드그룹 목록 조회", value = "/{codeGroupId}")
    public ResponseEntity<List<CodeEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String codeGroupId){
        return ResponseEntity.ok(codeService.findAll(admin, codeGroupId));
    }

    @GetMapping(name = "코드그룹 상세 조회", value = "/{codeGroupId}/{codeId}")
    public ResponseEntity<CodeEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String codeGroupId, @PathVariable String codeId){
        return ResponseEntity.ok(codeService.detail(admin, codeGroupId, codeId));
    }

    @PostMapping(name = "코드그룹 저장", value = "/{codeGroupId}")
    public ResponseEntity<CodeEntity.Response> save(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String codeGroupId, @RequestBody CodeEntity.SaveRequest request){
        return ResponseEntity.ok(codeService.save(admin, codeGroupId, request));
    }

    @PutMapping(name = "코드그룹 노출상태 변경", value = "/{codeGroupId}/{codeId}/display")
    public ResponseEntity<CodeEntity.Response> display(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String codeGroupId, @PathVariable String codeId, @RequestParam boolean display){
        return ResponseEntity.ok(codeService.display(admin, codeGroupId, codeId, display));
    }

    @DeleteMapping(name = "코드그룹 삭제", value = "/{codeGroupId}/{codeId}")
    public ResponseEntity<CodeEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String codeGroupId, @PathVariable String codeId){
        return ResponseEntity.ok(codeService.delete(admin, codeGroupId, codeId));
    }
}
