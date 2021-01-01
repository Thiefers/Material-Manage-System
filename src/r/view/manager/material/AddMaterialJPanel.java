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

// 入库管理员<==>采购员
public class AddMaterialJPanel extends JPanel implements MyListeners {

	private JComboBox<String> materialNameTxt; // 物资名
	private JComboBox<String> manufacturerTxt; // 厂家
	private JComboBox<String> typeTxt; // 型号
	private JTextField specificationsTxt; // 规格
	private JTextField countTxt; // 物资数
	private DatePicker datepick; // 时间
	private JComboBox<String> entryUnitTxt; // 入库
	private JLabel storageTxt; // 采购员==入库管理员
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
				"pref,5dlu,pref,5dlu,pref");// 列间隔5dlu，显示8列(共17列，7列作为间隔列，端点处两列为了表格而存在)；行间隔3dlu，显示3行，2行间隔行
		constraints = new CellConstraints();
		this.setLayout(formLayout);
		// 厂家，型号都跟入库单位一样，有就从数据库拿，没有就自己填(有新的信息则填写后更新下拉框)
		JLabel materialNameLab = new JLabel("物资");
		materialNameLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(materialNameLab, constraints.xy(2, 1));

		createBox(); // 创建==>>物资、厂家、型号下拉框

		JLabel manufacturerLab = new JLabel("厂家");
		manufacturerLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(manufacturerLab, constraints.xy(6, 1));

		JLabel specificationsLab = new JLabel("规格");
		specificationsLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(specificationsLab, constraints.xy(10, 1));
		specificationsTxt = new JTextField(10);
		this.add(specificationsTxt, constraints.xy(12, 1));

		JLabel entryUnitLab = new JLabel("入库单位");
		entryUnitLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(entryUnitLab, constraints.xy(14, 1));
		List<Warehouse> warehousesAvailable = wController.queryWarehouse("", "", "开仓");
		String[] warehouses;
		if (warehousesAvailable != null) {
			warehouses = new String[warehousesAvailable.size()];
			for (int i = 0; i < warehousesAvailable.size(); i++) {
				warehouses[i] = warehousesAvailable.get(i).getName()+"/"+warehousesAvailable.get(i).getAddr();
			}
		} else {
			warehouses = new String[] { "暂无可用仓库" };
		}
		entryUnitTxt = new JComboBox<String>(warehouses);
		this.add(entryUnitTxt, constraints.xy(16, 1));

		JLabel countLab = new JLabel("数量");
		countLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(countLab, constraints.xy(2, 3));
		countTxt = new JTextField(10);
		this.add(countTxt, constraints.xy(4, 3));

		JLabel typeLab = new JLabel("型号");
		typeLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(typeLab, constraints.xy(6, 3));

		JLabel dateTimeLab = new JLabel("时间");
		dateTimeLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(dateTimeLab, constraints.xy(10, 3));
		datepick = DatePickerUtil.getDatePicker();
		this.add(datepick, constraints.xy(12, 3));

		addBtn = new JButton("添加");
		addBtn.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
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
			String countProp = countTxt.getText(); // 判断是否是数值型,正则表达式
			String dateTimeProp = sdf.format(datepick.getValue());
			String entryUnitProp = (String) entryUnitTxt.getSelectedItem();
			String storageProp = storageTxt.getText();

			boolean flag = mController.addStorageBill(materialNameProp.trim(), manufacturerProp.trim(), typeProp.trim(),
					specificationsProp.trim(), countProp.trim(), dateTimeProp, entryUnitProp, storageProp);
			if (flag) {
				JOptionPane.showMessageDialog(this, "添加成功");
			} else {
				JOptionPane.showMessageDialog(this, "添加失败");
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
		String[] names = { "物资", "数量", "入库单位", "厂商", "型号", "时间", "规格","库存" };
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
		// 物资下拉框
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
		// 厂商下拉框
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
		// 型号下拉框
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