package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import sql.Sqloper;

/**@author Find 游戏大厅，刷新列表*/
public class Home extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 400, WIDTH = 400;
	JTable table;
	JPanel mainpanel = new JPanel();// 主面板
	JPanel tpanel = new JPanel();// 表格面板
	JLabel title = new JLabel("房间列表", JLabel.CENTER);
	static String user1;
	Sqloper alivecheck = new Sqloper();
	static boolean notplay=true;
	public Home(String user1) throws SQLException {

		// 窗体设置
		super("游戏大厅--" + user1);
		Home.user1 = user1;// 传递用户，仅用名字即可
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setLocation(300, 200);
		// setUndecorated(true);//边框
		// 导入数据库里的当前房间列表
		// 创建部分
		mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));
		tpanel.setLayout(new BoxLayout(tpanel, BoxLayout.Y_AXIS));
		mainpanel.add(title);
		mainpanel.add(tpanel);
		roomlist();
		add(mainpanel);
		// 监听器部分

		new smallthread().start(); // 定时刷新
		pack();
		setVisible(true);// 这个要放到最后。。。。
	}

	/*
	 * public static void main(String[] args) throws SQLException { new
	 * Home(user1); }
	 */
	public void roomlist() throws SQLException {
		// 导入数据库里的当前房间
		Sqloper datasql = new Sqloper();
		String[] tablename = { "房间名", "状态", "允许观战", "玩家1", "玩家2" };
		table = new JTable(datasql.print(false), tablename) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};// 设置表格不可更改
		};
		tpanel.add(table.getTableHeader());// 这句用来添加表头！！
		tpanel.add(table);
		tpanel.validate();
		tpanel.repaint();
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {

			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent e) {
				if (e.isMetaDown()) {
					JPopupMenu popupMenu2 = new JPopupMenu();
					JMenuItem menuItem1 = new JMenuItem("新建房间");
					menuItem1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							new Room().Roomadd(user1);
							notplay=false;
						}
					});
					JMenuItem menuItem2 = new JMenuItem("加入房间");
					menuItem2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							notplay=false;
							int selectRows=table.getSelectedRows().length;// 取得用户所选行的行数
							if(selectRows==1){
								int selectedRowIndex = table.getSelectedRow(); // 取得用户所选单行 
								//先进的执黑棋,后进的执白棋，由最后一个参数确定
								try {
									new Gameing(user1, (String)table.getValueAt(selectedRowIndex, 0), false);
									alivecheck.joinGame((String)table.getValueAt(selectedRowIndex, 0),user1);
								} catch (SQLException e) {
									e.printStackTrace();
								}
						}}
					});
					JMenuItem menuItem3 = new JMenuItem("观战");
					JMenuItem menuItem4 = new JMenuItem("不想玩了");
					menuItem4.addActionListener(new WindowDestroy());
					popupMenu2.add(menuItem1);
					popupMenu2.add(menuItem2);
					popupMenu2.add(menuItem3);
					popupMenu2.add(menuItem4);
					popupMenu2.show(table, e.getX(), e.getY());
				}
			}
		});
	}

	/**@author Find 刷新列表，并且检测删除空房间*/
	public class smallthread extends Thread {
		public void run() {
			try {
				while (true) {
					Thread.sleep(3000);
					while(notplay){
					alivecheck.cleanDeadRoom();
					tpanel.remove(table.getTableHeader());
					tpanel.remove(table);
					tpanel.validate();
					tpanel.repaint();
					roomlist();
					pack();
					}
					

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class WindowDestroy implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int result = JOptionPane.showConfirmDialog(rootPane, "你确定要退出游戏？",
					"Warning！！", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.NO_OPTION)
				return;
			System.exit(0);
		}
	}

}
