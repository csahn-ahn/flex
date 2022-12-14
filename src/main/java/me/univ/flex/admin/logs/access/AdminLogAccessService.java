package me.univ.flex.admin.logs.access;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.logs.AdminLogAccessEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminLogAccessService {

	private final AdminLogAccessRepository adminLogAccessRepository;

	@Async
	public void save(String adminId, int menuId, String accessUrl) {
		AdminLogAccessEntity entity = AdminLogAccessEntity.builder()
			.adminId(adminId)
			.menuId(menuId)
			.accessUrl(accessUrl)
			.accessTime(TimestampUtil.now())
			.build();

		adminLogAccessRepository.save(entity);
	}

}
