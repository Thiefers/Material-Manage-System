package r.controller;

import r.service.impl.UserServiceImpl;
import r.utils.JDBCUtil;
import r.utils.MD5Util;

import java.util.List;

import r.domain.User;
import r.service.UserService;

public class UserController {

	private UserService userService = new UserServiceImpl();

	public User queryUserByLoginAct(String loginAct) {
		User user = userService.queryUserByLoginAct(loginAct);
		if (user != null) {
			JDBCUtil.commitAndClose();
			return user;
		} else {
			JDBCUtil.rollbackAndClose();
			return null;
		}
	}

	public boolean updateUserInfoBySelf(String loginAct, String name, String loginPwd) {
		if ("".equals(name) || "".equals(loginPwd) || name == null || loginPwd == null) {
			return false;
		} else {
			boolean flag = userService.updateUserInfoBySelf(loginAct, name.trim(), MD5Util.getMD5(loginPwd).trim());
			if (flag) {
				JDBCUtil.commitAndClose();
				return true;
			} else {
				JDBCUtil.rollbackAndClose();
				return false;
			}
		}
	}

	public List<User> queryUsers(String loginActProp, String lockStateProp, String positionProp) {
		List<User> userList = userService.queryUsers(loginActProp, lockStateProp, positionProp);
		if (userList != null) {
			JDBCUtil.commitAndClose();
			return userList;
		} else {
			JDBCUtil.rollbackAndClose();
			return null;
		}
	}

	public boolean delUser(List<String> userIdList) {
		if (userIdList.contains("qwertyuiopasdfghjklzxcvbnmqwe123")) {
			return false;
		} else {
			boolean flag = userService.delUser(userIdList);
			if (flag) {
				JDBCUtil.commitAndClose();
				return true;
			} else {
				JDBCUtil.rollbackAndClose();
				return false;
			}
		}
	}

	public boolean resetPwdBySingleId(String resetId) {
		boolean flag = userService.resetPwdBySingleId(resetId);
		if (flag) {
			JDBCUtil.commitAndClose();
			return true;
		} else {
			JDBCUtil.rollbackAndClose();
			return false;
		}
	}

	public boolean resetPwdByIdList(List<String> resetIdList) {
		boolean flag = userService.resetPwdByIdList(resetIdList);
		if (flag) {
			JDBCUtil.commitAndClose();
			return true;
		} else {
			JDBCUtil.rollbackAndClose();
			return false;
		}
	}

	public boolean updateUserInfo(String expireTime, String stateChange, String position, String id) {
		boolean flag = userService.updateUserInfo(expireTime, stateChange, position, id);
		if (flag) {
			JDBCUtil.commitAndClose();
			return true;
		} else {
			JDBCUtil.rollbackAndClose();
			return false;
		}
	}

	public boolean addUserInfo(String loginActProp, String nameProp, String loginPwdProp, String expireTimeProp,
			String lockStateProp, String positionProp, String loginAct) {
		if ("".equals(loginActProp) || "".equals(nameProp) || "".equals(loginPwdProp) || loginActProp.startsWith(" ")
				|| nameProp.startsWith(" ") || loginPwdProp.startsWith(" ")) {
			return false;
		} else {
			boolean flag = userService.addUserInfo(loginActProp, nameProp, MD5Util.getMD5(loginPwdProp), expireTimeProp,
					lockStateProp, positionProp, loginAct);
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
