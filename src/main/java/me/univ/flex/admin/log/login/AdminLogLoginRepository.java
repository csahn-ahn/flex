package me.univ.flex.admin.log.login;

import me.univ.flex.admin.log.AdminLogLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLogLoginRepository extends JpaRepository<AdminLogLoginEntity, Integer>, AdminLogLoginCustomRepository {
}
