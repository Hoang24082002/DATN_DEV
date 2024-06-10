package jsoft.ads.productCategory;

import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Quintet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.UserObject;

public class ProductCategoryControl {
	private ProductCategoryModel pcm;

	public ProductCategoryControl(ConnectionPool cp) {
		this.pcm = new ProductCategoryModel(cp);
	}

	public ConnectionPool getCP() {
		return this.pcm.getCP();
	}

	public void releaseConnection() {
		this.pcm.releaseConnection();
	}

//	------------------------------------------
	public boolean addProductCategory(ProductCategoryObject item) {
		return this.pcm.addProductCategory(item);
	}

	public boolean editProductCategory(ProductCategoryObject item, PRODUCTCATEGORY_EDIT_TYPE et) {
		return this.pcm.editProductCategory(item, et);
	}

	public boolean delProductCategory(ProductCategoryObject item) {
		return this.pcm.delProductCategory(item);
	}

//	---------------------------------------------
	public ProductCategoryObject getProductCategory(int id) {
		return this.pcm.getProductCategory(id);
	}

//	----------------------------------------------
	public ArrayList<String> viewProductCategory (
			Quintet<ProductCategoryObject, Short, Byte, UserObject, ProductGroupObject> infos,
			Triplet<PRODUCTCATEGORY_SOFT, ORDER,ADD_END_UPDATE> so) {
		Sextet<ArrayList<ProductCategoryObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>, ArrayList<ProductGroupObject>, ArrayList<ProductSectionObject>> datas = this.pcm.getProductCategorys(infos, so);

		return ProductCategoryLibrary.viewProductCategory(
				datas.getValue0(), 
				infos.getValue0(), 
				infos.getValue3(), 
				datas.getValue2(),
				datas.getValue3(), 
				datas.getValue4(), 
				datas.getValue5(), 
				so.getValue2(), 
				infos.getValue4(),
				datas.getValue1(),
				infos.getValue1(),
				infos.getValue2());
	}
}
