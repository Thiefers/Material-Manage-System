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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.eltima.components.ui.DatePicker;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import r.controller.BillController;
import r.controller.MaterialController;
import r.controller.WarehouseController;
import r.domain.Bill;
import r.domain.Material;
import r.domain.Warehouse;
import r.utils.DatePickerUtil;
import r.utils.MyListeners;

// ������Ա<==>�ɹ�Ա
public class AddMaterialJPanel extends JPanel implements MyListeners {

	private JComboBox<String> materialNameTxt; // ������
	private JComboBox<String> manufacturerTxt; // ����
	private JComboBox<String> typeTxt; // �ͺ�
	private JTextField specificationsTxt; // ���
	private JTextField countTxt; // ������
	private DatePicker datepick; // ʱ��
	private JComboBox<String> entryUnitTxt; // ���
	private JLabel storageTxt; // �ɹ�Ա==������Ա
	private JButton addBtn;
	private JTable storageTable;
	private JScrollPane storageTableScrollPane;
	private CellConstraints constraints;
	private WarehouseController wController = new WarehouseController();
	private MaterialController mController = new MaterialController();
	private BillController bController = new BillController();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public AddMaterialJPanel(String loginAct) {
		FormLayout formLayout = new FormLayout(
				"right:40dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,pref,5dlu,pref,5dlu,50dlu,40dlu:none",
				"pref,5dlu,pref,5dlu,pref");// �м��5dlu����ʾ8��(��17�У�7����Ϊ����У��˵㴦����Ϊ�˱�������)���м��3dlu����ʾ3�У�2�м����
		constraints = new CellConstraints();
		this.setLayout(formLayout);
		// ���ң��ͺŶ�����ⵥλһ�����оʹ����ݿ��ã�û�о��Լ���(���µ���Ϣ����д�����������)
		JLabel materialNameLab = new JLabel("����");
		materialNameLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(materialNameLab, constraints.xy(2, 1));

		createBox(); // ����==>>���ʡ����ҡ��ͺ�������

		JLabel manufacturerLab = new JLabel("����");
		manufacturerLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(manufacturerLab, constraints.xy(6, 1));

		JLabel specificationsLab = new JLabel("���");
		specificationsLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(specificationsLab, constraints.xy(10, 1));
		specificationsTxt = new JTextField(10);
		this.add(specificationsTxt, constraints.xy(12, 1));

		JLabel entryUnitLab = new JLabel("��ⵥλ");
		entryUnitLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(entryUnitLab, constraints.xy(14, 1));
		List<Warehouse> warehousesAvailable = wController.queryWarehouse("", "", "����");
		String[] warehouses;
		if (warehousesAvailable != null) {
			warehouses = new String[warehousesAvailable.size()];
			for (int i = 0; i < warehousesAvailable.size(); i++) {
				warehouses[i] = warehousesAvailable.get(i).getName()+"/"+warehousesAvailable.get(i).getAddr();
			}
		} else {
			warehouses = new String[] { "���޿��òֿ�" };
		}
		entryUnitTxt = new JComboBox<String>(warehouses);
		this.add(entryUnitTxt, constraints.xy(16, 1));

		JLabel countLab = new JLabel("����");
		countLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(countLab, constraints.xy(2, 3));
		countTxt = new JTextField(10);
		this.add(countTxt, constraints.xy(4, 3));

		JLabel typeLab = new JLabel("�ͺ�");
		typeLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(typeLab, constraints.xy(6, 3));

		JLabel dateTimeLab = new JLabel("ʱ��");
		dateTimeLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(dateTimeLab, constraints.xy(10, 3));
		datepick = DatePickerUtil.getDatePicker();
		this.add(datepick, constraints.xy(12, 3));

		addBtn = new JButton("���");
		addBtn.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		addBtn.addMouseListener(this);
		this.add(addBtn, constraints.xy(14, 3));

		storageTxt = new JLabel(loginAct);
		storageTxt.setVisible(false);
		this.add(storageTxt, constraints.xy(16, 3));

		createTable("init");

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == addBtn) {
			String materialNameProp = (String) materialNameTxt.getSelectedItem();
			String manufacturerProp = (String) manufacturerTxt.getSelectedItem();
			String typeProp = (String) typeTxt.getSelectedItem();
			String specificationsProp = specificationsTxt.getText();
			String countProp = countTxt.getText(); // �ж��Ƿ�����ֵ��,������ʽ
			String dateTimeProp = sdf.format(datepick.getValue());
			String entryUnitProp = (String) entryUnitTxt.getSelectedItem();
			String storageProp = storageTxt.getText();

			boolean flag = mController.addStorageBill(materialNameProp.trim(), manufacturerProp.trim(), typeProp.trim(),
					specificationsProp.trim(), countProp.trim(), dateTimeProp, entryUnitProp, storageProp);
			if (flag) {
				JOptionPane.showMessageDialog(this, "��ӳɹ�");
			} else {
				JOptionPane.showMessageDialog(this, "���ʧ��");
			}
			createTable("");
			createBox();
			specificationsTxt.setText("");
			countTxt.setText("");
			entryUnitTxt.setSelectedIndex(0);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int row = storageTable.rowAtPoint(e.getPoint());
		int col = storageTable.columnAtPoint(e.getPoint());
		if (row > -1 && col > -1) {
			Object value = storageTable.getValueAt(row, col);
			if (null != value && !"".equals(value)) {
				storageTable.setToolTipText(value.toString());
			} else {
				storageTable.setToolTipText(null);
			}
		}
	}

	private void createTable(String string) {
		if (storageTable != null) {
			storageTableScrollPane.remove(storageTable);
			this.remove(storageTableScrollPane);
		}
		String[] names = { "����", "����", "��ⵥλ", "����", "�ͺ�", "ʱ��", "���","���" };
		String[][] storageTableInfo = null;
		List<Bill> bills = bController.queryAllBill();
		if (bills != null) {
			storageTableInfo = new String[bills.size()][names.length];
			for (int i = 0; i < bills.size(); i++) {
				storageTableInfo[i][0] = bills.get(i).getMaterialName();
				storageTableInfo[i][1] = String.valueOf(bills.get(i).getCount());
				storageTableInfo[i][2] = bills.get(i).getEntryUnit();
				storageTableInfo[i][3] = bills.get(i).getManufacturer();
				storageTableInfo[i][4] = bills.get(i).getType();
				storageTableInfo[i][5] = bills.get(i).getDateTime();
				storageTableInfo[i][6] = bills.get(i).getSpecifications();
				storageTableInfo[i][7] = String.valueOf(bills.get(i).getStock());
			}
		}

		storageTable = new JTable(new DefaultTableModel(storageTableInfo, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		storageTable.setRowHeight(25);
		storageTable.addMouseMotionListener(this);

		storageTableScrollPane = new JScrollPane(storageTable);
		storageTableScrollPane.setPreferredSize(new Dimension(728, 310));
		storageTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(storageTableScrollPane, constraints.xywh(1, 5, 17, 1));

		this.repaint();
		this.validate();
	}

	private void createBox() {
		if (materialNameTxt != null) {
			this.remove(materialNameTxt);
		}
		if (manufacturerTxt != null) {
			this.remove(manufacturerTxt);
		}
		if (typeTxt != null) {
			this.remove(typeTxt);
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
		materialNameTxt.setEditable(true);
		this.add(materialNameTxt, constraints.xy(4, 1));
		// ����������
		Vector<String> manufacturers = new Vector<String>();
		if (materialAvailable != null) {
			manufacturers.add("");
			for (int i = 0; i < materialAvailable.size(); i++) {
				if (manufacturers.contains(materialAvailable.get(i).getManufacturer())) {
					continue;
				}
				manufacturers.add(materialAvailable.get(i).getManufacturer());
			}
		} else {
			manufacturers.add("");
		}
		manufacturerTxt = new JComboBox<String>(manufacturers);
		manufacturerTxt.setEditable(true);
		this.add(manufacturerTxt, constraints.xy(8, 1));
		// �ͺ�������
		Vector<String> types = new Vector<String>();
		if (materialAvailable != null) {
			types.add("");
			for (int i = 0; i < materialAvailable.size(); i++) {
				if (types.contains(materialAvailable.get(i).getType())) {
					continue;
				}
				types.add(materialAvailable.get(i).getType());
			}
		} else {
			types.add("");
		}
		typeTxt = new JComboBox<String>(types);
		typeTxt.setEditable(true);
		this.add(typeTxt, constraints.xy(8, 3));

		this.repaint();
		this.validate();
	}

}