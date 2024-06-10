package jsoft.ads.color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ColorObject;
import jsoft.objects.UserObject;

public class ColorModel {

	private Color c;

	public ColorModel(ConnectionPool cp) {
		this.c = new ColorImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.c.getCP();
	}

	public void releaseConnection() {
		this.c.releaseConnection();
	}

//	----------------------------------
	public boolean addColor(ColorObject item) {
		return this.c.addColor(item);
	}

	public boolean editColor(ColorObject item, COLOR_EDIT_TYPE et) {
		return this.c.editColor(item, et);
	}

	public boolean delColor(ColorObject item) {
		return this.c.delColor(item);
	}

//	----------------------------------
	public ColorObject getColor(int id) {
		ColorObject item = null;
		ResultSet rs = this.c.getColor(id);
		
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new ColorObject();
					item.setC_id(rs.getShort("c_id"));
					item.setC_name(rs.getString("c_name"));
					item.setC_manager_id(rs.getInt("c_manager_id"));
					item.setC_notes(rs.getString("c_notes"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Quartet<ArrayList<ColorObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> getColors(
			Quartet<ColorObject, Short, Byte, UserObject> infos, Pair<COLOR_SOFT, ORDER> so) {

		HashMap<Integer, String> managerName = new HashMap<>();

		ArrayList<ColorObject> items = new ArrayList<>();

		ColorObject item = null;

		ArrayList<ResultSet> res = this.c.getColors(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new ColorObject();
					item.setC_id(rs.getShort("c_id"));
					item.setC_name(rs.getString("c_name"));
					item.setC_manager_id(rs.getInt("c_manager_id"));
					item.setC_notes(rs.getString("c_notes"));
					item.setC_delete(rs.getBoolean("c_delete"));
					item.setC_created_date(rs.getString("c_created_date"));
					item.setC_deleted_date(rs.getString("c_deleted_date"));
					item.setC_modified_date(rs.getString("c_modified_date"));
					item.setC_deleted_author(rs.getString("c_deleted_author"));
					item.setC_created_author_id(rs.getInt("c_created_author_id"));
					
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
