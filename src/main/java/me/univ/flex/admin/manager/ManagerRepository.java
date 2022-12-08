package me.univ.flex.admin.manager;

import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ManagerRepository extends JpaRepository<ManagerEntity, String>, ManagerCustomRepository {
}
