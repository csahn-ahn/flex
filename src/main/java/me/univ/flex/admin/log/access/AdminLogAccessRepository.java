package me.univ.flex.admin.log.access;

import me.univ.flex.admin.log.AdminLogAccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLogAccessRepository extends JpaRepository<AdminLogAccessEntity, Integer>, AdminLogAccessCustomRepository {
}
