package me.univ.flex.entity.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "managers")
public class ManagerEntity implements UserDetails {
    @Id
    @Column(nullable = true)
    private String username;
    @Column(nullable = true)
    private String password;
    @Column(nullable = true)
    private String name;
    @Column(nullable = true)
    private String grade;
    private Timestamp registerTime;
    private Timestamp lastLoginTime;
    private Timestamp lastUpdateTime;
    private boolean active;
    private boolean del;

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
