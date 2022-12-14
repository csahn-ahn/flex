package me.univ.flex.admin.log.access;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.logs.AdminLogAccessEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminLogAccessService {

	private final AdminLogAccessRepository adminLogAccessRepository;

	public Page<AdminLogAccessEntity> findAll(AdminLogAccessEntity.PageRequest request) {
		PageRequest pageRequest = PageRequest.of(
			request.getPage() -1,
			request.getPageSize(),
			Sort.by(Sort.Direction.DESC, "accessTime")
		);

		Page<AdminLogAccessEntity> page = adminLogAccessRepository.findCustomAll(pageRequest, request);
		return page;
	}

	@Async
	public void save(String adminId, int menuId, String menuName, String accessUrl) {
		AdminLogAccessEntity entity = AdminLogAccessEntity.builder()
			.adminId(adminId)
			.menuId(menuId)
			.menuName(menuName)
			.accessUrl(accessUrl)
			.accessTime(TimestampUtil.now())
			.build();

		adminLogAccessRepository.save(entity);
	}

}
