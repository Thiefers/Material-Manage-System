package r.view.manager.warehouseManage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import r.controller.WarehouseController;
import r.domain.Warehouse;
import r.utils.JTableUtil;
import r.utils.MyListeners;

public class DelWarehouseJPanel extends JPanel implements MyListeners {

	private WarehouseController warehouseController = new WarehouseController();
	private JTextField nameText;
	private JTextField addrText;
	private JComboBox<String> comboBox;
	private JButton queryBtn;
	private JButton delBtn;
	private JTable warehouseTable;
	private JScrollPane warehouseScrollPane;
	private JPanel panelTop;
	private JPanel panelMiddle;

	public DelWarehouseJPanel() {
		this.setLayout(new BorderLayout());

		panelTop = new JPanel();

		JLabel nameLabel = new JLabel("�ֿ�����");
		panelTop.add(nameLabel);
		nameText = new JTextField(10);
		panelTop.add(nameText);

		JLabel addrLabel = new JLabel("�ֿ��ַ");
		panelTop.add(addrLabel);
		addrText = new JTextField(10);
		panelTop.add(addrText);

		JLabel stateLabel = new JLabel("�ֿ�״̬");
		panelTop.add(stateLabel);
		String[] listData = new String[] { "����&�ղ�", "����", "�ղ�" };
		comboBox = new JComboBox<String>(listData);
		panelTop.add(comboBox);

		queryBtn = new JButton("��ѯ");
		queryBtn.addMouseListener(this);
		panelTop.add(queryBtn);

		delBtn = new JButton("ɾ��ѡ�вֿ�");
		delBtn.addMouseListener(this);
		panelTop.add(delBtn);
		this.add(panelTop, BorderLayout.NORTH);

		createQueryTable("init");

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == queryBtn) {
			createQueryTable("");
		} else if (e.getSource() == delBtn) {
			int[] rows = warehouseTable.getSelectedRows();
			// TODO
			if (rows.length != 0) {
				List<String> warehouseIdList = new ArrayList<String>();
				for (int row : rows) {
					warehouseIdList.add((String) warehouseTable.getValueAt(row, 3));
				}
				if (JOptionPane.showConfirmDialog(this, "ȷ��ɾ��", "", JOptionPane.YES_NO_OPTION) == 0) {
					// ��תcontroller����������Ⱦ
					boolean flag = warehouseController.delWarehouse(warehouseIdList);
					if (flag) {
						JOptionPane.showMessageDialog(this, "�ɹ�ɾ��ָ���ֿ�");
						createQueryTable("");
					} else {
						JOptionPane.showMessageDialog(this, "ɾ��ʧ��,��ȷ�ϲֿ�ʹ��״̬");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "δѡ�вֿ�");
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

	protected void createQueryTable(String operate) {
		if (panelMiddle == null) {
			panelMiddle = new JPanel();
		}

		if (warehouseTable != null) {
			warehouseScrollPane.remove(warehouseTable);
			panelMiddle.remove(warehouseScrollPane);
		}
		String nameProp = nameText.getText();
		String addrProp = addrText.getText();
		String stateProp = (String) comboBox.getSelectedItem();

		// ����Ϊ�գ����õ��˲�ѯ�Ĳֿ⼯
		List<Warehouse> warehouses = warehouseController.queryWarehouse(nameProp, addrProp, stateProp);
		// ��������
		String[] names = { "�ֿ�����", "�ֿ��ַ", "�ֿ�״̬", "", "Operation" };
		// TODO ��ֵ����
		Object[][] warehouseInfoStr = null;
		if (warehouses != null) {
			warehouseInfoStr = new Object[warehouses.size()][5];
			for (int i = 0; i < warehouses.size(); i++) {
				warehouseInfoStr[i][0] = warehouses.get(i).getName();
				warehouseInfoStr[i][1] = warehouses.get(i).getAddr();
				if ("0".equals(warehouses.get(i).getState())) {
					warehouseInfoStr[i][2] = "�ղ�";
				} else {
					warehouseInfoStr[i][2] = "����";
				}
				warehouseInfoStr[i][3] = warehouses.get(i).getId();
			}
		} else {
			warehouseInfoStr = new Object[0][5];
		}

		warehouseTable = new JTable(new DefaultTableModel(warehouseInfoStr, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		// ����ID��
		JTableUtil.hideColumn(warehouseTable, 3);
		JTableUtil.addCheckbox(warehouseTable,4);

		warehouseTable.setRowHeight(25);
		warehouseTable.addMouseMotionListener(this);

		warehouseScrollPane = new JScrollPane(warehouseTable);
		warehouseScrollPane.setPreferredSize(new Dimension(400, 300));
		warehouseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelMiddle.add(warehouseScrollPane);
		if ("init".equals(operate)) {
			this.add(panelMiddle, BorderLayout.CENTER);
		}
		this.repaint();
		this.validate();
	}

}