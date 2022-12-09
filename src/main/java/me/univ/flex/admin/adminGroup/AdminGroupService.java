package me.univ.flex.admin.adminGroup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.adminGroupMenu.AdminGroupMenuRepository;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.adminGroup.AdminGroupEntity;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminGroupService {

    private final AdminGroupRepository adminGroupRepository;
    private final AdminGroupMenuRepository adminGroupMenuRepository;

    public List<AdminGroupEntity> findAll() {
        List<AdminGroupEntity> list = adminGroupRepository.findAll();
        return list;
    }
}
