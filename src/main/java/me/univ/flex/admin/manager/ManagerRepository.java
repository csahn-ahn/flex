package me.univ.flex.admin.manager;

import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ManagerRepository extends JpaRepository<ManagerEntity, String>, ManagerCustomRepository {
	boolean existsByGroupIdAndDelIsNotNull(int groupId);
	long countByGroupIdAndDelIsNotNull(int groupId);
	Optional<ManagerEntity> findByUsername(String username);
}
