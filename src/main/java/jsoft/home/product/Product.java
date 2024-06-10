package jsoft.home.product;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ShareControl;
import jsoft.ads.productSection.PRODUCTSECTION_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public interface Product extends ShareControl {
	// các chức năng lấy dữ liệu
	public ResultSet getProduct(int id);
	
	public ArrayList<ResultSet> getProductObjects(Quintet<ProductObject, Short, Byte, UserObject, String> infos, Pair<PRODUCT_SORT, ORDER> so, boolean isDetail);
}
