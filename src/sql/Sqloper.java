package sql;

import java.sql.*;

import javax.swing.JOptionPane;

/** @author Find 数据库操作 */
public class Sqloper {
	Connection con;
	ResultSet rs = null;

	public Sqloper() throws SQLException {
		connect();
		// print(false);
		// if(insertuser("find", "fdfd", "mail", true))
	}

	public void connect() {// 数据库连接
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(
					"jdbc:mysql://bchine.com:3306/design1", "design1",
					"bojishidai");
			System.out.println("数据库连接成功");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String aname, String akey) throws SQLException {
		String bkey = null;
		if (find(aname, true)) {
			Statement state = con.createStatement();
			rs = state.executeQuery("select * from Finduser where name='"
					+ aname + "'");// 选择
			while (rs.next()) {
				bkey = rs.getString("key");
			}

			if (akey.equals(bkey)) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "密码错误", "请重新输入",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "没有此用户", "请重新输入",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/** @author Find isuser true则进入Finduer表否则Findroom表 */
	public boolean find(String aname, boolean isuser) throws SQLException {
		// 查找这个用户是否存在，或者房间是否存在
		String whichtable = isuser ? "Finduser" : "Findroom";
		Statement state = con.createStatement();
		rs = state.executeQuery("select * from " + whichtable + " where name='"
				+ aname + "'");// 选择
		if (rs.next()) {
			state.close();
			return true;
		}
		state.close();
		return false;
	}

	/** @author Find 插入用户数据 */
	public boolean insertuser(String name, String key, String email,
			boolean isboy) throws SQLException {
		PreparedStatement Statement2 = con
				.prepareStatement("INSERT INTO Finduser VALUES(?,?,?,?,?,?,?,?,?)");
		Statement2.setString(1, name);
		Statement2.setString(2, key);
		Statement2.setString(3, email);
		Statement2.setBoolean(4, true);// isboy
		Statement2.setInt(5, 0);// score
		Statement2.setInt(6, 0);// win
		Statement2.setInt(7, 0);// lose
		Statement2.setInt(8, 0);// tie
		Statement2.setString(9, "");// playroom

		Statement2.executeUpdate();
		Statement2.close();
		return true;
	}

	/** @author Find 插入房间数据 */
	public boolean insertroom(String name, boolean canvisit, boolean isplaying,
			String user1, String user2) throws SQLException {
		PreparedStatement Statement2 = con
				.prepareStatement("INSERT INTO Findroom VALUES(?,?,?,?,?)");
		Statement2.setString(1, name);
		Statement2.setBoolean(2, canvisit);// canvisit 0false 1true;
		Statement2.setBoolean(3, false);// canvisit 0false 1true;
		Statement2.setString(4, user1);
		Statement2.setString(5, user2);
		Statement2.executeUpdate();
		Statement2.close();
		return true;
	}

	/** @author Find 输出数据表 */
	public Object[][] print(boolean isuser) throws SQLException {
		String whichtable = isuser ? "Finduser" : "Findroom";
		Statement state = con.createStatement();
		Object[][] biao = new Object[1][7];
		int len = 0;
		rs = state.executeQuery("select * from " + whichtable);// 选择
		while (rs.next()) {
			if (len >= biao.length) {// 扩展表格
				Object[][] biao2 = new Object[len + 1][8];
				for (int i = 0; i < len; i++)
					biao2[i] = biao[i];
				biao = biao2;
			}
			if (isuser) {
				String sex = rs.getBoolean("sex") ? "男" : "女";
				biao[len] = new Object[] { rs.getString("name"),
						rs.getString("email"), sex, rs.getInt("score"),
						rs.getInt("win"), rs.getInt("lose"), rs.getInt("tie") };
			} else {
				String roomstate = rs.getBoolean("isplaying") ? "游戏中" : "等待加入";
				String roomvisit = rs.getBoolean("canvisit") ? "是" : "否";
				biao[len] = new Object[] { rs.getString("name"), roomstate,
						roomvisit, rs.getString("user1"), rs.getString("user2") };

			}
			len++;

		}
		state.close();
		return biao;
	}

	/** @author Find 删除空房间 */
	public void cleanDeadRoom() throws SQLException {
		Statement state = con.createStatement();
		rs = state
				.executeQuery("select * from Findroom where (user1=''AND user2='')");// 选择
		while (rs.next()) {
			PreparedStatement delete = con
					.prepareStatement("delete from Findroom where(name='"
							+ rs.getString("name") + "')");
			delete.execute();
		}
		state.close();
	}

	/** @author Find 查看房间是否是两个人 */
	public String isHeJoin(String username, String roomName)
			throws SQLException {
		String name1 = "", name2 = "";
		Statement state = con.createStatement();
		rs = state.executeQuery("select * from Findroom where (user1='"
				+ username + "'OR user2='" + username + "')");// 选择
		if (rs.next()) {
			name1 = rs.getString("user2");
			name2 = rs.getString("user1");

		}
		state.close();
		if (name1.equals(username))
			return name2;
		else
			return name1;

	}

	/** @author Find 进入房间 */
	public void joinGame(String roomName, String userName) throws SQLException {
		String name1 = "", user = "";
		Statement state = con.createStatement();
		rs = state.executeQuery("select * from Findroom where name = '"
				+ roomName + "'");// 选择
		if (rs.next()) {
			name1 = rs.getString("user1");
			user = name1.equals("") ? "user1" : "user2";
		}
		state.close();
		PreparedStatement update = con
				.prepareStatement("update Findroom set " + user + " = '"
						+ userName + "' where name= '" + roomName + "'");
		update.execute();
		update.close();
	}
	/**@author Find 离开房间 */
	public void quitGame(String roomName, String userName) throws SQLException {
		String name1 = "", user = "";
		Statement state = con.createStatement();
		rs = state.executeQuery("select * from Findroom where name = '"
				+ roomName + "'");// 选择
		if (rs.next()) {
			name1 = rs.getString("user1");
			user = name1.equals(userName) ? "user1" : "user2";
		}
		state.close();
		PreparedStatement update = con
				.prepareStatement("update Findroom set "+user+" = '' where name= '"
						+ roomName + "'");
		update.execute();
		update.close();
	}
	public void UpdateScore(String user,int x) throws SQLException{
		PreparedStatement update = con
				.prepareStatement("update Finduser set score = '"+x+"' where name= '"
						+ user + "'");
		update.execute();
		update.close();
	}
}
