package me.univ.flex.common.bootstrap;

import java.sql.Timestamp;
import java.time.Instant;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.manager.ManagerEntity;
import me.univ.flex.entity.user.UserEntity;
import me.univ.flex.entity.user.UserRepository;
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
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 초기 관리자 추가.
	 */
	@PostConstruct
	public void init() {
		initSuperAdminUser();
		initUser();
	}

	public boolean isExistAdminUser() {
		return false;
	}

	public void initSuperAdminUser() {

		String adminUsername = messageSourceAccessor.getMessage("admin.super-admin.username");
		String adminPpassword = messageSourceAccessor.getMessage("admin.super-admin.password");
		String adminName = messageSourceAccessor.getMessage("admin.super-admin.name");
		String adminEmail = messageSourceAccessor.getMessage("admin.super-admin.email");
		String adminHp = messageSourceAccessor.getMessage("admin.super-admin.hp");

		String operationUsername = messageSourceAccessor.getMessage("admin.operation.username");
		String operationPpassword = messageSourceAccessor.getMessage("admin.operation.password");
		String operationName = messageSourceAccessor.getMessage("admin.operation.name");
		String operationEmail = messageSourceAccessor.getMessage("admin.operation.email");
		String operationHp = messageSourceAccessor.getMessage("admin.operation.hp");


		log.info("SUPER ADMIN CREATED: [id: {}, pass: {}]", adminUsername, adminPpassword);

		ManagerEntity managerEntity = ManagerEntity.builder()
			.username(adminUsername)
			.password(passwordEncoder.encode(adminPpassword))
			.name(adminName)
			.email(adminEmail)
			.hp(adminHp)
			.groupId(1)
			.tempPassword(null)
			.active(true)
			.del(false)
			.registerTime(Timestamp.from(Instant.now()))
			.build();

		managerRepository.save(managerEntity);

		ManagerEntity manager2Entity = ManagerEntity.builder()
			.username(operationUsername)
			.password(passwordEncoder.encode(operationPpassword))
			.name(operationName)
			.email(operationEmail)
			.hp(operationHp)
			.groupId(2)
			.tempPassword(null)
			.active(true)
			.del(false)
			.registerTime(Timestamp.from(Instant.now()))
			.build();

		managerRepository.save(manager2Entity);
	}

	public void initUser() {

		String username = messageSourceAccessor.getMessage("user.username");
		String ppassword = messageSourceAccessor.getMessage("user.password");
		String name = messageSourceAccessor.getMessage("user.name");
		String email = messageSourceAccessor.getMessage("user.email");
		String hp = messageSourceAccessor.getMessage("user.hp");

		log.info("USER CREATED: [id: {}, pass: {}]", username, ppassword);

		UserEntity userEntity = UserEntity.builder()
			.username((username))
			.password(passwordEncoder.encode(ppassword))
			.name(name)
			.hp(hp)
			.email(email)
			.gender(true)
			.foreigner(false)
			.del(false)
			.registerTime(TimestampUtil.now())
			.build();

		userRepository.save(userEntity);
	}

	@Transactional
	public void cleanUpSuperUser() {
	}

	public String getSuperAdminUsername() {
		String username = messageSourceAccessor.getMessage("admin.super-admin.username");
		return username;
	}

}
