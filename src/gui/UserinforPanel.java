package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/** @author Find查看用户信息的面板 */
public class UserinforPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JLabel boy = new JLabel(new ImageIcon("src/res/boy.gif")),
			girl = new JLabel(new ImageIcon("src/res/girl.gif"));
	private TitledBorder titledBorder;

	public UserinforPanel(Object[] temp) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(0, 1, 0, 0));
		setPreferredSize(new Dimension(200, 250));// 这个可以强制固定大小，不管布局管理器
		boolean isboy = temp[2].equals("男") ? true : false;
		if (isboy)
			add(boy);
		else
			add(girl);
		JPanel panel_2 = new JPanel();// 创建置顶标题的边框面板
		titledBorder = new TitledBorder(null, "玩家:\t" + temp[0],
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("宋体", 1, 18),
				Color.BLUE);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		panel_2.setPreferredSize(new Dimension(200, 200));
		panel_2.setBorder(titledBorder);
		panel_2.add(new JLabel("积分：\t" + temp[3]));
		panel_2.add(new JLabel("赢   ：\t" + temp[4]));
		panel_2.add(new JLabel("输   ：\t" + temp[5]));
		panel_2.add(new JLabel("平   ：\t" + temp[6]));
		panel_2.add(new JLabel("Email：\t" + temp[1]));
		// panel_2.add(new
		// JLabel("<HTML><font color=red>红</font><font color=blue>蓝</font></html>"));
		add(panel_2);

		setBorder(new EmptyBorder(10, 10, 10, 10));
		setVisible(true);
	}
}
