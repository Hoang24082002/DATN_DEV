package jsoft.home.productGroup;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;

public class ProductGroupImpl extends BasicImpl implements ProductGroup {

	public ProductGroupImpl(ConnectionPool cp) {
		super(cp, "ProductGroup-Home");
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized ArrayList<ResultSet> getProductGroups() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblproductgroup WHERE pg_delete = 0 AND pg_enable = 1; ");

		return this.getMR(sql.toString());
	}

}
