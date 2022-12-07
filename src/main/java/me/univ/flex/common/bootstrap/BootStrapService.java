package me.univ.flex.common.bootstrap;

import java.sql.Timestamp;
import java.time.Instant;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BootStrapService {

	private final MessageSourceAccessor messageSourceAccessor;

	private final ManagerRepository managerRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 초기 관리자 추가.
	 */
	@PostConstruct
	public void init() {
		if (!isExistAdminUser()) {
			initSuperAdminUser();
		}
	}

	public boolean isExistAdminUser() {
		return false;
	}

	public void initSuperAdminUser() {

		String username = messageSourceAccessor.getMessage("admin.super-admin.username");
		String password = messageSourceAccessor.getMessage("admin.super-admin.password");
		String name = messageSourceAccessor.getMessage("admin.super-admin.name");
		log.info("SUPER ADMIN CREATED: [id: {}, pass: {}]", username, password);

		ManagerEntity managerEntity = ManagerEntity.builder()
			.username(username)
			.password(passwordEncoder.encode(password))
			.name(name)
			.groupId(1)
			.active(true)
			.del(false)
			.registerTime(Timestamp.from(Instant.now()))
			.build();

		managerRepository.save(managerEntity);
	}

	@Transactional
	public void cleanUpSuperUser() {
	}

	public String getSuperAdminUsername() {
		String username = messageSourceAccessor.getMessage("admin.super-admin.username");
		return username;
	}

}
