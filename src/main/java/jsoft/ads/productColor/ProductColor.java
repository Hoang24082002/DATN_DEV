package jsoft.ads.productColor;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Septet;
import org.javatuples.Triplet;

import jsoft.ShareControl;
import jsoft.ads.product.ADD_END_UPDATE;
import jsoft.ads.product.PRODUCT_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.ProductColorObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductSizeObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.UserObject;

public interface ProductColor extends ShareControl {
	// các chức năng cập nhật
	public boolean addProductColor(ProductColorObject item);
	public boolean editProductColor(ProductColorObject item, PRODUCTCOLOR_EDIT_TYPE et);
	public boolean delProductColor(ProductColorObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getProductColor(int id);
	
	public ArrayList<ResultSet> getProductColors(
			Septet<ProductColorObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject, ProductCategoryObject> infos, 
			Triplet<PRODUCT_SOFT, ORDER, ADD_END_UPDATE> so);
}
