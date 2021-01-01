package r.view.global;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame {
	// �����
	private JFrame jFrame;
	// �����
	private JPanel mainJPanel;
	// ��Ƭ����
	private CardLayout cardLayout;
	// ��¼���
	private LoginJPanel loginJPanel;
	// ע�����
	private RegisterJPanel registerJPanel;

	public MainFrame() {
		// �˳���¼������µ�¼���˺�
		jFrame = new JFrame();
		mainJPanel = new JPanel();
		cardLayout = new CardLayout();
		mainJPanel.setLayout(cardLayout);
		loginJPanel = new LoginJPanel(mainJPanel, cardLayout);
		registerJPanel = new RegisterJPanel(mainJPanel, cardLayout);

		// ��Ƭ���ƣ�������ơ���Ƭ����show()ʱҪ��
		// ��Ƭ���֣���ʼʱ��ʾ������ӵ����
		mainJPanel.add(loginJPanel, "loginJPanel");
		mainJPanel.add(registerJPanel, "registerJPanel");

		JPanel container = (JPanel) jFrame.getContentPane();
		container.add(mainJPanel);

		int x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - Global.CANVAS_WIDTH / 2;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - Global.CANVAS_HEIGHT / 2;

		jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/�ִ��ֿ�.png"));

		// jFrame.setUndecorated(true);
		// jFrame.setExtendedState(jFrame.ICONIFIED);
		// jFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.setBounds(x, y, Global.CANVAS_WIDTH, Global.CANVAS_HEIGHT);
		jFrame.setResizable(false);
		jFrame.setTitle("���ʹ���ϵͳ");
		jFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int flag = JOptionPane.showConfirmDialog(jFrame, "ȷ���˳�������", "��ʾ", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (flag == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					return;
				}
			}
		});
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
