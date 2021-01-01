package r.service;

import java.util.List;

import r.domain.User;

public interface UserService {
	public User login(String loginAct, String loginPwd);

	public boolean register(String loginAct, String name, String loginPwd, String position);

	public User queryUserByLoginAct(String loginAct);

	public boolean updateUserInfoBySelf(String loginAct, String name, String loginPwd);

	public List<User> queryUsers(String loginActProp, String lockStateProp, String positionProp);

	public boolean delUser(List<String> userIdList);

	public boolean resetPwdBySingleId(String resetId);

	public boolean resetPwdByIdList(List<String> resetIdList);

	public boolean updateUserInfo(String expireTime, String stateChange, String position, String id);

	public boolean addUserInfo(String loginActProp, String nameProp, String loginPwdProp, String expireTimeProp,
			String lockStateProp, String positionProp, String loginAct);
}
