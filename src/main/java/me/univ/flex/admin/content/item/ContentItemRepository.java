package me.univ.flex.admin.content.item;

import java.util.List;
import me.univ.flex.entity.content.ContentItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentItemRepository extends JpaRepository<ContentItemEntity, Integer> {
	List<ContentItemEntity> findByContentIdAndDelOrderByItemIdDesc(String contentId, boolean del);
}
