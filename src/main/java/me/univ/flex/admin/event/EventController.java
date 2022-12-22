package me.univ.flex.admin.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.event.EventApplyEntity;
import me.univ.flex.event.EventEntity;
import me.univ.flex.event.EventService;
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
@RequestMapping(name = "이벤트 관리", value = BaseConstants.ADMIN_API_PREFIX + "/events")
public class EventController {

    private final EventService eventService;

    @GetMapping(name = "이벤트 목록 조회")
    public ResponseEntity<Page<EventEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, EventEntity.PageRequest request){
        return ResponseEntity.ok(eventService.findAll(admin, request));
    }

    @GetMapping(name = "이벤트 상세 조회", value = "/{eventId}")
    public ResponseEntity<EventEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int eventId){
        return ResponseEntity.ok(eventService.detail(admin, eventId));
    }

    @PostMapping(name = "이벤트 저장")
    public ResponseEntity<EventEntity.Response> save(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody EventEntity.SaveRequest request){
        return ResponseEntity.ok(eventService.save(admin, request));
    }

    @DeleteMapping(name = "이벤트 삭제", value = "/{eventId}")
    public ResponseEntity<EventEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int eventId){
        return ResponseEntity.ok(eventService.delete(admin, eventId));
    }

    @GetMapping(name = "이벤트 신청목록 조회", value = "/{eventId}/apply")
    public ResponseEntity<Page<EventApplyEntity>> findAllApply(@AuthenticationPrincipal UserDetailsImpl admin, EventApplyEntity.PageRequest request){
        return ResponseEntity.ok(eventService.findApplyAll(admin, request));
    }

    @DeleteMapping(name = "이벤트 신청취소", value = "/{eventId}/{applyId}")
    public ResponseEntity<EventApplyEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int eventId, @PathVariable int applyId) {
        return ResponseEntity.ok(eventService.deleteApply(admin, eventId, applyId));
    }
}
