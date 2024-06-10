package jsoft.home.productGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.objects.ProductGroupObject;

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
	
	// lay danh sach nhom san pham
	public ArrayList<ProductGroupObject> getProductGroups() {
		ArrayList<ProductGroupObject> items = new ArrayList<>();
		ProductGroupObject item = null;
		
		ArrayList<ResultSet> res = this.pg.getProductGroups();
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					item = new ProductGroupObject();
					item.setPg_id(rs.getInt("pg_id"));
					item.setPg_name(rs.getString("pg_name"));
					
					items.add(item);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return items;
	}
	
}
