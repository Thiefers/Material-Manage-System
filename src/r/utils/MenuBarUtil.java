package r.utils;

import java.awt.GridLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import r.view.manager.global.ManagerJPanel;

public class MenuBarUtil {

	public static JPanel getMenu(ManagerJPanel managerJPanel, String userPosition) {
		return createMenuBar(managerJPanel, userPosition);
	}

	private static JPanel createMenuBar(ManagerJPanel managerJPanel, String userPosition) {
		JPanel menuBarJPanel = new JPanel();
		menuBarJPanel.setLayout(new GridLayout(1, 3));
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createUserInfoMenu(managerJPanel, userPosition));
		menuBar.add(createWarehouseManageMenuForSysManager(managerJPanel));
		menuBar.add(createMaterialManageMenu(managerJPanel, userPosition));
		// 根据职位选取显示的一级菜单
		if (!"系统管理员".equals(userPosition)) {
			managerJPanel.getWarehouseMenu().setVisible(false);
		}
		if ("系统管理员".equals(userPosition)) {
			managerJPanel.getMaterialMenu().setVisible(false);
		}

		menuBarJPanel.add(menuBar);
		return menuBarJPanel;
	}

	private static JMenu createMaterialManageMenu(ManagerJPanel mj, String userPosition) {
		/*
		 * addMaterial，入库管理，采购员填写入库单，更新单据，库存（新物资则加更新物资表）
		 * delMaterial，出库管理，取料人填写出库单，更新单据，库存
		 * materialManage，查询物资(联合仓库，库存显示)(根据名称，出入库日期均可，模糊查询吧)，删除物资，管理员
		 * materialCount，物资数量统计(按年、月、日，对出入库)
		 */
		mj.setMaterialMenu(new JMenu("物资管理"));
		mj.setAddMaterial(new JMenuItem("入库管理"));
		mj.getAddMaterial().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getAddMaterial());
		if (!"采购员".equals(userPosition)) {
			mj.getAddMaterial().setVisible(false);
		}

		mj.setDelMaterial(new JMenuItem("出库管理"));
		mj.getDelMaterial().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getDelMaterial());
		if (!"取料人".equals(userPosition)) {
			mj.getDelMaterial().setVisible(false);
		}

		mj.setMaterialManage(new JMenuItem("物资管理"));
		mj.getMaterialManage().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getMaterialManage());

		mj.setMaterialCount(new JMenuItem("物资统计"));
		mj.getMaterialCount().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getMaterialCount());
		if (!"管理员".equals(userPosition)) {
			mj.getMaterialManage().setVisible(false);
			mj.getMaterialCount().setVisible(false);
		}

		return mj.getMaterialMenu();
	}

	private static JMenu createWarehouseManageMenuForSysManager(ManagerJPanel mj) {
		mj.setWarehouseMenu(new JMenu("仓库管理"));
		mj.setAddWarehouse(new JMenuItem("添加仓库"));
		mj.getAddWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getAddWarehouse());

		mj.setDelWarehouse(new JMenuItem("删除仓库"));
		mj.getDelWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getDelWarehouse());

		mj.setEditWarehouse(new JMenuItem("修改仓库状态"));
		mj.getEditWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getEditWarehouse());

		mj.setQueryWarehouse(new JMenuItem("查看仓库信息"));
		mj.getQueryWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getQueryWarehouse());
		return mj.getWarehouseMenu();
	}

	private static JMenu createUserInfoMenu(ManagerJPanel mj, String userPosition) {
		mj.setUserInfoMenu(new JMenu("用户信息管理"));
		mj.setShowUserInfo(new JMenuItem("查看个人信息"));
		mj.getShowUserInfo().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getShowUserInfo());

		mj.setEditUserSelfInfo(new JMenuItem("编辑个人信息"));
		mj.getEditUserSelfInfo().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getEditUserSelfInfo());

		/************** 系统管理员 **************/
		mj.setSysUserManage(new JMenuItem("系统用户管理"));
		mj.getSysUserManage().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getSysUserManage());
		if (!"系统管理员".equals(userPosition)) {
			mj.getSysUserManage().setVisible(false);
		}
		/************** 系统管理员 **************/
		mj.getUserInfoMenu().addSeparator();

		mj.setLogOut(new JMenuItem("账号登出"));
		mj.getLogOut().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getLogOut());

		return mj.getUserInfoMenu();
	}

}