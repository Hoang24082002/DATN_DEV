package jsoft.home.order;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.javatuples.Pair;

import jsoft.ShareControl;
import jsoft.objects.OrderObject;
import jsoft.objects.UserObject;

public interface Order extends ShareControl{
	// các chức năng cập nhật
	public boolean addOrder(OrderObject item);
	public boolean editOrder(OrderObject item);
	public boolean delOrder(OrderObject item);
	
	public boolean editProductOrdered(int cart_id);
	public boolean editProductSold(int cart_id);
	
	// các chức năng lấy dữ liệu
	public ResultSet getOrder(int id);
	
	public ArrayList<ResultSet> getOrders(Pair<OrderObject, UserObject> infos);
}
