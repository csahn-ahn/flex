package me.univ.flex.admin.code;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.adminGroup.AdminGroupService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminGroup.AdminGroupEntity;
import me.univ.flex.entity.code.CodeGroupEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "코드그룹 관리", value = BaseConstants.ADMIN_API_PREFIX + "/codeGroups")
public class CodeGroupController {

    private final CodeGroupService codeGroupService;

    @GetMapping(name = "코드그룹 목록 조회")
    public ResponseEntity<List<CodeGroupEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, CodeGroupEntity.SearchRequest request){
        return ResponseEntity.ok(codeGroupService.findAll(admin, request));
    }

    @GetMapping(name = "코드그룹 상세 조회", value = "/{codeGroupId}")
    public ResponseEntity<CodeGroupEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String codeGroupId){
        return ResponseEntity.ok(codeGroupService.detail(admin, codeGroupId));
    }

    @PostMapping(name = "코드그룹 저장")
    public ResponseEntity<CodeGroupEntity.Response> save(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody CodeGroupEntity.SaveRequest request){
        return ResponseEntity.ok(codeGroupService.save(admin, request));
    }

    @DeleteMapping(name = "코드그룹 삭제", value = "/{codeGroupId}")
    public ResponseEntity<CodeGroupEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String codeGroupId){
        return ResponseEntity.ok(codeGroupService.delete(admin, codeGroupId));
    }
}
