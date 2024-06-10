package jsoft.home.order;

import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.objects.CartObject;
import jsoft.objects.OrderObject;
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
	public boolean addOrder(OrderObject item) {
		return this.pgm.addOrder(item);
	}

	public boolean editOrder(OrderObject item) {
		return this.pgm.editOrder(item);
	}

	public boolean delOrder(OrderObject item) {
		return this.pgm.delOrder(item);
	}
	
	public boolean editProductOrdered(int cart_id) {
		return this.pgm.editProductOrdered(cart_id);
	}
	
	public boolean editProductSold(int cart_id) {
		return this.pgm.editProductSold(cart_id);
	}

//	---------------------------------------------
	public OrderObject getOrder(int id) {
		return this.pgm.getOrder(id);
	}
	
//	----------------------------------------------
	public ArrayList<String> viewOrder(Pair<OrderObject, UserObject> infos) {
		Septet<ArrayList<OrderObject>, Short, Integer, ArrayList<CartObject>, ArrayList<CartObject>, ArrayList<ProductObject>, Integer> datas = this.pgm.getOrders(infos);
		return OrderLibrary.viewOrder(
				datas.getValue0(),
				datas.getValue1(),
				datas.getValue2(),
				datas.getValue3(),
				datas.getValue4(),
				infos.getValue1(),
				datas.getValue5(),
				datas.getValue6());
		
	}
}
