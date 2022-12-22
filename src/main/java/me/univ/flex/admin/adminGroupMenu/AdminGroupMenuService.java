package me.univ.flex.admin.adminGroupMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.BaseConstants;
import me.univ.flex.common.security.UserDetailsImpl;
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

    public AdminGroupMenuEntity getMenuAuthority(String uri, int groupId) {
        if(uri.indexOf(BaseConstants.ADMIN_PREFIX + "/main") > -1) {
            return AdminGroupMenuEntity.builder()
                .hasCreate(true)
                .hasRead(true)
                .hasUpdate(true)
                .hasDelete(true)
                .hasDownload(true)
                .build();
        }

        List<AdminGroupMenuEntity> upperList = findMyGroupMenu(groupId)
            .stream()
            .filter(obj -> obj.getUpperMenuId() == 0 && uri.indexOf(obj.getLinkUrl()) > -1 && obj.isHasRead())
            .collect(Collectors.toList());

        if(upperList == null || upperList.isEmpty()) {
            return null;
        }

        String[] uriArray = menuDepth(uri);

        AdminGroupMenuEntity upperMenu = upperList.get(0);
        List<AdminGroupMenuEntity> lowerList = upperMenu.getLowerMenus()
            .stream()
            .filter(obj -> obj.isHasRead())
            .collect(Collectors.toList());

        AdminGroupMenuEntity groupMenu = null;
        for(AdminGroupMenuEntity lowerMenu : lowerList) {
            String[] menuUriArray = menuDepth(lowerMenu.getLinkUrl());
            if(menuUriArray[0].equals(uriArray[0]) && menuUriArray[1].equals(uriArray[1])){
                groupMenu = lowerMenu;
            }
        }

        return groupMenu;
    }

    private String[] menuDepth(String uri) {
        String[] uriArray = uri.split("/");
        String[] array = new String[]{
            uriArray[2], uriArray[3]
        };
        return array;
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
