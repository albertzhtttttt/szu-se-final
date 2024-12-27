package com.szu.weboj.test;

import com.szu.weboj.util.EncryptUtil;
import com.szu.weboj.util.JwtUtil;
import org.junit.Test;

/**
 * ClassName: TestJwtUtil
 * Package: com.szu.weboj.test
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 16:35
 * @Version 1.0.0
 */
public class TestJwtUtil {
	@Test
	public void testMethods() throws InterruptedException {
		String token = JwtUtil.createToken(1L);
		System.out.println(token);

		Long uid = JwtUtil.getUserId(token);
		System.out.println(uid);

		System.out.println(JwtUtil.isExpiration(token));

		Thread.sleep(6000);

		System.out.println(JwtUtil.isExpiration(token));
	}

	@Test
	public void testEncrypt() {
		System.out.println(EncryptUtil.encrypt("11111111111"));
	}

	@Test
	public void test() {
//		String token = JwtUtil.createToken(1L);
//		System.out.println(token);
		System.out.println(JwtUtil.getUserId("eyJhbGciOiJIUzUxdMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNDc2MbcwNDI011EqLU4t8kxRsjKtBQADk2s-LwAAAA.yClLljaylW5FeM6Xm-7x85PIZkhoLfKTMcFJrbnBS_scuNjD7ldBMP4FA0n_Hn8hDjjnlN_56lmFOp-Y4-t7Cg"));
		System.out.println(JwtUtil.getUserId("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSiox099ANDXYNUtJRSq0oULIyNDc2sTA2MzW21FEqLU4t8kxRsjKtBQADHO4ALwAAAA.eOAc7bLgaqmqjtv-ihPLDgBTXfmJAQQPrB5jzRN7Vff9j7nwv_XYNOnJpf_uMgAHr-geh8WTEyWEeUBl8jONqA"));
	}
}
