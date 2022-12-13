package me.univ.flex.common.properties;

import lombok.Data;

@Data
public class SecurityProperties {
	private String passwordEncoder;
	private String keyAes256;
	private boolean useOtp;
	private int otpAuthSkipMin;
}
