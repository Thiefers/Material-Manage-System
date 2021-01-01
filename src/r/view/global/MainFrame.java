package r.view.global;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame {
	// 主框架
	private JFrame jFrame;
	// 主面板
	private JPanel mainJPanel;
	// 卡片布局
	private CardLayout cardLayout;
	// 登录面板
	private LoginJPanel loginJPanel;
	// 注册面板
	private RegisterJPanel registerJPanel;

	public MainFrame() {
		// 退出登录后可重新登录新账号
		jFrame = new JFrame();
		mainJPanel = new JPanel();
		cardLayout = new CardLayout();
		mainJPanel.setLayout(cardLayout);
		loginJPanel = new LoginJPanel(mainJPanel, cardLayout);
		registerJPanel = new RegisterJPanel(mainJPanel, cardLayout);

		// 卡片名称，组件名称。卡片名称show()时要用
		// 卡片布局，初始时显示最先添加的组件
		mainJPanel.add(loginJPanel, "loginJPanel");
		mainJPanel.add(registerJPanel, "registerJPanel");

		JPanel container = (JPanel) jFrame.getContentPane();
		container.add(mainJPanel);

		int x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - Global.CANVAS_WIDTH / 2;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - Global.CANVAS_HEIGHT / 2;

		jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/仓储仓库.png"));

		// jFrame.setUndecorated(true);
		// jFrame.setExtendedState(jFrame.ICONIFIED);
		// jFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jFrame.setBounds(x, y, Global.CANVAS_WIDTH, Global.CANVAS_HEIGHT);
		jFrame.setResizable(false);
		jFrame.setTitle("物资管理系统");
		jFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int flag = JOptionPane.showConfirmDialog(jFrame, "确认退出程序吗？", "提示", JOptionPane.YES_NO_OPTION,
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
