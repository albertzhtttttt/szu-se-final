package com.szu.weboj.common;

/**
 * ClassName: ResultCodeEnum
 * Package: com.szu.weboj.common
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 13:57
 * @Version 1.0.0
 */

/**
 * 统一返回结果状态信息类
 */
public enum ResultCodeEnum {

	SUCCESS(200, "success"),
	USERNAME_ERROR(501, "usernameError"),
	ACCOUNT_NOT_EXIST(502, "accountNotExist"),
	PASSWORD_ERROR(503, "passwordError"),
	NOTLOGIN(504, "notLogin"),
	USERNAME_USED(505, "userNameUsed"),
	UPLOAD_FAILED(506, "uploadProfileFailed"),
	DOWNLOAD_FAILED(507, "downloadProfileFailed");

	private Integer code;
	private String message;

	private ResultCodeEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}

