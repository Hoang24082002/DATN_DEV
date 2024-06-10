package jsoft.home.productCategory;

import java.util.ArrayList;
import jsoft.ConnectionPool;
import jsoft.objects.ProductCategoryObject;


public class ProductCategoryControl {
	private ProductCategoryModel am;

	public ProductCategoryControl(ConnectionPool cp) {
		this.am = new ProductCategoryModel(cp);
	}

	public ConnectionPool getCP() {
		return this.am.getCP();
	}

	public void releaseConnection() {
		this.am.releaseConnection();
	}

	public ArrayList<ProductCategoryObject> getProductCategoryObjects() {
		return this.am.getProductCategorys();
	}

}
