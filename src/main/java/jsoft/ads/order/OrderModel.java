package jsoft.ads.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.library.Utilities;
import jsoft.objects.CartObject;
import jsoft.objects.OrderObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class OrderModel {

	private Order pg;

	public OrderModel(ConnectionPool cp) {
		this.pg = new OrderImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.pg.getCP();
	}

	public void releaseConnection() {
		this.pg.releaseConnection();
	}

//	----------------------------------

	public boolean editOrder(OrderObject item, ORDER_EDIT_TYPE et) {
		return this.pg.editOrder(item, et);
	}


//	----------------------------------
	public OrderObject getOrder(int id) {
		OrderObject item = null;
		ResultSet rs = this.pg.getOrder(id);
		
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new OrderObject();
					item.setOrder_id(rs.getInt("order_id"));
					item.setUser_id(rs.getInt("user_id"));
					item.setOrder_date(rs.getString("order_date"));
					item.setOrder_price(rs.getInt("order_price"));
					item.setOrder_address(rs.getString("order_address"));
					item.setOrder_payment_method(rs.getInt("order_payment_method"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Sextet<ArrayList<OrderObject>, Short, Integer, ArrayList<UserObject>, ArrayList<CartObject>, ArrayList<ProductObject>> getOrders(Quartet<OrderObject, Short, Byte, UserObject> infos,
			Pair<ORDER_SOFT, ORDER> so) {
		
		ArrayList<OrderObject> itemOrders = new ArrayList<>();
		OrderObject itemOrder = null;
		
		ArrayList<UserObject> userOrdereds = new ArrayList<>();
		UserObject userOrdered = null;
		
		ArrayList<ResultSet> res = this.pg.getOrders(infos, so);

		ResultSet rs = res.get(0);
		
		if (rs != null) {
			try {
				while (rs.next()) {
					itemOrder = new OrderObject();					
					itemOrder.setOrder_id(rs.getInt("order_id"));
					itemOrder.setUser_id(rs.getInt("user_id"));
					itemOrder.setOrder_date(rs.getString("order_date"));
					itemOrder.setOrder_price(rs.getInt("order_price"));
					itemOrder.setOrder_address(rs.getString("order_address"));
					itemOrder.setOrder_payment_method(rs.getInt("order_payment_method"));
					itemOrder.setOrder_detail_cartId(rs.getString("order_detail_cartId"));
					itemOrder.setOrder_status(rs.getInt("order_status"));
					itemOrder.setOrder_destroy(rs.getBoolean("order_destroy"));
					itemOrder.setOrder_delete_date(rs.getString("order_delete_date"));
					itemOrders.add(itemOrder);
					
					userOrdered = new UserObject();
					userOrdered.setUser_id(rs.getInt("u.user_id"));
					userOrdered.setUser_fullname(Utilities.decode(rs.getString("u.user_fullname")));
					userOrdereds.add(userOrdered);
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
		
		// Lay danh sach san pham trong moi don hang
		rs = res.get(3);	
		ArrayList<ProductObject> itemProductOrdereds = new ArrayList<>();
		ProductObject itemProductOrdered = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemProductOrdered = new ProductObject();					
					itemProductOrdered.setProduct_id(rs.getInt("p.product_id"));
					itemProductOrdered.setProduct_name(Utilities.decode(rs.getString("p.product_name")));
					itemProductOrdered.setProduct_image(rs.getString("p.product_image"));

					itemProductOrdereds.add(itemProductOrdered);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// Lay danh cart - da dat hang theo don
		rs = res.get(4);	
		ArrayList<CartObject> itemCartOrdereds = new ArrayList<>();
		CartObject itemCartOrdered = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemCartOrdered = new CartObject();					
					itemCartOrdered.setCart_id(rs.getInt("cart_id"));
					itemCartOrdered.setUser_id(rs.getInt("user_id"));
					itemCartOrdered.setProduct_id(rs.getInt("product_id"));
					itemCartOrdered.setProduct_quantity(rs.getInt("product_quantity"));
					itemCartOrdered.setProduct_color(Utilities.decode(rs.getString("product_color")));
					itemCartOrdered.setProduct_size(rs.getString("product_size"));
					itemCartOrdered.setProduct_price(rs.getInt("product_price"));

					itemCartOrdereds.add(itemCartOrdered);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new Sextet(itemOrders, total, orderPrice, userOrdereds, itemCartOrdereds, itemProductOrdereds);
	}
}
