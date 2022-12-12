package me.univ.flex.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("flex")
@Data
public class FlexProperties {
	private String applicationName;
	private SecurityProperties securityProps;
}
