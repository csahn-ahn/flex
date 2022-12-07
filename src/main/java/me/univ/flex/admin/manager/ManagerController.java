package me.univ.flex.admin.manager;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.model.PageDataResponse;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping(name = "운영자 목록 조회", value = BaseConstants.ADMIN_API_PREFIX + "/managers")
    public ResponseEntity<List<ManagerEntity>> findAll(@AuthenticationPrincipal ManagerEntity managerEntity){
        return ResponseEntity.ok(managerService.findAll());
    }
}
