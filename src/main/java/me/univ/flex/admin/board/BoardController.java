package me.univ.flex.admin.board;

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
@RequestMapping(name = "게시판 관리", value = BaseConstants.ADMIN_API_PREFIX + "/boards")
public class BoardController {

    private final BoardService service;
    private final BoardContentService boardContentService;

    @GetMapping(name = "게시판 목록 조회")
    public ResponseEntity<Page<BoardEntity>> findAll(@AuthenticationPrincipal UserDetailsImpl admin, BoardEntity.PageRequest request){
        return ResponseEntity.ok(service.findAll(admin, request));
    }

    @GetMapping(name = "게시판 상세 조회", value = "/{boardId}")
    public ResponseEntity<BoardEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int boardId){
        return ResponseEntity.ok(service.detail(admin, boardId));
    }

    @PostMapping(name = "게시판 저장")
    public ResponseEntity<BoardEntity.Response> save(@AuthenticationPrincipal UserDetailsImpl admin, @RequestBody BoardEntity.SaveRequest request){
        return ResponseEntity.ok(service.save(admin, request));
    }

    @DeleteMapping(name = "게시판 삭제", value = "/{boardId}")
    public ResponseEntity<BoardEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int boardId){
        return ResponseEntity.ok(service.delete(admin, boardId));
    }

    @GetMapping(name = "게시물 목록 조회", value = "/{boardId}/contents")
    public ResponseEntity<Page<BoardContentEntity>> findContentAll(@AuthenticationPrincipal UserDetailsImpl admin, BoardContentEntity.PageRequest request, @PathVariable int boardId){
        return ResponseEntity.ok(boardContentService.findAll(admin, request, boardId));
    }

    @GetMapping(name = "게시물 상세 조회", value = "/{boardId}/contents/{contentId}")
    public ResponseEntity<BoardContentEntity> detail(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int boardId, @PathVariable int contentId){
        return ResponseEntity.ok(boardContentService.detail(admin, boardId, contentId));
    }

    @PutMapping(name = "게시물 노출변경", value = "/{boardId}/contents/{contentId}/{visible}")
    public ResponseEntity<BoardContentEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int boardId, @PathVariable int contentId, @PathVariable boolean visible){
        return ResponseEntity.ok(boardContentService.visible(admin, boardId, contentId, visible));
    }

    @DeleteMapping(name = "게시판 삭제", value = "/{boardId}/contents/{contentId}")
    public ResponseEntity<BoardContentEntity.Response> delete(@AuthenticationPrincipal UserDetailsImpl admin, @PathVariable int boardId, @PathVariable int contentId){
        return ResponseEntity.ok(boardContentService.delete(admin, boardId, contentId));
    }
}
