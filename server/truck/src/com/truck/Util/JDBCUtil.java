package com.truck.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	private static final String Driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/truck_system?useUnicode=true&characterEncoding=utf-8";
	private static final String user = "root";
	private static final String pwd = "root";

	public static Connection getCon() {
		Connection con = null;
		try {
			Class.forName(Driver);
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("连接成功!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JDBCUtil.getCon();
	}

}
