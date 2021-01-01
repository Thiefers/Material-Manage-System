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
import r.utils.MyListeners;

public class MaterialCountJPanel extends JPanel implements MyListeners {
	// 实现分别按日、月和年对出入库物质数量的统计。
	private JComboBox<String> materialNameTxt; // 物资名
	private DatePicker dateBegin;
	private DatePicker dateEnd;
	private JButton queryBtn;
	private JButton queryDayBtn;
	private JButton queryMonthBtn;
	private JButton queryYearBtn;
	private JTable billTable;
	private JScrollPane billTableScrollPane;
	private CellConstraints constraints;
	private JComboBox<String> timeBox = new JComboBox<String>(new String[] { "", "入库", "出库" });
	private MaterialController mController = new MaterialController();
	private BillController bController = new BillController();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public MaterialCountJPanel() {

		FormLayout formLayout = new FormLayout(
				"right:30dlu,pref,5dlu,50dlu,5dlu,pref,5dlu,pref,10dlu,pref,5dlu,pref,5dlu,pref,5dlu,pref,5dlu,pref,30dlu:none",
				"pref,5dlu,pref,5dlu,pref");
		constraints = new CellConstraints();
		this.setLayout(formLayout);

		JLabel materialNameLab = new JLabel("物资");
		materialNameLab.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(materialNameLab, constraints.xy(2, 1));

		createBox(); // 创建==>>物资、厂家、型号下拉框

		this.add(timeBox, constraints.xy(6, 1));
		dateBegin = DatePickerUtil.getDatePickerAndNoTimePanle();
		this.add(dateBegin, constraints.xy(8, 1));
		JLabel dateConcat = new JLabel("至");
		dateConcat.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		this.add(dateConcat, constraints.xy(9, 1));
		dateEnd = DatePickerUtil.getDatePickerAndNoTimePanle();
		this.add(dateEnd, constraints.xy(10, 1));

		queryBtn = new JButton("查询");
		queryBtn.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		queryBtn.addMouseListener(this);
		this.add(queryBtn, constraints.xy(12, 1));

		queryYearBtn = new JButton("年查");
		queryYearBtn.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		queryYearBtn.addMouseListener(this);
		this.add(queryYearBtn, constraints.xy(14, 1));

		queryMonthBtn = new JButton("月查");
		queryMonthBtn.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		queryMonthBtn.addMouseListener(this);
		this.add(queryMonthBtn, constraints.xy(16, 1));

		queryDayBtn = new JButton("日查");
		queryDayBtn.setFont(new Font("楷体", Font.CENTER_BASELINE, 15));
		queryDayBtn.addMouseListener(this);
		this.add(queryDayBtn, constraints.xy(18, 1));

		createTable("init");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == queryDayBtn) {
			 StringBuffer beginSb = new StringBuffer();
			 String[] dateBeginArr = sdf.format(dateBegin.getValue()).split("-");
			 beginSb.append(dateBeginArr[0]).append(dateBeginArr[1]).append(dateBeginArr[2]);
			 int beginDay = Integer.parseInt(beginSb.toString());
			
			 StringBuffer endSb = new StringBuffer();
			 String[] dateEndArr = sdf.format(dateEnd.getValue()).split("-");
			 endSb.append(dateEndArr[0]).append(dateEndArr[1]).append(dateEndArr[2]);
			 int endDay = Integer.parseInt(endSb.toString());
			 if ((endDay - beginDay) > 1) {
			 JOptionPane.showMessageDialog(this, "日期" + sdf.format(dateBegin.getValue()) +
			 "距离"
			 + sdf.format(dateEnd.getValue()) + "间隔超过一天，请重新选择");
			 return;
			 }
			createTable("queryDay");
		} else if (e.getSource() == queryMonthBtn) {
			createTable("queryMonth");
		} else if (e.getSource() == queryYearBtn) {
			// StringBuffer beginSb = new StringBuffer();
			// String[] dateBeginArr = sdf.format(dateBegin.getValue()).split("-");
			// beginSb.append(dateBeginArr[0]);
			// int beginYear = Integer.parseInt(beginSb.toString());
			//
			// StringBuffer endSb = new StringBuffer();
			// String[] dateEndArr = sdf.format(dateEnd.getValue()).split("-");
			// endSb.append(dateEndArr[0]);
			// int endYear = Integer.parseInt(endSb.toString());
			// if ((endYear - beginYear) > 1) {
			// JOptionPane.showMessageDialog(this, "年份" + beginYear + "距离" + endYear +
			// "间隔超过一年，请重新选择");
			// return;
			// }
			createTable("queryYear");
		} else if (e.getSource() == queryBtn) {
			createTable("queryAll");
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

	private void createTable(String queryProp) {
		if (billTable != null) {
			billTableScrollPane.remove(billTable);
			this.remove(billTableScrollPane);
		}

		String materialNameProp = ((String) materialNameTxt.getSelectedItem()).trim();
		String dateBeginProp = sdf.format(dateBegin.getValue());
		String dateEndProp = sdf.format(dateEnd.getValue());
		String inOrOutChoice = (String) timeBox.getSelectedItem(); // 入库/出库选择
		String stateProp = "";
		if (!"".equals(inOrOutChoice)) {
			stateProp = DicMapUtil.dicMap.get(inOrOutChoice);
		}

		List<Bill> bills = null;
		if ("init".equals(queryProp) || "queryAll".equals(queryProp)) {
			bills = bController.queryBill(materialNameProp, dateBeginProp, dateEndProp, stateProp);
		} else if ("queryYear".equals(queryProp)) {
			bills = bController.queryYearBill(materialNameProp, dateBeginProp, stateProp);
		} else if ("queryMonth".equals(queryProp)) {
			bills = bController.queryMonthBill(materialNameProp, dateBeginProp, stateProp);
		} else if ("queryDay".equals(queryProp)) {
			bills = bController.queryDayBill(materialNameProp, dateBeginProp, stateProp);
		}

		String[] names = { "物资", "数量", "仓库", "厂商", "型号", "规格", "日期", "库存" };
		String[][] billTableInfo = null;
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
				billTableInfo[i][7] = String.valueOf(bills.get(i).getStock());
			}
		}

		billTable = new JTable(new DefaultTableModel(billTableInfo, names) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		billTable.setRowHeight(25);
		billTable.addMouseMotionListener(this);

		billTableScrollPane = new JScrollPane(billTable);
		billTableScrollPane.setPreferredSize(new Dimension(728, 310));
		billTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(billTableScrollPane, constraints.xywh(2, 5, 17, 1));

		this.repaint();
		this.validate();
	}

	private void createBox() {
		if (materialNameTxt != null) {
			this.remove(materialNameTxt);
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
		this.add(materialNameTxt, constraints.xy(4, 1));

		this.repaint();
		this.validate();
	}
}