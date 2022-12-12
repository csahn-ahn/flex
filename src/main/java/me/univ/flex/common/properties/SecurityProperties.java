package me.univ.flex.common.properties;

import lombok.Data;

@Data
public class SecurityProperties {
	private String passwordEncoder;
	private KeysProperties keysProperties;
}
