package r.view.global;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import r.controller.RegisterController;
import r.utils.MyListeners;

public class RegisterJPanel extends JPanel implements MyListeners {

	// ǰ����дע����Ϣ���˺ţ��ǳƣ����룬ְλ
	// �������ע����Ϣ��id��ʧЧʱ�䣨Ĭ�Ͽ�==�����ڣ�������״̬����ע����Ϊ1�����ã�������ʱ�䣬�����ˣ������˺�����
	private JPanel mainJPanel;
	private CardLayout cardLayout;
	private JTextField loginActText;
	private JTextField nameText;
	private JPasswordField loginPwdText;
	private JButton confirmRegisterBtn;
	private JComboBox<String> positionBox = new JComboBox<>(new String[] { "����Ա", "�ɹ�Ա", "ȡ����" });

	public RegisterJPanel() {
	}

	public RegisterJPanel(JPanel mainJPanel, CardLayout cardLayout) {
		this.mainJPanel = mainJPanel;
		this.cardLayout = cardLayout;
		this.setLayout(null);

		JLabel jLabel = new JLabel("���ʹ���ϵͳMMS", new ImageIcon("src/image/�ִ��ֿ�.png"), SwingConstants.LEFT);
		jLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, 50, 200, 50);
		jLabel.setFont(new Font("����", Font.BOLD, 20));
		this.add(jLabel);

		JLabel panelTipLabel = new JLabel("�˺�ע��");
		panelTipLabel.setBounds(Global.CANVAS_WIDTH / 2 - 40, jLabel.getY() + jLabel.getHeight(), 100, 50);
		panelTipLabel.setFont(new Font("����", Font.BOLD, 18));
		this.add(panelTipLabel);

		JLabel loginActLabel = new JLabel("�˺�");
		loginActLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, panelTipLabel.getY() + panelTipLabel.getHeight(), 40,
				20);
		loginActLabel.setFont(new Font("����", Font.BOLD, 16));
		this.add(loginActLabel);
		loginActText = new JTextField();
		loginActText.setBounds(loginActLabel.getX() + 50, loginActLabel.getY(), 140, 25);
		this.add(loginActText);

		JLabel nameLabel = new JLabel("�ǳ�");
		nameLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, loginActLabel.getY() + loginActLabel.getHeight() + 20, 40,
				20);
		nameLabel.setFont(new Font("����", Font.BOLD, 16));
		this.add(nameLabel);
		nameText = new JTextField();
		nameText.setBounds(nameLabel.getX() + 50, nameLabel.getY(), 140, 25);
		this.add(nameText);

		JLabel loginPwdLabel = new JLabel("����");
		loginPwdLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, nameLabel.getY() + nameLabel.getHeight() + 20, 40, 20);
		loginPwdLabel.setFont(new Font("����", Font.BOLD, 16));
		this.add(loginPwdLabel);
		loginPwdText = new JPasswordField();
		loginPwdText.setBounds(loginPwdLabel.getX() + 50, loginPwdLabel.getY(), 140, 25);
		this.add(loginPwdText);

		JLabel positionLabel = new JLabel("ְλ");
		positionLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, loginPwdLabel.getY() + loginPwdLabel.getHeight() + 20,
				40, 20);
		positionLabel.setFont(new Font("����", Font.BOLD, 16));
		this.add(positionLabel);
		positionBox.setBounds(positionLabel.getX() + 50, positionLabel.getY(), 140, 25);
		this.add(positionBox);

		confirmRegisterBtn = new JButton("ע��");
		confirmRegisterBtn.setBounds(Global.CANVAS_WIDTH / 2 - 39,
				positionLabel.getY() + positionLabel.getHeight() + 25, 100, 30);
		confirmRegisterBtn.addMouseListener(this);
		this.add(confirmRegisterBtn);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == confirmRegisterBtn) {
			// ��ȡ�˺ţ��ǳƣ����룬ְλ
			String loginAct = loginActText.getText();
			String name = nameText.getText();
			String loginPwd = new String(loginPwdText.getPassword());
			String position = (String) positionBox.getSelectedItem();

			RegisterController registerController = new RegisterController();
			boolean flag = registerController.register(loginAct, name, loginPwd, position);

			if (flag) {
				// System.out.println("ע��ɹ���3�����ת����¼����");
				JOptionPane.showMessageDialog(this, "ע��ɹ���������ת����¼����");
				try {
					Thread.sleep(300);
					this.cardLayout.show(mainJPanel, "loginJPanel");
					// ����ı�
					loginActText.setText("");
					nameText.setText("");
					loginPwdText.setText("");
					positionBox.setSelectedIndex(0);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "�Ƿ���Ϣ�����������룡");
			}
		}
	}
}