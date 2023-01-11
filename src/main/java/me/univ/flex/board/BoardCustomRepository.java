package me.univ.flex.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
	Page<BoardEntity> findCustomAll(Pageable pageable, BoardEntity.PageRequest request);
}
