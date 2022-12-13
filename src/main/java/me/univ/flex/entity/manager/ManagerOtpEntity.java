package me.univ.flex.entity.manager;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "manager_otp")
public class ManagerOtpEntity {
    @Id
    @Column(nullable = false)
    private String username;
    private String secretKey;
    private Timestamp lastAuthTime;
}
