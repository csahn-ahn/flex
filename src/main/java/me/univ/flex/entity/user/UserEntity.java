package me.univ.flex.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(nullable = false)
    private String username;
    private String password;
    private String name;
    private String hp;
    private String email;
    private String birth;
    private boolean gender;
    private boolean foreigner;
    private String snsType;
    private String snsUid;
    private String tempPassword;
    @Column(nullable = false)
    private boolean del;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp lastLoginTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp lastUpdatePasswordTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;
}
