package r.service.impl;

import java.util.List;

import r.dao.UserDao;
import r.dao.impl.UserDaoImpl;
import r.domain.User;
import r.service.UserService;
import r.utils.DateTimeUtil;
import r.utils.DicMapUtil;
import r.utils.UUIDUtil;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();

	@Override
	public User login(String loginAct, String loginPwd) {
		User user = userDao.login(loginAct, loginPwd);
		if (user == null) {
			System.out.println("账号密码错误");
			return null;
		}

		String expireTime = user.getExpireTime();
		String currentTime = DateTimeUtil.getSysTime();
		// 有填写失效时间字段的才进行比对
		if (!"".equals(expireTime) && currentTime.compareTo(expireTime) > 0) {
			System.out.println("账号已失效");
			return null;
		}

		String lockState = user.getLockState();
		if ("0".equals(lockState)) {
			System.out.println("账号已锁定");
			return null;
		}
		return user;
	}

	@Override
	public boolean register(String loginAct, String name, String loginPwd, String position) {
		boolean flag = true;
		// 判断用户名是否重复
		if (userDao.queryUserByLoginAct(loginAct) != null) {
			// 已存在账号，注册失败
			flag = false;
		} else {
			// 账号合法，尝试注册
			User user = new User();
			user.setId(UUIDUtil.getUUID());
			user.setLoginAct(loginAct);
			user.setName(name);
			user.setLoginPwd(loginPwd);
			user.setExpireTime(DateTimeUtil.addYear(DateTimeUtil.getSysTime()));
			user.setLockState("1");
			user.setCreateTime(DateTimeUtil.getSysTime());
			user.setCreateBy(loginAct);
			user.setPosition(position);
			int count = userDao.register(user);
			if (count != 1) {
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public User queryUserByLoginAct(String loginAct) {
		return userDao.queryUserByLoginAct(loginAct);
	}

	@Override
	public boolean updateUserInfoBySelf(String loginAct, String name, String loginPwd) {
		User user = new User();
		user.setLoginAct(loginAct);
		user.setName(name);
		user.setLoginPwd(loginPwd);
		return userDao.updateUserInfoBySelf(user) == 1;
	}

	@Override
	public List<User> queryUsers(String loginActProp, String lockStateProp, String positionProp) {
		User user = new User();
		user.setLoginAct(loginActProp);
		if (!"".equals(lockStateProp)) {
			user.setLockState(DicMapUtil.dicMap.get(lockStateProp));
		} else {
			user.setLockState(lockStateProp);
		}
		user.setPosition(positionProp);
		return userDao.queryUsers(user);
	}

	@Override
	public boolean delUser(List<String> userIdList) {
		return userDao.delUser(userIdList) == userIdList.size();
	}

	@Override
	public boolean resetPwdBySingleId(String resetId) {
		return userDao.resetPwdBySingleId(resetId) == 1;
	}

	@Override
	public boolean resetPwdByIdList(List<String> resetIdList) {
		return userDao.resetPwdByIdList(resetIdList) == resetIdList.size();
	}

	@Override
	public boolean updateUserInfo(String expireTime, String stateChange, String position, String id) {
		return userDao.updateUserInfo(expireTime, stateChange, position, id) == 1;
	}

	@Override
	public boolean addUserInfo(String loginActProp, String nameProp, String loginPwdProp, String expireTimeProp,
			String lockStateProp, String positionProp, String loginAct) {
		boolean flag = true;
		// 判断用户名是否重复
		if (userDao.queryUserByLoginAct(loginActProp) != null) {
			// 已存在账号，注册失败
			flag = false;
		} else {
			// 账号合法，尝试注册
			User user = new User();
			user.setId(UUIDUtil.getUUID());
			user.setLoginAct(loginActProp);
			user.setName(nameProp);
			user.setLoginPwd(loginPwdProp);
			user.setExpireTime(expireTimeProp);
			user.setLockState(lockStateProp);
			user.setCreateTime(DateTimeUtil.getSysTime());
			user.setCreateBy(loginAct);
			user.setPosition(positionProp);
			int count = userDao.register(user);
			if (count != 1) {
				flag = false;
			}
		}
		return flag;
	}

}
