package r.dao;

import java.util.List;

import r.domain.User;

public interface UserDao {

	public User login(String loginAct, String loginPwd);

	public int register(User user);

	public User queryUserByLoginAct(String loginAct);
	
	public int updateUserInfoBySelf(User user);

	public List<User> queryUsers(User user);

	public int delUser(List<String> userIdList);

	public int resetPwdBySingleId(String resetId);

	public int resetPwdByIdList(List<String> resetIdList);

	public int updateUserInfo(String expireTime, String stateChange, String position, String id);
}
