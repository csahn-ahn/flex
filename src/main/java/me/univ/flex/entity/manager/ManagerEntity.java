package me.univ.flex.entity.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "managers")
public class ManagerEntity implements UserDetails {
    @Id
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int groupId;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    private Timestamp registerTime;
    private Timestamp lastLoginTime;
    private Timestamp lastUpdateTime;
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    @Transient
    private String groupName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new SimpleGrantedAuthority("ADMIN"));
        return collectors;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료여부 (true-정상, false-만료)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠김여부 (true-정상, false-잠김)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 만료여부 (true-정상, false-만료)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 활성화 여부 (true-정상, false-비활성화)
        return true;
    }
}
