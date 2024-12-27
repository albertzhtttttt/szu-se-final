package com.szu.weboj.util;

import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * ClassName: JwtUtil
 * Package: com.szu.weboj.util
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:13
 * @Version 1.0.0
 */
public class JwtUtil {
	private static long tokenExpiration = 5 * 60 * 1000; // 1分钟后过期
	private static String tokenSignKey = "123456";

	//生成token字符串
	public static String createToken(Long userId) {
		String token = Jwts.builder()

				.setSubject("YYGH-USER")
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
				.claim("userId", userId)
				.signWith(SignatureAlgorithm.HS512, tokenSignKey)
				.compressWith(CompressionCodecs.GZIP)
				.compact();
		return token;
	}

	//从token字符串获取userid
	public static Long getUserId(String token) {
		if (StringUtils.isEmpty(token)) return null;
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
		Claims claims = claimsJws.getBody();
		Integer userId = (Integer) claims.get("userId");
		return userId.longValue();
	}


	//判断token是否有效
	public static boolean isExpiration(String token) {
		try {
			boolean isExpire = Jwts.parser()
					.setSigningKey(tokenSignKey)
					.parseClaimsJws(token)
					.getBody()
					.getExpiration().before(new Date());
			//没有过期，有效，返回false
			return isExpire;
		} catch (Exception e) {
			//过期出现异常，返回true
			return true;
		}
	}
}

