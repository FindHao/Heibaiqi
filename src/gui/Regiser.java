package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import sql.Sqloper;

/** @author Find 注册界面 */
public class Regiser extends JFrame {
	// 组件创建
	JButton regiser = new JButton("注册");
	JButton login = new JButton("登录");
	JLabel label1 = new JLabel("用户名");
	JTextField inname = new JTextField(10);
	JLabel label2 = new JLabel("密码");
	JPasswordField pwd = new JPasswordField(10);
	JLabel label3 = new JLabel("请确认密码");
	JPasswordField pwd2 = new JPasswordField(10);
	JLabel label4 = new JLabel("邮箱");
	JTextField mail = new JTextField(20);
	ButtonGroup group = new ButtonGroup();
	JRadioButton male = new JRadioButton("男", true);
	JRadioButton female = new JRadioButton("女", false);
	// 存储注册信息的变量
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 300, WIDTH = 200;

	public Regiser() {
		// 设置窗体
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setLocation(300, 200);
		// 创建
		Panel p = new Panel();
		Panel logp = new Panel();
		Panel sexchoice = new Panel();
		group.add(male);
		group.add(female);
		// 添加
		p.add(login);
		p.add(regiser);
		logp.setLayout(new BoxLayout(logp, BoxLayout.Y_AXIS));
		logp.add(label1);
		logp.add(inname);
		logp.add(label2);
		logp.add(pwd);
		logp.add(label3);
		logp.add(pwd2);
		logp.add(label4);
		logp.add(mail);
		sexchoice.add(male);
		sexchoice.add(female);
		logp.add(sexchoice);
		add(logp, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
		// 监听器
		regiserbtn rebtn = new regiserbtn();// 注册按钮
		regiser.addActionListener(rebtn);
		flistener bulistener = new flistener();// 返回登录
		login.addActionListener(bulistener);
		WindowDestroyer loginListener = new WindowDestroyer();// 销毁
		addWindowListener(loginListener);
		setVisible(true);

	}

	class WindowDestroyer extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			dispose();
			new Login();
		}
	}

	class flistener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			dispose();
			new Login();
		}
	}

	class regiserbtn implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Sqloper sql = new Sqloper();
				if (sql.find(inname.getText(), true))
					JOptionPane.showMessageDialog(null, "此用户名已存在", "请重新输入",
							JOptionPane.ERROR_MESSAGE);
				else if (sql.insertuser(inname.getText(),
						new String(pwd.getPassword()), mail.getText(),
						male.isSelected())) {
					JOptionPane.showMessageDialog(null, "注册成功", "成功啦",
							JOptionPane.PLAIN_MESSAGE);
					dispose();
					new Home(inname.getText());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
