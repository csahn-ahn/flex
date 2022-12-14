package me.univ.flex.admin.log.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.logs.AdminLogLoginEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminLogLoginService {

	private final AdminLogLoginRepository adminLogLoginRepository;

	@Async
	public void save(String adminId, String ip) {
		AdminLogLoginEntity entity = AdminLogLoginEntity.builder()
			.adminId(adminId)
			.loginTime(TimestampUtil.now())
			.ip(ip)
			.build();

		adminLogLoginRepository.save(entity);
	}

}
