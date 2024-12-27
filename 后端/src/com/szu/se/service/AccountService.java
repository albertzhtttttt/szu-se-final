package com.szu.weboj.service;

import com.szu.weboj.pojo.Account;
import com.szu.weboj.pojo.Management;
import com.szu.weboj.pojo.Profile;

import java.util.List;

/**
 * ClassName: AccountService
 * Package: com.szu.weboj.service.impl
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:38
 * @Version 1.0.0
 */
public interface AccountService {
	/**
	 * 查询所有账户信息（不含密码）的方法
	 *
	 * @return 多个账户信息以List<Account>集合形式返回
	 */
	List<Management> findAll();

	/**
	 * 根据用户名查询账户
	 *
	 * @param userName 用户名
	 * @return 查询成功返回账户，失败返回null
	 */
	Account findByUserName(String userName);

	/**
	 * 根据uid查询账户
	 *
	 * @param uid 用户ID
	 * @return 查询成功返回账户，失败返回null
	 */
	Account findByUid(Integer uid);

	/**
	 * 注册账号
	 *
	 * @param registerAccount 注册账户信息
	 * @return 成功返回1，失败返回0
	 */
	Integer registerAccount(Account registerAccount);

	/**
	 * 上传个人信息
	 *
	 * @param profile 个人信息
	 * @return 成功返回1，失败返回0
	 */
	Integer uploadProfile(Profile profile);

	/**
	 * 下载个人信息
	 *
	 * @return 个人信息
	 */
	List<Profile> downloadProfile(int uid);

	/**
	 * 设置账户状态
	 *
	 * @param uid    账户ID
	 * @param status 账户状态
	 * @return 成功返回1，失败返回0
	 */
	Integer setStatus(Integer uid, Integer status);

	/**
	 * 获取账户状态
	 *
	 * @param uid 账户ID
	 * @return 账户信息以List<Account>集合形式返回
	 */
	List<Account> getStatus(Integer uid);

	/**
	 * 重置密码的业务接口实现
	 *
	 * @param uid        账户ID
	 * @param defaultPwd 前端加密过的默认密码
	 * @return 成功返回1，失败返回0
	 */
	Integer resetPassword(Integer uid, String defaultPwd);

	/**
	 * 删除账号的业务接口实现
	 * @param uid 账户ID
	 * @return 成功返回1，失败返回0
	 */
	Integer deleteAccount(Integer uid);

	List<Account> findUidByUsername(String username);
}
