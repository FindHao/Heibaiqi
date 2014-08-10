package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteOrder;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.xml.transform.OutputKeys;
public class Demo02 implements ActionListener{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SizeFrame sf=new SizeFrame();
		//退出时关闭虚拟机
		sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//显示窗体
		sf.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
class SizeFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//构造方法
	public SizeFrame(){
		//边框  普通边框
		Border border=BorderFactory.createEtchedBorder(Color.BLACK,Color.BLUE);
		//边框标题
		Border title=BorderFactory.createTitledBorder(border,"力天教育",TitledBorder.RIGHT,TitledBorder.TOP,new Font("黑体",Font.ITALIC, 16),Color.RED);
		//复选框
		//默认勾选中
		JCheckBox jcb=new JCheckBox("学习",true);
		JCheckBox jcb1=new JCheckBox("读书");
		JCheckBox jcb2=new JCheckBox("写字",new ImageIcon("image/01.png"));
		jcb2.setSelected(true);
		final JPanel jp1=new JPanel();
		//设置勾选中
		//jcb1.setSelected(true);
		jcb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			//	System.out.println("选中了");
				//JOptionPane.showConfirmDialog(jp1,"力天教育");
				//JOptionPane.showMessageDialog(jp1, "力天世纪");
				//JOptionPane.showOptionDialog(jp1, "力天软件", warningString, defaultCloseOperation, defaultCloseOperation, null, eventCache, anchor);
			}
		});
		JPanel jp2=new JPanel();
		jp2.setBorder(border);
		jp1.setBorder(title);
		jp1.add(jcb);
		jp1.add(jcb1);
		jp1.add(jcb2);
		jp2.add(jp1);
		this.add(jp2,BorderLayout.CENTER);
		
		//密码框
		JPasswordField jpf=new JPasswordField(20);
		jpf.setEchoChar('!');
		//文本域
		JTextArea jta=new JTextArea(10,50);
		//将多余的内容自动换行
		jta.setLineWrap(true);
		//滚动条
		JScrollPane jsp=new JScrollPane(jta); 
		this.add(jsp,BorderLayout.SOUTH);
		//将密码加入到窗体中
		this.add(jpf,BorderLayout.NORTH);
		//得到屏幕对象Toolkit
		Toolkit kit=Toolkit.getDefaultToolkit();
		//得到屏幕高
		int screenHeight=kit.getScreenSize().height;
		//得到屏幕宽
		int screenWidht=kit.getScreenSize().width;
		//设置大小
		setSize(screenHeight/2, screenWidht/2);
		//设置位置
		//setLocation(screenHeight/2, screenWidht/2);
		//得到图片
		Image image=kit.getImage("image/browser.png");
		//设置图标
		setIconImage(image);
		//设置标题
		setTitle("力天"); 
	}
}
