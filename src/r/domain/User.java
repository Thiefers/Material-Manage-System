package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_user")
public class User {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id; // id标识

	@MyField(columnName = "loginAct", type = "varchar", length = 255)
	private String loginAct; // 账号

	@MyField(columnName = "name", type = "varchar", length = 255)
	private String name; // 用户名==昵称

	@MyField(columnName = "loginPwd", type = "varchar", length = 255)
	private String loginPwd; // 密码

	@MyField(columnName = "expireTime", type = "char", length = 19)
	private String expireTime; // 账号失效时间，为空则永久有效

	@MyField(columnName = "lockState", type = "char", length = 1)
	private String lockState; // 账号锁定状态：0锁，1开

	@MyField(columnName = "createTime", type = "char", length = 19)
	private String createTime; // 账号创建时间

	@MyField(columnName = "createBy", type = "varchar", length = 255)
	private String createBy; // 账号创建人

	@MyField(columnName = "position", type = "varchar", length = 255)
	private String position; // 账号所属职位：系统管理员，管理员(注册默认持有)

	public User() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginAct() {
		return loginAct;
	}

	public void setLoginAct(String loginAct) {
		this.loginAct = loginAct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getLockState() {
		return lockState;
	}

	public void setLockState(String lockState) {
		this.lockState = lockState;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginAct=" + loginAct + ", name=" + name + ", loginPwd=" + loginPwd
				+ ", expireTime=" + expireTime + ", lockState=" + lockState + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", position=" + position + "]";
	}

}
