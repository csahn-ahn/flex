package me.univ.flex.admin.content;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.content.ContentService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.content.ContentEntity;
import org.springframework.data.domain.Page;
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
@RequestMapping(name = "콘텐츠 관리", value = BaseConstants.ADMIN_API_PREFIX + "/contents")
public class ContentController {

    private final ContentService contentService;

    @GetMapping(name = "콘텐츠 목록 조회")
    public ResponseEntity<Page<ContentEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, ContentEntity.PageRequest request){
        return ResponseEntity.ok(contentService.findAll(admin, request));
    }

    @GetMapping(name = "콘텐츠 상세 조회", value = "/{contentId}")
    public ResponseEntity<ContentEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId){
        return ResponseEntity.ok(contentService.detail(admin, contentId));
    }

    @PostMapping(name = "콘텐츠 저장")
    public ResponseEntity<ContentEntity.Response> save(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody ContentEntity.SaveRequest request){
        return ResponseEntity.ok(contentService.save(admin, request));
    }

    @DeleteMapping(name = "콘텐츠 삭제", value = "/{contentId}")
    public ResponseEntity<ContentEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId){
        return ResponseEntity.ok(contentService.delete(admin, contentId));
    }
}
