package jsoft.home.cart;

import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ads.productGroup.PRODUCTGROUP_EDIT_TYPE;
import jsoft.objects.CartObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class CartControl {
	private CartModel pgm;

	public CartControl(ConnectionPool cp) {
		this.pgm = new CartModel(cp);
	}

	public ConnectionPool getCP() {
		return this.pgm.getCP();
	}

	public void releaseConnection() {
		this.pgm.releaseConnection();
	}

//	------------------------------------------
	public boolean addOrUpdateCart(CartObject item) {
		return this.pgm.addOrUpdateCart(item);
	}

	public boolean editCart(CartObject item) {
		return this.pgm.editCart(item);
	}

	public boolean delCart(CartObject item) {
		return this.pgm.delCart(item);
	}

//	---------------------------------------------
	public CartObject getCart(int id) {
		return this.pgm.getCart(id);
	}
	
//	----------------------------------------------
	public ArrayList<String> viewCart(Pair<CartObject, UserObject> infos) {
		Quartet<ArrayList<CartObject>, ArrayList<ProductObject>, Short, Integer> datas = this.pgm.getCarts(infos);
		return CartLibrary.viewCart(
				datas.getValue0(), 
				datas.getValue1(), 
				datas.getValue2(),
				infos.getValue0(), 
				infos.getValue1(),
				datas.getValue3());
		
	}
}
