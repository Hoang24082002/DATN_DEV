package jsoft.ads.order;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import jsoft.ShareControl;
import jsoft.library.ORDER;
import jsoft.objects.OrderObject;
import jsoft.objects.UserObject;

public interface Order extends ShareControl {
	// các chức năng cập nhật
	public boolean editOrder(OrderObject item, ORDER_EDIT_TYPE et);
	// các chức năng lấy dữ liệu
	public ResultSet getOrder(int id);
	
	public ArrayList<ResultSet> getOrders(Quartet<OrderObject, Short, Byte, UserObject> infos, Pair<ORDER_SOFT, ORDER> so);
}
