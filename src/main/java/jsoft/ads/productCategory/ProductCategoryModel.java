package jsoft.ads.productCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.UserObject;

public class ProductCategoryModel {

	private ProductCategory pc;

	public ProductCategoryModel(ConnectionPool cp) {
		this.pc = new ProductCategoryImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.pc.getCP();
	}

	public void releaseConnection() {
		this.pc.releaseConnection();
	}

//	----------------------------------
	public boolean addProductCategory(ProductCategoryObject item) {
		return this.pc.addProductCategory(item);
	}

	public boolean editProductCategory(ProductCategoryObject item, PRODUCTCATEGORY_EDIT_TYPE et) {
		return this.pc.editProductCategory(item, et);
	}

	public boolean delProductCategory(ProductCategoryObject item) {
		return this.pc.delProductCategory(item);
	}

//	----------------------------------
	public ProductCategoryObject getProductCategory(int id) {
		ProductCategoryObject item = null;
		ResultSet rs = this.pc.getProductCategory(id);

		if (rs != null) {
			try {
				if (rs.next()) {
					item = new ProductCategoryObject();
					item.setPc_id(rs.getInt("pc_id"));
					item.setPc_name(rs.getString("pc_name"));
					item.setPc_pg_id(rs.getInt("pc_pg_id"));
					item.setPc_ps_id(rs.getInt("pc_ps_id"));
					item.setPc_manager_id(rs.getInt("pc_manager_id"));
					item.setPc_notes(rs.getString("pc_notes"));
					item.setPc_image(rs.getString("pc_image"));
					item.setPc_enable(rs.getBoolean("pc_enable"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Sextet<ArrayList<ProductCategoryObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>, ArrayList<ProductGroupObject>, ArrayList<ProductSectionObject>> getProductCategorys(
			Quintet<ProductCategoryObject, Short, Byte, UserObject, ProductGroupObject> infos,
			Triplet<PRODUCTCATEGORY_SOFT, ORDER, ADD_END_UPDATE> so) {

		HashMap<Integer, String> managerName = new HashMap<>();

		ArrayList<ProductCategoryObject> items = new ArrayList<>();

		ProductCategoryObject item = null;

		ArrayList<ResultSet> res = this.pc.getProductCategorys(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new ProductCategoryObject();
					item.setPc_id(rs.getInt("pc_id"));
					item.setPc_name(rs.getString("pc_name"));
					item.setPc_pg_id(rs.getInt("pc_pg_id"));
					item.setPc_ps_id(rs.getInt("pc_ps_id"));
					item.setPc_manager_id(rs.getInt("pc_manager_id"));
					item.setPc_notes(rs.getString("pc_notes"));
					item.setPc_delete(rs.getBoolean("pc_delete"));
					item.setPc_deleted_date(rs.getString("pc_deleted_date"));
					item.setPc_deleted_author(rs.getString("pc_deleted_author"));
					item.setPc_modified_date(rs.getString("pc_modified_date"));
					item.setPc_created_date(rs.getString("pc_created_date"));
					item.setPc_image(rs.getString("pc_image"));
					item.setPc_enable(rs.getBoolean("pc_enable"));
					item.setPc_created_author_id(rs.getInt("pc_created_author_id"));

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

		// rs - danh sach thanh phan san pham theo nhom san pham
		rs = res.get(4);
		ArrayList<ProductSectionObject> productSections = new ArrayList<>();
		ProductSectionObject itemProductSection = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemProductSection = new ProductSectionObject();

					itemProductSection.setPs_id(rs.getShort("ps_id"));
					itemProductSection.setPs_name(rs.getString("ps_name"));

					productSections.add(itemProductSection);

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new Sextet(items, total, managerName, users, productGroups, productSections);
	}
}
