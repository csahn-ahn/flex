package me.univ.flex.admin.manager;

import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerCustomRepository {
	Page<ManagerEntity> findCustomAll(Pageable pageable, ManagerEntity.PageRequest request);
}
