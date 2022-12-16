package me.univ.flex.admin.content.item;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.content.ContentService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.content.ContentEntity;
import me.univ.flex.entity.content.ContentItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "콘텐츠 아이템 관리", value = BaseConstants.ADMIN_API_PREFIX + "/contents")
public class ContentItemController {

    private final ContentItemService contentItemService;

    @GetMapping(name = "콘텐츠 아이템 목록 조회", value = "/{contentId}/items")
    public ResponseEntity<List<ContentItemEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId){
        return ResponseEntity.ok(contentItemService.findAll(admin, contentId));
    }

    @GetMapping(name = "콘텐츠 아이템 상세 조회", value = "/{contentId}/items/{itemId}")
    public ResponseEntity<ContentItemEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId, @PathVariable int itemId){
        return ResponseEntity.ok(contentItemService.detail(admin, contentId, itemId));
    }

    @PostMapping(name = "콘텐츠 아이템 저장", value = "/{contentId}/items")
    public ResponseEntity<ContentItemEntity.Response> save(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId, @RequestBody ContentItemEntity.SaveRequest request){
        return ResponseEntity.ok(contentItemService.save(admin, contentId, request));
    }

    @PostMapping(name = "콘텐츠 아이템 복사", value = "/{contentId}/items/{itemId}/copy")
    public ResponseEntity<ContentItemEntity.Response> copy(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId, @PathVariable int itemId){
        return ResponseEntity.ok(contentItemService.copy(admin, contentId, itemId));
    }

    @DeleteMapping(name = "콘텐츠 아이템 삭제", value = "/{contentId}/items/{itemId}")
    public ResponseEntity<ContentItemEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId, @PathVariable int itemId){
        return ResponseEntity.ok(contentItemService.delete(admin, contentId, itemId));
    }

    @PutMapping(name = "콘텐츠 아이템 LIVE 상태 변경", value = "/{contentId}/items/{itemId}/live/{status}")
    public ResponseEntity<ContentItemEntity.Response> live(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId, @PathVariable int itemId, @PathVariable boolean status){
        return ResponseEntity.ok(contentItemService.setLive(admin, contentId, itemId, status));
    }

    @PutMapping(name = "콘텐츠 아이템 PREVIEW 상태 변경", value = "/{contentId}/items/{itemId}/preview/{status}")
    public ResponseEntity<ContentItemEntity.Response> preview(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable String contentId, @PathVariable int itemId, @PathVariable boolean status){
        return ResponseEntity.ok(contentItemService.setPreview(admin, contentId, itemId, status));
    }
}
