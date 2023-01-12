package me.univ.flex.board;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.utils.TimestampUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardContentService {

    private final BoardContentRepository repository;


    public Page<BoardContentEntity> findAll(BoardContentEntity.PageRequest request, int boardId) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "contentId")
        );
        request.setBoardId(boardId);
        Page<BoardContentEntity> page = repository.findCustomAll(pageRequest, request);
        return page;
    }

    public BoardContentEntity detail(int boardId, int contentId) {
        Optional<BoardContentEntity> optionalBoardContent = repository.findById(contentId);
        if(!optionalBoardContent.isPresent()) {
            return null;
        }
        return optionalBoardContent.get();
    }

    public BoardContentEntity.Response save(UserDetailsImpl userDetails, BoardContentEntity.SaveRequest request) {
        Optional<BoardContentEntity> optionalBoardContent = repository.findById(request.getContentId());
        BoardContentEntity entity = null;

        if(StringUtils.isEmpty(request.getTitle())) {
            return BoardContentEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        if(!optionalBoardContent.isPresent()) {
            // 등록
            entity = BoardContentEntity.builder()
                .typeId(request.getTypeId())
                .title(request.getTitle())
                .body(request.getBody())
                .del(false)
                .registerTime(TimestampUtil.now())
                .registerId(userDetails.getUsername())
                .build();

        }else{
            // 수정
            entity = optionalBoardContent.get();
            entity.setTypeId(request.getTypeId());
            entity.setTitle(request.getTitle());
            entity.setBody(request.getBody());
            entity.setLastUpdateTime(TimestampUtil.now());
            entity.setLastUpdateId(userDetails.getUsername());
        }
        repository.save(entity);

        return BoardContentEntity.Response.builder()
            .success(true)
            .build();
    }

    public BoardContentEntity.Response visible(int boardId, int contentId, boolean visible) {
        Optional<BoardContentEntity> optionalContent = repository.findById(contentId);

        if(!optionalContent.isPresent()){
            return BoardContentEntity.Response.builder()
                .success(false)
                .message("변경할 대상이 존재하지 않습니다.")
                .build();
        }

        BoardContentEntity entity = optionalContent.get();
        entity.setVisible(visible);

        repository.save(entity);

        return BoardContentEntity.Response.builder()
            .success(true)
            .build();

    }

    public BoardContentEntity.Response delete(UserDetailsImpl userDetails, int boardId, int contentId) {
        Optional<BoardContentEntity> optionalContent = repository.findById(contentId);

        if(!optionalContent.isPresent()){
            return BoardContentEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        BoardContentEntity entity = optionalContent.get();
        entity.setDel(true);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(userDetails.getUsername());

        repository.save(entity);

        return BoardContentEntity.Response.builder()
            .success(true)
            .build();

    }
}
