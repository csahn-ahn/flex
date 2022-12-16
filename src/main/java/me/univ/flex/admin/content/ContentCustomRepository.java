package me.univ.flex.admin.content;

import me.univ.flex.entity.content.ContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentCustomRepository {
	Page<ContentEntity> findCustomAll(Pageable pageable, ContentEntity.PageRequest request);
}
