package me.univ.flex.user.content;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.content.ContentItemEntity;
import me.univ.flex.content.ContentItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "콘텐츠 관리", value = BaseConstants.USER_API_PREFIX + "/contents")
public class UserContentController {

    private final ContentItemService contentItemService;

    @GetMapping(name = "콘텐츠 상세 조회")
    public ResponseEntity<List<ContentItemEntity>> detail(ContentItemEntity.ServiceRequest request){
        return ResponseEntity.ok(contentItemService.serviceItem(request));
    }

    @GetMapping(name = "콘텐츠 상세 조회", value = "/{contentId}")
    public ResponseEntity<ContentItemEntity> detail(@PathVariable String contentId, @Nullable Boolean isPreview){
        return ResponseEntity.ok(contentItemService.serviceItem(contentId, isPreview == null ? false : isPreview.booleanValue()));
    }
}
