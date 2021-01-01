package r.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import r.service.UserService;
import r.service.impl.UserServiceImpl;
import r.utils.JDBCUtil;
import r.utils.MD5Util;

public class RegisterController {
	private UserService userService = new UserServiceImpl();

	public boolean register(String loginAct, String name, String loginPwd, String position) {
		if ("".equals(loginAct.trim()) || "".equals(name.trim()) || "".equals(loginPwd.trim())) {
			return false;
		} else {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9]\\w{5,11}$");
			Matcher isCorrect = pattern.matcher(loginPwd);
			if (!isCorrect.matches()) {
				return false;
			} else {
				boolean flag = userService.register(loginAct.trim(), name.trim(), MD5Util.getMD5(loginPwd.trim()),
						position);
				if (flag) {
					JDBCUtil.commitAndClose();
					return true;
				} else {
					JDBCUtil.rollbackAndClose();
					return false;
				}
			}
		}
	}
}
