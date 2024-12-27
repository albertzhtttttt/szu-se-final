package com.szu.weboj.service.impl;

import com.szu.weboj.dao.AccountDao;
import com.szu.weboj.dao.impl.AccountDaoImpl;
import com.szu.weboj.pojo.Account;
import com.szu.weboj.pojo.Management;
import com.szu.weboj.pojo.Profile;
import com.szu.weboj.service.AccountService;
import com.szu.weboj.util.EncryptUtil;

import java.util.List;

/**
 * ClassName: AccountServiceImpl
 * Package: com.szu.weboj.service.impl
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:38
 * @Version 1.0.0
 */
public class AccountServiceImpl implements AccountService {
	private AccountDao accountDao = new AccountDaoImpl();

	@Override
	public List<Management> findAll() {
		return accountDao.findAll();
	}

	@Override
	public Account findByUserName(String userName) {
		return accountDao.findByUserName(userName);
	}

	@Override
	public Account findByUid(Integer uid) {
		return accountDao.findByUid(uid);
	}

	@Override
	public Integer registerAccount(Account registerAccount) {
		registerAccount.setPassword(EncryptUtil.encrypt(registerAccount.getPassword()));
		return accountDao.insertAccount(registerAccount);
	}

	@Override
	public Integer uploadProfile(Profile profile) {
		return accountDao.insertProfile(profile);
	}

	@Override
	public List<Profile> downloadProfile(int uid) {
		return accountDao.findProfile(uid);
	}

	@Override
	public Integer setStatus(Integer uid, Integer status) {
		return accountDao.setStatus(uid, status);
	}

	@Override
	public List<Account> getStatus(Integer uid) {
		return accountDao.getStatus(uid);
	}

	@Override
	public Integer resetPassword(Integer uid, String defaultPwd) {
		String pwd = EncryptUtil.encrypt(defaultPwd);
		return accountDao.updatePasswordDefault(uid, pwd);
	}

	@Override
	public Integer deleteAccount(Integer uid) {
		return accountDao.updateDeleteAccount(uid);
	}

	@Override
	public List<Account> findUidByUsername(String username) {
		return accountDao.findUidByUsername(username);
	}
}
