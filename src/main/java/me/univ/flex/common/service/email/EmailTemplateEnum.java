package me.univ.flex.common.service.email;

public enum EmailTemplateEnum {
	PASSWORD_TEMP("email/email-password-temp", "임시비밀번호 발급"),
	MANAGER_REGISTER("email/email-manager-register", "운영자 초대"),
	PASSWORD_RESET("email/email-password-reset", "비밀번호 재설정");

	private final String templateHtml;
	private final String subject;

	EmailTemplateEnum(String templateHtml, String subject) {
		this.templateHtml = templateHtml;
		this.subject = subject;
	}

	public String getTemplateHtml() {
		return templateHtml;
	}

	public String getSubject() {
		return subject;
	}
}
