package me.univ.flex.content;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.utils.TimestampUtil;
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

    public List<ContentItemEntity> serviceItem(ContentItemEntity.ServiceRequest request) {

        List<ContentItemEntity> list = new ArrayList<>();

        List<String> contentIdArray = request.getContentId();
        boolean isPreview = request.getIsPreview() == null ? false : request.getIsPreview().booleanValue();
        String contentId = "";
        for(int i=0; i<contentIdArray.size(); i++) {
            ContentItemEntity itemEntity = null;
            contentId = contentIdArray.get(i);
            List<ContentItemEntity> itemList = contentItemRepository.findByContentIdAndDelOrderByItemIdDesc(contentId, false);
            if (itemList != null && !itemList.isEmpty()) {
                for (int j = 0; j < itemList.size(); j++) {
                    ContentItemEntity entity = itemList.get(j);
                    if (isPreview) {
                        // 미리보기
                        if (itemEntity == null && entity.isPreview() == true
                            && entity.getServiceStatus() == true) {
                            itemEntity = entity;
                        }

                    } else {
                        if (itemEntity == null && entity.isLive() == true
                            && entity.getServiceStatus() == true) {
                            itemEntity = entity;
                        }
                    }
                }
            }
            list.add(itemEntity);
        }
        return list;
    }

    public ContentItemEntity serviceItem(String contentId, boolean isPreview) {

        ContentItemEntity itemEntity = null;
        List<ContentItemEntity> itemList = contentItemRepository.findByContentIdAndDelOrderByItemIdDesc(
            contentId, false);
        if (itemList != null && !itemList.isEmpty()) {
            for (int i = 0; i < itemList.size(); i++) {
                ContentItemEntity entity = itemList.get(i);
                if (isPreview) {
                    // 미리보기
                    if (itemEntity == null && entity.isPreview() == true
                        && entity.getServiceStatus() == true) {
                        itemEntity = entity;
                    }

                } else {
                    if (itemEntity == null && entity.isLive() == true
                        && entity.getServiceStatus() == true) {
                        itemEntity = entity;
                    }
                }
            }
        }

        return itemEntity;
    }
}
