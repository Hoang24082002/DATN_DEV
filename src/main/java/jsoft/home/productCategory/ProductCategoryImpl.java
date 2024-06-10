package jsoft.home.productCategory;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;

public class ProductCategoryImpl extends BasicImpl implements ProductCategory {

	public ProductCategoryImpl(ConnectionPool cp) {
		super(cp, "ProductGroup-Home");
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized ArrayList<ResultSet> getProductCategorys() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblproductcategory WHERE pc_delete = 0 AND pc_enable = 1; ");

		return this.getMR(sql.toString());
	}

}
