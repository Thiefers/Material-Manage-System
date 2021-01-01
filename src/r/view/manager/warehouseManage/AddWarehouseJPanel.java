package r.view.manager.warehouseManage;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import r.controller.WarehouseController;
import r.utils.MyListeners;

public class AddWarehouseJPanel extends JPanel implements MyListeners {

	private JTextField nameText;
	private JTextField addrText;
	private JComboBox<String> comboBox;
	private JButton submitBtn;

	public AddWarehouseJPanel() {
		JLabel nameLabel = new JLabel("�ֿ�����");
		this.add(nameLabel);
		nameText = new JTextField(10);
		this.add(nameText);

		JLabel addrLabel = new JLabel("�ֿ��ַ");
		this.add(addrLabel);
		addrText = new JTextField(10);
		this.add(addrText);

		JLabel stateLabel = new JLabel("�ֿ�״̬");
		this.add(stateLabel);
		String[] listData = new String[] { "����", "�ղ�" };
		comboBox = new JComboBox<String>(listData);
		comboBox.setSelectedIndex(0);
		comboBox.addItemListener(this);
		this.add(comboBox);

		submitBtn = new JButton("�ύ");
		submitBtn.addMouseListener(this);
		this.add(submitBtn);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == submitBtn) {
			String nameProp = nameText.getText();
			String addrProp = addrText.getText();
			String stateProp = (String) comboBox.getSelectedItem();
			WarehouseController warehouseController = new WarehouseController();
			boolean flag = warehouseController.addWarehouse(nameProp, addrProp, stateProp);
			if (flag) {
				nameText.setText("");
				addrText.setText("");
				comboBox.setSelectedIndex(0);
				System.out.println("��ӳɹ�");
			} else {
				System.out.println("���ʧ��");
			}
		}
	}

}