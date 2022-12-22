package me.univ.flex.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventApplyCustomRepository {
	Page<EventApplyEntity> findCustomAll(Pageable pageable, EventApplyEntity.PageRequest request);
}
