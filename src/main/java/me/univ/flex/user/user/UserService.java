package me.univ.flex.user.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.entity.user.UserEntity;
import me.univ.flex.entity.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public Optional<UserEntity> findById(String username) {
        return this.userRepository.findById(username);
    }

    public UserEntity save(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    public boolean delete(String username) {
        if(!this.userRepository.existsById(username))
            return false;

        this.userRepository.deleteById(username);
        return true;
    }

    public UserEntity.Response login(UserEntity.LoginRequest request) {
        Map<String, Object> data = new HashMap<>();
        Optional<UserEntity> optionalUser = userRepository.findById(request.getUsername());

        if(!optionalUser.isPresent()) {
            return UserEntity.Response.builder()
                .success(false)
                .message("계정이 존재하지 않습니다.")
                .build();
        }

        UserEntity user = optionalUser.get();

        boolean isTempPassword = false;
        if(StringUtils.isNotEmpty(user.getTempPassword())){
            isTempPassword = true;
            if (!passwordEncoder.matches(request.getPassword(), user.getTempPassword())) {
                return UserEntity.Response.builder()
                    .success(false)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
            } else {
                data.put("isTempPassword", isTempPassword);
                return UserEntity.Response.builder()
                    .success(true)
                    .data(data)
                    .build();
            }

        }else {
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return UserEntity.Response.builder()
                    .success(false)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
            }
        }

        request.setGrant("user");
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        user.setLastLoginTime(TimestampUtil.now());

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    public UserEntity.Response loginSns(UserEntity.LoginSnsRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findBySnsTypeAndSnsUid(request.getSnsType(), request.getSnsUid());
        if(!optionalUser.isPresent()) {
            return UserEntity.Response.builder()
                .success(false)
                .message("계정이 존재하지 않습니다.")
                .build();
        }

        UserEntity user = optionalUser.get();

        request.setGrant("user");
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    public UserEntity.Response logout() {
        SecurityContextHolder.clearContext();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().invalidate();

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }
}
