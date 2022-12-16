package me.univ.flex.admin.content;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.content.ContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public Page<ContentEntity> findAll(UserDetailsImpl admin, ContentEntity.PageRequest request) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "registerTime")
        );

        Page<ContentEntity> page = contentRepository.findCustomAll(pageRequest, request);
        return page;
    }

    public ContentEntity detail(UserDetailsImpl admin, String contentId) {
        Optional<ContentEntity> optionalContent = contentRepository.findById(contentId);
        if(!optionalContent.isPresent()) {
            return null;
        }
        return optionalContent.get();
    }

    public ContentEntity.Response save(UserDetailsImpl admin, ContentEntity.SaveRequest request) {
        Optional<ContentEntity> optionalContent = contentRepository.findById(request.getContentId());
        ContentEntity entity = null;

        if(StringUtils.isEmpty(request.getContentId()) || StringUtils.isEmpty(request.getTitle())) {
            return ContentEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        if(!optionalContent.isPresent()) {
            // 등록
            entity = ContentEntity.builder()
                .contentId(request.getContentId())
                .contentType(request.getContentType())
                .title(request.getTitle())
                .description(request.getDescription())
                .url(request.getUrl())
                .del(false)
                .registerTime(TimestampUtil.now())
                .registerId(admin.getUsername())
                .build();

        }else{
            // 수정
            entity = optionalContent.get();
            entity.setContentId(request.getContentId());
            entity.setContentType(request.getContentType());
            entity.setTitle(request.getTitle());
            entity.setDescription(request.getDescription());
            entity.setUrl(request.getUrl());
            entity.setLastUpdateTime(TimestampUtil.now());
            entity.setLastUpdateId(admin.getUsername());
        }
        contentRepository.save(entity);

        return ContentEntity.Response.builder()
            .success(true)
            .build();
    }

    public ContentEntity.Response delete(UserDetailsImpl admin, String contentId) {
        Optional<ContentEntity> optionalContent = contentRepository.findById(contentId);

        if(!optionalContent.isPresent()){
            return ContentEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        ContentEntity entity = optionalContent.get();
        entity.setDel(true);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(admin.getUsername());

        contentRepository.save(entity);

        return ContentEntity.Response.builder()
            .success(true)
            .build();

    }
}
