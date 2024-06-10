package jsoft.ads.productGroup;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public interface ProductGroup extends ShareControl {
	// các chức năng cập nhật
	public boolean addProductGroup(ProductGroupObject item);
	public boolean editProductGroup(ProductGroupObject item, PRODUCTGROUP_EDIT_TYPE et);
	public boolean delProductGroup(ProductGroupObject item);
	
	// các chức năng lấy dữ liệu
	public ResultSet getProductGroup(int id);
	
	public ArrayList<ResultSet> getProductGroups(Quartet<ProductGroupObject, Short, Byte, UserObject> infos, Pair<PRODUCTGROUP_SOFT, ORDER> so);
}
