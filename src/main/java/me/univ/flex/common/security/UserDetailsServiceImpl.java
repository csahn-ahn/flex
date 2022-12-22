package me.univ.flex.common.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.log.login.AdminLogLoginService;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.admin.manager.ManagerEntity;
import me.univ.flex.user.user.UserEntity;
import me.univ.flex.user.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String grant = StringUtils.defaultIfBlank(request.getParameter("grant"), "user");
        if(grant.equals("admin")) {
            Optional<ManagerEntity> optionalManager = managerRepository.findById(username);
            if(!optionalManager.isPresent()) {
                throw new UsernameNotFoundException("Not found account");
            }

            ManagerEntity manager = optionalManager.get();

            if(manager.isDel()) {
                throw new UsernameNotFoundException("Deleted account");
            }

            if(!manager.isActive()) {
                throw new UsernameNotFoundException("Inactive account");
            }

            String ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null)
                ip = request.getRemoteAddr();
            // 로그인 이력 저장.
            adminLogLoginService.save(manager.getUsername(), ip);

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

            return UserDetailsImpl.builder()
                .username(manager.getUsername())
                .password(manager.getPassword())
                .name(manager.getName())
                .hp(manager.getHp())
                .email(manager.getEmail())
                .groupId(manager.getGroupId())
                .active(manager.isActive())
                .lastUpdatePasswordTime(manager.getLastUpdatePasswordTime())
                .authorities(authorities)
                .build();

        }else {
            Optional<UserEntity> optionalUser = userRepository.findById(username);
            if(!optionalUser.isPresent()) {
                throw new UsernameNotFoundException("Not found user account");
            }

            UserEntity user = optionalUser.get();

            if(user.isDel()) {
                throw new UsernameNotFoundException("Deleted user account");
            }

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return UserDetailsImpl.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .hp(user.getHp())
                .email(user.getEmail())
                .groupId(0)
                .active(true)
                .lastUpdatePasswordTime(user.getLastUpdatePasswordTime())
                .authorities(authorities)
                .build();
        }
    }
}



