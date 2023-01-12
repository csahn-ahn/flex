package me.univ.flex.user.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.board.BoardContentEntity;
import me.univ.flex.board.BoardContentService;
import me.univ.flex.board.BoardEntity;
import me.univ.flex.board.BoardService;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "게시물", value = BaseConstants.USER_API_PREFIX + "/boards")
public class UserBoardController {

    private final BoardService service;
    private final BoardContentService boardContentService;

    @GetMapping(name = "게시판 상세 조회", value = "/{boardId}")
    public ResponseEntity<BoardEntity> detail(@PathVariable int boardId){
        return ResponseEntity.ok(service.detail(boardId));
    }

    @GetMapping(name = "게시물 목록 조회", value = "/{boardId}/contents")
    public ResponseEntity<Page<BoardContentEntity>> findContentAll(BoardContentEntity.PageRequest request, @PathVariable int boardId){
        return ResponseEntity.ok(boardContentService.findAll(request, boardId));
    }

    @PostMapping(name = "게시물 저장", value = "/{boardId}/contents")
    public ResponseEntity<BoardContentEntity.Response> save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardContentEntity.SaveRequest request){
        return ResponseEntity.ok(boardContentService.save(userDetails, request));
    }

    @GetMapping(name = "게시물 상세 조회", value = "/{boardId}/contents/{contentId}")
    public ResponseEntity<BoardContentEntity> detail(@PathVariable int boardId, @PathVariable int contentId){
        return ResponseEntity.ok(boardContentService.detail(boardId, contentId));
    }

    @DeleteMapping(name = "게시물 삭제", value = "/{boardId}/contents/{contentId}")
    public ResponseEntity<BoardContentEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int boardId, @PathVariable int contentId){
        return ResponseEntity.ok(boardContentService.delete(userDetails, boardId, contentId));
    }
}
