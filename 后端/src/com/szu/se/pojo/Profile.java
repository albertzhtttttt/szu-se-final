package com.szu.weboj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: Profile
 * Package: com.szu.weboj.pojo
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/23 14:04
 * @Version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Profile implements Serializable {
	private Integer uid;
	private String avatar;
	private String nickname;
	private String signature;
}
