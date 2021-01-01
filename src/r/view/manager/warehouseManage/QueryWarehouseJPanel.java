package r.view.manager.warehouseManage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import r.controller.WarehouseController;
import r.domain.Warehouse;
import r.utils.MyListeners;
import r.view.manager.global.ManagerJPanel;

public class QueryWarehouseJPanel extends JPanel implements MyListeners {

	private ManagerJPanel managerJPanel;
	private JTextField nameText;
	private JTextField addrText;
	private JComboBox<String> comboBox;
	private JButton queryBtn;
	private JTable warehouseTable;
	private JScrollPane warehouseScrollPane;
	private JButton addBtn;
	private JButton delBtn;
	private JButton editBtn;
	private JPanel panelNorth;
	private JPanel panelCenter;
	private JPanel panelSouth;

	public QueryWarehouseJPanel() {
	}

	public QueryWarehouseJPanel(ManagerJPanel managerJPanel) {
		this.managerJPanel = managerJPanel;
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
		this.add(panelNorth, BorderLayout.NORTH);

		createQueryTable("first");

		panelSouth = new JPanel();
		addBtn = new JButton("添加仓库信息");
		addBtn.addMouseListener(this);
		panelSouth.add(addBtn);

		delBtn = new JButton("删除仓库信息");
		delBtn.addMouseListener(this);
		panelSouth.add(delBtn);

		editBtn = new JButton("修改仓库状态");
		editBtn.addMouseListener(this);
		panelSouth.add(editBtn);
		this.add(panelSouth, BorderLayout.SOUTH);
	}

	private void createQueryTable(String operate) {
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
		String[] names = { "仓库名称", "仓库地址", "仓库状态" };
		// 列值数组
		String[][] warehouseInfoStr = new String[warehouses.size()][names.length];
		for (int i = 0; i < warehouses.size(); i++) {
			warehouseInfoStr[i][0] = warehouses.get(i).getName();
			warehouseInfoStr[i][1] = warehouses.get(i).getAddr();
			if ("0".equals(warehouses.get(i).getState())) {
				warehouseInfoStr[i][2] = "闭仓";
			} else {
				warehouseInfoStr[i][2] = "开仓";
			}
		}
		warehouseTable = new JTable(new DefaultTableModel(warehouseInfoStr, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		warehouseTable.setRowHeight(25);
		warehouseTable.addMouseMotionListener(this);

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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == queryBtn) {
			createQueryTable("");
		} else if (e.getSource() == addBtn) {
			managerJPanel.removeAndAddRequestOne(this, new AddWarehouseJPanel());
		} else if (e.getSource() == delBtn) {
			managerJPanel.removeAndAddRequestOne(this, new DelWarehouseJPanel());
		} else if (e.getSource() == editBtn) {
			managerJPanel.removeAndAddRequestOne(this, new UpdateWarehouseJPanel());
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