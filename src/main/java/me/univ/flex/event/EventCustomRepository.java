package me.univ.flex.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventCustomRepository {
	Page<EventEntity> findCustomAll(Pageable pageable, EventEntity.PageRequest request);
}
