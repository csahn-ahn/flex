package me.univ.flex.admin.manager;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.security.UserDetailsImpl;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<ManagerEntity> findAll(ManagerEntity.PageRequest request) {
        PageRequest pageRequest = PageRequest.of(
            request.getPage() -1,
            request.getPageSize(),
            Sort.by(Sort.Direction.DESC, "registerTime")
        );

        Page<ManagerEntity> page = managerRepository.findCustomAll(pageRequest);
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
            // 등록
            managerEntity = ManagerEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .hp(request.getHp())
                .email(request.getEmail())
                .groupId(1)
                .active(true)
                .del(false)
                .registerTime(Timestamp.from(Instant.now()))
                .registerId(admin.getUsername())
                .build();
            managerEntity = managerRepository.save(managerEntity);
        }

        return managerEntity;
    }

    public ManagerEntity.DeleteResponse delete(UserDetailsImpl admin, String username) {

        Optional<ManagerEntity> optionalManager = managerRepository.findById(username);
        if(optionalManager.isPresent()) {
            ManagerEntity managerEntity = optionalManager.get();

            if(managerEntity.isDel()) {
                return ManagerEntity.DeleteResponse.builder()
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

            return ManagerEntity.DeleteResponse.builder()
                .success(true)
                .build();

        }

        return ManagerEntity.DeleteResponse.builder()
            .success(false)
            .message("삭제할 대상이 없습니다.")
            .build();

    }
}



