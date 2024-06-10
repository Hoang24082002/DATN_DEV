package jsoft.ads.productSection;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

public interface ProductSection extends ShareControl {
	// các chức năng cập nhật
	public boolean addProductSection(ProductSectionObject item);
	public boolean editProductSection(ProductSectionObject item, PRODUCTSECTION_EDIT_TYPE et);
	public boolean delProductSection(ProductSectionObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getProductSection(int id);
	
	public ArrayList<ResultSet> getProductSections(Quartet<ProductSectionObject, Short, Byte, UserObject> infos, Pair<PRODUCTSECTION_SOFT, ORDER> so);
}
