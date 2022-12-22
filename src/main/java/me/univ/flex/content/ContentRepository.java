package me.univ.flex.content;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, String>, ContentCustomRepository {
	List<ContentEntity> findByUrlAndContentType(String url, int contentType);
}
