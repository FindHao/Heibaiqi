package gui;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import sql.Sqloper;

public class Room extends JFrame{
	private static final long serialVersionUID = 1L;
	JLabel Roomname=new JLabel("房间名");
	JTextField inname=new JTextField(10);
	JLabel Visit=new JLabel("是否允许观战");
	ButtonGroup group=new ButtonGroup();
	JRadioButton yes=new JRadioButton("是",true);
	JRadioButton no=new JRadioButton("否",false);
	JButton btn1=new JButton("确认");
	JButton btn2=new JButton("取消");
	String user1;
	public Room(){
		
	}
public void Roomadd(String user1){
	this.user1=user1;
	setResizable(false);
	setSize(200,130);
	setLayout(new BorderLayout());
	setLocation(400, 400);
	Panel p=new Panel();
	group.add(yes);
	group.add(no);
	p.add(Roomname);
	p.add(inname);
	p.add(Visit);
	p.add(yes);
	p.add(no);
	p.add(btn1);
	p.add(btn2);
	
	btn1.addActionListener(new Roominsert());
	btn2.addActionListener(new BackHome());
	
	add(p);
	setVisible(true);
} 
class Roominsert implements ActionListener{
	public void actionPerformed(ActionEvent event) {
		try {
			Sqloper sql=new Sqloper();
			if(sql.find(inname.getText(), false))
				JOptionPane.showMessageDialog(null, "此房间已存在", "请重新输入", JOptionPane.ERROR_MESSAGE);
			else
				if(sql.insertroom(inname.getText(), yes.isSelected(),false,user1,"")){
					JOptionPane.showMessageDialog(null, "新建房间成功", "成功啦", JOptionPane.PLAIN_MESSAGE);
					dispose();
					new Gameing(user1, inname.getText(), true);
				}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
class BackHome implements ActionListener{
	public void actionPerformed(ActionEvent e){
		dispose();
	}
}
}
