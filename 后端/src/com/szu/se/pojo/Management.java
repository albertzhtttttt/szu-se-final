package com.szu.weboj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Management
 * Package: com.szu.weboj.pojo
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/23 21:06
 * @Version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Management {
	private Integer uid;
	private UserType role;
	private String username;
	private String nickname;
	private Integer status;
}
