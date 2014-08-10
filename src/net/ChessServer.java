package net;

import java.io.*;
import java.net.*;
import java.util.*;

/** @author Find 这是放棋子服务器 */
public class ChessServer {

	boolean started = false;
	ServerSocket ss = null;
	// 存储接入的client
	List<Client> clients = new ArrayList<Client>();

	public static void main(String[] args) {
		new ChessServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8000);
			started = true;
		} catch (BindException e) {
			System.out.println("端口使用中....");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 处理客户端的连接d
		try {
			while (started) {
				Socket s = ss.accept();
				Client c = new Client(s);
				System.out.println("a client connected!");
				new Thread(c).start();
				clients.add(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** @author Find 多线程处理客户端 */
	class Client implements Runnable {
		private Socket s;
		BufferedReader dis = null;
		PrintWriter dos = null;
		private boolean bConnected = false;
		private String user1 = null;
		private String user2 = null;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new BufferedReader(new InputStreamReader(
						s.getInputStream()));
				dos = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(s.getOutputStream())), true);
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/** @author Find 发送消息，并处理关闭 */
		public void send(String str) {
			dos.println(str);
		}

		public void run() {
			try {
				user1 = dis.readLine();
				System.out.println(user1);
				user2 = dis.readLine();
				System.out.println(user2);
				while (bConnected) {
					String str = dis.readLine();
					System.out.println(str);
					for (int i = 0; i < clients.size(); i++) {
						 Client c = clients.get(i);
						 if (c.user1.equals(this.user2))
						dos.println(str);
					}
				}
			} catch (EOFException e) {
				System.out.println("Client closed!");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (s != null) {
						s.close();
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}
}
