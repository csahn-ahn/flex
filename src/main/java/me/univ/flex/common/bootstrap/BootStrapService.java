package me.univ.flex.common.bootstrap;

import java.sql.Timestamp;
import java.time.Instant;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.admin.manager.ManagerRepository;
import me.univ.flex.admin.manager.ManagerService;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BootStrapService {

  private static final String SUPER_ADMIN_USERNAME = "manager";
  private static final String SUPER_ADMIN_PASSWORD = "1234";
  private static final String SUPER_ADMIN_NAME = "슈퍼관리자";
  private static final String SUPER_ADMIN_GRADE = "SUPER";

  private final ManagerRepository managerRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 초기 관리자 추가.
   */
  @PostConstruct
  public void init() {
    if (!isExistAdminUser()) {
      initSuperAdminUser();
    }
  }

  public boolean isExistAdminUser() {
    return false;
  }

  public void initSuperAdminUser() {
    log.info("SUPER ADMIN CREATED: [id: {}, pass: {}]", SUPER_ADMIN_USERNAME, SUPER_ADMIN_PASSWORD);

    ManagerEntity managerEntity = ManagerEntity.builder()
        .username(SUPER_ADMIN_USERNAME)
        .password(passwordEncoder.encode(SUPER_ADMIN_PASSWORD))
        .name(SUPER_ADMIN_NAME)
        .grade(SUPER_ADMIN_GRADE)
        .registerTime(Timestamp.from(Instant.now()))
        .active(true)
        .del(false)
        .build();

    managerRepository.save(managerEntity);
  }

  @Transactional
  public void cleanUpSuperUser() {
  }

  public String getSuperAdminUsername() {
    return SUPER_ADMIN_USERNAME;
  }

}
