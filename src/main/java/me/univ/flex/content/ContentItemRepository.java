package me.univ.flex.content;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentItemRepository extends JpaRepository<ContentItemEntity, Integer> {
	List<ContentItemEntity> findByContentIdAndDelOrderByItemIdDesc(String contentId, boolean del);
}
