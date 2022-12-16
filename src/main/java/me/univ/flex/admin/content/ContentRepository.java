package me.univ.flex.admin.content;

import me.univ.flex.entity.content.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, String>, ContentCustomRepository {
}
