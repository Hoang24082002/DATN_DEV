package jsoft.home.productSection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.objects.ProductSectionObject;

public class ProductSectionModel {
	private ProductSection pg;
	
	public ProductSectionModel(ConnectionPool cp) {
		this.pg = new ProductSectionImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.pg.getCP();
	}

	public void releaseConnection() {
		this.pg.releaseConnection();
	}
	
	// lay danh sach thanh phan san pham
	public ArrayList<ProductSectionObject> getProductSections() {
		ArrayList<ProductSectionObject> items = new ArrayList<>();
		ProductSectionObject item = null;
		
		ArrayList<ResultSet> res = this.pg.getProductSections();
		
		ResultSet rs = res.get(0);
		if(rs!=null) {
			try {
				while(rs.next()) {
					item = new ProductSectionObject();
					item.setPs_id(rs.getInt("ps_id"));
					item.setPs_pg_id(rs.getInt("ps_pg_id"));
					item.setPs_name(rs.getString("ps_name"));
					
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
