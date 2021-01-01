package r.view.manager.global;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import r.utils.MenuBarUtil;
import r.utils.MyListeners;
import r.view.manager.material.AddMaterialJPanel;
import r.view.manager.material.DelMaterialJPanel;
import r.view.manager.material.MaterialCountJPanel;
import r.view.manager.material.MaterialManageJPanel;
import r.view.manager.userInfoManage.EditUserInfoJPanel;
import r.view.manager.userInfoManage.ShowUserInfoJPanel;
import r.view.manager.userInfoManage.SysUserManageJPanel;
import r.view.manager.warehouseManage.AddWarehouseJPanel;
import r.view.manager.warehouseManage.DelWarehouseJPanel;
import r.view.manager.warehouseManage.QueryWarehouseJPanel;
import r.view.manager.warehouseManage.UpdateWarehouseJPanel;

public class ManagerJPanel extends JPanel implements MyListeners {

	private JMenu userInfoMenu, warehouseMenu, materialMenu;
	private JMenuItem showUserInfo, editUserSelfInfo, sysUserManage, logOut;
	private JMenuItem addWarehouse, delWarehouse, editWarehouse, queryWarehouse;
	/*
	 * addMaterial���������ɹ�Ա��д��ⵥ�����µ��ݣ���棨��������Ӹ������ʱ�
	 * delMaterial���������ȡ������д���ⵥ�����µ��ݣ����
	 * materialManage����ѯ����(���ϲֿ⣬�����ʾ)(�������ƣ���������ھ��ɣ�ģ����ѯ��)��ɾ�����ʣ�����Ա
	 * materialCount����������ͳ��(���ꡢ�¡��գ��Գ����)
	 */
	private JMenuItem addMaterial, delMaterial, materialManage, materialCount;
	private JPanel mainJPanel;
	private CardLayout cardLayout;
	private JPanel menuBarJPanel; // �˵����ײ����
	private JPanel contentJPanel; // �������ĵײ���壬����ѡ�񲼾�
	private ShowUserInfoJPanel showUserInfoJPanel;
	private String loginAct;
	private String userPosition;

	public JMenu getUserInfoMenu() {
		return userInfoMenu;
	}

	public JMenu getWarehouseMenu() {
		return warehouseMenu;
	}

	public JMenu getMaterialMenu() {
		return materialMenu;
	}

	public JMenuItem getShowUserInfo() {
		return showUserInfo;
	}

	public JMenuItem getEditUserSelfInfo() {
		return editUserSelfInfo;
	}

	public JMenuItem getSysUserManage() {
		return sysUserManage;
	}

	public JMenuItem getLogOut() {
		return logOut;
	}

	public JMenuItem getAddWarehouse() {
		return addWarehouse;
	}

	public JMenuItem getDelWarehouse() {
		return delWarehouse;
	}

	public JMenuItem getEditWarehouse() {
		return editWarehouse;
	}

	public JMenuItem getQueryWarehouse() {
		return queryWarehouse;
	}

	public JMenuItem getAddMaterial() {
		return addMaterial;
	}

	public JMenuItem getDelMaterial() {
		return delMaterial;
	}

	public JMenuItem getMaterialManage() {
		return materialManage;
	}

	public JMenuItem getMaterialCount() {
		return materialCount;
	}

	public void setUserInfoMenu(JMenu userInfoMenu) {
		this.userInfoMenu = userInfoMenu;
	}

	public void setWarehouseMenu(JMenu warehouseMenu) {
		this.warehouseMenu = warehouseMenu;
	}

	public void setMaterialMenu(JMenu materialMenu) {
		this.materialMenu = materialMenu;
	}

	public void setShowUserInfo(JMenuItem showUserInfo) {
		this.showUserInfo = showUserInfo;
	}

	public void setEditUserSelfInfo(JMenuItem editUserSelfInfo) {
		this.editUserSelfInfo = editUserSelfInfo;
	}

	public void setSysUserManage(JMenuItem sysUserManage) {
		this.sysUserManage = sysUserManage;
	}

	public void setLogOut(JMenuItem logOut) {
		this.logOut = logOut;
	}

	public void setAddWarehouse(JMenuItem addWarehouse) {
		this.addWarehouse = addWarehouse;
	}

	public void setDelWarehouse(JMenuItem delWarehouse) {
		this.delWarehouse = delWarehouse;
	}

	public void setEditWarehouse(JMenuItem editWarehouse) {
		this.editWarehouse = editWarehouse;
	}

	public void setQueryWarehouse(JMenuItem queryWarehouse) {
		this.queryWarehouse = queryWarehouse;
	}

	public void setAddMaterial(JMenuItem addMaterial) {
		this.addMaterial = addMaterial;
	}

	public void setDelMaterial(JMenuItem delMaterial) {
		this.delMaterial = delMaterial;
	}

	public void setMaterialManage(JMenuItem materialManage) {
		this.materialManage = materialManage;
	}

	public void setMaterialCount(JMenuItem materialCount) {
		this.materialCount = materialCount;
	}

	public ManagerJPanel(JPanel mainJPanel, CardLayout cardLayout, String loginAct, String userPosition) {
		this.mainJPanel = mainJPanel;
		this.cardLayout = cardLayout;
		this.loginAct = loginAct;
		this.userPosition = userPosition;
		this.setLayout(new BorderLayout());
		// �˵���
		menuBarJPanel = MenuBarUtil.getMenu(this, userPosition);
		this.add(menuBarJPanel, BorderLayout.NORTH);
		// �������
		contentJPanel = new JPanel();

		showUserInfoJPanel = new ShowUserInfoJPanel(this, loginAct);
		contentJPanel.add(showUserInfoJPanel);

		this.add(contentJPanel, BorderLayout.CENTER);
	}

	/**
	 * �Ƴ�ָ��������������ָ�����
	 * 
	 * @param removeComp
	 *            ָ���Ƴ������
	 * @param addComp
	 *            ָ����ӵ����
	 */
	public void removeAndAddRequestOne(Component removeComp, Component addComp) {
		contentJPanel.remove(removeComp);
		contentJPanel.add(addComp);
		contentJPanel.repaint();
		contentJPanel.validate();
	}

	/**
	 * �Ƴ�ȫ��������������ָ�����
	 * 
	 * @param addComp
	 *            ָ����ӵ����
	 */
	public void removeAllAndAddRequestOne(Component addComp) {
		contentJPanel.removeAll();
		contentJPanel.add(addComp);
		contentJPanel.repaint();
		contentJPanel.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO ��ť����¼�
		if (e.getSource() == showUserInfo) {
			// ������Ϣ���
			this.removeAllAndAddRequestOne(new ShowUserInfoJPanel(this, loginAct));
		} else if (e.getSource() == editUserSelfInfo) {
			// �޸ĸ�����Ϣ���
			this.removeAllAndAddRequestOne(new EditUserInfoJPanel(this, loginAct));
		} else if (e.getSource() == logOut) {
			// �˺ŵǳ����˳�������
			int check = JOptionPane.showConfirmDialog(this, "ȷ�ϵǳ���", "��ʾ", JOptionPane.YES_NO_OPTION);
			if (check == JOptionPane.YES_OPTION) {
				this.cardLayout.show(mainJPanel, "loginJPanel");
			}
		} else if (e.getSource() == addWarehouse) {
			// ��Ӳֿ�
			this.removeAllAndAddRequestOne(new AddWarehouseJPanel());
		} else if (e.getSource() == delWarehouse) {
			// ɾ���ֿ�
			this.removeAllAndAddRequestOne(new DelWarehouseJPanel());
		} else if (e.getSource() == editWarehouse) {
			// �޸Ĳֿ�
			this.removeAllAndAddRequestOne(new UpdateWarehouseJPanel());
		} else if (e.getSource() == queryWarehouse) {
			// �鿴�ֿ�
			this.removeAllAndAddRequestOne(new QueryWarehouseJPanel(this));
		} else if (e.getSource() == sysUserManage) {
			// ϵͳ�û�����
			this.removeAllAndAddRequestOne(new SysUserManageJPanel(loginAct));
		} else if (e.getSource() == addMaterial) {
			// �������
			this.removeAllAndAddRequestOne(new AddMaterialJPanel(loginAct));
		} else if (e.getSource() == delMaterial) {
			// ���ʳ���
			this.removeAllAndAddRequestOne(new DelMaterialJPanel(loginAct));
		} else if (e.getSource() == materialManage) {
			// ���ʹ���(��ѯ���ʣ�ɾ������)
			this.removeAllAndAddRequestOne(new MaterialManageJPanel());
		} else if (e.getSource() == materialCount) {
			// ����ͳ��
			this.removeAllAndAddRequestOne(new MaterialCountJPanel());
		}
	}
}