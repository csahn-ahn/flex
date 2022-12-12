package me.univ.flex.common.security;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.adminGroupMenu.AdminGroupMenuService;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ManagerRepository managerRepository;
    private final AdminGroupMenuService adminGroupMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ManagerEntity> optionalManager = managerRepository.findById(username);
        if(!optionalManager.isPresent()) {
            throw new UsernameNotFoundException("Not found account");
        }

        ManagerEntity managerEntity = optionalManager.get();

        if(managerEntity.isDel()) {
            throw new UsernameNotFoundException("Deleted account");
        }

        if(!managerEntity.isActive()) {
            throw new UsernameNotFoundException("Inactive account");
        }

        List<AdminGroupMenuEntity> adminGroupMenus = adminGroupMenuService.findMyGroupMenu(managerEntity.getGroupId());

        return UserDetailsImpl.builder()
            .username(optionalManager.get().getUsername())
            .password(optionalManager.get().getPassword())
            .name(optionalManager.get().getName())
            .hp(optionalManager.get().getHp())
            .email(optionalManager.get().getEmail())
            .groupId(optionalManager.get().getGroupId())
            .active(optionalManager.get().isActive())
            .tempPassword(optionalManager.get().isTempPassword())
            .lastUpdatePasswordTime(optionalManager.get().getLastUpdatePasswordTime())
            .adminGroupMenus(adminGroupMenus)
            .build();
    }
}



