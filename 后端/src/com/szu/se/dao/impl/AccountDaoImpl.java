package com.szu.weboj.dao.impl;

import com.szu.weboj.dao.AccountDao;
import com.szu.weboj.dao.BaseDao;
import com.szu.weboj.pojo.Account;
import com.szu.weboj.pojo.Management;
import com.szu.weboj.pojo.Profile;
import com.szu.weboj.util.EncryptUtil;

import java.util.Base64;
import java.util.List;

/**
 * ClassName: AccountDaoImpl
 * Package: com.szu.weboj.dao.impl
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:36
 * @Version 1.0.0
 */
public class AccountDaoImpl extends BaseDao implements AccountDao {
	@Override
	public List<Management> findAll() {
		String sql = """
				SELECT a.uid, a.role, a.username, COALESCE(p.nickname, '') AS nickname, a.status
				FROM account a
				LEFT JOIN profile p ON a.uid = p.uid;
				""";

		return baseQuery(Management.class, sql);
	}

	@Override
	public Account findByUserName(String userName) {
		String sql = """
				select uid, role, username, password
				from account
				where username = ?
				""";
		List<Account> list = baseQuery(Account.class, sql, userName);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Account findByUid(Integer uid) {
		String sql = """
				select uid, role, username
				from account
				where uid = ?
				""";
		List<Account> list = baseQuery(Account.class, sql, uid);
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Integer insertAccount(Account registerAccount) {
		String sql = "insert into account values(DEFAULT, ?, ?, ?, DEFAULT)";
		return baseUpdate(sql,
				registerAccount.getRole().toString(),
				registerAccount.getUsername(),
				registerAccount.getPassword());
	}

	@Override
	public Integer insertProfile(Profile profile) {
		byte[] avatar = Base64.getDecoder().decode(profile.getAvatar().split(",")[1]);
		String sql = """
				update profile
				set avatar = ?, nickname = ?, signature = ?
				where uid = ?
				""";

		int rows = baseUpdate(sql,
				avatar,
				profile.getNickname(),
				profile.getSignature(),
				profile.getUid()
		);
		if (rows > 0) return rows;

		sql = "insert into profile values(?, ?, ?, ?)";
		return baseUpdate(sql,
				profile.getUid(),
				avatar,
				profile.getNickname(),
				profile.getSignature()
		);
	}

	@Override
	public List<Profile> findProfile(int uid) {
		String sql = "select * from profile where uid = ?";
		return baseQuery(Profile.class, sql, uid);
	}

	@Override
	public Integer setStatus(Integer uid, Integer status) {
		String sql = """
				update account
				set status = ?
				where uid = ?
				""";
		return baseUpdate(sql, status, uid);
	}

	@Override
	public List<Account> getStatus(Integer uid) {
		String sql = "select status from account where uid = ?";
		return baseQuery(Account.class, sql, uid);
	}

	@Override
	public Integer updatePasswordDefault(Integer uid, String pwd) {
		String sql = """
				update account
				set password = ?
				where uid = ?
				""";
		return baseUpdate(sql, pwd, uid);
	}

	@Override
	public Integer updateDeleteAccount(Integer uid) {
		String sql = "delete from account where uid = ?";
		return baseUpdate(sql, uid);
	}

	@Override
	public List<Account> findUidByUsername(String username) {
		String sql = "select uid from account where username = ?";
		return baseQuery(Account.class, sql, username);
	}
}
