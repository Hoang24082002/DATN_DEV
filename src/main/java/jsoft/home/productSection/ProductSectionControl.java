package jsoft.home.productSection;

import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.objects.ProductSectionObject;

public class ProductSectionControl {
	private ProductSectionModel am;

	public ProductSectionControl(ConnectionPool cp) {
		this.am = new ProductSectionModel(cp);
	}

	public ConnectionPool getCP() {
		return this.am.getCP();
	}

	public void releaseConnection() {
		this.am.releaseConnection();
	}

	public ArrayList<ProductSectionObject> getProductSectionObjects() {
		return this.am.getProductSections();
	}
}
