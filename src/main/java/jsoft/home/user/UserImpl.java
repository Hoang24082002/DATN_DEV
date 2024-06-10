package jsoft.home.user;

import java.sql.*;

import org.javatuples.*;

import com.mysql.cj.xdevapi.Result;

import jsoft.objects.*;

import jsoft.ads.basic.*;
import jsoft.library.ORDER;
import jsoft.*;

import java.util.*;

public class UserImpl extends BasicImpl implements User {

	public UserImpl(ConnectionPool cp) {
		super(cp, "User");
	}

	@Override
	public boolean addUser(UserObject item) {
		// TODO Auto-generated method stub
		if (this.isExisting(item)) {
//			System.out.println("------------------ADD FAIL isExisting---------------------");
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tbluser( ");
		sql.append("user_name, user_pass, user_fullname, user_birthday, user_mobilephone, ");
		sql.append("user_email, user_address, user_permission, user_created_date, user_last_logined, user_parent_id ");
		sql.append(")");
		
		sql.append("VALUE(?,md5(?),?,?,?,?,?,?,?,?,?)");

		// bien dich
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getUser_name());
			pre.setString(2, item.getUser_pass());
			pre.setString(3, item.getUser_fullname());
			pre.setString(4, item.getUser_birthday());
			pre.setString(5, item.getUser_mobilephone());
			pre.setString(6, item.getUser_email());
			pre.setString(7, item.getUser_address());
			pre.setByte(8, item.getUser_permission());
			pre.setString(9, item.getUser_created_date());
			pre.setString(10, item.getUser_last_logined());
			pre.setInt(11, item.getUser_parent_id());

			return this.add(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// tro ve trang thai an toan cua ket noi
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return false;
	}

	private boolean isExisting(UserObject item) {
		boolean flag = false;
		String sql = "SELECT user_id FROM tblUser WHERE user_name = '" + item.getUser_name() + "'";
		ResultSet rs = this.get(sql, 0);
		if (rs != null) {
			try {
				if (rs.next()) {
					flag = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return flag;
	}

}
