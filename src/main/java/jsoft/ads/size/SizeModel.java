package jsoft.ads.size;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class SizeModel {

	private Size s;

	public SizeModel(ConnectionPool cp) {
		this.s = new SizeImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.s.getCP();
	}

	public void releaseConnection() {
		this.s.releaseConnection();
	}

//	----------------------------------
	public boolean addSize(SizeObject item) {
		return this.s.addSize(item);
	}

	public boolean editSize(SizeObject item, SIZE_EDIT_TYPE et) {
		return this.s.editSize(item, et);
	}

	public boolean delSize(SizeObject item) {
		return this.s.delSize(item);
	}

//	----------------------------------
	public SizeObject getSize(int id) {
		SizeObject item = null;
		ResultSet rs = this.s.getSize(id);
		
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new SizeObject();
					item.setS_id(rs.getShort("s_id"));
					item.setS_name(rs.getString("s_name"));
					item.setS_manager_id(rs.getInt("s_manager_id"));
					item.setS_notes(rs.getString("s_notes"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Quartet<ArrayList<SizeObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> getSizes(
			Quartet<SizeObject, Short, Byte, UserObject> infos, Pair<SIZE_SOFT, ORDER> so) {

		HashMap<Integer, String> managerName = new HashMap<>();

		ArrayList<SizeObject> items = new ArrayList<>();

		SizeObject item = null;

		ArrayList<ResultSet> res = this.s.getSizes(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new SizeObject();
					item.setS_id(rs.getShort("s_id"));
					item.setS_name(rs.getString("s_name"));
					item.setS_manager_id(rs.getInt("s_manager_id"));
					item.setS_notes(rs.getString("s_notes"));
					item.setS_delete(rs.getBoolean("s_delete"));
					item.setS_created_date(rs.getString("s_created_date"));
					item.setS_deleted_date(rs.getString("s_deleted_date"));
					item.setS_modified_date(rs.getString("s_modified_date"));
					item.setS_deleted_author(rs.getString("s_deleted_author"));
					item.setS_created_author_id(rs.getInt("s_created_author_id"));
					
					items.add(item);

					managerName.put(rs.getInt("user_id"),
							rs.getString("user_fullname") + "(" + rs.getString("user_name") + ")");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		rs = res.get(1);
		short total = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					total = rs.getShort("total");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		rs = res.get(2);
		ArrayList<UserObject> users = new ArrayList<>();
		UserObject user = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					user = new UserObject();
					user.setUser_id(rs.getInt("user_id"));
					user.setUser_name(rs.getString("user_name"));
					user.setUser_fullname(rs.getString("user_fullname"));
					
					users.add(user);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new Quartet<>(items, total, managerName, users);
	}
}
