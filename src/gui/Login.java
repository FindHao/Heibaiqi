package gui;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sql.Sqloper;

public class Login extends JFrame{

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT=150,WIDTH=200;
	//创建部分	
	JButton regiser=new JButton("注册");
	JButton login=new JButton("登录");
	Panel p = new Panel();
    Panel logp=new Panel();
    
	JLabel label1=new JLabel("用户名");
	JTextField inname=new JTextField(10);
	JLabel label2=new JLabel("密码");
	JPasswordField pwd=new JPasswordField(10);
public Login(){
//窗体设置	
	setResizable(false);
	setSize(WIDTH, HEIGHT);
	setLayout(new BorderLayout());
	setLocation(300, 200);
	
	logp.setLayout(new BoxLayout(logp,BoxLayout.Y_AXIS));
//添加部分	
    p.add(login);
    p.add(regiser);
	logp.add(label1);
	logp.add(inname);
	logp.add(label2);
	logp.add(pwd);	
	add(logp,BorderLayout.CENTER);
	add(p,BorderLayout.SOUTH);
//监听器部分
	regiser.addActionListener(new flistener());
	login.addActionListener(new loglistener());
	addWindowListener(new WindowDestroyer());
	

	
	setVisible(true);//这个要放到最后。。。。
	
}
class WindowDestroyer extends WindowAdapter{
	public void windowClosing(WindowEvent e){
		
		int result=JOptionPane.showConfirmDialog(Login.this,"你确定要退出游戏？", "Warning！！", JOptionPane.YES_NO_OPTION);
		if(result==JOptionPane.NO_OPTION)
			new Login();
		  System.exit(0);
}
}
/** @author Find 窗体转换*/
class flistener implements ActionListener{

	public void actionPerformed(ActionEvent event) {
		dispose();
		new Regiser();
	}
}
class loglistener implements ActionListener{
	public void actionPerformed(ActionEvent event) {
		try {
			if(new Sqloper().login(inname.getText(),new String(pwd.getPassword()))){
				dispose();
				new Home(inname.getText());
			}else{
				inname.setText(null);
				pwd.setText(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
public static void main(String[] args) {
	new Login();
}
}
