package me.univ.flex.admin.adminGroupMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminGroupMenuService {

    private final AdminGroupMenuRepository adminGroupMenuRepository;

    public List<AdminGroupMenuEntity> findByGroupId(int groupId) {
        List<AdminGroupMenuEntity> list = adminGroupMenuRepository.findCustomAll(groupId);

        List<AdminGroupMenuEntity> sortedList = new ArrayList<>();

        List<AdminGroupMenuEntity> menus = list.stream()
            .filter(obj -> obj.getUpperMenuId() == 0)
            .sorted(Comparator.comparing(AdminGroupMenuEntity::getSort))
            .collect(Collectors.toList());

        for (AdminGroupMenuEntity upperMenu : menus) {
            sortedList.add(upperMenu);

            List<AdminGroupMenuEntity> lowerMenus = list.stream()
                .filter(obj -> obj.getUpperMenuId() == upperMenu.getMenuId())
                .sorted(Comparator.comparing(AdminGroupMenuEntity::getSort))
                .collect(Collectors.toList());

            sortedList.addAll(lowerMenus);
        }

        return sortedList;
    }

    public List<AdminGroupMenuEntity> findMyGroupMenu(int groupId) {
        List<AdminGroupMenuEntity> list = adminGroupMenuRepository.findMyGroupMenu(groupId);

        List<AdminGroupMenuEntity> menus = list.stream()
            .filter(obj -> obj.getUpperMenuId() == 0)
            .sorted(Comparator.comparing(AdminGroupMenuEntity::getSort))
            .collect(Collectors.toList());

        for (AdminGroupMenuEntity upperMenu : menus) {
            List<AdminGroupMenuEntity> lowerMenus = list.stream()
                .filter(obj -> obj.getUpperMenuId() == upperMenu.getMenuId())
                .sorted(Comparator.comparing(AdminGroupMenuEntity::getSort))
                .collect(Collectors.toList());

            upperMenu.setLowerMenus(lowerMenus);
        }
        return menus;
    }

    @Transactional
    public AdminGroupMenuEntity.AdminGroupMenuResponse save(UserDetailsImpl admin, AdminGroupMenuEntity.AdminGroupMenuRequest request) {
        // 기존 설정 삭제.
        adminGroupMenuRepository.deleteByGroupId(request.getGroupId());
        adminGroupMenuRepository.saveAll(request.getAdminGroupMenuList());

        return AdminGroupMenuEntity.AdminGroupMenuResponse.builder()
            .success(true)
            .build();
    }
}
