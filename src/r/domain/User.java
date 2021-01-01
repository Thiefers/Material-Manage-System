package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_user")
public class User {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id; // id��ʶ

	@MyField(columnName = "loginAct", type = "varchar", length = 255)
	private String loginAct; // �˺�

	@MyField(columnName = "name", type = "varchar", length = 255)
	private String name; // �û���==�ǳ�

	@MyField(columnName = "loginPwd", type = "varchar", length = 255)
	private String loginPwd; // ����

	@MyField(columnName = "expireTime", type = "char", length = 19)
	private String expireTime; // �˺�ʧЧʱ�䣬Ϊ����������Ч

	@MyField(columnName = "lockState", type = "char", length = 1)
	private String lockState; // �˺�����״̬��0����1��

	@MyField(columnName = "createTime", type = "char", length = 19)
	private String createTime; // �˺Ŵ���ʱ��

	@MyField(columnName = "createBy", type = "varchar", length = 255)
	private String createBy; // �˺Ŵ�����

	@MyField(columnName = "position", type = "varchar", length = 255)
	private String position; // �˺�����ְλ��ϵͳ����Ա������Ա(ע��Ĭ�ϳ���)

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
