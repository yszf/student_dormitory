package cn.edu.xsyu.dorm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDB {

	private String username = null;
	private String password = null;
	private String className = null;
	private String url = null;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public ConnDB() {
		username = "root";
		password = "root";
		className = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/sushe";
	}

	// 获取驱动、数据库连接对象、sql语句对象
	public void getConn() {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 获取查询到的结果集对象
	public ResultSet queryDB(String sql) {
		getConn();
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 增加、修改、删除
	public int updateDB(String sql) {
		int rowCount = -1;
		try {
			getConn();
			rowCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		System.out.println("行计数：" + rowCount);
		return rowCount;
	}

	// 释放资源
	public void closeDB() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
