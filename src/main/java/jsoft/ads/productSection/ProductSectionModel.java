package jsoft.ads.productSection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

public class ProductSectionModel {

	private ProductSection ps;

	public ProductSectionModel(ConnectionPool cp) {
		this.ps = new ProductSectionImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.ps.getCP();
	}

	public void releaseConnection() {
		this.ps.releaseConnection();
	}

//	----------------------------------
	public boolean addProductSection(ProductSectionObject item) {
		return this.ps.addProductSection(item);
	}

	public boolean editProductSection(ProductSectionObject item, PRODUCTSECTION_EDIT_TYPE et) {
		return this.ps.editProductSection(item, et);
	}

	public boolean delProductSection(ProductSectionObject item) {
		return this.ps.delProductSection(item);
	}

//	----------------------------------
	public ProductSectionObject getProductSection(int id) {
		ProductSectionObject item = null;
		ResultSet rs = this.ps.getProductSection(id);

		if (rs != null) {
			try {
				if (rs.next()) {
					item = new ProductSectionObject();
					item.setPs_id(rs.getInt("ps_id"));
					item.setPs_name(rs.getString("ps_name"));
					item.setPs_pg_id(rs.getInt("ps_pg_id"));
					item.setPs_manager_id(rs.getInt("ps_manager_id"));
					item.setPs_notes(rs.getString("ps_notes"));
					item.setPs_image(rs.getString("ps_image"));
					item.setPs_enable(rs.getBoolean("ps_enable"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Quintet<ArrayList<ProductSectionObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>, ArrayList<ProductGroupObject>> getProductSections(
			Quartet<ProductSectionObject, Short, Byte, UserObject> infos, Pair<PRODUCTSECTION_SOFT, ORDER> so) {

		HashMap<Integer, String> managerName = new HashMap<>();

		ArrayList<ProductSectionObject> items = new ArrayList<>();

		ProductSectionObject item = null;

		ArrayList<ResultSet> res = this.ps.getProductSections(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new ProductSectionObject();
					item.setPs_id(rs.getInt("ps_id"));
					item.setPs_name(rs.getString("ps_name"));
					item.setPs_pg_id(rs.getInt("ps_pg_id"));
					item.setPs_manager_id(rs.getInt("ps_manager_id"));
					item.setPs_notes(rs.getString("ps_notes"));
					item.setPs_delete(rs.getBoolean("ps_delete"));
					item.setPs_deleted_date(rs.getString("ps_deleted_date"));
					item.setPs_deleted_author(rs.getString("ps_deleted_author"));
					item.setPs_modified_date(rs.getString("ps_modified_date"));
					item.setPs_created_date(rs.getString("ps_created_date"));
					item.setPs_image(rs.getString("ps_image"));
					item.setPs_enable(rs.getBoolean("ps_enable"));
					item.setPs_created_author_id(rs.getInt("ps_created_author_id"));

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
		if (rs != null) {
			try {
				while (rs.next()) {
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

		// danh sach nhom san pham
		rs = res.get(3);
		ArrayList<ProductGroupObject> productGroups = new ArrayList<>();
		ProductGroupObject productGroup = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					productGroup = new ProductGroupObject();
					productGroup.setPg_id(rs.getInt("pg_id"));
					productGroup.setPg_name(rs.getString("pg_name"));

					productGroups.add(productGroup);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new Quintet(items, total, managerName, users, productGroups);
	}
}
