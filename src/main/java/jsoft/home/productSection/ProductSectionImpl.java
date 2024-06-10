package jsoft.home.productSection;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;

public class ProductSectionImpl extends BasicImpl implements ProductSection {

	public ProductSectionImpl(ConnectionPool cp) {
		super(cp, "ProductGroup-Home");
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized ArrayList<ResultSet> getProductSections() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblproductsection WHERE ps_delete = 0 AND ps_enable = 1; ");

		return this.getMR(sql.toString());
	}

}
