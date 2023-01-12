package me.univ.flex.user.code;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.code.CodeEntity;
import me.univ.flex.code.CodeService;
import me.univ.flex.common.constants.BaseConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "코드 관리", value = BaseConstants.USER_API_PREFIX + "/codes")
public class UserCodeController {

    private final CodeService codeService;

    @GetMapping(name = "코드 목록 조회", value = "/{codeGroupId}")
    public ResponseEntity<List<CodeEntity>> findAll(@PathVariable String codeGroupId){
        return ResponseEntity.ok(codeService.findAll(codeGroupId));
    }
}
