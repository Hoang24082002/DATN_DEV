//package jsoft.ads.productColor;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import org.javatuples.Pair;
//import org.javatuples.Quartet;
//
//import jsoft.ConnectionPool;
//import jsoft.library.ORDER;
//import jsoft.objects.ProductColorObject;
//import jsoft.objects.UserObject;
//
//public class ProductColorModel {
//
//	private ProductColor pg;
//
//	public ProductColorModel(ConnectionPool cp) {
//		this.pg = new ProductColorImpl(cp);
//	}
//
//	public ConnectionPool getCP() {
//		return this.pg.getCP();
//	}
//
//	public void releaseConnection() {
//		this.pg.releaseConnection();
//	}
//
////	----------------------------------
//	public boolean addProductColor(ProductColorObject item) {
//		return this.pg.addProductColor(item);
//	}
//
//	public boolean editProductColor(ProductColorObject item, PRODUCTCOLOR_EDIT_TYPE et) {
//		return this.pg.editProductColor(item, et);
//	}
//
//	public boolean delProductColor(ProductColorObject item) {
//		return this.pg.delProductColor(item);
//	}
//
////	----------------------------------
//	public ProductColorObject getProductColor(int id) {
//		ProductColorObject item = null;
//		ResultSet rs = this.pg.getProductColor(id);
//		
//		if(rs!=null) {
//			try {
//				if(rs.next()) {
//					item = new ProductColorObject();
//					item.setPg_id(rs.getShort("pg_id"));
//					item.setPg_name(rs.getString("pg_name"));
//					item.setPg_manager_id(rs.getInt("pg_manager_id"));
//					item.setPg_notes(rs.getString("pg_notes"));
//					item.setPg_enable(rs.getBoolean("pg_enable"));
//				}
//				rs.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return item;
//	}
//
//	public Quartet<ArrayList<ProductColorObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> getProductColors(
//			Quartet<ProductColorObject, Short, Byte, UserObject> infos, Pair<PRODUCTCOLOR_SOFT, ORDER> so) {
//
//		HashMap<Integer, String> managerName = new HashMap<>();
//
//		ArrayList<ProductColorObject> items = new ArrayList<>();
//
//		ProductColorObject item = null;
//
//		ArrayList<ResultSet> res = this.pg.getProductColors(infos, so);
//
//		ResultSet rs = res.get(0);
//
//		if (rs != null) {
//			try {
//				while (rs.next()) {
//					item = new ProductColorObject();
//					item.setPg_id(rs.getShort("pg_id"));
//					item.setPg_name(rs.getString("pg_name"));
//					item.setPg_manager_id(rs.getInt("pg_manager_id"));
//					item.setPg_notes(rs.getString("pg_notes"));
//					item.setPg_delete(rs.getBoolean("pg_delete"));
//					item.setPg_created_date(rs.getString("pg_created_date"));
//					item.setPg_deleted_date(rs.getString("pg_deleted_date"));
//					item.setPg_modified_date(rs.getString("pg_modified_date"));
//					item.setPg_deleted_author(rs.getString("pg_deleted_author"));
//					item.setPg_enable(rs.getBoolean("pg_enable"));
//					item.setPg_created_author_id(rs.getInt("pg_created_author_id"));
//					
//					items.add(item);
//
//					managerName.put(rs.getInt("user_id"),
//							rs.getString("user_fullname") + "(" + rs.getString("user_name") + ")");
//				}
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		rs = res.get(1);
//		short total = 0;
//		if (rs != null) {
//			try {
//				if (rs.next()) {
//					total = rs.getShort("total");
//				}
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		rs = res.get(2);
//		ArrayList<UserObject> users = new ArrayList<>();
//		UserObject user = null;
//		if(rs!=null) {
//			try {
//				while(rs.next()) {
//					user = new UserObject();
//					user.setUser_id(rs.getInt("user_id"));
//					user.setUser_name(rs.getString("user_name"));
//					user.setUser_fullname(rs.getString("user_fullname"));
//					
//					users.add(user);
//				}
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return new Quartet<>(items, total, managerName, users);
//	}
//}
