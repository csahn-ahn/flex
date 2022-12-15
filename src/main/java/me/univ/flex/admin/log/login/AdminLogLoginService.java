package me.univ.flex.admin.log.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.logs.AdminLogAccessEntity;
import me.univ.flex.entity.logs.AdminLogLoginEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminLogLoginService {

	private final AdminLogLoginRepository adminLogLoginRepository;

	public Page<AdminLogLoginEntity> findAll(AdminLogLoginEntity.PageRequest request) {
		PageRequest pageRequest = PageRequest.of(
			request.getPage() -1,
			request.getPageSize(),
			Sort.by(Sort.Direction.DESC, "loginTime")
		);

		Page<AdminLogLoginEntity> page = adminLogLoginRepository.findCustomAll(pageRequest, request);
		return page;
	}

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
