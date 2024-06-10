package jsoft.home.productCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.objects.ProductCategoryObject;

public class ProductCategoryModel {
	private ProductCategory pg;
	
	public ProductCategoryModel(ConnectionPool cp) {
		this.pg = new ProductCategoryImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.pg.getCP();
	}

	public void releaseConnection() {
		this.pg.releaseConnection();
	}
	
	// lay danh sach thanh phan san pham
	public ArrayList<ProductCategoryObject> getProductCategorys() {
		ArrayList<ProductCategoryObject> items = new ArrayList<>();
		ProductCategoryObject item = null;
		
		ArrayList<ResultSet> res = this.pg.getProductCategorys();
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					item = new ProductCategoryObject();
					item.setPc_id(rs.getInt("pc_id"));
					item.setPc_name(rs.getString("pc_name"));
					item.setPc_pg_id(rs.getInt("pc_pg_id"));
					item.setPc_ps_id(rs.getInt("pc_ps_id"));
					
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
