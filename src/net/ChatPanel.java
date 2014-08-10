package net;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** @author Find 聊天面板*/
public class ChatPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	private boolean bConnected = false;
	Thread tRecv = new Thread(new RecvThread());
	JTextArea chatmap = new JTextArea(45, 12);// 消息展现窗口
	JTextField chattiao = new JTextField();// 消息填写窗口
	String aname;// 我的名字
	String bname;// 对方
	JPanel textpanel = new JPanel();
	JPanel bottompanel = new JPanel();

	public ChatPanel(String aname1, String bname1) {
		super();
		this.aname = aname1;
		this.bname = bname1;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		chatmap.setFont(new Font("黑体", 1, 14));
		chatmap.setLineWrap(true);
		textpanel.setPreferredSize(new Dimension(300, 100));
		JButton send = new JButton("Send");// 单击send发送消息
		chattiao.addActionListener(new TFListener());// 回车发送消息
		send.addActionListener(new TFListener());
		add(chatmap);
		add(textpanel);
		bottompanel.setLayout(new BoxLayout(bottompanel, BoxLayout.X_AXIS));
		bottompanel.add(chattiao);
		bottompanel.add(send);
		add(bottompanel);
		setBounds(50, 50, 200, 300);
		setVisible(true);
		connect();

		tRecv.start();
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
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			System.out.println("connected!");
			bConnected = true;
			dos.writeUTF(aname);
			dos.flush();
			dos.writeUTF(bname);
			dos.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** @author Find 按钮监听 */
	private class TFListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str = chattiao.getText().trim();
			chattiao.setText("");
			if (!str.equals("")) {
				chatmap.append("\n" + aname + "说：" + str);
				try {
					dos.writeUTF(str);
					dos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "请勿发送空消息", "warning",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/** @author Find 接收消息的线程 */
	private class RecvThread implements Runnable {
		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					chatmap.append("\n" + bname + "说：" + str);
				}
			} catch (SocketException e) {
				System.out.println("退出了，bye!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
