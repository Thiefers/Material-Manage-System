package r.utils;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JTableUtil {

	public static void hideColumn(JTable theHideTable, Object... cols) {
		for(int i = 0; i < cols.length; i++) {
			theHideTable.getTableHeader().getColumnModel().getColumn((int)cols[i]).setMaxWidth(0);
			theHideTable.getTableHeader().getColumnModel().getColumn((int)cols[i]).setMinWidth(0);
			theHideTable.getColumnModel().getColumn((int)cols[i]).setMaxWidth(0);
			theHideTable.getColumnModel().getColumn((int)cols[i]).setMinWidth(0);
		}
	}

	public static void addCheckbox(JTable functionTable, int col) {
		functionTable.getColumnModel().getColumn(col).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JCheckBox checkBox = new JCheckBox();
				checkBox.setSelected(isSelected);
				checkBox.setHorizontalAlignment((int) 0.5f);
				return checkBox;
			}
		});
	}
	
}
