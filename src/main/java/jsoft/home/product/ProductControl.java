package jsoft.home.product;

import java.util.ArrayList;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ColorObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class ProductControl {
	private ProductModel am;

	public ProductControl(ConnectionPool cp) {
		this.am = new ProductModel(cp);
	}

	public ConnectionPool getCP() {
		return this.am.getCP();
	}

	public void releaseConnection() {
		this.am.releaseConnection();
	}

	public ProductObject getProduct(int id) {
		return this.am.getProduct(id);
	}
	
	public ArrayList<String> viewProduct(Quintet<ProductObject, Short, Byte, UserObject, String> infos, Pair<PRODUCT_SORT, ORDER> so, boolean isDetail) {
		Decade<
			ArrayList<ProductGroupObject>, 
			ArrayList<ProductObject>, 
			ArrayList<ProductObject>, 
			ArrayList<ProductObject>,
			ArrayList<ProductSectionObject>,
			ArrayList<ProductCategoryObject>,
			ArrayList<SizeObject>,
			ArrayList<ColorObject>,
			ArrayList<ProductObject>,
			Integer> datas = this.am.getProductObjects(infos, so, isDetail);
		
		return ProductLibrary.viewProduct(
				datas.getValue0(), 
				datas.getValue1(), 
				datas.getValue2(),
				datas.getValue3(),
				datas.getValue4(),
				datas.getValue5(),
				datas.getValue6(),
				datas.getValue7(),
				datas.getValue8(),
				infos.getValue0(),
				infos.getValue3(),
				infos.getValue4(),
				datas.getValue9(),
				infos.getValue1(),
				infos.getValue2()); 
				
	}
}
