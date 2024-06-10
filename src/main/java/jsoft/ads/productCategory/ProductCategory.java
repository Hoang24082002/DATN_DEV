package jsoft.ads.productCategory;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public interface ProductCategory extends ShareControl {
	// các chức năng cập nhật
	public boolean addProductCategory(ProductCategoryObject item);
	public boolean editProductCategory(ProductCategoryObject item, PRODUCTCATEGORY_EDIT_TYPE et);
	public boolean delProductCategory(ProductCategoryObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getProductCategory(int id);
	
	public ArrayList<ResultSet> getProductCategorys(Quintet<ProductCategoryObject, Short, Byte, UserObject, ProductGroupObject> infos, Triplet<PRODUCTCATEGORY_SOFT, ORDER, ADD_END_UPDATE> so);
}
