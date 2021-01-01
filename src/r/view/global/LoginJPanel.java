package r.view.global;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import r.controller.LoginController;
import r.domain.User;
import r.utils.MD5Util;
import r.utils.MyListeners;
import r.view.manager.global.ManagerJPanel;

public class LoginJPanel extends JPanel implements MyListeners {

	private JPanel mainJPanel;
	private CardLayout cardLayout;
	private JTextField loginActText;
	private JPasswordField loginPwdText;
	private JButton loginBtn;
	private JButton registerBtn;

	public LoginJPanel() {
	}

	public LoginJPanel(JPanel mainJPanel, CardLayout cardLayout) {
		this.mainJPanel = mainJPanel; // 获取主面板,show()时用到
		this.cardLayout = cardLayout; // 获取卡片布局管理器,show()时用到
		this.setLayout(null); // 绝对布局
		JLabel jLabel = new JLabel("物资管理系统MMS", new ImageIcon("src/image/仓储仓库.png"), SwingConstants.LEFT);
		jLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, 50, 200, 50);
		jLabel.setFont(new Font("黑体", Font.BOLD, 20));
		this.add(jLabel);

		JLabel loginActLabel = new JLabel("账号");
		loginActLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, jLabel.getY() + jLabel.getHeight() + 25, 40, 20);
		loginActLabel.setFont(new Font("楷体", Font.BOLD, 16));
		this.add(loginActLabel);
		loginActText = new JTextField();
		loginActText.setBounds(loginActLabel.getX() + 50, loginActLabel.getY(), 140, 25);
		loginActText.addKeyListener(this);
		this.add(loginActText);

		JLabel loginPwdLabel = new JLabel("密码");
		loginPwdLabel.setBounds((Global.CANVAS_WIDTH - 200) / 2, loginActLabel.getY() + loginActLabel.getHeight() + 25,
				40, 20);
		loginPwdLabel.setFont(new Font("楷体", Font.BOLD, 16));
		this.add(loginPwdLabel);
		loginPwdText = new JPasswordField();
		loginPwdText.setBounds(loginPwdLabel.getX() + 50, loginPwdLabel.getY(), 140, 25);
		loginPwdText.addKeyListener(this);
		this.add(loginPwdText);

		loginBtn = new JButton("登录");
		loginBtn.setBounds(Global.CANVAS_WIDTH / 2 - 39, loginPwdLabel.getY() + loginPwdLabel.getHeight() + 25, 100,
				30);
		loginBtn.addMouseListener(this);
		loginBtn.addKeyListener(this);
		this.add(loginBtn);

		registerBtn = new JButton("<html><u>无账号，前往注册</u></html>");
		registerBtn.setBounds(Global.CANVAS_WIDTH / 2 - 59, loginBtn.getY() + loginBtn.getHeight() + 5, 138, 30);
		registerBtn.setFont(new Font("", Font.ITALIC, 13));
		registerBtn.setBorderPainted(false);
		registerBtn.setContentAreaFilled(false);
		registerBtn.addMouseListener(this);
		this.add(registerBtn);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == registerBtn) {
			this.loginActText.setText("");
			this.loginPwdText.setText("");
			this.loginActText.requestFocus();
			this.cardLayout.show(mainJPanel, "registerJPanel");
		} else if (e.getSource() == loginBtn) {
			loginCheck();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			loginCheck();
		}
	}
	
	private void loginCheck() {
		String loginAct = loginActText.getText().trim();
		String loginPwd = new String(loginPwdText.getPassword()).trim();
		if ("".equals(loginAct) || "".equals(loginPwd)) {
			JOptionPane.showMessageDialog(this, "输入非法");
		} else {
			LoginController loginController = new LoginController();
			User inlineUser = loginController.loginCheck(loginAct, MD5Util.getMD5(loginPwd));
			if (inlineUser != null) {
				String userPosition = inlineUser.getPosition();
				// TODO 验证成功之后跳转到相应操作页
				ManagerJPanel managerJPanel = new ManagerJPanel(mainJPanel, cardLayout, loginAct, userPosition);
				mainJPanel.add(managerJPanel, "managerJPanel");
				this.cardLayout.show(mainJPanel, "managerJPanel");
				System.out.println("User:" + inlineUser);
				System.out.println(inlineUser.getPosition());
				this.loginActText.setText("");
				this.loginPwdText.setText("");
				this.loginActText.requestFocus();
			} else {
				// 账户有问题
				JOptionPane.showMessageDialog(this, "登录失败，请检查账号密码/有效期/状态等信息");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

}
