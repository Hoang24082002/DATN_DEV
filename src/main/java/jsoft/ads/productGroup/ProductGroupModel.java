package jsoft.ads.productGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public class ProductGroupModel {

	private ProductGroup pg;

	public ProductGroupModel(ConnectionPool cp) {
		this.pg = new ProductGroupImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.pg.getCP();
	}

	public void releaseConnection() {
		this.pg.releaseConnection();
	}

//	----------------------------------
	public boolean addProductGroup(ProductGroupObject item) {
		return this.pg.addProductGroup(item);
	}

	public boolean editProductGroup(ProductGroupObject item, PRODUCTGROUP_EDIT_TYPE et) {
		return this.pg.editProductGroup(item, et);
	}

	public boolean delProductGroup(ProductGroupObject item) {
		return this.pg.delProductGroup(item);
	}

//	----------------------------------
	public ProductGroupObject getProductGroup(int id) {
		ProductGroupObject item = null;
		ResultSet rs = this.pg.getProductGroup(id);
		
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new ProductGroupObject();
					item.setPg_id(rs.getShort("pg_id"));
					item.setPg_name(rs.getString("pg_name"));
					item.setPg_manager_id(rs.getInt("pg_manager_id"));
					item.setPg_notes(rs.getString("pg_notes"));
					item.setPg_enable(rs.getBoolean("pg_enable"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Quartet<ArrayList<ProductGroupObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> getProductGroups(
			Quartet<ProductGroupObject, Short, Byte, UserObject> infos, Pair<PRODUCTGROUP_SOFT, ORDER> so) {

		HashMap<Integer, String> managerName = new HashMap<>();

		ArrayList<ProductGroupObject> items = new ArrayList<>();

		ProductGroupObject item = null;

		ArrayList<ResultSet> res = this.pg.getProductGroups(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new ProductGroupObject();
					item.setPg_id(rs.getShort("pg_id"));
					item.setPg_name(rs.getString("pg_name"));
					item.setPg_manager_id(rs.getInt("pg_manager_id"));
					item.setPg_notes(rs.getString("pg_notes"));
					item.setPg_delete(rs.getBoolean("pg_delete"));
					item.setPg_created_date(rs.getString("pg_created_date"));
					item.setPg_deleted_date(rs.getString("pg_deleted_date"));
					item.setPg_modified_date(rs.getString("pg_modified_date"));
					item.setPg_deleted_author(rs.getString("pg_deleted_author"));
					item.setPg_enable(rs.getBoolean("pg_enable"));
					item.setPg_created_author_id(rs.getInt("pg_created_author_id"));
					
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
