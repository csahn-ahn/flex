package me.univ.flex.admin.manager;

import java.util.List;
import me.univ.flex.entity.manager.ManagerEntity;

public interface ManagerCustomRepository {
	List<ManagerEntity> findCustomAll();
}
