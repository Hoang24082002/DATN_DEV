package jsoft.ads.order;

import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.CartObject;
import jsoft.objects.OrderObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class OrderControl {
	private OrderModel pgm;

	public OrderControl(ConnectionPool cp) {
		this.pgm = new OrderModel(cp);
	}

	public ConnectionPool getCP() {
		return this.pgm.getCP();
	}

	public void releaseConnection() {
		this.pgm.releaseConnection();
	}

//	------------------------------------------
	public boolean editOrder(OrderObject item, ORDER_EDIT_TYPE et) {
		return this.pgm.editOrder(item, et);
	}

//	---------------------------------------------
	public OrderObject getOrder(int id) {
		return this.pgm.getOrder(id);
	}

//	----------------------------------------------
	public ArrayList<String> viewOrderAdmin (
			Quartet<OrderObject, Short, Byte, UserObject> infos, 
			Pair<ORDER_SOFT, ORDER> so) {
		Sextet<ArrayList<OrderObject>, Short, Integer, ArrayList<UserObject>, ArrayList<CartObject>, ArrayList<ProductObject>> datas = this.pgm.getOrders(infos, so);

		return OrderAdminLibrary.viewOrderAdmin(
				datas.getValue0(),
				infos.getValue0(),
				infos.getValue3(),
				datas.getValue1(),
				infos.getValue1(),
				infos.getValue2(),
				datas.getValue3(),
				datas.getValue4(),
				datas.getValue5());
	}
}
