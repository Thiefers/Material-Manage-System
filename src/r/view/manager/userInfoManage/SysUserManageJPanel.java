package r.view.manager.userInfoManage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.eltima.components.ui.DatePicker;

import r.controller.UserController;
import r.domain.User;
import r.utils.DatePickerUtil;
import r.utils.DicMapUtil;
import r.utils.JTableUtil;
import r.utils.MyListeners;

public class SysUserManageJPanel extends JPanel implements MyListeners {

	private UserController uController = new UserController();
	private JTextField loginActTxt = new JTextField(8);
	private JComboBox<String> lockStateBox = new JComboBox<String>(new String[] { "", "�˺���Ч", "�˺�����" });
	private JComboBox<String> positionBox = new JComboBox<String>(new String[] { "", "ϵͳ����Ա", "����Ա", "�ɹ�Ա", "ȡ����" });
	private JButton queryBtn = new JButton("��ѯ");
	private JButton addBtn = new JButton("���");
	private JButton delBtn = new JButton("ɾ��");
	private JButton updateBtn = new JButton("�޸�");
	private JButton resetPwdBtn = new JButton("��������");
	private JPanel panelNorth;
	private JPanel panelCenter;
	private JTable userTable;
	private JScrollPane userTableScrollPane;
	protected String[] windowLife = { "init", "dead" };
	private String windowState;
	private String loginAct;

	public String getLoginAct() {
		return loginAct;
	}

	public JButton getAddBtn() {
		return addBtn;
	}

	public JButton getUpdateBtn() {
		return updateBtn;
	}

	public void setWindowState(String windowState) {
		this.windowState = windowState;
	}

	public SysUserManageJPanel(String loginAct) {
		this.loginAct = loginAct;
		this.setLayout(new BorderLayout());

		panelNorth = new JPanel();
		JLabel loginActLabel = new JLabel("�˻�");
		panelNorth.add(loginActLabel);
		panelNorth.add(loginActTxt);

		JLabel lockStateLabel = new JLabel("״̬");
		panelNorth.add(lockStateLabel);
		panelNorth.add(lockStateBox);

		JLabel positionLabel = new JLabel("ְλ");
		panelNorth.add(positionLabel);
		panelNorth.add(positionBox);

		panelNorth.add(queryBtn);
		queryBtn.addMouseListener(this);
		panelNorth.add(addBtn);
		addBtn.addMouseListener(this);
		panelNorth.add(delBtn);
		delBtn.addMouseListener(this);
		panelNorth.add(updateBtn);
		updateBtn.addMouseListener(this);
		panelNorth.add(resetPwdBtn);
		resetPwdBtn.addMouseListener(this);

		this.add(panelNorth, BorderLayout.NORTH);

		createTable("init");
	}

	/**
	 * ���ݲ����Ա����д���/�ػ� initΪ���δ������մ���Ϊ�ػ�
	 * 
	 * @param operate
	 */
	protected void createTable(String operate) {
		if (panelCenter == null) {
			panelCenter = new JPanel();
		}

		if (userTable != null) {
			userTableScrollPane.remove(userTable);
			panelCenter.remove(userTableScrollPane);
		}

		String loginActProp = loginActTxt.getText();
		String lockStateProp = (String) lockStateBox.getSelectedItem();
		String positionProp = (String) positionBox.getSelectedItem();
		List<User> userList = uController.queryUsers(loginActProp.trim(), lockStateProp, positionProp);

		// id(����)���˻����ǳƣ�ʧЧʱ�䣬�˻�״̬�������ˣ�����ʱ�䣬ְλ��CHECK
		String[] names = { "", "�˻�", "�ǳ�", "ʧЧʱ��", "�˻�״̬", "������", "����ʱ��", "ְλ", "CHECK" };
		String[][] usersInfo = null;
		if (userList != null) {
			usersInfo = new String[userList.size()][names.length];
			for (int i = 0; i < userList.size(); i++) {
				usersInfo[i][0] = userList.get(i).getId();
				usersInfo[i][1] = userList.get(i).getLoginAct();
				usersInfo[i][2] = userList.get(i).getName();
				usersInfo[i][3] = "".equals(userList.get(i).getExpireTime()) ? "������Ч" : userList.get(i).getExpireTime();
				usersInfo[i][4] = "1".equals(userList.get(i).getLockState()) ? "�˺���Ч" : "�˺�����";
				usersInfo[i][5] = userList.get(i).getCreateBy();
				usersInfo[i][6] = userList.get(i).getCreateTime();
				usersInfo[i][7] = userList.get(i).getPosition();
			}
		}

		userTable = new JTable(new DefaultTableModel(usersInfo, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		userTable.addMouseMotionListener(this);
		
		// TODO ����id����Ӹ�ѡ��
		JTableUtil.hideColumn(userTable, 0);
		JTableUtil.addCheckbox(userTable,8);
		
		userTable.setRowHeight(25);

		userTableScrollPane = new JScrollPane(userTable);
		userTableScrollPane.setPreferredSize(new Dimension(728, 330));
		userTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelCenter.add(userTableScrollPane);

		if ("init".equals(operate)) {
			this.add(panelCenter, BorderLayout.CENTER);
		}
		this.repaint();
		this.validate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == queryBtn) {
			createTable("");
		} else if (e.getSource() == addBtn) {
			// ����û�,����Ҫѡ���У���������λ��-1����ʾ����Ҫ�к�
			if (!addBtn.isEnabled()) {
				return;
			}
			addBtn.setEnabled(false);
			new UpdateWindow(this, userTable, addBtn, -1);
		} else if (e.getSource() == delBtn) {
			int[] rows = userTable.getSelectedRows();
			if (rows.length != 0) {
				List<String> userIdList = new ArrayList<String>();
				for (int row : rows) {
					userIdList.add((String) userTable.getValueAt(row, 0));
				}
				if (JOptionPane.showConfirmDialog(this, "ȷ��ɾ����", "��ʾ", JOptionPane.YES_NO_OPTION) == 0) {// YES_OPTION
					boolean flag = uController.delUser(userIdList);
					if (flag) {
						JOptionPane.showMessageDialog(this, "�ɹ�ɾ��ָ���û�");
						createTable("");
					} else {
						JOptionPane.showMessageDialog(this, "ɾ��ʧ��,��ȷ���û���Ϣ");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "δѡ���û�");
			}
		} else if (e.getSource() == updateBtn) {
			int rowCount = userTable.getSelectedRowCount();
			if (rowCount > 1) {
				JOptionPane.showMessageDialog(this, "��֧����������");
			} else if (rowCount == 0) {
				JOptionPane.showMessageDialog(this, "δѡ�м�¼");
			} else if (rowCount == 1) {
				// ִ�и��²���
				if (!updateBtn.isEnabled()) {
					return;
				}
				int row = userTable.getSelectedRow();
				updateBtn.setEnabled(false);
				new UpdateWindow(this, userTable, updateBtn, row);
			}
		} else if (e.getSource() == resetPwdBtn) {
			int rowCount = userTable.getSelectedRowCount();
			if (rowCount > 1 && JOptionPane.showConfirmDialog(this, "ȷ���������ã�", "��ʾ", JOptionPane.YES_NO_OPTION) == 0) {
				List<String> resetIdList = new ArrayList<String>();
				int[] rows = userTable.getSelectedRows();
				for (int row : rows) {
					resetIdList.add((String) userTable.getValueAt(row, 0));
				}
				boolean flag = uController.resetPwdByIdList(resetIdList);
				if (flag) {
					JOptionPane.showMessageDialog(this, "���óɹ�");
				} else {
					JOptionPane.showMessageDialog(this, "����ʧ��");
				}
			} else if (rowCount == 1
					&& JOptionPane.showConfirmDialog(this, "ȷ�����ã�", "��ʾ", JOptionPane.YES_NO_OPTION) == 0) {
				int row = userTable.getSelectedRow();
				String resetId = (String) userTable.getValueAt(row, 0);
				boolean flag = uController.resetPwdBySingleId(resetId);
				if (flag) {
					JOptionPane.showMessageDialog(this, "���óɹ�");
				} else {
					JOptionPane.showMessageDialog(this, "����ʧ��");
				}
			} else if (rowCount == 0) {
				JOptionPane.showMessageDialog(this, "δѡ�м�¼");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int row = userTable.rowAtPoint(e.getPoint());
		int col = userTable.columnAtPoint(e.getPoint());
		if (row > -1 && col > -1) {
			Object value = userTable.getValueAt(row, col);
			if (value != null && !"".equals(value)) {
				userTable.setToolTipText(value.toString());
			} else {
				userTable.setToolTipText(null);
			}
		}
	}
}

// �޸�/����ô���
class UpdateWindow implements MyListeners {

	private UserController uController = new UserController();
	private SysUserManageJPanel sysUserManageJPanel;
	private JFrame frame;
	private JLabel idTxt; // ����id����Ҫ��Ϣ
	private DatePicker datepick;
	private JRadioButton openState; // ����
	private JRadioButton closeState; // ����
	private JComboBox<String> positionBox = new JComboBox<String>(new String[] { "ϵͳ����Ա", "����Ա", "�ɹ�Ա", "ȡ����" });
	private JButton submitUpdateBtn;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private JTextField loginActTxt;
	private JTextField nameTxt;
	private JPasswordField loginPwdTxt;
	private JButton submitInsertBtn;

	public UpdateWindow(SysUserManageJPanel sysUserManageJPanel, JTable userTable, JButton btn, int row) {
		this.sysUserManageJPanel = sysUserManageJPanel;
		this.sysUserManageJPanel.setWindowState(this.sysUserManageJPanel.windowLife[0]);
		frame = new JFrame();
		if (this.sysUserManageJPanel.getUpdateBtn() == btn) {
			// ��ʼ�����´���
			initUpdate(userTable, row);
			frame.setTitle("�û���" + (String) userTable.getValueAt(row, 1)); // �û���
		} else if (this.sysUserManageJPanel.getAddBtn() == btn) {
			// ��ʼ����Ӵ���
			initInsert(userTable);
			frame.setTitle("������û�");
		}
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// ʹ��dispose�ر�ʱ���ã�֤���Ǵӵ���ύ��ť�������Ͽ��޸�/���
				sysUserManageJPanel.createTable("");
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// ����������Ͻǹر�ʱʹ�ã���ʱ�����޸�/���
				sysUserManageJPanel.getUpdateBtn().setEnabled(true);
				sysUserManageJPanel.getAddBtn().setEnabled(true);
			}
		});
	}

	private void initInsert(JTable userTable) {
		JPanel container = (JPanel) frame.getContentPane();
		JPanel infoPanel = new JPanel();

		infoPanel.setLayout(null);

		JLabel loginActLab = new JLabel("�û��˺ţ�");
		loginActLab.setBounds(80, 40, 65, 20);
		infoPanel.add(loginActLab);
		loginActTxt = new JTextField();
		loginActTxt.setBounds(loginActLab.getX() + loginActLab.getWidth() + 10, loginActLab.getY(), 160, 25);
		infoPanel.add(loginActTxt);

		JLabel nameLab = new JLabel("�û��ǳƣ�");
		nameLab.setBounds(loginActLab.getX(), loginActLab.getY() + loginActLab.getHeight() + 20, 65, 20);
		infoPanel.add(nameLab);
		nameTxt = new JTextField();
		nameTxt.setBounds(nameLab.getX() + nameLab.getWidth() + 10, nameLab.getY(), 160, 25);
		infoPanel.add(nameTxt);

		JLabel loginPwdLab = new JLabel("�û����룺");
		loginPwdLab.setBounds(nameLab.getX(), nameLab.getY() + nameLab.getHeight() + 20, 65, 20);
		infoPanel.add(loginPwdLab);
		loginPwdTxt = new JPasswordField();
		loginPwdTxt.setBounds(loginPwdLab.getX() + loginPwdLab.getWidth() + 10, loginPwdLab.getY(), 160, 25);
		infoPanel.add(loginPwdTxt);

		JLabel expireTimeLab = new JLabel("ʧЧʱ�䣺");
		expireTimeLab.setBounds(loginPwdLab.getX(), loginPwdLab.getY() + loginPwdLab.getHeight() + 20, 65, 20);
		infoPanel.add(expireTimeLab);
		datepick = DatePickerUtil.getDatePicker(sdf.format(new Date()));
		datepick.setLocation(expireTimeLab.getX() + expireTimeLab.getWidth() + 10, expireTimeLab.getY());
		infoPanel.add(datepick);

		JLabel lockStateLab = new JLabel("����״̬��");
		lockStateLab.setBounds(expireTimeLab.getX(), expireTimeLab.getY() + expireTimeLab.getHeight() + 20, 65, 20);
		infoPanel.add(lockStateLab);
		closeState = new JRadioButton("�˺�����");
		openState = new JRadioButton("�˺���Ч");
		ButtonGroup group = new ButtonGroup();
		group.add(closeState);
		group.add(openState);
		openState.setSelected(true);
		closeState.setBounds(lockStateLab.getX() + lockStateLab.getWidth() + 10, lockStateLab.getY(), 80, 20);
		infoPanel.add(closeState);
		openState.setBounds(closeState.getX() + closeState.getWidth() + 10, lockStateLab.getY(), 80, 20);
		infoPanel.add(openState);

		JLabel positionLab = new JLabel("�û�ְλ��");
		positionLab.setBounds(lockStateLab.getX(), lockStateLab.getY() + lockStateLab.getHeight() + 20, 65, 20);
		infoPanel.add(positionLab);
		positionBox.setSelectedIndex(0);
		positionBox.setBounds(positionLab.getX() + positionLab.getWidth() + 10, positionLab.getY(), 100, 20);
		infoPanel.add(positionBox);

		submitInsertBtn = new JButton("���");
		submitInsertBtn.addMouseListener(this);
		submitInsertBtn.setBounds(positionBox.getX(), positionBox.getY() + positionBox.getHeight() + 20, 110, 25);
		infoPanel.add(submitInsertBtn);

		container.add(infoPanel);
	}

	private void initUpdate(JTable userTable, int row) {
		JPanel container = (JPanel) frame.getContentPane();
		JPanel infoPanel = new JPanel();

		String userId = (String) userTable.getValueAt(row, 0);
		idTxt = new JLabel(userId);
		idTxt.setVisible(false);

		infoPanel.setLayout(null);

		JLabel loginActLab = new JLabel("�û��˺ţ�");
		loginActLab.setBounds(80, 40, 65, 20);
		infoPanel.add(loginActLab);
		JLabel loginActTxt = new JLabel((String) userTable.getValueAt(row, 1));
		loginActTxt.setBounds(loginActLab.getX() + loginActLab.getWidth() + 10, loginActLab.getY(), 100, 25);
		infoPanel.add(loginActTxt);

		JLabel nameLab = new JLabel("�û��ǳƣ�");
		nameLab.setBounds(loginActLab.getX(), loginActLab.getY() + loginActLab.getHeight() + 20, 65, 20);
		infoPanel.add(nameLab);
		JLabel nameTxt = new JLabel((String) userTable.getValueAt(row, 2));
		nameTxt.setBounds(nameLab.getX() + nameLab.getWidth() + 10, nameLab.getY(), 160, 25);
		infoPanel.add(nameTxt);

		JLabel expireTimeLab = new JLabel("ʧЧʱ�䣺");
		expireTimeLab.setBounds(nameLab.getX(), nameLab.getY() + nameLab.getHeight() + 20, 65, 20);
		infoPanel.add(expireTimeLab);
		JLabel expireTimeTxt = new JLabel((String) userTable.getValueAt(row, 3));
		datepick = DatePickerUtil.getDatePicker(expireTimeTxt.getText());
		datepick.setLocation(expireTimeLab.getX() + expireTimeLab.getWidth() + 10, expireTimeLab.getY());
		infoPanel.add(datepick);

		JLabel lockStateLab = new JLabel("����״̬��");
		lockStateLab.setBounds(expireTimeLab.getX(), expireTimeLab.getY() + expireTimeLab.getHeight() + 20, 65, 20);
		infoPanel.add(lockStateLab);
		String stateSource = (String) userTable.getValueAt(row, 4);
		closeState = new JRadioButton("�˺�����");
		openState = new JRadioButton("�˺���Ч");
		ButtonGroup group = new ButtonGroup();
		group.add(closeState);
		group.add(openState);
		if ("�˺�����".equals(stateSource)) {
			closeState.setSelected(true);
			openState.setSelected(false);
		} else if ("�˺���Ч".equals(stateSource)) {
			openState.setSelected(true);
			closeState.setSelected(false);
		}
		closeState.setBounds(lockStateLab.getX() + lockStateLab.getWidth() + 10, lockStateLab.getY(), 80, 20);
		infoPanel.add(closeState);
		openState.setBounds(closeState.getX() + closeState.getWidth() + 10, lockStateLab.getY(), 80, 20);
		infoPanel.add(openState);

		JLabel positionLab = new JLabel("�û�ְλ��");
		positionLab.setBounds(lockStateLab.getX(), lockStateLab.getY() + lockStateLab.getHeight() + 20, 65, 20);
		infoPanel.add(positionLab);
		positionBox.setSelectedItem(userTable.getValueAt(row, 7));
		positionBox.setBounds(positionLab.getX() + positionLab.getWidth() + 10, positionLab.getY(), 100, 20);
		infoPanel.add(positionBox);

		submitUpdateBtn = new JButton("�ύ");
		submitUpdateBtn.addMouseListener(this);
		submitUpdateBtn.setBounds(positionBox.getX(), positionBox.getY() + positionBox.getHeight() + 20, 110, 25);
		infoPanel.add(submitUpdateBtn);

		container.add(infoPanel);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == submitUpdateBtn) {
			boolean flag = updateUserInfo();
			if (flag) {
				sysUserManageJPanel.setWindowState(sysUserManageJPanel.windowLife[1]);
				sysUserManageJPanel.getUpdateBtn().setEnabled(true);
				frame.dispose();
			}
		} else if (e.getSource() == submitInsertBtn) {
			boolean flag = addUserInfo();
			if (flag) {
				JOptionPane.showMessageDialog(frame, "��ӳɹ�");
				sysUserManageJPanel.setWindowState(sysUserManageJPanel.windowLife[1]);
				sysUserManageJPanel.getAddBtn().setEnabled(true);
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, "���ʧ�ܣ�����¼����Ϣ�Ƿ���ȷ");
			}
		}
	}

	// ����û�����
	private boolean addUserInfo() {
		// �ص��ǻ�ȡ������
		String loginActProp = loginActTxt.getText();
		String nameProp = nameTxt.getText();
		String loginPwdProp = new String(loginPwdTxt.getPassword());
		String expireTimeProp = sdf.format(datepick.getValue());
		String lockStateProp = openState.isSelected() ? DicMapUtil.dicMap.get(openState.getText())
				: DicMapUtil.dicMap.get(closeState.getText());
		String positionProp = (String) positionBox.getSelectedItem();
		return uController.addUserInfo(loginActProp, nameProp, loginPwdProp, expireTimeProp, lockStateProp,
				positionProp, this.sysUserManageJPanel.getLoginAct());
	}

	// �޸��û�����
	private boolean updateUserInfo() {
		// TODO ���ʹ�䡰������Ч���Ǹ�������
		String stateChange = "";
		if (closeState.isSelected()) {
			stateChange = DicMapUtil.dicMap.get(closeState.getText());
		} else if (openState.isSelected()) {
			stateChange = DicMapUtil.dicMap.get(openState.getText());
		}
		String position = (String) positionBox.getSelectedItem();
		String expireTime = sdf.format(datepick.getValue());
		String id = (String) idTxt.getText();
		// System.out.println(
		// "position��" + position + "��expireTime��" + expireTime + "��lockState��" +
		// stateChange + "��id��" + id);
		// return false;
		return uController.updateUserInfo(expireTime, stateChange, position, id);
	}

}