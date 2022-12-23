package me.univ.flex.admin.manager;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.properties.FlexProperties;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.common.service.email.EmailParameterKey;
import me.univ.flex.common.service.email.EmailTemplateEnum;
import me.univ.flex.common.service.email.MailService;
import me.univ.flex.common.utils.FlexGenerator;
import me.univ.flex.common.utils.FormatUtils;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.user.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final FlexProperties flexProperties;
    private final MailService mailService;

    public Page<ManagerEntity> findAll(ManagerEntity.PageRequest request) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "registerTime")
        );

        Page<ManagerEntity> page = managerRepository.findCustomAll(pageRequest, request);
        return page;
    }

    public ManagerEntity detail(UserDetailsImpl admin, String username) {
        Optional<ManagerEntity> optionalManager = managerRepository.findById(username);
        if(optionalManager.isPresent()) {
            return optionalManager.get();
        }
        return ManagerEntity.builder().build();
    }

    public ManagerEntity save(UserDetailsImpl admin, ManagerEntity.SaveRequest request) {
        ManagerEntity managerEntity = null;
        Optional<ManagerEntity> optionalManager = managerRepository.findById(request.getUsername());

        if(optionalManager.isPresent()) {
            // 수정
            managerEntity = optionalManager.get();
            managerEntity.setName(request.getName());
            managerEntity.setHp(request.getHp());
            managerEntity.setEmail(request.getEmail());
            managerEntity.setGroupId(request.getGroupId());
            managerEntity.setLastUpdateTime(Timestamp.from(Instant.now()));
            managerEntity.setLastUpdateId(admin.getUsername());
            managerEntity = managerRepository.save(managerEntity);

        } else {

            String tempPassword = FlexGenerator.generate(12);

            // 등록
            managerEntity = ManagerEntity.builder()
                .username(request.getUsername())
                .password(null)
                .name(request.getName())
                .hp(request.getHp())
                .email(request.getEmail())
                .tempPassword(tempPassword)
                .groupId(1)
                .active(false)
                .del(false)
                .registerTime(Timestamp.from(Instant.now()))
                .registerId(admin.getUsername())
                .build();
            managerEntity = managerRepository.save(managerEntity);

            // 메일로 임시 비밀번호 발송.
            Map<String, Object> params = new HashMap<>();
            params.put(EmailParameterKey.URL_PREFIX, flexProperties.getEmailProps().getAdminUrl());
            params.put(EmailParameterKey.NAME, request.getName());
            params.put(EmailParameterKey.USERNAME, request.getUsername());
            params.put(EmailParameterKey.PASSWORD, tempPassword);
            params.put(EmailParameterKey.BTN_LINK_URL, flexProperties.getEmailProps().getAdminUrl() + "/auth/changeTempPassword");
            mailService.send(request.getEmail(), EmailTemplateEnum.MANAGER_REGISTER, params, request.getName());
        }

        return managerEntity;
    }

    public ManagerEntity.Response delete(UserDetailsImpl admin, String username) {

        Optional<ManagerEntity> optionalManager = managerRepository.findById(username);
        if(!optionalManager.isPresent()) {
            return ManagerEntity.Response.builder()
                .success(false)
                .message("삭제할 대상이 없습니다.")
                .build();
        }

        ManagerEntity managerEntity = optionalManager.get();

        if(managerEntity.isDel()) {
            return ManagerEntity.Response.builder()
                .success(false)
                .message("이미 삭제된 대상입니다.")
                .build();
        }

        managerEntity.setName("삭제됨");
        managerEntity.setHp(null);
        managerEntity.setEmail(null);
        managerEntity.setActive(false);
        managerEntity.setDel(true);
        managerEntity.setDeleteTime(Timestamp.from(Instant.now()));
        managerEntity.setDeleteId(admin.getUsername());

        managerRepository.save(managerEntity);

        return ManagerEntity.Response.builder()
            .success(true)
            .build();
    }

    public ManagerEntity.Response updatePassword(UserDetailsImpl admin, ManagerEntity.UpdatePasswordRequest request) {
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())){
            return ManagerEntity.Response.builder()
                .success(false)
                .message("현재 비밀번호와 일치하지 않습니다.")
                .build();
        }

        if(!FormatUtils.validPassword(request.getNewPassword())) {
            return ManagerEntity.Response.builder()
                .success(false)
                .message("비밀번호 형식이 맞지않습니다.")
                .build();
        }

        if(request.getPassword().equals(request.getNewPassword())) {
            return ManagerEntity.Response.builder()
                .success(false)
                .message("이전 비밀번호와 같은 비밀번호는 사용할 수 없습니다.")
                .build();
        }

        ManagerEntity managerEntity = managerRepository.findById(admin.getUsername()).orElseThrow();
        managerEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        managerEntity.setLastUpdatePasswordTime(TimestampUtil.now());
        managerEntity.setLastUpdateTime(TimestampUtil.now());
        managerEntity.setLastUpdateId(admin.getUsername());
        managerRepository.save(managerEntity);

        return ManagerEntity.Response.builder()
            .success(true)
            .build();
    }

    public boolean existManagerInGroup(int groupId) {
        return managerRepository.existsByGroupIdAndDelIsNotNull(groupId);
    }

    public long countManagerInGroup(int groupId) {
        return managerRepository.countByGroupIdAndDelIsNotNull(groupId);
    }

}



