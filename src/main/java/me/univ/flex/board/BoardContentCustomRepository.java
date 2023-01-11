package me.univ.flex.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardContentCustomRepository {
	Page<BoardContentEntity> findCustomAll(Pageable pageable, BoardContentEntity.PageRequest request);
}
