package r.view.manager.material;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import r.controller.MaterialController;
import r.controller.MaterialWarehouseStockRelationController;
import r.controller.WarehouseController;
import r.domain.Material;
import r.domain.MaterialWarehouseStockRelation;
import r.domain.Warehouse;
import r.utils.JTableUtil;
import r.utils.MyListeners;

public class DelMaterialJPanel extends JPanel implements MyListeners {

	private JComboBox<String> materialNameTxt; // ������
	private JComboBox<String> manufacturerTxt; // ����
	private JComboBox<String> typeTxt; // �ͺ�
	private JTextField specificationsTxt; // ���
	private JTextField countTxt; // ������
	private JComboBox<String> outUnitTxt; // ���ⵥλ
	private JLabel outboundTxt; // ȡ����==�������Ա
	private JButton delBtn;
	private JButton queryBtn;
	private JTable relationTable;
	private JScrollPane relationTableScrollPane;
	private CellConstraints constraints;
	private WarehouseController wController = new WarehouseController();
	private MaterialController mController = new MaterialController();
	private MaterialWarehouseStockRelationController mwsrController = new MaterialWarehouseStockRelationController();

	public DelMaterialJPanel(String loginAct) {
		// ��д���ⵥ��
		// ��д��count(������������),materialName,�ֿ�,manufacturer,type,specifications(�ɲ�ѯ)
		// ���ɣ�����id,state(0������),dateTime
		// ����ȡ��materialName,manufacturer,type,specifications,outUnit,outbound(loginAct)
		// ���أ�����id���ֿ�id��(���id)
		// �����ʾ������,���,�ֿ�/����/״̬,����,�ͺ�,���
		// ���ʱ������ֿ��

		// ���ʱ�id, name, manufacturer, type, specifications
		// �ֿ��id, name, addr, state
		// ����id, materialId, warehouseId, stock, checkTime
		// ���ݱ�id, materialName, manufacturer, type, specifications, count(��), dateTime,
		// state, outUnit, outbound

		// �µı�materialId, warehouseId, stockId, materialName, stock, manufacturer,
		// type, specifications, warehouseNameAddrState

		// select materialName,concat(warehouseName,'/',addr,'/',state) as
		// '�ֿ�/��ַ/״̬',Stock,manufacturer,type,specifications from tbl;

		FormLayout formLayout = new FormLayout(
				"right:40dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,50dlu,40dlu:none",
				"pref,5dlu,pref,5dlu,pref");
		constraints = new CellConstraints();
		this.setLayout(formLayout);

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

		JLabel verticalLine1 = new JLabel(" |");
		this.add(verticalLine1, constraints.xy(13, 1));
		JLabel verticalLine2 = new JLabel(" |");
		this.add(verticalLine2, constraints.xy(13, 2));
		JLabel verticalLine3 = new JLabel(" |");
		this.add(verticalLine3, constraints.xy(13, 3));

		JLabel countLab = new JLabel("������������");
		countLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(countLab, constraints.xy(14, 1));
		countTxt = new JTextField(10);
		this.add(countTxt, constraints.xy(16, 1));

		JLabel entryUnitLab = new JLabel("�ֿ�");
		entryUnitLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(entryUnitLab, constraints.xy(2, 3));

		JLabel typeLab = new JLabel("�ͺ�");
		typeLab.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		this.add(typeLab, constraints.xy(6, 3));

		queryBtn = new JButton("��ѯ");
		queryBtn.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		queryBtn.addMouseListener(this);
		this.add(queryBtn, constraints.xy(12, 3));

		delBtn = new JButton("����");
		delBtn.setFont(new Font("����", Font.CENTER_BASELINE, 15));
		delBtn.addMouseListener(this);
		this.add(delBtn, constraints.xy(16, 3));

		outboundTxt = new JLabel(loginAct);
		outboundTxt.setVisible(false);
		this.add(outboundTxt, constraints.xy(16, 3));

		createTable("init");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == delBtn) {
			if (relationTable.getSelectedRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "δѡ�м�¼");
				return;
			} else if (relationTable.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(this, "��ִ�е�ѡ����");
				return;
			} else {
				// ��ѡ��һ��
				String delCount = countTxt.getText();
				if ("".equals(delCount)) {
					JOptionPane.showMessageDialog(this, "������������������");
					return;
				}
				if (!Pattern.compile("[0-9]+").matcher(delCount).matches()) {
					JOptionPane.showMessageDialog(this, "����������");
					return;
				}
				int row = relationTable.getSelectedRow();
				int nowStock = Integer.parseInt((String) relationTable.getValueAt(row, 1));
				if (nowStock < Integer.parseInt(delCount)) {
					JOptionPane.showMessageDialog(this, "��治�㣬ת��ʧ��");
					return;
				}
				if (0 == Integer.parseInt(delCount)) {
					JOptionPane.showMessageDialog(this, "δת���κ�����");
					return;
				}
				String warehouseInfos = (String) relationTable.getValueAt(row, 2); // �ֿ�/��ַ/״̬
				String[] warehouseInfoArr = warehouseInfos.split("/");
				if ("�ղ�".equals(warehouseInfoArr[2])) { // �ֿ�״̬
					JOptionPane.showMessageDialog(this, "�ղ��У��������");
					return;
				}
				String outUnit = warehouseInfoArr[0] + "/" + warehouseInfoArr[1]; // �ֿ���/��ַ
				String materialName = (String) relationTable.getValueAt(row, 0); // ����
				String manufacturer = (String) relationTable.getValueAt(row, 3); // ����
				String type = (String) relationTable.getValueAt(row, 4); // �ͺ�
				String specifications = (String) relationTable.getValueAt(row, 5); // ���
				String materialId = (String) relationTable.getValueAt(row, 7); // ����id
				String warehouseId = (String) relationTable.getValueAt(row, 8); // �ֿ�id
				String stockId = (String) relationTable.getValueAt(row, 9); // ���id,�����ڹ�ϵ��Ϳ���
				String outbound = outboundTxt.getText(); // ȡ����
				boolean flag = mController.addOutBill(materialName.trim(), manufacturer.trim(), type.trim(),
						specifications.trim(), Integer.parseInt(delCount.trim()), outUnit, outbound, stockId,
						materialId, warehouseId);
				if (flag) {
					JOptionPane.showMessageDialog(this, "ת���ɹ�");
				} else {
					JOptionPane.showMessageDialog(this, "ת��ʧ��");
				}
				createTable("");
			}
		} else if (e.getSource() == queryBtn) {
			createTable("");
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int row = relationTable.rowAtPoint(e.getPoint());
		int col = relationTable.columnAtPoint(e.getPoint());
		if (row > -1 && col > -1) {
			Object value = relationTable.getValueAt(row, col);
			if (null != value && !"".equals(value)) {
				relationTable.setToolTipText(value.toString());
			} else {
				relationTable.setToolTipText(null);
			}
		}
	}

	private void createTable(String string) {
		if (relationTable != null) {
			relationTableScrollPane.remove(relationTable);
			this.remove(relationTableScrollPane);
		}
		// ���ʣ����ң��ͺţ���񣬲ֿ�
		String materialNameProp = ((String) materialNameTxt.getSelectedItem()).trim();
		String manufacturerProp = ((String) manufacturerTxt.getSelectedItem()).trim();
		String typeProp = ((String) typeTxt.getSelectedItem()).trim();
		String specificationsProp = specificationsTxt.getText().trim();
		String warehouseNameProp = ((String) outUnitTxt.getSelectedItem()).trim();
		// ���أ�����id���ֿ�id�����id
		String[] names = { "����", "���", "�ֿ�/��ַ/״̬", "����", "�ͺ�", "���", "CHECK", "", "", "" };
		String[][] relationTableInfo = null;
		List<MaterialWarehouseStockRelation> mwsRelations = mwsrController.queryRelation(materialNameProp,
				manufacturerProp, typeProp, specificationsProp, warehouseNameProp);
		if (mwsRelations != null) {
			relationTableInfo = new String[mwsRelations.size()][names.length];
			for (int i = 0; i < mwsRelations.size(); i++) {
				relationTableInfo[i][0] = mwsRelations.get(i).getMaterialName();
				relationTableInfo[i][1] = String.valueOf(mwsRelations.get(i).getStock());
				// ƴ�Ӳֿ⣬��ַ��״̬
				StringBuffer sb = new StringBuffer();
				String warehouseName = mwsRelations.get(i).getWarehouseName();
				String warehouseAddr = mwsRelations.get(i).getWarehouseAddr();
				String warehouseState = "0".equals(mwsRelations.get(i).getWarehouseState()) ? "�ղ�" : "����";
				relationTableInfo[i][2] = sb.append(warehouseName).append("/").append(warehouseAddr).append("/")
						.append(warehouseState).toString();
				relationTableInfo[i][3] = mwsRelations.get(i).getManufacturer();
				relationTableInfo[i][4] = mwsRelations.get(i).getType();
				relationTableInfo[i][5] = mwsRelations.get(i).getSpecifications();
				relationTableInfo[i][7] = mwsRelations.get(i).getMaterialId();
				relationTableInfo[i][8] = mwsRelations.get(i).getWarehouseId();
				relationTableInfo[i][9] = mwsRelations.get(i).getStockId();
			}
		}

		relationTable = new JTable(new DefaultTableModel(relationTableInfo, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		JTableUtil.hideColumn(relationTable, 7, 8, 9);
		JTableUtil.addCheckbox(relationTable, 6);

		relationTable.setRowHeight(25);
		relationTable.addMouseMotionListener(this);

		relationTableScrollPane = new JScrollPane(relationTable);
		relationTableScrollPane.setPreferredSize(new Dimension(728, 310));
		relationTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(relationTableScrollPane, constraints.xywh(1, 5, 17, 1));

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
		if (outUnitTxt != null) {
			this.remove(outUnitTxt);
		}

		List<Warehouse> warehousesAvailable = wController.queryWarehouse("", "", "����");
		Vector<String> warehouses = new Vector<String>();
		if (warehousesAvailable != null) {
			warehouses.add("");
			for (int i = 0; i < warehousesAvailable.size(); i++) {
				if (warehouses.contains(warehousesAvailable.get(i).getName())) {
					continue;
				}
				warehouses.add(warehousesAvailable.get(i).getName());
			}
		} else {
			warehouses.add("���޿��òֿ�");
		}
		outUnitTxt = new JComboBox<String>(warehouses);
		this.add(outUnitTxt, constraints.xy(4, 3));

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