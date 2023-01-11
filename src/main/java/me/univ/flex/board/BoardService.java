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
public class BoardService {

    private final BoardRepository repository;


    public Page<BoardEntity> findAll(UserDetailsImpl admin, BoardEntity.PageRequest request) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "boardId")
        );

        Page<BoardEntity> page = repository.findCustomAll(pageRequest, request);
        return page;
    }

    public BoardEntity detail(UserDetailsImpl admin, int boardId) {
        Optional<BoardEntity> optionalBoard = repository.findById(boardId);
        if(!optionalBoard.isPresent()) {
            return null;
        }
        return optionalBoard.get();
    }

    public BoardEntity.Response save(UserDetailsImpl admin, BoardEntity.SaveRequest request) {
        Optional<BoardEntity> optionalBoard = repository.findById(request.getBoardId());
        BoardEntity entity = null;

        if(request.getBoardType() == 0 || StringUtils.isEmpty(request.getTitle())) {
            return BoardEntity.Response.builder()
                .success(false)
                .message("필수 입력정보가 누락되었습니다.")
                .build();
        }

        if(!optionalBoard.isPresent()) {
            // 등록
            entity = BoardEntity.builder()
                .boardType(request.getBoardType())
                .title(request.getTitle())
                .description(request.getDescription())
                .del(false)
                .registerTime(TimestampUtil.now())
                .registerId(admin.getUsername())
                .build();

        }else{
            // 수정
            entity = optionalBoard.get();
            entity.setBoardType(request.getBoardType());
            entity.setTitle(request.getTitle());
            entity.setDescription(request.getDescription());
            entity.setLastUpdateTime(TimestampUtil.now());
            entity.setLastUpdateId(admin.getUsername());
        }
        repository.save(entity);

        return BoardEntity.Response.builder()
            .success(true)
            .build();
    }

    public BoardEntity.Response delete(UserDetailsImpl admin, int boardId) {
        Optional<BoardEntity> optionalBoard = repository.findById(boardId);

        if(!optionalBoard.isPresent()){
            return BoardEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 존재하지 않습니다.")
                .build();
        }

        BoardEntity entity = optionalBoard.get();
        entity.setDel(true);
        entity.setDeleteTime(TimestampUtil.now());
        entity.setDeleteId(admin.getUsername());

        repository.save(entity);

        return BoardEntity.Response.builder()
            .success(true)
            .build();

    }
}
