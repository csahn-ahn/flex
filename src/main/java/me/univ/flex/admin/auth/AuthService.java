package me.univ.flex.admin.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.common.properties.FlexProperties;
import me.univ.flex.common.service.email.EmailParameterKey;
import me.univ.flex.common.service.email.MailService;
import me.univ.flex.common.utils.FlexGenerator;
import me.univ.flex.common.utils.FormatUtils;
import me.univ.flex.common.utils.TimestampUtil;
import me.univ.flex.admin.manager.ManagerEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final FlexProperties flexProperties;
    private final MailService mailService;

    public AuthDTO.Response tempPassword(AuthDTO.FindPasswordRequest request) {
        Optional<ManagerEntity> optionalManager = managerRepository.findByUsername(request.getUsername());
        if(!optionalManager.isPresent()) {
            // 아이디가 존재하지 않음.
            return AuthDTO.Response.builder()
                .success(false)
                .message("정보가 올바르지 않습니다.[1]")
                .build();
        }

        ManagerEntity manager = optionalManager.get();
        if(!manager.getName().equals(request.getName())) {
            // 이름이 일치하지 않음.
            return AuthDTO.Response.builder()
                .success(false)
                .message("정보가 올바르지 않습니다.[2]")
                .build();
        }

        if(!manager.getEmail().equals(request.getEmail())) {
            // 이메일이 일치하지 않음.
            return AuthDTO.Response.builder()
                .success(false)
                .message("정보가 올바르지 않습니다.[3]")
                .build();
        }

        String tempPassword = FlexGenerator.generate(12);

        // 관리자 정보에 임시 비밀번호 저장.
        manager.setPassword(null);
        manager.setTempPassword(tempPassword);
        manager.setActive(false);
        managerRepository.save(manager);

        // 메일로 임시 비밀번호 발송.
        Map<String, Object> params = new HashMap<>();
        params.put(EmailParameterKey.URL_PREFIX, flexProperties.getEmailProps().getAdminUrl());
        params.put(EmailParameterKey.USERNAME, manager.getUsername());
        params.put(EmailParameterKey.PASSWORD, tempPassword);
        params.put(EmailParameterKey.BTN_LINK_URL, flexProperties.getEmailProps().getAdminUrl() + "/auth/changeTempPassword");
        //mailService.send(manager.getEmail(), EmailTemplateEnum.PASSWORD_TEMP, params, manager.getName());

        return AuthDTO.Response.builder()
            .success(true)
            .message("등록되어 있는 메일주소로 임시 비밀번호를 발송하였습니다.")
            .build();

    }


    public AuthDTO.Response changeTempPassword(AuthDTO.ChangeTempPasswordRequest request) {

        if(StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getTempPassword()) || StringUtils.isEmpty(request.getPassword())) {
            // 파라미터 정보 누락.
            return AuthDTO.Response.builder()
                .success(false)
                .message("필수입력 정보가 누락되었습니다.")
                .build();
        }

        Optional<ManagerEntity> optionalManager = managerRepository.findByUsername(request.getUsername());
        if(!optionalManager.isPresent()) {
            // 아이디가 존재하지 않음.
            return AuthDTO.Response.builder()
                .success(false)
                .message("아이디가 일치하지 않습니다.")
                .build();
        }

        ManagerEntity manager = optionalManager.get();
        if(!manager.getTempPassword().equals(request.getTempPassword())) {
            // 임시 비밀번호가 일치하지 않음.
            return AuthDTO.Response.builder()
                .success(false)
                .message("임시 비밀번호가 일치하지 않습니다.")
                .build();
        }

        if(!FormatUtils.validPassword(request.getPassword())) {
            return AuthDTO.Response.builder()
                .success(false)
                .message("비밀번호 형식이 맞지않습니다.")
                .build();
        }

        String password = passwordEncoder.encode(request.getPassword());

        // 관리자 정보에 임시 비밀번호 저장.
        manager.setPassword(password);
        manager.setTempPassword(null);
        manager.setLastUpdatePasswordTime(TimestampUtil.now());
        manager.setActive(true);
        managerRepository.save(manager);

        return AuthDTO.Response.builder()
            .success(true)
            .build();

    }
}



