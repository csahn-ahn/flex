package me.univ.flex.admin.adminMenu;

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
        return list;
    }

    public List<AdminMenuEntity> findMyMenus() {
        // 전체 메뉴 조회.
        List<AdminMenuEntity> list = adminMenuRepository.findAll();

        // 상위 메뉴 필터링.
        List<AdminMenuEntity> menus = list.stream().filter(obj -> obj.getUpperMenuId() == 0).collect(Collectors.toList());

        for(AdminMenuEntity menu : menus) {
            // 하위메뉴 필터링
            List<AdminMenuEntity> lowerMenus = list.stream().filter(obj -> obj.getUpperMenuId() == menu.getMenuId()).collect(Collectors.toList());

            // 하위메뉴 추가.
            menu.setLowerMenus(lowerMenus);
        }

        return menus;
    }
}
