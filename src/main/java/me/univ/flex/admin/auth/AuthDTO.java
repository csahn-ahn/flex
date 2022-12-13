package me.univ.flex.admin.auth;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

public class AuthDTO {
	@Data
	@Builder
	public static class Response {
		private boolean success;
		private String message;
		private Map<String, Object> data;
	}

	@Data
	@Builder
	public static class FindPasswordRequest {
		private String username;
		private String name;
		private String email;
	}

	@Data
	@Builder
	public static class ChangeTempPasswordRequest {
		private String username;
		private String tempPassword;
		private String password;
	}

	@Data
	@Builder
	public static class ChangePasswordRequest {
		private String password;
		private String newPassword;
	}
}
