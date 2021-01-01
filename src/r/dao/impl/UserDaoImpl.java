package r.dao.impl;

import java.util.List;

import r.dao.UserDao;
import r.domain.User;
import r.utils.MD5Util;

public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public User login(String loginAct, String loginPwd) {
		String sql = "select * from t_user where loginAct=? and loginPwd=?";
		User user = queryForOne(User.class, sql, loginAct, loginPwd);
		return user;
	}

	@Override
	public User queryUserByLoginAct(String loginAct) {
		String sql = "select * from t_user where loginAct=?";
		User user = queryForOne(User.class, sql, loginAct);
		return user;
	}

	@Override
	public int updateUserInfoBySelf(User user) {
		String sql = "update t_user set name=?,loginPwd=? where loginAct=?";
		int count = update(sql, user.getName(), user.getLoginPwd(), user.getLoginAct());
		return count;
	}

	@Override
	public int register(User user) {
		String sql = "insert into t_user(id,loginAct,name,loginPwd,expireTime,lockState,createTime,createBy,position) values(?,?,?,?,?,?,?,?,?)";
		return update(sql, user.getId(), user.getLoginAct(), user.getName(), user.getLoginPwd(), user.getExpireTime(),
				user.getLockState(), user.getCreateTime(), user.getCreateBy(), user.getPosition());
	}

	@Override
	public List<User> queryUsers(User user) {
		String sql = "select * from t_user where loginAct like '%' ? '%' and lockState like '%' ? '%' and position like '%' ? '%'";
		List<User> userList = queryForList(User.class, sql, user.getLoginAct(), user.getLockState(),
				user.getPosition());
		return userList;
	}

	@Override
	public int delUser(List<String> userIdList) {
		String sql = "DELETE FROM t_user WHERE `id` IN(";
		StringBuffer sb = new StringBuffer(sql);
		for (int i = 0; i < userIdList.size(); i++) {
			if (i == userIdList.size() - 1) {
				sb = sb.append("?)");
			} else {
				sb = sb.append("?,");
			}
		}
		sql = sb.append(" AND `lockState`=0").toString();
		int count = update(sql, userIdList);
		return count;
	}

	@Override
	public int resetPwdBySingleId(String resetId) {
		String sql = "update t_user set `loginPwd`=? where `id`=?";
		return update(sql, MD5Util.getMD5("666"), resetId);
	}

	@Override
	public int resetPwdByIdList(List<String> resetIdList) {
		String sql = "update t_user set `loginPwd`=? where `id` in(";
		StringBuffer sb = new StringBuffer(sql);
		for (int i = 0; i < resetIdList.size(); i++) {
			if (i == resetIdList.size() - 1) {
				sb = sb.append("?)");
			} else {
				sb = sb.append("?,");
			}
		}
		sql = sb.toString();
		return update(sql, resetIdList, MD5Util.getMD5("666"));
	}

	@Override
	public int updateUserInfo(String expireTime, String stateChange, String position, String id) {
		String sql = "update t_user set `expireTime`=?,`lockState`=?,`position`=? where `id`=?";
		return update(sql, expireTime, stateChange, position, id);
	}

}