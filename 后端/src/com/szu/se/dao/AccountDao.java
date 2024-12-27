package com.szu.weboj.dao;

import com.szu.weboj.pojo.Account;
import com.szu.weboj.pojo.Management;
import com.szu.weboj.pojo.Profile;

import java.util.List;

/**
 * ClassName: AccountDao
 * Package: com.szu.weboj.dao
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:35
 * @Version 1.0.0
 */
public interface AccountDao {
	/**
	 * 查询所有账户信息（不含密码）的DAO方法
	 *
	 * @return 多个账户信息以List<Account>集合形式返回
	 */
	List<Management> findAll();

	/**
	 * 根据用户名查询账户的DAO方法
	 *
	 * @param userName 用户名
	 * @return 查询成功返回账户，失败返回null
	 */
	Account findByUserName(String userName);

	/**
	 * 根据uid查询账户的DAO方法
	 *
	 * @param uid 用户ID
	 * @return 查询成功返回账户，失败返回null
	 */
	Account findByUid(Integer uid);

	/**
	 * 注册账号的DAO方法
	 *
	 * @param registerAccount 注册账户信息
	 * @return 影响数据库的行数
	 */
	Integer insertAccount(Account registerAccount);

	/**
	 * 上传个人信息的DAO方法
	 *
	 * @param profile 个人信息
	 * @return 影响数据库的行数
	 */
	Integer insertProfile(Profile profile);

	/**
	 * 下载个人信息的DAO方法
	 *
	 * @param uid 用户ID
	 * @return 个人信息
	 */
	List<Profile> findProfile(int uid);

	/**
	 * 设置账户状态的DAO方法
	 *
	 * @param uid    账户ID
	 * @param status 账户状态
	 * @return 影响数据库的行数
	 */
	Integer setStatus(Integer uid, Integer status);

	/**
	 * 获取账户状态的DAO方法
	 *
	 * @param uid 账户ID
	 * @return 账户信息以List<Account>集合形式返回
	 */
	List<Account> getStatus(Integer uid);

	/**
	 * 重置密码为默认（123456）的DAO方法
	 *
	 * @param uid 用户ID
	 * @param pwd 前端后端都加密过的默认密码
	 * @return 影响数据库的行数
	 */
	Integer updatePasswordDefault(Integer uid, String pwd);

	/**
	 * 删除账号的DAO方法
	 *
	 * @param uid 用户ID
	 * @return 影响数据库的行数
	 */
	Integer updateDeleteAccount(Integer uid);

	List<Account> findUidByUsername(String username);
}
