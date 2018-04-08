package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDao {

	Connection conn = null;
	Statement stat = null;
	PreparedStatement pstat = null;
	ResultSet rs = null;

	public void getConnection() {
		// 2 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// 3 建立连接
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/school?characterEncoding=utf-8",
							"root", "123456");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getStatement() {
		getConnection();
		// 4 建立sql执行器
		try {
			stat = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPreparedStatement(String sql) {
		getConnection();
		// 4 建立sql执行器
		try {
			pstat = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeAll() {

		try {
			if (conn != null) {
				conn.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (pstat != null) {
				pstat.close();
			}
			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
