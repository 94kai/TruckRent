package com.truck.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.truck.Util.JDBCUtil;
import com.truck.model.Truck;
import com.truck.model.TruckSource;
import com.truck.model.User;

public class TruckSourceDao {
	public int addTruckSource(TruckSource truckSource) {
		ResultSet rs = null;
		int executeUpdate = -1;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_truck_source( load_date,start_place,stop_place,start_place_point,introcduce,publish_date,state,truck_id) values(?,?,?,?,?,?,?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, truckSource.getLoad_date());
			pstm.setString(2, truckSource.getStart_place());
			pstm.setString(3, truckSource.getStop_place());
			pstm.setString(4, truckSource.getStart_place_point());
			pstm.setString(5, truckSource.getIntrocduce());
			pstm.setString(6, System.currentTimeMillis() + "");
			pstm.setString(7, truckSource.getState());
			pstm.setString(8, truckSource.getTruck_id());
			executeUpdate = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return queryLastTruckSourceId();
	}

	/**
	 * 查询当前数据库中最后一条数据的id 也就是刚添加的
	 * 
	 * @return
	 */
	private int queryLastTruckSourceId() {
		int id = -1;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_truck_source  ";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
			} // 循环到最后一条
			rs.last();
			id = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return id;
	}

	public boolean addTruckSourceUserRelation(String userId, String truckSourceId) {
		ResultSet rs = null;
		int executeUpdate = -1;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_user_trucksource(userid,trucksourceid) values(?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, userId);
			pstm.setString(2, truckSourceId);
			executeUpdate = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return executeUpdate > 0 ? true : false;
	}

	public List<TruckSource> queryAllTruckSourceByUserid(String userid) {
		ResultSet rs = null;
		ArrayList<TruckSource> truckSources = new ArrayList<TruckSource>();
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_user_trucksource where userid=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, userid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				String truckSourceid = rs.getString(2);
				TruckSource truckSrouce = queryTruckSourceByTruckSourceid(truckSourceid);
				truckSources.add(truckSrouce);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return truckSources;
	}

	public TruckSource queryTruckSourceByTruckSourceid(String truckSourceid) {
		UserDao userDao=new UserDao();
		ResultSet rs = null;
		TruckSource truckSource = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_trucksource where _id=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, truckSourceid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				truckSource = new TruckSource();
				truckSource.setLoad_date(rs.getString(2));
				truckSource.setStart_place(rs.getString(3));
				truckSource.setStop_place(rs.getString(4));
				truckSource.setStart_place_point(rs.getString(5));
				truckSource.setIntrocduce(rs.getString(6));
				truckSource.setPublish_date(rs.getString(7));
				truckSource.setState(rs.getString(8));
				//通过trucksourceid 查询userid
				String turcksourceid = rs.getString(1);
				String userId = queryUserIdByTruckSourceId(truckSourceid);
				User user = userDao.queryUserById(userId);
				truckSource.setUser(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return truckSource;
	}

	public List<TruckSource> queryAllTruckSourceByCondition(String startplace, String stopplace, String starttime) {
		TruckDao truckDao = new TruckDao();
		UserDao userDao=new UserDao();
		ResultSet rs = null;
		TruckSource truckSource = null;
		ArrayList<TruckSource> truckSources = new ArrayList<TruckSource>();
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_truck_source where start_place like ? and stop_place like ? and load_date like ? order by publish_date desc";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, startplace);
			pstm.setString(2, stopplace);
			pstm.setString(3, starttime);
			rs = pstm.executeQuery();
			while (rs.next()) {
				truckSource = new TruckSource();
				truckSource.setLoad_date(rs.getString(2));
				truckSource.setStart_place(rs.getString(3));
				truckSource.setStop_place(rs.getString(4));
				truckSource.setStart_place_point(rs.getString(5));
				truckSource.setIntrocduce(rs.getString(6));
				truckSource.setPublish_date(rs.getString(7));
				truckSource.setState(rs.getString(8));
				truckSource.setTruck(truckDao.queryTruckByTruckid(rs.getString(9)));
				//通过trucksourceid 查询userid
				String turcksourceid = rs.getString(1);
				String userId = queryUserIdByTruckSourceId(turcksourceid);
				User user = userDao.queryUserById(userId);
				truckSource.setUser(user);
				truckSources.add(truckSource);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return truckSources;
	}

	public String queryUserIdByTruckSourceId(String truckId){
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String userId="-1";
		String sql = "select * from tb_user_trucksource where trucksourceid = ? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, truckId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				userId=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return userId;
	}
	
}
