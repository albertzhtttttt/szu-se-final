package com.szu.weboj.controller;

import com.szu.weboj.common.Result;
import com.szu.weboj.common.ResultCodeEnum;
import com.szu.weboj.pojo.Account;
import com.szu.weboj.pojo.Management;
import com.szu.weboj.pojo.Profile;
import com.szu.weboj.service.AccountService;
import com.szu.weboj.service.impl.AccountServiceImpl;
import com.szu.weboj.util.EncryptUtil;
import com.szu.weboj.util.JwtUtil;
import com.szu.weboj.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: AccountController
 * Package: com.szu.weboj.controller
 * Description:
 *
 * @Author Mallard
 * @Create 2024/12/21 14:41
 * @Version 1.0.0
 */
@WebServlet("/account/*")
public class AccountController extends BaseController {
	private AccountService accountService = new AccountServiceImpl();

	/**
	 * 查询所有账户信息（不含密码）的业务接口实现
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void findAllAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("FIND ALL ACCOUNT INFO");

		List<Management> accountList = accountService.findAll();

		WebUtil.writeJson(resp, Result.ok(accountList));
	}

	/**
	 * 处理登录表单提交的业务接口实现
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("LOGIN FORM SUBMIT");

		// 接收用户名和密码
		Account paramAccount = WebUtil.readJson(req, Account.class);

		// 调用服务层接口实现登录
		Account loginAccount = accountService.findByUserName(paramAccount.getUsername());
		Result<Object> res = null;
		if (loginAccount != null) {
			if (EncryptUtil.encrypt(paramAccount.getPassword()).equals(loginAccount.getPassword())) {
				String token = JwtUtil.createToken(loginAccount.getUid().longValue());
				// 使用Map转换token，保证返回的是key-value格式
				Map data = new HashMap<>();
				data.put("token", token);
				res = Result.ok(data);
			} else {
				res = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
			}
		} else {
			res = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
		}

		// 向客户端响应登录验证信息
		WebUtil.writeJson(resp, res);
	}

	/**
	 * 获取账户信息的业务接口实现
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getAccountInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("GET ACCOUNT INFO");

		// 根据token得到uid
		String token = req.getHeader("token");

		Result res = Result.build(null, ResultCodeEnum.NOTLOGIN);

		if (token != null && !"".equals(token)) {
			Account loginAccount = null;
			try {
				Integer uid = JwtUtil.getUserId(token).intValue();
				loginAccount = accountService.findByUid(uid);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (loginAccount != null) {
				HashMap<Object, Object> data = new HashMap<>();
				data.put("account", loginAccount);
				res = Result.ok(data);
			}
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 检验用户名是否重名业务接口实现
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void checkUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("CHECK SAME USERNAME");

		String username = req.getParameter("username");

		Account account = accountService.findByUserName(username);

		Result<Object> res = Result.ok(null);

		if (account != null) {
			res = Result.build(null, ResultCodeEnum.USERNAME_USED);
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 注册表单提交的业务接口
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("REGISTER FORM SUBMIT");

		Account registerAccount = WebUtil.readJson(req, Account.class);

		Integer rows = accountService.registerAccount(registerAccount);

		Result<Object> res = Result.ok(null);
		if (rows == 0)
			res = Result.build(null, ResultCodeEnum.USERNAME_USED);

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 检验登录状态是否过期的业务接口实现
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("CHECK LOGIN");

		String token = req.getHeader("token");

		Result<Object> res = Result.build(null, ResultCodeEnum.NOTLOGIN);

		if (token != null && !JwtUtil.isExpiration(token)) {
			res = Result.ok(null);
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 上传个人信息的业务接口实现
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void uploadProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("UPLOAD PROFILE");

		Profile profile = WebUtil.readJson(req, Profile.class);

		Integer rows = accountService.uploadProfile(profile);

		Result<Object> res = Result.ok(null);
		if (rows == 0)
			res = Result.build(null, ResultCodeEnum.UPLOAD_FAILED);

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 读取个人信息的业务接口实现
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void downloadProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("DOWNLOAD PROFILE");

		Profile profile = WebUtil.readJson(req, Profile.class);
		int uid = profile.getUid();
		List<Profile> list = accountService.downloadProfile(uid);

		Result<Object> res = Result.build(null, ResultCodeEnum.DOWNLOAD_FAILED);

		if (list.size() > 0) {
			Profile data = list.get(0);
			data.setAvatar("data:image/png;base64," + data.getAvatar());
			res = Result.ok(data);
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 设置账户状态的业务接口实现
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void setStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SET STATUS");

		Account statusAccount = WebUtil.readJson(req, Account.class);
		Integer uid = statusAccount.getUid();
		Integer status = statusAccount.getStatus();

		Integer rows  = accountService.setStatus(uid, status);

		Result<Object> res = Result.ok(null);

		if (rows == 0) {
			res = Result.build(null, ResultCodeEnum.ACCOUNT_NOT_EXIST);
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 获取账户状态的业务接口实现
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("GET STATUS");

		Account statusAccount = WebUtil.readJson(req, Account.class);
		Integer uid = statusAccount.getUid();

		List<Account> list  = accountService.getStatus(uid);

		Result<Object> res = Result.build(null, ResultCodeEnum.ACCOUNT_NOT_EXIST);

		if (list.size() > 0) {
			HashMap<String, Integer> map = new HashMap<>();
			map.put("status", list.get(0).getStatus());
			res = Result.ok(map);
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 重置密码的业务接口实现
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void resetPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("RESET PASSWORD");

		Account infoAccount = WebUtil.readJson(req, Account.class);
		Integer uid = infoAccount.getUid();
		String defaultPwd = infoAccount.getPassword();

		Integer rows = accountService.resetPassword(uid,defaultPwd);

		Result<Object> res = Result.ok(null);

		if (rows == 0) {
			res = Result.build(null, ResultCodeEnum.ACCOUNT_NOT_EXIST);
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 删除账号的业务接口实现
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void deleteAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("DELETE ACCOUNT");

		Integer uid = WebUtil.readJson(req, Account.class).getUid();

		Integer rows = accountService.deleteAccount(uid);

		Result<Object> res = Result.ok(null);

		if (rows == 0) {
			res = Result.build(null, ResultCodeEnum.ACCOUNT_NOT_EXIST);
		}

		WebUtil.writeJson(resp, res);
	}

	/**
	 * 根据账户ID获取账户名的业务接口实现
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void findUidByUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("FIND UID BY USERNAME");

		String username = WebUtil.readJson(req, Account.class).getUsername();
		List<Account> list = accountService.findUidByUsername(username);

		Result<Object> res = Result.build(null, ResultCodeEnum.ACCOUNT_NOT_EXIST);

		if (list.size() > 0) {
			HashMap<String, Integer> map = new HashMap<>();
			map.put("uid", list.get(0).getUid());
			res = Result.ok(map);
		}

		WebUtil.writeJson(resp, res);
	}
}
