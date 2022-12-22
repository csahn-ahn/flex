package me.univ.flex.admin.log.login;

import me.univ.flex.admin.log.AdminLogLoginEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminLogLoginCustomRepository {
	Page<AdminLogLoginEntity> findCustomAll(Pageable pageable, AdminLogLoginEntity.PageRequest request);
}
