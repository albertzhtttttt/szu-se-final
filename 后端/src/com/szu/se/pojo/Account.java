package com.szu.weboj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: Account
 * Package: com.szu.weboj.pojo
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:24
 * @Version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account implements Serializable {
	private Integer uid;
	private UserType role;
	private String username;
	private String password;
	private Integer status;
}
