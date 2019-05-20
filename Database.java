package application;

import java.sql.*;

public class Database {
	Connection con;

	public Database(String database,String user,String password) {
		super();
		try {
			this.getInstance(database,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int executeQuery(String req) throws Exception {
		Statement stmt = con.createStatement();
		int r = stmt.executeUpdate(req);
		// con.close();
		return r;
	}

	public String[][] excuteReader(String req) throws Exception {

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(req);
		rs.last();
		int nrow = rs.getRow();
		int ncol = rs.getMetaData().getColumnCount();
		rs.beforeFirst();
		String[][] result = new String[nrow][ncol];

		int i = 0;
		while (rs.next()) {
			for (int j = 1; j <= ncol; j++) {
				result[i][j - 1] = rs.getString(j);
			}
			i++;
		}
		// con.close();
		return result;
	}

	private void getInstance(String database,String user,String password) throws Exception {
		if (con != null)
			return;
		Class.forName("com.mysql.jdbc.Driver");
		this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database,user, password);
	}

}
