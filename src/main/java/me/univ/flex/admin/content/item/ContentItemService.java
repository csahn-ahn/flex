package me.univ.flex.admin.content.item;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.content.ContentRepository;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.content.ContentEntity;
import me.univ.flex.entity.content.ContentItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentItemService {

    private final ContentItemRepository contentItemRepository;

    public List<ContentItemEntity> findAll(UserDetailsImpl admin, String contentId) {
        List<ContentItemEntity> list = contentItemRepository.findByContentIdAndDelOrderByItemIdDesc(contentId, false);
        return list;
    }

    public ContentItemEntity detail(UserDetailsImpl admin, String contentId, int itemId) {
        Optional<ContentItemEntity> optionalContentItem = contentItemRepository.findById(itemId);
        if(!optionalContentItem.isPresent()) {
            return null;
        }
        return optionalContentItem.get();
    }

    public ContentItemEntity.Response save(UserDetailsImpl admin, String contentId, ContentItemEntity.SaveRequest request) {
        ContentItemEntity entity = null;

        if(StringUtils.isEmpty(request.getContentId())) {
            return ContentItemEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        if(request.getItemId() == 0) {
            // 등록
            entity = ContentItemEntity.builder()
                .contentId(request.getContentId())
                .title(request.getTitle())
                .live(false)
                .preview(false)
                .body(request.getBody())
                .serviceStartTime(Timestamp.valueOf(request.getServiceStartTime()))
                .serviceEndTime(Timestamp.valueOf(request.getServiceStartTime()))
                .del(false)
                .registerTime(TimestampUtil.now())
                .registerId(admin.getUsername())
                .build();

        }else{

            Optional<ContentItemEntity> optionalContentItem = contentItemRepository.findById(request.getItemId());
            if(!optionalContentItem.isPresent()) {
                return ContentItemEntity.Response.builder()
                    .success(false)
                    .message("비정상적인 데이터 입니다.")
                    .build();
            }
            // 수정
            entity = optionalContentItem.get();
            entity.setTitle(request.getTitle());
            entity.setBody(request.getBody());
            entity.setServiceStartTime(Timestamp.valueOf(request.getServiceStartTime()));
            entity.setServiceEndTime(Timestamp.valueOf(request.getServiceEndTime()));
            entity.setLastUpdateTime(TimestampUtil.now());
            entity.setLastUpdateId(admin.getUsername());
        }
        contentItemRepository.save(entity);

        return ContentItemEntity.Response.builder()
            .success(true)
            .build();
    }

    public ContentItemEntity.Response copy(UserDetailsImpl admin, String contentId, int itemId) {
        Optional<ContentItemEntity> optionalContentItem = contentItemRepository.findById(itemId);
        if(!optionalContentItem.isPresent()) {
            return ContentItemEntity.Response.builder()
                .success(false)
                .message("비정상적인 데이터 입니다.")
                .build();
        }

        ContentItemEntity entity = optionalContentItem.get();

        // 신규 아이템 생성.
        ContentItemEntity copyEntity = ContentItemEntity.builder()
            .contentId(entity.getContentId())
            .title(entity.getTitle() + " (복사)")
            .live(false)
            .preview(false)
            .body(entity.getBody())
            .serviceStartTime(entity.getServiceStartTime())
            .serviceEndTime(entity.getServiceEndTime())
            .del(false)
            .registerTime(TimestampUtil.now())
            .registerId(admin.getUsername())
            .build();


        contentItemRepository.save(copyEntity);

        return ContentItemEntity.Response.builder()
            .success(true)
            .build();
    }

    public ContentItemEntity.Response delete(UserDetailsImpl admin, String contentId, int itemId) {
        Optional<ContentItemEntity> optionalContentItem = contentItemRepository.findById(itemId);

        if(!optionalContentItem.isPresent()){
            return ContentItemEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        ContentItemEntity entity = optionalContentItem.get();
        entity.setDel(true);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(admin.getUsername());

        contentItemRepository.save(entity);

        return ContentItemEntity.Response.builder()
            .success(true)
            .build();

    }

    public ContentItemEntity.Response setLive(UserDetailsImpl admin, String contentId, int itemId, boolean status) {
        Optional<ContentItemEntity> optionalContentItem = contentItemRepository.findById(itemId);

        if(!optionalContentItem.isPresent()){
            return ContentItemEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        ContentItemEntity entity = optionalContentItem.get();
        entity.setLive(status);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(admin.getUsername());

        contentItemRepository.save(entity);

        return ContentItemEntity.Response.builder()
            .success(true)
            .build();

    }

    public ContentItemEntity.Response setPreview(UserDetailsImpl admin, String contentId, int itemId, boolean status) {
        Optional<ContentItemEntity> optionalContentItem = contentItemRepository.findById(itemId);

        if(!optionalContentItem.isPresent()){
            return ContentItemEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        ContentItemEntity entity = optionalContentItem.get();
        entity.setPreview(status);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(admin.getUsername());

        contentItemRepository.save(entity);

        return ContentItemEntity.Response.builder()
            .success(true)
            .build();

    }
}
