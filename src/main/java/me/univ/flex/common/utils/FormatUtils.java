package me.univ.flex.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtils {
	public static boolean validPassword(String password) {

		// 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
		Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
		Matcher passMatcher1 = passPattern1.matcher(password);

		if (!passMatcher1.find()) {
			return false;
		}
		return true;
	}
}
