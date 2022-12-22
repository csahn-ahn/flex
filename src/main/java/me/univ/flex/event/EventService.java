package me.univ.flex.event;

import groovy.transform.Synchronized;
import java.util.List;
import java.util.Optional;
import javax.persistence.Transient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.utils.TimestampUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventApplyRepository eventApplyRepository;

    public Page<EventEntity> findAll(UserDetailsImpl admin, EventEntity.PageRequest request) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "registerTime")
        );

        Page<EventEntity> page = eventRepository.findCustomAll(pageRequest, request);
        List<EventEntity> list = page.getContent();
        if(list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                EventEntity event = list.get(i);
                int count = eventApplyRepository.countByEventId(event.getEventId());
                event.setApplyCount(count);
            }
        }
        return page;
    }

    public EventEntity detail(UserDetailsImpl admin, int eventId) {
        Optional<EventEntity> optionalContent = eventRepository.findById(eventId);
        if(!optionalContent.isPresent()) {
            return null;
        }
        return optionalContent.get();
    }

    public EventEntity.Response save(UserDetailsImpl admin, EventEntity.SaveRequest request) {
        Optional<EventEntity> optionalEvent = eventRepository.findById(request.getEventId());
        EventEntity entity = null;

        if(request.getEventId() == 0 || StringUtils.isEmpty(request.getTitle())) {
            return EventEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        if(!optionalEvent.isPresent()) {
            // 등록
            entity = EventEntity.builder()
                .eventId(request.getEventId())
                .title(request.getTitle())
                .description(request.getDescription())
                .del(false)
                .registerTime(TimestampUtil.now())
                .registerId(admin.getUsername())
                .build();

        }else{
            // 수정
            entity = optionalEvent.get();
            entity.setEventId(request.getEventId());
            entity.setTitle(request.getTitle());
            entity.setDescription(request.getDescription());
            entity.setLastUpdateTime(TimestampUtil.now());
            entity.setLastUpdateId(admin.getUsername());
        }
        eventRepository.save(entity);

        return EventEntity.Response.builder()
            .success(true)
            .build();
    }

    public EventEntity.Response delete(UserDetailsImpl admin, int eventId) {
        Optional<EventEntity> optionalEvent = eventRepository.findById(eventId);

        if(!optionalEvent.isPresent()){
            return EventEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        EventEntity entity = optionalEvent.get();
        entity.setDel(true);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(admin.getUsername());

        eventRepository.save(entity);

        return EventEntity.Response.builder()
            .success(true)
            .build();

    }


    @Synchronized
    public EventEntity.Response apply(UserDetailsImpl userDetailsImpl, EventEntity.ApplyRequest request) {
        if(request.getEventId() == 0) {
            return EventEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        Optional<EventEntity> optionalEvent = eventRepository.findById(request.getEventId());
        if(!optionalEvent.isPresent()) {
            return EventEntity.Response.builder()
                .success(false)
                .message("신청할 수 없는 이벤트 입니다.")
                .build();
        }

        boolean exist = eventApplyRepository.existsByEventIdAndUsername(request.getEventId(), userDetailsImpl.getUsername());
        if(exist) {
            return EventEntity.Response.builder()
                .success(false)
                .message("이미 신청되어 있습니다.")
                .build();
        }

        EventApplyEntity eventApplyEntity = EventApplyEntity.builder()
            .eventId(request.getEventId())
            .username(userDetailsImpl.getUsername())
            .applyTime(TimestampUtil.now())
            .etc1(request.getEtc1())
            .etc2(request.getEtc2())
            .etc3(request.getEtc3())
            .build();

        eventApplyRepository.save(eventApplyEntity);

        return EventEntity.Response.builder()
            .success(true)
            .build();
    }

    public Page<EventApplyEntity> findApplyAll(UserDetailsImpl admin, EventApplyEntity.PageRequest request) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "applyTime")
        );

        Page<EventApplyEntity> page = eventApplyRepository.findCustomAll(pageRequest, request);
        return page;
    }

    @Transactional
    public EventApplyEntity.Response deleteApply(UserDetailsImpl admin, int eventId, int applyId) {
        Optional<EventApplyEntity> optionalEventApply = eventApplyRepository.findById(applyId);

        if(!optionalEventApply.isPresent()){
            return EventApplyEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        EventApplyEntity entity = optionalEventApply.get();
        eventApplyRepository.delete(entity);

        return EventApplyEntity.Response.builder()
            .success(true)
            .build();
    }
}
