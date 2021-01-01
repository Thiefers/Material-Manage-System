package r.view.manager.warehouseManage;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import r.controller.WarehouseController;
import r.domain.Warehouse;
import r.utils.JTableUtil;
import r.utils.MyListeners;

public class UpdateWarehouseJPanel extends JPanel implements MyListeners {

	private JPanel panelNorth;
	private JTextField nameTxt;
	private JTextField addrTxt;
	private JComboBox<String> jComboBox;
	private JTextField nameText;
	private JTextField addrText;
	private JComboBox<String> comboBox;
	private JButton queryBtn;
	private JButton editBtn;
	private JPanel panelCenter;
	private JTable warehouseTable;
	private JScrollPane warehouseScrollPane;
	protected String[] editWindowLife = { "init", "alive", "dead" };
	private String editWindowAlive;

	public JButton getEditBtn() {
		return editBtn;
	}

	public void setEditWindowAlive(String editWindowLife) {
		this.editWindowAlive = editWindowLife;
	}

	public String getEditWindowAlive() {
		return editWindowAlive;
	}

	public UpdateWarehouseJPanel() {
		this.setLayout(new BorderLayout());

		panelNorth = new JPanel();
		JLabel nameLabel = new JLabel("仓库名称");
		panelNorth.add(nameLabel);
		nameText = new JTextField(10);
		panelNorth.add(nameText);

		JLabel addrLabel = new JLabel("仓库地址");
		panelNorth.add(addrLabel);
		addrText = new JTextField(10);
		panelNorth.add(addrText);

		JLabel stateLabel = new JLabel("仓库状态");
		panelNorth.add(stateLabel);
		String[] listData = new String[] { "开仓&闭仓", "开仓", "闭仓" };
		comboBox = new JComboBox<String>(listData);
		panelNorth.add(comboBox);

		queryBtn = new JButton("查询");
		queryBtn.addMouseListener(this);
		panelNorth.add(queryBtn);

		editBtn = new JButton("修改选中仓库状态");
		editBtn.addMouseListener(this);
		panelNorth.add(editBtn);
		this.add(panelNorth, BorderLayout.NORTH);

		createQueryTable("first");

	}

	protected void createQueryTable(String operate) {
		if (panelCenter == null) {
			panelCenter = new JPanel();
		}
		if (warehouseTable != null) {
			warehouseScrollPane.remove(warehouseTable);
			panelCenter.remove(warehouseScrollPane);
		}
		String nameProp = nameText.getText();
		String addrProp = addrText.getText();
		String stateProp = (String) comboBox.getSelectedItem();

		WarehouseController warehouseController = new WarehouseController();
		// 若不为空，则拿到了查询的仓库集
		List<Warehouse> warehouses = warehouseController.queryWarehouse(nameProp, addrProp, stateProp);
		// 列名数组
		String[] names = { "仓库名称", "仓库地址", "仓库状态", "" };
		// 列值数组
		String[][] warehouseInfoStr = new String[warehouses.size()][4];

		for (int i = 0; i < warehouses.size(); i++) {
			warehouseInfoStr[i][0] = warehouses.get(i).getName();
			warehouseInfoStr[i][1] = warehouses.get(i).getAddr();
			if ("0".equals(warehouses.get(i).getState())) {
				warehouseInfoStr[i][2] = "闭仓";
			} else {
				warehouseInfoStr[i][2] = "开仓";
			}
			warehouseInfoStr[i][3] = warehouses.get(i).getId();
		}
		warehouseTable = new JTable(new DefaultTableModel(warehouseInfoStr, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		warehouseTable.addMouseMotionListener(this);
		
		JTableUtil.hideColumn(warehouseTable, 3);

		warehouseTable.setRowHeight(25);
		// changeToCombobox(warehouseTable.getRowCount());
		// warehouseTable.getModel().addTableModelListener(this);

		warehouseScrollPane = new JScrollPane(warehouseTable);
		warehouseScrollPane.setPreferredSize(new Dimension(400, 300));
		warehouseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelCenter.add(warehouseScrollPane);

		if ("first".equals(operate)) {
			this.add(panelCenter, BorderLayout.CENTER);
		}
		this.repaint();
		this.validate();
	}

	// private void changeToCombobox(int count) {
	// // TODO 自动生成的方法存根，不要了！！！！！！！！！！！！
	// String state = "";
	// for (int i = 0; i < count; i++) {
	// state = (String) warehouseTable.getValueAt(i, 2);
	// JComboBox<String> stateBox = new JComboBox<String>(new String[] { "闭仓", "开仓"
	// });
	// if ("闭仓".equals(state)) {
	// stateBox.setSelectedIndex(0);
	// } else if ("开仓".equals(state)) {
	// stateBox.setSelectedIndex(1);
	// }
	// // stateBox.addItemListener(this);
	// warehouseTable.getColumnModel().getColumn(2).setCellEditor(new
	// DefaultCellEditor(stateBox));
	// }
	// }

	// @Override
	// public void tableChanged(TableModelEvent e) {
	// // TODO 自动生成的方法存根
	//// MyListeners.super.tableChanged(e);
	// // 离开或修改后就执行
	// System.out.println(e.getFirstRow());
	// System.out.println(e.getColumn());
	// System.out.println(e.getSource());
	// System.out.println("---------------");
	// System.out.println(warehouseTable.getSelectedRow());
	// System.out.println(warehouseTable.getValueAt(e.getFirstRow(),
	// e.getColumn()));
	// System.out.println("***************");
	// }

	// @Override
	// public void itemStateChanged(ItemEvent e) {
	// // TODO 自动生成的方法存根
	// if (e.getStateChange() == ItemEvent.SELECTED &&
	// warehouseTable.getSelectedRow() != -1) {
	//// System.out.println(warehouseTable.getValueAt(warehouseTable.getSelectedRow(),
	// 3));
	//// System.out.println("-----------1----------");
	// System.out.println(warehouseTable.getValueAt(warehouseTable.getSelectedRow(),
	// 3));
	// JOptionPane.showConfirmDialog(this, "确认修改?");
	// System.out.println(e.getStateChange());
	// }
	// }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == queryBtn) {
			createQueryTable("");
		} else if (e.getSource() == editBtn) {
			if (!editBtn.isEnabled()) {
				return;
			}
			int rowCount = warehouseTable.getSelectedRowCount();
			if (rowCount > 1) {
				JOptionPane.showMessageDialog(this, "不支持批量更新");
			} else if (rowCount == 0) {
				JOptionPane.showMessageDialog(this, "未选中记录");
			} else if (rowCount == 1) {
				int row = warehouseTable.getSelectedRow();
				editBtn.setEnabled(false);
				// TODO a little error
				// new Thread(new EditListener(this)).start();
				new EditWindow(this, warehouseTable, row);
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		int row = warehouseTable.rowAtPoint(e.getPoint());
		int col = warehouseTable.columnAtPoint(e.getPoint());
		if (row > -1 && col > -1) {
			Object value = warehouseTable.getValueAt(row, col);
			if (value != null && !"".equals(value)) {
				warehouseTable.setToolTipText(value.toString());
			} else {
				warehouseTable.setToolTipText(null);
			}
		}
	}

}

class EditWindow implements MyListeners {

	private WarehouseController controller = new WarehouseController();
	private UpdateWarehouseJPanel updateWarehouseJPanel;
	private JFrame frame;
	private JLabel idLab;
	private JRadioButton openState;
	private JRadioButton closeState;
	private JButton submitBtn;

	public EditWindow(UpdateWarehouseJPanel updateWarehouseJPanel, JTable warehouseTable, int row) {
		frame = new JFrame();
		this.updateWarehouseJPanel = updateWarehouseJPanel;
		init(warehouseTable, row);
		frame.setTitle((String) warehouseTable.getValueAt(row, 0));
		frame.setSize(400, 200);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// 使用dispose关闭时调用，证明是从点击提交按钮过来，认可修改
				updateWarehouseJPanel.createQueryTable("");
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// 点击窗体右上角关闭时使用，此时对仓库状态做出的修改不予认同，不做修改
				updateWarehouseJPanel.getEditBtn().setEnabled(true);
			}
		});
	}

	private void init(JTable warehouseTable, int row) {
		JPanel container = (JPanel) frame.getContentPane();
		JPanel infoPanel = new JPanel();

		String warehouseId = (String) warehouseTable.getValueAt(row, 3);
		idLab = new JLabel(warehouseId);
		idLab.setVisible(false);

		infoPanel.setLayout(null);

		JLabel nameLab = new JLabel("名称：");
		nameLab.setBounds(100, 10, 40, 20);
		infoPanel.add(nameLab);
		JLabel nameTxt = new JLabel((String) warehouseTable.getValueAt(row, 0));
		nameTxt.setBounds(nameLab.getX() + nameLab.getWidth() + 10, nameLab.getY(), 100, 25);
		infoPanel.add(nameTxt);

		JLabel addrLab = new JLabel("地址：");
		addrLab.setBounds(nameLab.getX(), nameLab.getY() + nameLab.getHeight() + 10, 40, 20);
		infoPanel.add(addrLab);
		JLabel addrTxt = new JLabel((String) warehouseTable.getValueAt(row, 1));
		addrTxt.setBounds(addrLab.getX() + addrLab.getWidth() + 10, addrLab.getY(), 160, 25);
		infoPanel.add(addrTxt);

		JLabel stateLab = new JLabel("状态：");
		stateLab.setBounds(addrLab.getX(), addrLab.getY() + addrLab.getHeight() + 10, 40, 20);
		infoPanel.add(stateLab);
		String stateSource = (String) warehouseTable.getValueAt(row, 2);
		closeState = new JRadioButton("闭仓");
		openState = new JRadioButton("开仓");
		ButtonGroup group = new ButtonGroup();
		group.add(closeState);
		group.add(openState);

		closeState.setBounds(stateLab.getX() + stateLab.getWidth() + 10, stateLab.getY(), 60, 20);
		infoPanel.add(closeState);
		openState.setBounds(closeState.getX() + closeState.getWidth() + 10, stateLab.getY(), 60, 20);
		infoPanel.add(openState);

		if ("闭仓".equals(stateSource)) {
			closeState.setSelected(true);
			openState.setSelected(false);
		} else if ("开仓".equals(stateSource)) {
			openState.setSelected(true);
			closeState.setSelected(false);
		}

		submitBtn = new JButton("提交");
		submitBtn.addMouseListener(this);
		submitBtn.setBounds(closeState.getX(), closeState.getY() + closeState.getHeight() + 10, 120, 30);
		infoPanel.add(submitBtn);

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
		if (e.getSource() == submitBtn) {
			boolean flag = updateWarehouseState();
			if (flag) {
				updateWarehouseJPanel.setEditWindowAlive(updateWarehouseJPanel.editWindowLife[2]);
				updateWarehouseJPanel.getEditBtn().setEnabled(true);
				frame.dispose();
			}
		}
	}

	private boolean updateWarehouseState() {
		String stateChange = "";
		if (closeState.isSelected()) {
			stateChange = closeState.getText();
		} else if (openState.isSelected()) {
			stateChange = openState.getText();
		}
		System.out.println(stateChange);
		return controller.updateWarehouseState(idLab.getText(), stateChange) == 1;
	}
}

// class EditListener implements Runnable {
//
// private UpdateWarehouseJPanel updateWarehouseJPanel;
//
// public EditListener(UpdateWarehouseJPanel updateWarehouseJPanel) {
// this.updateWarehouseJPanel = updateWarehouseJPanel;
// }
//
// @Override
// public void run() {
// while(true) {
//// try {
//// Thread.sleep(100);
//// } catch (InterruptedException e) {
//// e.printStackTrace();
//// }
// if ("dead".equals(updateWarehouseJPanel.getEditWindowAlive())) {
// updateWarehouseJPanel.createQueryTable("first");
// System.out.println("asd");
// break;
// }
// }
// }
// }