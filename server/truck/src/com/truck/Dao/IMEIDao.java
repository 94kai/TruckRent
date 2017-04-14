package com.truck.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.truck.Util.JDBCUtil;
import com.truck.model.User;

import net.sf.json.JSONObject;

public class IMEIDao {

	/**
	 * 从表中查询一个IMEI PHONENUMBER的关系 如果有 返回true 否则 false
	 * 
	 * @param phoneNumber
	 * @param password
	 * @return
	 */
	public boolean query(String phoneNumber, String IMEI) {
		PreparedStatement pstm = null;
		ResultSet re = null;
		boolean next = false;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_imei where phonenumber=? and imei=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phoneNumber);
			pstm.setString(2, IMEI);
			re = pstm.executeQuery();
			next = re.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, re);
		}
		return next;
	}

	/**
	 * 如果表中存在phonenumber 就更新它 不存在就创建 然后返回boolean 表示是否操作成功
	 * 
	 * @param phoneNumber
	 * @param IMEI
	 * @return
	 */
	public boolean updateIMEI(String phoneNumber, String IMEI) {
		PreparedStatement pstm = null;
		ResultSet re = null;
		boolean next = false;
		int i = -1;
		boolean result = false;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_imei(phonenumber,IMEI) values(?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phoneNumber);
			pstm.setString(2, IMEI);
			i = pstm.executeUpdate();
			if (i >= 0) {
				// 插入成功 return true；
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			// 如果插入的数据存在 会catch 此时更新数据
			sql = "update tb_imei set imei=? where phonenumber=?";
			try {
				pstm = con.prepareStatement(sql);
				pstm.setString(1, IMEI);
				pstm.setString(2, phoneNumber);
				i = pstm.executeUpdate();
				if (i >= 0) {
					// 更新成功 return true；
					result = true;
				} else {
					result = false;
				}
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		} finally {
			JDBCUtil.close(con, pstm, re);
		}
		return result;
	}

	/**
	 * 退出登录 即将phonenumber为参数的那条数据的imei更新为-1
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public boolean deleteIMEI(String phoneNumber) {
		ResultSet rs = null;
		int i = -1;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "update tb_imei set imei=-1 where phonenumber =?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phoneNumber);
			i = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return i > 0;
	}
}
