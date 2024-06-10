package jsoft.ads.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ColorObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductColorObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductSizeObject;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class ProductControl {
	private ProductModel pm;

	public ProductControl(ConnectionPool cp) {
		this.pm = new ProductModel(cp);
	}

	public ConnectionPool getCP() {
		return this.pm.getCP();
	}

	public void releaseConnection() {
		this.pm.releaseConnection();
	}

//	------------------------------------------
	public boolean addProduct(ProductObject item) {
		return this.pm.addProduct(item);
	}

	public boolean editProduct(ProductObject item, PRODUCT_EDIT_TYPE et) {
		return this.pm.editProduct(item, et);
	}

	public boolean delProduct(ProductObject item) {
		return this.pm.delProduct(item);
	}
	
//	----------------------------------
	public boolean addProductColor(ProductColorObject item) {
		return this.pm.addProductColor(item);
	}

	public boolean addProductSize(ProductSizeObject item) {
		return this.pm.addProductSize(item);
	}
		

//	---------------------------------------------
	public ProductObject getProduct(int id) {
		return this.pm.getProduct(id);
	}

//	----------------------------------------------
	public ArrayList<String> viewProduct (
			Sextet<ProductObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject> infos, 
			Triplet<PRODUCT_SOFT, ORDER, ADD_END_UPDATE> so) {
				Decade<
					ArrayList<ProductObject>, 
					Short, 
					HashMap<Integer, String>, 
					ArrayList<UserObject>, 
					ArrayList<ProductGroupObject>, 
					ArrayList<ProductSectionObject>, 
					ArrayList<ProductCategoryObject>,
					ArrayList<ColorObject>,
					ArrayList<SizeObject>,
					Pair<ArrayList<ColorObject>, ArrayList<SizeObject>>> datas = this.pm.getProducts(infos, so);

		return ProductLibrary.viewProduct(
				datas.getValue0(), 
				infos.getValue0(), 
				infos.getValue3(), 
				datas.getValue2(),
				datas.getValue3(), 
				datas.getValue4(), 
				datas.getValue5(), 
				datas.getValue6(), 
				so.getValue2(),
				datas.getValue7(),
				datas.getValue8(),
				datas.getValue9().getValue0(),
				datas.getValue9().getValue1(),
				datas.getValue1(),
				infos.getValue1(),
				infos.getValue2());
	}
}
