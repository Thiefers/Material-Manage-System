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
	 * addMaterial，入库管理，采购员填写入库单，更新单据，库存（新物资则加更新物资表）
	 * delMaterial，出库管理，取料人填写出库单，更新单据，库存
	 * materialManage，查询物资(联合仓库，库存显示)(根据名称，出入库日期均可，模糊查询吧)，删除物资，管理员
	 * materialCount，物资数量统计(按年、月、日，对出入库)
	 */
	private JMenuItem addMaterial, delMaterial, materialManage, materialCount;
	private JPanel mainJPanel;
	private CardLayout cardLayout;
	private JPanel menuBarJPanel; // 菜单栏底层面板
	private JPanel contentJPanel; // 各个面板的底层面板，自主选择布局
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
		// 菜单栏
		menuBarJPanel = MenuBarUtil.getMenu(this, userPosition);
		this.add(menuBarJPanel, BorderLayout.NORTH);
		// 内容面板
		contentJPanel = new JPanel();

		showUserInfoJPanel = new ShowUserInfoJPanel(this, loginAct);
		contentJPanel.add(showUserInfoJPanel);

		this.add(contentJPanel, BorderLayout.CENTER);
	}

	/**
	 * 移除指定组件并重新添加指定组件
	 * 
	 * @param removeComp
	 *            指定移除的组件
	 * @param addComp
	 *            指定添加的组件
	 */
	public void removeAndAddRequestOne(Component removeComp, Component addComp) {
		contentJPanel.remove(removeComp);
		contentJPanel.add(addComp);
		contentJPanel.repaint();
		contentJPanel.validate();
	}

	/**
	 * 移除全部组件并重新添加指定组件
	 * 
	 * @param addComp
	 *            指定添加的组件
	 */
	public void removeAllAndAddRequestOne(Component addComp) {
		contentJPanel.removeAll();
		contentJPanel.add(addComp);
		contentJPanel.repaint();
		contentJPanel.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 按钮点击事件
		if (e.getSource() == showUserInfo) {
			// 个人信息面板
			this.removeAllAndAddRequestOne(new ShowUserInfoJPanel(this, loginAct));
		} else if (e.getSource() == editUserSelfInfo) {
			// 修改个人信息面板
			this.removeAllAndAddRequestOne(new EditUserInfoJPanel(this, loginAct));
		} else if (e.getSource() == logOut) {
			// 账号登出，退出主界面
			int check = JOptionPane.showConfirmDialog(this, "确认登出？", "提示", JOptionPane.YES_NO_OPTION);
			if (check == JOptionPane.YES_OPTION) {
				this.cardLayout.show(mainJPanel, "loginJPanel");
			}
		} else if (e.getSource() == addWarehouse) {
			// 添加仓库
			this.removeAllAndAddRequestOne(new AddWarehouseJPanel());
		} else if (e.getSource() == delWarehouse) {
			// 删除仓库
			this.removeAllAndAddRequestOne(new DelWarehouseJPanel());
		} else if (e.getSource() == editWarehouse) {
			// 修改仓库
			this.removeAllAndAddRequestOne(new UpdateWarehouseJPanel());
		} else if (e.getSource() == queryWarehouse) {
			// 查看仓库
			this.removeAllAndAddRequestOne(new QueryWarehouseJPanel(this));
		} else if (e.getSource() == sysUserManage) {
			// 系统用户管理
			this.removeAllAndAddRequestOne(new SysUserManageJPanel(loginAct));
		} else if (e.getSource() == addMaterial) {
			// 物资入库
			this.removeAllAndAddRequestOne(new AddMaterialJPanel(loginAct));
		} else if (e.getSource() == delMaterial) {
			// 物资出库
			this.removeAllAndAddRequestOne(new DelMaterialJPanel(loginAct));
		} else if (e.getSource() == materialManage) {
			// 物资管理(查询物资，删除物资)
			this.removeAllAndAddRequestOne(new MaterialManageJPanel());
		} else if (e.getSource() == materialCount) {
			// 物资统计
			this.removeAllAndAddRequestOne(new MaterialCountJPanel());
		}
	}
}