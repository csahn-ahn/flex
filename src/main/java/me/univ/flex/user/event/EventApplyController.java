package me.univ.flex.user.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.event.EventEntity;
import me.univ.flex.event.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "이벤트 신청", value = BaseConstants.USER_API_PREFIX + "/events/apply")
public class EventApplyController {

    private final EventService eventService;

    @PostMapping(name = "이벤트 신청")
    public ResponseEntity<EventEntity.Response> apply(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody EventEntity.ApplyRequest request){
        return ResponseEntity.ok(eventService.apply(userDetailsImpl, request));
    }
}
