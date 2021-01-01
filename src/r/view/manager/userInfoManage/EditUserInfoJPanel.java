package r.view.manager.userInfoManage;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import r.controller.UserController;
import r.domain.User;
import r.utils.MyListeners;
import r.view.manager.global.ManagerJPanel;

public class EditUserInfoJPanel extends JPanel implements MyListeners {

	private UserController userController = new UserController();
	private User user;
	private JTable table;
	private JButton submitBtn;
	private String loginAct;
	private ManagerJPanel managerJPanel;

	public EditUserInfoJPanel() {
	}

	public EditUserInfoJPanel(ManagerJPanel managerJPanel, String loginAct) {
		this.managerJPanel = managerJPanel;
		this.user = userController.queryUserByLoginAct(loginAct);
		this.loginAct = loginAct;
		submitBtn = new JButton("提交");
		this.add(submitBtn);
		submitBtn.addMouseListener(this);

		String[][] tableData = { { "昵称", user.getName() }, { "密码", "" } };
		String[] name = { "", "" };
		table = new JTable(tableData, name) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1) {
					return true;
				} else {
					return false;
				}
			}
		};
		this.add(table);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == submitBtn) {
			String name = table.getValueAt(0, 1).toString();
			String loginPwd = (String) table.getValueAt(1, 1);
			boolean flag = userController.updateUserInfoBySelf(loginAct, name, loginPwd);
			if (flag) {
				System.out.println("修改成功");
				managerJPanel.removeAndAddRequestOne(this, new ShowUserInfoJPanel(managerJPanel, loginAct));
			} else {
				System.out.println("修改失败");
			}

		}
	}

}