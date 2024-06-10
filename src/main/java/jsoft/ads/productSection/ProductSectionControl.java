package jsoft.ads.productSection;

import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

public class ProductSectionControl {
	private ProductSectionModel pgm;

	public ProductSectionControl(ConnectionPool cp) {
		this.pgm = new ProductSectionModel(cp);
	}

	public ConnectionPool getCP() {
		return this.pgm.getCP();
	}

	public void releaseConnection() {
		this.pgm.releaseConnection();
	}

//	------------------------------------------
	public boolean addProductSection(ProductSectionObject item) {
		return this.pgm.addProductSection(item);
	}

	public boolean editProductSection(ProductSectionObject item, PRODUCTSECTION_EDIT_TYPE et) {
		return this.pgm.editProductSection(item, et);
	}

	public boolean delProductSection(ProductSectionObject item) {
		return this.pgm.delProductSection(item);
	}

//	---------------------------------------------
	public ProductSectionObject getProductSection(int id) {
		return this.pgm.getProductSection(id);
	}

//	----------------------------------------------
	public ArrayList<String> viewProductSection (
			Quartet<ProductSectionObject, Short, Byte, UserObject> infos,
			Pair<PRODUCTSECTION_SOFT, ORDER> so) {
		Quintet<ArrayList<ProductSectionObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>, ArrayList<ProductGroupObject>> datas = this.pgm.getProductSections(infos, so);

		return ProductSectionLibrary.viewProductSection(
				datas.getValue0(), 
				infos.getValue0(), 
				infos.getValue3(), 
				datas.getValue2(),
				datas.getValue3(), 
				datas.getValue4(),
				datas.getValue1(),
				infos.getValue1(),
				infos.getValue2());
	}
}
