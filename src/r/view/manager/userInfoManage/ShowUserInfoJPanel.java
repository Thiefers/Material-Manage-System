package r.view.manager.userInfoManage;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import r.controller.UserController;
import r.domain.User;
import r.utils.MyListeners;
import r.view.manager.global.ManagerJPanel;

public class ShowUserInfoJPanel extends JPanel implements MyListeners {

	private UserController userController = new UserController();
	private User user;
	private JButton editBtn;
	private String loginAct;
	private ManagerJPanel managerJPanel;

	public ShowUserInfoJPanel() {
	}

	public ShowUserInfoJPanel(ManagerJPanel managerJPanel, String loginAct) {
		this.managerJPanel = managerJPanel;
		this.loginAct = loginAct;
		this.user = userController.queryUserByLoginAct(loginAct);
		editBtn = new JButton("�༭");
		this.add(editBtn);
		editBtn.addMouseListener(this);
		
		String[][] tableData = { { "�˺�", user.getLoginAct() }, { "�ǳ�", user.getName() },
				{ "�˺�ʧЧʱ��", "".equals(user.getExpireTime()) ? "������Ч" : user.getExpireTime() },
				{ "����ʱ��", user.getCreateTime() }, { "������", user.getCreateBy() },
				{ "ְλ", user.getPosition() } };
		String[] name = {"",""};
		JTable table = new JTable(tableData, name);
		table.setEnabled(false);
		table.setRowHeight(20);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(200);
			table.getColumnModel().getColumn(i).setMaxWidth(400);
		}
		this.add(table);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == editBtn) {
			managerJPanel.removeAndAddRequestOne(this, new EditUserInfoJPanel(managerJPanel, loginAct));
		}
	}
}
