package com.truck.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.truck.Util.JDBCUtil;
import com.truck.model.User;

public class UserDao {

	public User query(String phoneNumber, String password) {
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		User user = null;
		String sql = "select * from tb_user where phonenumber=? and password=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phoneNumber);
			pstm.setString(2, password);
			rs = pstm.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setPassword(rs.getString("password"));
				user.setPhoneNumber(rs.getString("phoneNumber"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return user;
	}

	public int addUser(String phoneNumber, String password) {
		ResultSet rs = null;
		User user = new User();
		int executeUpdate = -1;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_user(phonenumber,password) values(?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phoneNumber);
			pstm.setString(2, password);
			executeUpdate = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return -1;
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return executeUpdate;
	}

	public User queryUserById(String userId) {
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		User user = null;
		String sql = "select * from tb_user where phonenumber=?  ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setPhoneNumber(rs.getString("phoneNumber"));
				user.setUsername(rs.getString("username"));
				user.setProvince(rs.getString("province"));
				user.setCity(rs.getString("city"));
				user.setPiecearea(rs.getString("piecearea"));
				user.setIdcard(rs.getString("idcard"));
				user.setAvatarurl(rs.getString("avatarurl"));
				user.setIntroduce(rs.getString("introduce"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return user;
	}

	public boolean updataUserInfo(User user) {
		int count = 0;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "update tb_user set username=? ,city=? ,avatarurl=? ,introduce=? where phonenumber=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, user.getUsername());
			pstm.setString(2, user.getCity());
			pstm.setString(3, user.getAvatarurl());
			pstm.setString(4, user.getIntroduce());
			pstm.setString(5, user.getPhoneNumber());
			count = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return count == 1;
	}
	/**
	 * 通过phone模糊查询用户信息
	 * @param phoneNumber
	 * @return
	 */
	public List<User> queryUserByPhoneNumber(String phoneNumber) {
		ArrayList<User> users = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		User user = null;
		String sql = "select * from tb_user where phonenumber like ?  ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,"%"+ phoneNumber+"%" );
			rs = pstm.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setPhoneNumber(rs.getString("phoneNumber"));
				user.setUsername(rs.getString("username"));
				user.setAvatarurl(rs.getString("avatarurl"));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return users;
	}
	/**
	 * 通过phone查询他所关注的人的id
	 * @param phoneNumber
	 * @return
	 */
	public List<String> queryMyFriend(String phoneNumber) {
		ArrayList<String> friends = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		User user = null;
		String sql = "select * from tb_user_cared where phone = ?  ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, phoneNumber );
			rs = pstm.executeQuery();
			while (rs.next()) {
				friends.add(rs.getString("caredphone"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return friends;
	}
	/**
	 * 将一个人设为另一个人的关注
	 * @param phoneNumber
	 * @return
	 */
	public boolean caredAFriend(String myPhone,String otherPhone) {
		int update = 0;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_user_cared(phone,caredphone) values(?,?) ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, myPhone );
			pstm.setString(2, otherPhone );
			update = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
			return update!=0;
		}
	}
	/**
	 * 取消关注某一个人
	 * @param phoneNumber
	 * @return
	 */
	public boolean cancelCaredAFriend(String myPhone,String otherPhone) {
		int update=0;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "delete  from tb_user_cared where phone=? and caredphone=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, myPhone );
			pstm.setString(2, otherPhone );
			 update = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
			return update!=0;
		}
	}


}
