package me.univ.flex.common.security;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
public class UserDetailsImpl implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String name;
	private String hp;
	private String email;
	private int groupId;
	private boolean active;
	private boolean tempPassword;
	private Timestamp lastUpdatePasswordTime;
	private final Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
