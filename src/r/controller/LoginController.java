package r.controller;

import r.domain.User;
import r.service.UserService;
import r.service.impl.UserServiceImpl;
import r.utils.JDBCUtil;

public class LoginController {
	private UserService userService = new UserServiceImpl();
	
	public User loginCheck(String loginAct, String loginPwd) {
		User user = userService.login(loginAct, loginPwd);
		if (user == null) {
			JDBCUtil.rollbackAndClose();
			return null;
		} else {
			JDBCUtil.commitAndClose();
			return user;
		}
	}
}
