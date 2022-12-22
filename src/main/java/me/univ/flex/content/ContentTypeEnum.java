package me.univ.flex.content;

import lombok.Getter;

@Getter
public enum ContentTypeEnum {
	TYPE_UNKNOWN(0, "유형없음"),
	TYPE_URL(1, "URL 기반 콘텐츠"),
	TYPE_ID(1, "ID 기반 콘텐츠");

	private final int code;
	private final String value;

	ContentTypeEnum(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public boolean equalsContentType(ContentTypeEnum contentTypeEnum) {
		return this.code == contentTypeEnum.getCode();
	}
}
