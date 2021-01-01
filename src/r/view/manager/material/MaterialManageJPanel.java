package r.view.manager.material;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.eltima.components.ui.DatePicker;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import r.controller.BillController;
import r.controller.MaterialController;
import r.domain.Bill;
import r.domain.Material;
import r.utils.DatePickerUtil;
import r.utils.DicMapUtil;
import r.utils.JTableUtil;
import r.utils.MyListeners;

public class MaterialManageJPanel extends JPanel implements MyListeners {
	private JComboBox<String> materialNameTxt; // ������
	private DatePicker dateBegin;
	private DatePicker dateEnd;
	private JButton delBtn;
	private JButton queryBtn;
	private JTable billTable;
	private JScrollPane billTableScrollPane;
	private CellConstraints constraints;
	private JComboBox<String> timeBox = new JComboBox<String>(new String[] { "", "���", "����" });
	private MaterialController mController = new MaterialController();
	private BillController bController = new BillController();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public MaterialManageJPanel() {

		FormLayout formLayout = new FormLayout(
				"right:40dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,pref,10dlu,pref,5dlu,pref,5dlu,pref,5dlu,50dlu,30dlu:none",
				"pref,5dlu,pref,5dlu,pref");
		constraints = new CellConstraints();
		this.setLayout(formLayout);

		JLabel materialNameLab = new JLabel("����");
		materialNameLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(materialNameLab, constraints.xy(2, 1));

		createBox(); // ����==>>���ʡ����ҡ��ͺ�������

		queryBtn = new JButton("��ѯ");
		queryBtn.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		queryBtn.addMouseListener(this);
		this.add(queryBtn, constraints.xy(12, 1));

		this.add(timeBox, constraints.xy(6, 1));
		dateBegin = DatePickerUtil.getDatePickerAndNoTimePanle();
		this.add(dateBegin, constraints.xy(8, 1));
		JLabel dateConcat = new JLabel("��");
		dateConcat.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(dateConcat, constraints.xy(9, 1));
		dateEnd = DatePickerUtil.getDatePickerAndNoTimePanle();
		this.add(dateEnd, constraints.xy(10, 1));

		delBtn = new JButton("ɾ��");
		delBtn.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		delBtn.addMouseListener(this);
		this.add(delBtn, constraints.xy(14, 1));

		createTable("init");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == delBtn) {
			if (billTable.getSelectedRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "δѡ�м�¼");
				return;
			} else if (billTable.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(this, "��ִ�е�ѡ����");
				return;
			} else {
				// ��ѡ��һ��
				int row = billTable.getSelectedRow();
				String materialName = (String) billTable.getValueAt(row, 0); // ����
				String warehouseInfos = (String) billTable.getValueAt(row, 2); // �ֿ�/��ַ
				String manufacturer = (String) billTable.getValueAt(row, 3); // ����
				String type = (String) billTable.getValueAt(row, 4); // �ͺ�
				String specifications = (String) billTable.getValueAt(row, 5); // ���
				boolean flag = mController.delMaterial(materialName, manufacturer, type, specifications,
						warehouseInfos);
				if (flag) {
					JOptionPane.showMessageDialog(this, "ɾ���ɹ�");
				} else {
					JOptionPane.showMessageDialog(this, "ɾ��ʧ�ܣ����ʿ���0");
				}
				createTable("");
			}
		} else if (e.getSource() == queryBtn) {
			createTable("");
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int row = billTable.rowAtPoint(e.getPoint());
		int col = billTable.columnAtPoint(e.getPoint());
		if (row > -1 && col > -1) {
			Object value = billTable.getValueAt(row, col);
			if (null != value && !"".equals(value)) {
				billTable.setToolTipText(value.toString());
			} else {
				billTable.setToolTipText(null);
			}
		}
	}

	private void createTable(String string) {
		if (billTable != null) {
			billTableScrollPane.remove(billTable);
			this.remove(billTableScrollPane);
		}
		// ���ʣ����ң��ͺţ���񣬲ֿ⣬��ֹʱ��
		String materialNameProp = ((String) materialNameTxt.getSelectedItem()).trim();
		String dateBeginProp = sdf.format(dateBegin.getValue());
		String dateEndProp = sdf.format(dateEnd.getValue());
		String inOrOutChoice = (String) timeBox.getSelectedItem();
		String stateProp = "";
		if (!"".equals(inOrOutChoice)) {
			stateProp = DicMapUtil.dicMap.get(inOrOutChoice);
		}

		String[] names = { "����", "����", "�ֿ�", "����", "�ͺ�", "���", "����", "CHECK" };
		String[][] billTableInfo = null;
		List<Bill> bills = bController.queryBill(materialNameProp, dateBeginProp, dateEndProp, stateProp);
		System.out.println(bills);
		if (bills != null) {
			billTableInfo = new String[bills.size()][names.length];
			for (int i = 0; i < bills.size(); i++) {
				billTableInfo[i][0] = bills.get(i).getMaterialName();
				billTableInfo[i][1] = String.valueOf(bills.get(i).getCount());
				if ("".equals(bills.get(i).getEntryUnit()) || bills.get(i).getEntryUnit() == null) {
					billTableInfo[i][2] = bills.get(i).getOutUnit();
				} else {
					billTableInfo[i][2] = bills.get(i).getEntryUnit();
				}
				billTableInfo[i][3] = bills.get(i).getManufacturer();
				billTableInfo[i][4] = bills.get(i).getType();
				billTableInfo[i][5] = bills.get(i).getSpecifications();
				billTableInfo[i][6] = bills.get(i).getDateTime();
			}
		}

		billTable = new JTable(new DefaultTableModel(billTableInfo, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		JTableUtil.addCheckbox(billTable, 7);

		billTable.setRowHeight(25);
		billTable.addMouseMotionListener(this);

		billTableScrollPane = new JScrollPane(billTable);
		billTableScrollPane.setPreferredSize(new Dimension(728, 310));
		billTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(billTableScrollPane, constraints.xywh(1, 5, 17, 1));

		this.repaint();
		this.validate();
	}

	private void createBox() {
		if (materialNameTxt != null) {
			this.remove(materialNameTxt);
		}

		List<Material> materialAvailable = mController.queryAllMaterial();
		// ����������
		Vector<String> materials = new Vector<String>();
		if (materialAvailable != null) {
			materials.add("");
			for (int i = 0; i < materialAvailable.size(); i++) {
				if (materials.contains(materialAvailable.get(i).getName())) {
					continue;
				}
				materials.add(materialAvailable.get(i).getName());
			}
		} else {
			materials.add("");
		}
		materialNameTxt = new JComboBox<String>(materials);
		this.add(materialNameTxt, constraints.xy(4, 1));

		this.repaint();
		this.validate();
	}
}
