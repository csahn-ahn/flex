package me.univ.flex.admin.adminMenu;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
}
