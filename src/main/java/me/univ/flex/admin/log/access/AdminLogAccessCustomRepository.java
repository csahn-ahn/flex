package me.univ.flex.admin.log.access;

import me.univ.flex.admin.log.AdminLogAccessEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminLogAccessCustomRepository {
	Page<AdminLogAccessEntity> findCustomAll(Pageable pageable, AdminLogAccessEntity.PageRequest request);
}
