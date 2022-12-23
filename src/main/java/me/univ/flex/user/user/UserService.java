package me.univ.flex.user.user;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.utils.FormatUtils;
import me.univ.flex.common.utils.TimestampUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public Page<UserEntity> findAll(UserEntity.PageRequest request) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "registerTime")
        );

        Page<UserEntity> page = userRepository.findCustomAll(pageRequest, request);
        return page;
    }

    public UserEntity detail(UserDetailsImpl admin, String username) {
        Optional<UserEntity> optionalUser = userRepository.findById(username);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return UserEntity.builder().build();
    }

    public Optional<UserEntity> findById(String username) {
        return this.userRepository.findByUsernameAndDel(username, false);
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
        Optional<UserEntity> optionalUser = findById(request.getUsername());

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
        userRepository.save(user);

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    public UserEntity.Response loginSns(UserEntity.LoginSnsRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findBySnsTypeAndSnsUidAndDel(request.getSnsType(), request.getSnsUid(), false);
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

    @Transactional
    public UserEntity.Response logout() {
        SecurityContextHolder.clearContext();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().invalidate();

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    @Transactional
    public UserEntity.Response join(UserEntity.JoinRequest request) {
        Optional<UserEntity> optionUser = userRepository.findById(request.getUsername());
        if(optionUser.isPresent()) {
            return UserEntity.Response.builder()
                .success(false)
                .message("중복된 아이디입니다.")
                .build();
        }

        if(!FormatUtils.validPassword(request.getPassword())) {
            return UserEntity.Response.builder()
                .success(false)
                .message("비밀번호 형식이 맞지않습니다.")
                .build();
        }

        UserEntity userEntity = UserEntity.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .hp(request.getHp())
            .email(request.getEmail())
            .gender(request.isGender())
            .foreigner(request.isForeigner())
            .snsType(request.getSnsType())
            .snsUid(request.getSnsUid())
            .del(false)
            .registerId(request.getUsername())
            .registerTime(TimestampUtil.now())
            .lastUpdateId(request.getUsername())
            .lastUpdateTime(TimestampUtil.now())
            .lastUpdatePasswordTime(TimestampUtil.now())
            .build();

        userEntity = userRepository.save(userEntity);

        UserEntity.LoginRequest loginRequest = UserEntity.LoginRequest.builder()
            .grant("user")
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

        userEntity.setLastLoginTime(TimestampUtil.now());
        userRepository.save(userEntity);

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    @Transactional
    public UserEntity.Response leave(UserDetailsImpl userDetails, UserEntity.LeaveRequest request) {
        Optional<UserEntity> optionUser = findById(userDetails.getUsername());
        if(!optionUser.isPresent()) {
            return UserEntity.Response.builder()
                .success(false)
                .message("잘못된 계정입니다.")
                .build();
        }

        UserEntity userEntity = optionUser.get();

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            return UserEntity.Response.builder()
                .success(false)
                .message("비밀번호가 일치하지 않습니다.")
                .build();
        }

        userEntity.setPassword(null);
        userEntity.setName(null);
        userEntity.setHp(null);
        userEntity.setEmail(null);
        userEntity.setBirth(null);
        userEntity.setGender(false);
        userEntity.setForeigner(false);
        userEntity.setSnsType(null);
        userEntity.setSnsUid(null);
        userEntity.setTempPassword(null);
        userEntity.setDel(true);
        userEntity.setDeleteId(userDetails.getUsername());
        userEntity.setDeleteTime(TimestampUtil.now());

        userRepository.save(userEntity);

        logout();

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    @Transactional
    public UserEntity.Response updateUser(UserDetailsImpl userDetails, UserEntity.UpdateRequest request) {
        Optional<UserEntity> optionUser = findById(userDetails.getUsername());
        if(!optionUser.isPresent()) {
            return UserEntity.Response.builder()
                .success(false)
                .message("잘못된 계정입니다.")
                .build();
        }

        UserEntity userEntity = optionUser.get();
        userEntity.setName(request.getName());
        userEntity.setHp(request.getHp());
        userEntity.setEmail(request.getEmail());
        userEntity.setLastUpdateId(userDetails.getUsername());
        userEntity.setLastUpdateTime(TimestampUtil.now());
        userEntity = userRepository.save(userEntity);

        // 로그아웃 처리.
        //logout();

        UserEntity.LoginRequest loginRequest = UserEntity.LoginRequest.builder()
            .grant("user")
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .build();

        // 로그인 처리.
        UserDetails newUserDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(newUserDetails, null, userDetails.getAuthorities()));

        userEntity.setLastLoginTime(TimestampUtil.now());
        userRepository.save(userEntity);

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    @Transactional
    public UserEntity.Response password(UserDetailsImpl userDetails, UserEntity.UpdatePasswordRequest request) {
        Optional<UserEntity> optionUser = findById(userDetails.getUsername());
        if(!optionUser.isPresent()) {
            return UserEntity.Response.builder()
                .success(false)
                .message("잘못된 계정입니다.")
                .build();
        }

        UserEntity userEntity = optionUser.get();

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            return UserEntity.Response.builder()
                .success(false)
                .message("비밀번호가 일치하지 않습니다.")
                .build();
        }

        if(!FormatUtils.validPassword(request.getNewPassword())) {
            return UserEntity.Response.builder()
                .success(false)
                .message("비밀번호 형식이 맞지않습니다.")
                .build();
        }

        if(request.getPassword().equals(request.getNewPassword())) {
            return UserEntity.Response.builder()
                .success(false)
                .message("이전 비밀번호와 같은 비밀번호는 사용할 수 없습니다.")
                .build();
        }

        userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userEntity.setLastUpdatePasswordTime(TimestampUtil.now());
        userEntity.setLastUpdateId(userDetails.getUsername());
        userEntity.setLastUpdateTime(TimestampUtil.now());
        userRepository.save(userEntity);

        // 로그아웃 처리.
        logout();

        return UserEntity.Response.builder()
            .success(true)
            .build();
    }

    public UserEntity.TodayStatsResponse todayStats() {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,1));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        Timestamp start = Timestamp.valueOf(startDatetime);
        Timestamp end = Timestamp.valueOf(endDatetime);

        long totalCount = userRepository.countByDel(false);
        long todayNewCount = userRepository.countByRegisterTimeBetween(start, end);
        long todayLoginCount = userRepository.countByLastLoginTimeBetween(start, end);
        long todayDeleteCount = userRepository.countByDeleteTimeBetweenAndDel(start, end, true);

        return UserEntity.TodayStatsResponse.builder()
            .totalCount(totalCount)
            .todayNewCount(todayNewCount)
            .todayLoginCount(todayLoginCount)
            .todayDeleteCount(todayDeleteCount)
            .build();

    }
}
