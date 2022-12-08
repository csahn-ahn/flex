package me.univ.flex.admin.adminMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
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
			.filter(obj -> obj.getUpperMenuId() == 0)
			.sorted(Comparator.comparing(AdminMenuEntity::getSort))
			.collect(Collectors.toList());

		for (AdminMenuEntity upperMenu : menus) {
			sortedList.add(upperMenu);

			List<AdminMenuEntity> lowerMenus = list.stream()
				.filter(obj -> obj.getUpperMenuId() == upperMenu.getMenuId())
				.sorted(Comparator.comparing(AdminMenuEntity::getSort))
				.collect(Collectors.toList());

			sortedList.addAll(lowerMenus);
		}

		return sortedList;
	}

	public List<AdminMenuEntity> findMyMenus() {
		// 전체 메뉴 조회.
		List<AdminMenuEntity> list = adminMenuRepository.findAll();

		// 상위 메뉴 필터링.
		List<AdminMenuEntity> menus = list.stream()
			.filter(obj -> obj.getUpperMenuId() == 0)
			.sorted(Comparator.comparing(AdminMenuEntity::getSort))
			.collect(Collectors.toList());

		for (AdminMenuEntity menu : menus) {
			// 하위메뉴 필터링
			List<AdminMenuEntity> lowerMenus = list.stream()
				.filter(obj -> obj.getUpperMenuId() == menu.getMenuId())
				.sorted(Comparator.comparing(AdminMenuEntity::getSort))
				.collect(Collectors.toList());

			// 하위메뉴 추가.
			menu.setLowerMenus(lowerMenus);
		}

		return menus;
	}
}
