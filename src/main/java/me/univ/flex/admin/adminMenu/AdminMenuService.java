package me.univ.flex.admin.adminMenu;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMenuService {

	private final AdminMenuRepository adminMenuRepository;

	public List<AdminMenuEntity> findAll() {
		List<AdminMenuEntity> list = adminMenuRepository.findAll();
		List<AdminMenuEntity> sortedList = new ArrayList<>();

		List<AdminMenuEntity> menus = list.stream()
			.filter(obj -> obj.getUpperMenuId() == 0 && obj.isDel() == false)
			.sorted(Comparator.comparing(AdminMenuEntity::getSort))
			.collect(Collectors.toList());

		for (AdminMenuEntity upperMenu : menus) {
			sortedList.add(upperMenu);

			List<AdminMenuEntity> lowerMenus = list.stream()
				.filter(obj -> obj.getUpperMenuId() == upperMenu.getMenuId() && obj.isDel() == false)
				.sorted(Comparator.comparing(AdminMenuEntity::getSort))
				.collect(Collectors.toList());

			sortedList.addAll(lowerMenus);
		}

		return sortedList;
	}

	public List<AdminMenuEntity> findUpperMenus() {
		List<AdminMenuEntity> list = adminMenuRepository.findAll();

		List<AdminMenuEntity> upperMenus = list.stream()
			.filter(obj -> obj.getUpperMenuId() == 0 && obj.isDel() == false)
			.sorted(Comparator.comparing(AdminMenuEntity::getSort))
			.collect(Collectors.toList());

		return upperMenus;
	}

	public AdminMenuEntity detail(UserDetailsImpl admin, int menuId) {
		Optional<AdminMenuEntity> optionalAdminMenu = adminMenuRepository.findById(menuId);
		if(optionalAdminMenu.isPresent()) {

			AdminMenuEntity adminMenuEntity = optionalAdminMenu.get();
			if(adminMenuEntity.getUpperMenuId() > 0) {
				// 하위 메뉴일 경우.
				Optional<AdminMenuEntity> optionalUpperAdminMenu = adminMenuRepository.findById(adminMenuEntity.getUpperMenuId());
				if(optionalUpperAdminMenu.isPresent()) {
					// 상위 메뉴명 추가.
					adminMenuEntity.setUpperMenuName(optionalUpperAdminMenu.get().getMenuName());
				}
			}

			return optionalAdminMenu.get();
		}
		return AdminMenuEntity.builder().build();
	}

	public AdminMenuEntity save(UserDetailsImpl admin, AdminMenuEntity.SaveRequest request) {
		AdminMenuEntity adminMenuEntity = null;

		if(request.getSort() > 0) {
			// 같은 정렬순서를 가진 메뉴가 있을경우 - 같거나 큰 정렬순서를 가진 메뉴들의 정렬순서를 1씩 뒤로 미룬다.(+1)
			if(adminMenuRepository.existsByUpperMenuIdAndSort(request.getUpperMenuId(), request.getSort())){
				List<AdminMenuEntity> updateSortTarget = adminMenuRepository.findByUpperMenuId(request.getUpperMenuId())
					.stream().filter(menu -> menu.getSort() >= request.getSort()).collect(Collectors.toList());
				for(AdminMenuEntity target : updateSortTarget) {
					target.setSort(target.getSort() + 1);
				}
				adminMenuRepository.saveAll(updateSortTarget);
			}
		}

		if(request.getMenuId() == 0) {

			Integer sort = request.getSort();
			if(sort == 0) {
				sort = adminMenuRepository.finMenuSortMax(request.getUpperMenuId());
				sort = sort == null ? 1 : sort + 1;
			}

			// 등록
			adminMenuEntity = AdminMenuEntity.builder()
				.upperMenuId(request.getUpperMenuId())
				.menuName(request.getMenuName())
				.linkType(request.getLinkType())
				.linkUrl(request.getLinkUrl())
				.icon(request.getIcon())
				.sort(sort.intValue())
				.del(false)
				.registerTime(Timestamp.from(Instant.now()))
				.registerId(admin.getUsername())
				.build();

		} else {
			// 수정
			adminMenuEntity = adminMenuRepository.findById(request.getMenuId()).orElseThrow();
			adminMenuEntity.setMenuName(request.getMenuName());
			adminMenuEntity.setLinkType(request.getLinkType());
			adminMenuEntity.setLinkUrl(request.getLinkUrl());
			adminMenuEntity.setIcon(request.getIcon());
			adminMenuEntity.setLastUpdateTime(Timestamp.from(Instant.now()));
			adminMenuEntity.setSort(request.getSort());
			adminMenuEntity.setLastUpdateId(admin.getUsername());
		}

		adminMenuEntity = adminMenuRepository.save(adminMenuEntity);
		return adminMenuEntity;
	}

	public AdminMenuEntity.DeleteResponse delete(UserDetailsImpl admin, int menuId) {
		Optional<AdminMenuEntity> optionalAdminMenuEntity = adminMenuRepository.findById(menuId);
		if(!optionalAdminMenuEntity.isPresent()) {
			return AdminMenuEntity.DeleteResponse.builder()
				.success(false)
				.message("삭제할 대상이 않습니다.")
				.build();
		}

		AdminMenuEntity adminMenuEntity = optionalAdminMenuEntity.get();

		if(adminMenuEntity.isDel()) {
			return AdminMenuEntity.DeleteResponse.builder()
				.success(false)
				.message("이미 삭제된 대상입니다.")
				.build();
		}

		if(adminMenuEntity.getUpperMenuId() == 0) {
			// 상위 메뉴일 경우 - 하위메뉴도 삭제처리.
			List<AdminMenuEntity> lowerMenus = adminMenuRepository.findByUpperMenuId(adminMenuEntity.getMenuId());
			for(AdminMenuEntity lowerMenu : lowerMenus) {
				lowerMenu.setDel(true);
				lowerMenu.setDeleteId(admin.getUsername());
				lowerMenu.setDeleteTime(Timestamp.from(Instant.now()));
			}
			adminMenuRepository.saveAll(lowerMenus);
		}

		// 해당 메뉴 삭제처리.
		adminMenuEntity.setDel(true);;
		adminMenuEntity.setDeleteTime(Timestamp.from(Instant.now()));
		adminMenuEntity.setDeleteId(admin.getUsername());
		adminMenuRepository.save(adminMenuEntity);

		return AdminMenuEntity.DeleteResponse.builder()
			.success(true)
			.build();
	}
}
