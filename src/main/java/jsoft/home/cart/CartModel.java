package jsoft.home.cart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.objects.CartObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;


public class CartModel {
	private Cart c;
	
	public CartModel(ConnectionPool cp) {
		this.c = new CartImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.c.getCP();
	}

	public void releaseConnection() {
		this.c.releaseConnection();
	}
	
//	----------------------------------
	public boolean addOrUpdateCart(CartObject item) {
		return this.c.addOrUpdateCart(item);
	}

	public boolean editCart(CartObject item) {
		return this.c.editCart(item);
	}

	public boolean delCart(CartObject item) {
		return this.c.delCart(item);
	}
	
//	----------------------------------
	public CartObject getCart(int id) {
		CartObject item = null;
		ResultSet rs = this.c.getCart(id);
		
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new CartObject();
					item.setCart_id(rs.getInt("cart_id"));
					item.setUser_id(rs.getInt("user_id"));
					item.setProduct_id(rs.getInt("product_id"));
					item.setProduct_quantity(rs.getInt("product_quantity"));
					item.setProduct_color(rs.getString("product_color"));
					item.setProduct_size(rs.getString("product_size"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}
	
	public Quartet<ArrayList<CartObject>, ArrayList<ProductObject>, Short, Integer> getCarts(Pair<CartObject, UserObject> infos) {
		ArrayList<CartObject> itemCarts = new ArrayList<>();
		ArrayList<ProductObject> itemProducts = new ArrayList<>();
		CartObject itemCart = null;
		ProductObject itemProduct = null;
		
		ArrayList<ResultSet> res = this.c.getCarts(infos);

		ResultSet rs = res.get(0);
		
		if (rs != null) {
			try {
				while (rs.next()) {
					itemCart = new CartObject();					
					itemCart.setCart_id(rs.getInt("cart_id"));
					itemCart.setUser_id(rs.getInt("user_id"));
					itemCart.setProduct_id(rs.getInt("c.product_id"));
					itemCart.setProduct_quantity(rs.getInt("c.product_quantity"));
					itemCart.setProduct_color(Utilities.decode(rs.getString("c.product_color")));
					itemCart.setProduct_size(rs.getString("c.product_size"));
					itemCart.setProduct_price(rs.getInt("c.product_price"));
					itemCart.setProduct_discount_price(rs.getInt("c.product_discount_price"));
					
					itemProduct = new ProductObject();
					itemProduct.setProduct_id(rs.getInt("p.product_id"));
					itemProduct.setProduct_name(rs.getString("p.product_name"));
					itemProduct.setProduct_image(rs.getString("p.product_image"));
					itemProduct.setProduct_total(rs.getShort("p.product_total"));
					
					itemCarts.add(itemCart);
					itemProducts.add(itemProduct);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		rs = res.get(1);
		short total = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					total = rs.getShort("total");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		rs = res.get(2);
		int orderPrice = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					orderPrice = rs.getInt("orderPrice");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new Quartet<>(itemCarts, itemProducts, total, orderPrice);
	}
	
}
