package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import net.ChatPanel;
import sql.Sqloper;

/** @author Find 游戏界面 */
public class Gameing extends JFrame {
	private static final long serialVersionUID = 1L;
	ChessPanel cpanel = new ChessPanel();
	JPanel lsidePanel = new JPanel();// 左信息面板
	ChatPanel rsidePanel ;// 右信息面板
	JTable inftable;
	ImageIcon ball;
	ImageIcon black_ball = new ImageIcon("src/res/black_ball.png");
	ImageIcon white_ball = new ImageIcon("src/res/white_ball.png");
	ChessList chess_list = new ChessList(true);
	Sqloper datasql = new Sqloper();
	String user1, roomName, user2;
	int score;
	boolean heisjoin = false;
	Thread task = new Thread(new isJoin());// 监听线程来等另一个用户。
	ChessClient isClient;
	public Gameing(String user1, String roomName, boolean isblack)
			throws SQLException {
		super("黑白棋");
		this.user1 = user1;
		this.roomName = roomName;
		
		setResizable(false);
		setSize(1100, 600);
		setLocation(100, 100);
		setLayout(new BorderLayout());
		inflist();
		lsidePanel.setLayout(new BoxLayout(lsidePanel, BoxLayout.Y_AXIS));
		lsidePanel.add(new UserinforPanel(getuser(user1)));

		ball = isblack ? black_ball : white_ball;
		add(cpanel, BorderLayout.CENTER);
		add(lsidePanel, BorderLayout.WEST);
		
		setVisible(true);

		task.start();
	}

	/** @author Find 获取成员信息列表 */
	public void inflist() throws SQLException {
		String[] tablename2 = { "姓名", "邮箱", "性别", "积分", "赢", "输", "平" };
		inftable = new JTable(datasql.print(true), tablename2) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};// 设置表格不可更改
		};
	}

	/** @author Find 获取用户信息 */
	public Object[] getuser(String userName) {
		// DefaultTableModel tableModel = (DefaultTableModel)
		// inftable.getModel();
		JTable tableModel = inftable;
		int rowCount = tableModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			if (tableModel.getValueAt(i, 0).equals(userName)) {
				return new Object[] { tableModel.getValueAt(i, 0),
						tableModel.getValueAt(i, 1),
						tableModel.getValueAt(i, 2),
						tableModel.getValueAt(i, 3),
						tableModel.getValueAt(i, 4),
						tableModel.getValueAt(i, 5),
						tableModel.getValueAt(i, 6) };
			}// 获取用户名字
		}
		return null;
	}

	/** @author Find 带有右键菜单的面板 */
	class ChessPanel extends JPanel {
		/**
		 * @author Find 带有右键菜单的面板
		 */
		private static final long serialVersionUID = 1L;
		JPopupMenu popupMenu1 = new JPopupMenu();
		JMenuItem menuItem1 = new JMenuItem("悔棋");
		JMenuItem menuItem2 = new JMenuItem("加入房间");
		JMenuItem menuItem3 = new JMenuItem("求和");
		JMenuItem menuItem4 = new JMenuItem("不想玩了");

		public ChessPanel() {
			// 鼠标事件
			this.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					try {
						this_mousePressed(e);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			popupMenu1.add(menuItem1);
			menuItem1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			popupMenu1.add(menuItem2);
			popupMenu1.add(menuItem3);
			popupMenu1.add(menuItem4);
			menuItem4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int result=JOptionPane.showConfirmDialog(Gameing.this,"你确定要退出游戏？", "Warning！！", JOptionPane.YES_NO_OPTION);
					if(result==JOptionPane.NO_OPTION)
						return;
					rsidePanel.disconnect();
					Home.notplay=true;
					try {
						datasql.quitGame(roomName, user1);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					dispose();
				}
			});
			add(popupMenu1);
		}

		void this_mousePressed(MouseEvent e) throws SQLException {
			int mods = e.getModifiers();
			// 鼠标右键
			if ((mods & InputEvent.BUTTON3_MASK) != 0) {
				// 弹出菜单
				popupMenu1.show(this, e.getX(), e.getY());
			}
			if ((mods & InputEvent.BUTTON1_MASK) != 0) {
				// 鼠标左键
				// 记得要加一个boolean来判断是不是两个用户
				presschess(getGraphics(), e.getX(), e.getY());
				System.out.println(e.getX() + "\t" + e.getY());
			}
		}

		public void paintComponent(Graphics g) {
			ImageIcon back = new ImageIcon("src/res/chess_board.png");
			g.drawImage(back.getImage(), 0, 0, getSize().width,
					getSize().height, this);
			g.drawImage(black_ball.getImage(), 228, 285, 60, 60, cpanel);
			g.drawImage(black_ball.getImage(), 296, 222, 60, 60, cpanel);
			g.drawImage(white_ball.getImage(), 228, 222, 60, 60, cpanel);
			g.drawImage(white_ball.getImage(), 296, 285, 60, 60, cpanel);
		}

		// 鼠标左键单击
		public void presschess(Graphics g, int xx, int yy) throws SQLException {
			int x = (xx - 24) / 68, y = (yy - 24) / 66;// 坐标转换成数组的索引
			/*
			if(!chess_list.checkset()){
				JOptionPane.showConfirmDialog(Gameing.this,"你输了！", "Warning！！", JOptionPane.YES_NO_OPTION);
				datasql.UpdateScore(user1, 20);
			}*/
				
			if (chess_list.canset(x, y)) {
				boolean[][] step = chess_list.getstep();
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (step[i][j])
							setball(i, j, getGraphics());
					}
				}
			isClient.send(x, y);

			}
		}
		/**@author Find 指定位置放子*/
		public void setball(int x, int y, Graphics g) {
			int xx = x * 68 + 24, yy = y * 66 + 24;
			g.drawImage(ball.getImage(), xx, yy, 60, 60, cpanel);
		}
	}
	/** @author Find 发送棋盘的客户端 */
	public class ChessClient {
		// 用来接收棋盘状态
		Socket s = null;
		BufferedReader dis = null;
		PrintWriter dos = null;
		private boolean bConnected = false;
		private String user1 = null;
		private String user2 = null;
		Thread tRecv = new Thread(new RecvThread());

		public ChessClient(String name1, String name2) {
			user1 = name1;
			user2 = name2;
			connect();
			tRecv.start();
			// send(temp);
		}

		public void disconnect() {
			try {
				dos.close();
				dis.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void connect() {
			try {
				s = new Socket("127.0.0.1", 8000);
				dis = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				dos = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(s.getOutputStream())), true);
				System.out.println("connected!");
				bConnected = true;

				dos.println(user1);
				dos.flush();
				dos.println(user2);
				dos.flush();

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void send(int x,int y){
			dos.print(x);
			dos.print(y);
		}
		private class RecvThread implements Runnable {
			public void run() {
				try {
					while (bConnected) {
//						String str = dis.readLine();
//						chess_list.setchesstable(returnchesstable(str));
						int x=dis.read(),y=dis.read();
						chess_list.setOp(x, y);
						boolean[][] step = chess_list.getstep();
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								if (step[i][j])
									cpanel.setball(i, j, getGraphics());
							}
						}
					}
				} catch (SocketException e) {
					System.out.println("退出了，bye!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public int[][] returnchesstable(String str) {
			int[][] temp = new int[8][8];
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					temp[i][j] = str.charAt(i * 8 + j) - 48;
				}
			}
			return temp;
		}
	}

	/** @author Find 检测另一个用户是否进来这个房间了 */
	public class isJoin implements Runnable {
		String temp;

		public void run() {
			try {
				while (!heisjoin) {
					Thread.sleep(5000);
					temp = datasql.isHeJoin(user1, roomName);
					if (!temp.equals("")) {
						user2 = temp;
						heisjoin = true;
						lsidePanel.add(new UserinforPanel(getuser(user2)));
						lsidePanel.updateUI();
//						这里测试一下，不行就改回来
						rsidePanel=new ChatPanel(user1, user2);
						add(rsidePanel, BorderLayout.EAST);
						rsidePanel.updateUI();
						cpanel.updateUI();
						isClient=new ChessClient(user1, user2);
						isClient.connect();
					}else{
						heisjoin=false;
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}