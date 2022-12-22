package me.univ.flex.content;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentCustomRepository {
	Page<ContentEntity> findCustomAll(Pageable pageable, ContentEntity.PageRequest request);

	List<ContentItemEntity> findByUrl(ContentEntity.ContentByUrlRequest request);
}
