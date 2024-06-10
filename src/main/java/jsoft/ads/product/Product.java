package jsoft.ads.product;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.ProductColorObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductSizeObject;
import jsoft.objects.UserObject;

public interface Product extends ShareControl {
	// các chức năng cập nhật
	public boolean addProduct(ProductObject item);
	public boolean editProduct(ProductObject item, PRODUCT_EDIT_TYPE et);
	public boolean delProduct(ProductObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getProduct(int id);
	
	public ArrayList<ResultSet> getProducts(Sextet<ProductObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject> infos, Triplet<PRODUCT_SOFT, ORDER, ADD_END_UPDATE> so);
	
	// các chức năng thêm màu sắc và kích cỡ
	public boolean addProductColor(ProductColorObject item);
	public boolean addProductSize(ProductSizeObject item);
	public boolean editProductColor(ProductColorObject pco, PRODUCTCOLORSIZE_EDIT_TYPE et);
	public boolean editProductSize(ProductSizeObject pso, PRODUCTCOLORSIZE_EDIT_TYPE et);
	public boolean delProductColor(ProductColorObject item);
	public boolean delProductSize(ProductSizeObject item);
}
