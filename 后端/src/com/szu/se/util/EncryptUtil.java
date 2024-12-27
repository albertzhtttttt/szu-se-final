package com.szu.weboj.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * ClassName: EncryptUtil
 * Package: com.szu.weboj.util
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:01
 * @Version 1.0.0
 */
public class EncryptUtil {
	private static final String FIXED_SALT = "salt";
	/**
	 * 对明文进行加盐哈希加密
	 *
	 * @param plaintext 明文字符串
	 * @return 加密后的密文，格式为 salt:hash
	 */
	public static String encrypt(String plaintext) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(FIXED_SALT.getBytes());  // 使用固定盐
			byte[] hash = digest.digest(plaintext.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Encryption error", e);
		}
	}
}

