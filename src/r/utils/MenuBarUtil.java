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
		// ����ְλѡȡ��ʾ��һ���˵�
		if (!"ϵͳ����Ա".equals(userPosition)) {
			managerJPanel.getWarehouseMenu().setVisible(false);
		}
		if ("ϵͳ����Ա".equals(userPosition)) {
			managerJPanel.getMaterialMenu().setVisible(false);
		}

		menuBarJPanel.add(menuBar);
		return menuBarJPanel;
	}

	private static JMenu createMaterialManageMenu(ManagerJPanel mj, String userPosition) {
		/*
		 * addMaterial���������ɹ�Ա��д��ⵥ�����µ��ݣ���棨��������Ӹ������ʱ�
		 * delMaterial���������ȡ������д���ⵥ�����µ��ݣ����
		 * materialManage����ѯ����(���ϲֿ⣬�����ʾ)(�������ƣ���������ھ��ɣ�ģ����ѯ��)��ɾ�����ʣ�����Ա
		 * materialCount����������ͳ��(���ꡢ�¡��գ��Գ����)
		 */
		mj.setMaterialMenu(new JMenu("���ʹ���"));
		mj.setAddMaterial(new JMenuItem("������"));
		mj.getAddMaterial().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getAddMaterial());
		if (!"�ɹ�Ա".equals(userPosition)) {
			mj.getAddMaterial().setVisible(false);
		}

		mj.setDelMaterial(new JMenuItem("�������"));
		mj.getDelMaterial().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getDelMaterial());
		if (!"ȡ����".equals(userPosition)) {
			mj.getDelMaterial().setVisible(false);
		}

		mj.setMaterialManage(new JMenuItem("���ʹ���"));
		mj.getMaterialManage().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getMaterialManage());

		mj.setMaterialCount(new JMenuItem("����ͳ��"));
		mj.getMaterialCount().addActionListener(mj);
		mj.getMaterialMenu().add(mj.getMaterialCount());
		if (!"����Ա".equals(userPosition)) {
			mj.getMaterialManage().setVisible(false);
			mj.getMaterialCount().setVisible(false);
		}

		return mj.getMaterialMenu();
	}

	private static JMenu createWarehouseManageMenuForSysManager(ManagerJPanel mj) {
		mj.setWarehouseMenu(new JMenu("�ֿ����"));
		mj.setAddWarehouse(new JMenuItem("��Ӳֿ�"));
		mj.getAddWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getAddWarehouse());

		mj.setDelWarehouse(new JMenuItem("ɾ���ֿ�"));
		mj.getDelWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getDelWarehouse());

		mj.setEditWarehouse(new JMenuItem("�޸Ĳֿ�״̬"));
		mj.getEditWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getEditWarehouse());

		mj.setQueryWarehouse(new JMenuItem("�鿴�ֿ���Ϣ"));
		mj.getQueryWarehouse().addActionListener(mj);
		mj.getWarehouseMenu().add(mj.getQueryWarehouse());
		return mj.getWarehouseMenu();
	}

	private static JMenu createUserInfoMenu(ManagerJPanel mj, String userPosition) {
		mj.setUserInfoMenu(new JMenu("�û���Ϣ����"));
		mj.setShowUserInfo(new JMenuItem("�鿴������Ϣ"));
		mj.getShowUserInfo().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getShowUserInfo());

		mj.setEditUserSelfInfo(new JMenuItem("�༭������Ϣ"));
		mj.getEditUserSelfInfo().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getEditUserSelfInfo());

		/************** ϵͳ����Ա **************/
		mj.setSysUserManage(new JMenuItem("ϵͳ�û�����"));
		mj.getSysUserManage().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getSysUserManage());
		if (!"ϵͳ����Ա".equals(userPosition)) {
			mj.getSysUserManage().setVisible(false);
		}
		/************** ϵͳ����Ա **************/
		mj.getUserInfoMenu().addSeparator();

		mj.setLogOut(new JMenuItem("�˺ŵǳ�"));
		mj.getLogOut().addActionListener(mj);
		mj.getUserInfoMenu().add(mj.getLogOut());

		return mj.getUserInfoMenu();
	}

}