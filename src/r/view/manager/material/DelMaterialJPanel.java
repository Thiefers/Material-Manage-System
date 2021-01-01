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

	private JComboBox<String> materialNameTxt; // 物资名
	private JComboBox<String> manufacturerTxt; // 厂家
	private JComboBox<String> typeTxt; // 型号
	private JTextField specificationsTxt; // 规格
	private JTextField countTxt; // 物资数
	private JComboBox<String> outUnitTxt; // 出库单位
	private JLabel outboundTxt; // 取料人==出库管理员
	private JButton delBtn;
	private JButton queryBtn;
	private JTable relationTable;
	private JScrollPane relationTableScrollPane;
	private CellConstraints constraints;
	private WarehouseController wController = new WarehouseController();
	private MaterialController mController = new MaterialController();
	private MaterialWarehouseStockRelationController mwsrController = new MaterialWarehouseStockRelationController();

	public DelMaterialJPanel(String loginAct) {
		// 填写出库单据
		// 填写：count(出库物资数量),materialName,仓库,manufacturer,type,specifications(可查询)
		// 生成：单据id,state(0：出库),dateTime
		// 表格获取：materialName,manufacturer,type,specifications,outUnit,outbound(loginAct)
		// 隐藏：物资id，仓库id，(库存id)
		// 表格显示：物资,库存,仓库/坐标/状态,厂商,型号,规格
		// 物资表，库存表，仓库表

		// 物资表：id, name, manufacturer, type, specifications
		// 仓库表：id, name, addr, state
		// 库存表：id, materialId, warehouseId, stock, checkTime
		// 单据表：id, materialName, manufacturer, type, specifications, count(填), dateTime,
		// state, outUnit, outbound

		// 新的表：materialId, warehouseId, stockId, materialName, stock, manufacturer,
		// type, specifications, warehouseNameAddrState

		// select materialName,concat(warehouseName,'/',addr,'/',state) as
		// '仓库/地址/状态',Stock,manufacturer,type,specifications from tbl;

		FormLayout formLayout = new FormLayout(
				"right:40dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,50dlu,40dlu:none",
				"pref,5dlu,pref,5dlu,pref");
		constraints = new CellConstraints();
		this.setLayout(formLayout);

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

		JLabel verticalLine1 = new JLabel(" |");
		this.add(verticalLine1, constraints.xy(13, 1));
		JLabel verticalLine2 = new JLabel(" |");
		this.add(verticalLine2, constraints.xy(13, 2));
		JLabel verticalLine3 = new JLabel(" |");
		this.add(verticalLine3, constraints.xy(13, 3));

		JLabel countLab = new JLabel("出库物资数量");
		countLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(countLab, constraints.xy(14, 1));
		countTxt = new JTextField(10);
		this.add(countTxt, constraints.xy(16, 1));

		JLabel entryUnitLab = new JLabel("仓库");
		entryUnitLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(entryUnitLab, constraints.xy(2, 3));

		JLabel typeLab = new JLabel("型号");
		typeLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(typeLab, constraints.xy(6, 3));

		queryBtn = new JButton("查询");
		queryBtn.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		queryBtn.addMouseListener(this);
		this.add(queryBtn, constraints.xy(12, 3));

		delBtn = new JButton("出库");
		delBtn.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
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
				JOptionPane.showMessageDialog(this, "未选中记录");
				return;
			} else if (relationTable.getSelectedRowCount() > 1) {
				JOptionPane.showMessageDialog(this, "请执行单选操作");
				return;
			} else {
				// 仅选择一条
				String delCount = countTxt.getText();
				if ("".equals(delCount)) {
					JOptionPane.showMessageDialog(this, "请输入出库的物资数量");
					return;
				}
				if (!Pattern.compile("[0-9]+").matcher(delCount).matches()) {
					JOptionPane.showMessageDialog(this, "请输入数字");
					return;
				}
				int row = relationTable.getSelectedRow();
				int nowStock = Integer.parseInt((String) relationTable.getValueAt(row, 1));
				if (nowStock < Integer.parseInt(delCount)) {
					JOptionPane.showMessageDialog(this, "库存不足，转出失败");
					return;
				}
				if (0 == Integer.parseInt(delCount)) {
					JOptionPane.showMessageDialog(this, "未转出任何物资");
					return;
				}
				String warehouseInfos = (String) relationTable.getValueAt(row, 2); // 仓库/地址/状态
				String[] warehouseInfoArr = warehouseInfos.split("/");
				if ("闭仓".equals(warehouseInfoArr[2])) { // 仓库状态
					JOptionPane.showMessageDialog(this, "闭仓中，请勿操作");
					return;
				}
				String outUnit = warehouseInfoArr[0] + "/" + warehouseInfoArr[1]; // 仓库名/地址
				String materialName = (String) relationTable.getValueAt(row, 0); // 物资
				String manufacturer = (String) relationTable.getValueAt(row, 3); // 厂商
				String type = (String) relationTable.getValueAt(row, 4); // 型号
				String specifications = (String) relationTable.getValueAt(row, 5); // 规格
				String materialId = (String) relationTable.getValueAt(row, 7); // 物资id
				String warehouseId = (String) relationTable.getValueAt(row, 8); // 仓库id
				String stockId = (String) relationTable.getValueAt(row, 9); // 库存id,作用于关系表和库存表
				String outbound = outboundTxt.getText(); // 取料人
				boolean flag = mController.addOutBill(materialName.trim(), manufacturer.trim(), type.trim(),
						specifications.trim(), Integer.parseInt(delCount.trim()), outUnit, outbound, stockId,
						materialId, warehouseId);
				if (flag) {
					JOptionPane.showMessageDialog(this, "转出成功");
				} else {
					JOptionPane.showMessageDialog(this, "转出失败");
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
		// 物资，厂家，型号，规格，仓库
		String materialNameProp = ((String) materialNameTxt.getSelectedItem()).trim();
		String manufacturerProp = ((String) manufacturerTxt.getSelectedItem()).trim();
		String typeProp = ((String) typeTxt.getSelectedItem()).trim();
		String specificationsProp = specificationsTxt.getText().trim();
		String warehouseNameProp = ((String) outUnitTxt.getSelectedItem()).trim();
		// 隐藏：物资id，仓库id，库存id
		String[] names = { "物资", "库存", "仓库/地址/状态", "厂商", "型号", "规格", "CHECK", "", "", "" };
		String[][] relationTableInfo = null;
		List<MaterialWarehouseStockRelation> mwsRelations = mwsrController.queryRelation(materialNameProp,
				manufacturerProp, typeProp, specificationsProp, warehouseNameProp);
		if (mwsRelations != null) {
			relationTableInfo = new String[mwsRelations.size()][names.length];
			for (int i = 0; i < mwsRelations.size(); i++) {
				relationTableInfo[i][0] = mwsRelations.get(i).getMaterialName();
				relationTableInfo[i][1] = String.valueOf(mwsRelations.get(i).getStock());
				// 拼接仓库，地址，状态
				StringBuffer sb = new StringBuffer();
				String warehouseName = mwsRelations.get(i).getWarehouseName();
				String warehouseAddr = mwsRelations.get(i).getWarehouseAddr();
				String warehouseState = "0".equals(mwsRelations.get(i).getWarehouseState()) ? "闭仓" : "开仓";
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

		List<Warehouse> warehousesAvailable = wController.queryWarehouse("", "", "开仓");
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
			warehouses.add("暂无可用仓库");
		}
		outUnitTxt = new JComboBox<String>(warehouses);
		this.add(outUnitTxt, constraints.xy(4, 3));

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