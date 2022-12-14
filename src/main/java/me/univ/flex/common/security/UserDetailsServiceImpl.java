package me.univ.flex.common.security;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.log.login.AdminLogLoginService;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ManagerRepository managerRepository;
    private final AdminLogLoginService adminLogLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();

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

        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = request.getRemoteAddr();
        // 로그인 이력 저장.
        adminLogLoginService.save(managerEntity.getUsername(), ip);

        return UserDetailsImpl.builder()
            .username(optionalManager.get().getUsername())
            .password(optionalManager.get().getPassword())
            .name(optionalManager.get().getName())
            .hp(optionalManager.get().getHp())
            .email(optionalManager.get().getEmail())
            .groupId(optionalManager.get().getGroupId())
            .active(optionalManager.get().isActive())
            .lastUpdatePasswordTime(optionalManager.get().getLastUpdatePasswordTime())
            .build();
    }
}



