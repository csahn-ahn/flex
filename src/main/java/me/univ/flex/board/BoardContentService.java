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


    public Page<BoardContentEntity> findAll(UserDetailsImpl admin, BoardContentEntity.PageRequest request, int boardId) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "contentId")
        );
        request.setBoardId(boardId);
        Page<BoardContentEntity> page = repository.findCustomAll(pageRequest, request);
        return page;
    }

    public BoardContentEntity detail(UserDetailsImpl admin, int boardId, int contentId) {
        Optional<BoardContentEntity> optionalBoard = repository.findById(contentId);
        if(!optionalBoard.isPresent()) {
            return null;
        }
        return optionalBoard.get();
    }

    public BoardContentEntity.Response visible(UserDetailsImpl admin, int boardId, int contentId, boolean visible) {
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

    public BoardContentEntity.Response delete(UserDetailsImpl admin, int boardId, int contentId) {
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
        entity.setDeleteId(admin.getUsername());

        repository.save(entity);

        return BoardContentEntity.Response.builder()
            .success(true)
            .build();

    }
}
