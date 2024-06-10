package jsoft.home.productGroup;

import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.objects.ProductGroupObject;

public class ProductGroupControl {
	private ProductGroupModel am;

	public ProductGroupControl(ConnectionPool cp) {
		this.am = new ProductGroupModel(cp);
	}

	public ConnectionPool getCP() {
		return this.am.getCP();
	}

	public void releaseConnection() {
		this.am.releaseConnection();
	}

	public ArrayList<ProductGroupObject> getProductGroupObjects() {
		return this.am.getProductGroups();
	}
}
