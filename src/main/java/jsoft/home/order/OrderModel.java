package jsoft.home.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.objects.CartObject;
import jsoft.objects.OrderObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;


public class OrderModel {
	private Order c;
	
	public OrderModel(ConnectionPool cp) {
		this.c = new OrderImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.c.getCP();
	}

	public void releaseConnection() {
		this.c.releaseConnection();
	}
	
//	----------------------------------
	public boolean addOrder(OrderObject item) {
		return this.c.addOrder(item);
	}

	public boolean editOrder(OrderObject item) {
		return this.c.editOrder(item);
	}

	public boolean delOrder(OrderObject item) {
		return this.c.delOrder(item);
	}
	
	public boolean editProductOrdered(int cart_id) {
		return this.c.editProductOrdered(cart_id);
	}
	
	public boolean editProductSold(int cart_id) {
		return this.c.editProductSold(cart_id);
	}
	
//	----------------------------------
	public OrderObject getOrder(int id) {
		OrderObject item = null;
		ResultSet rs = this.c.getOrder(id);
		
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
	
	public Septet<ArrayList<OrderObject>, Short, Integer, ArrayList<CartObject>, ArrayList<CartObject>, ArrayList<ProductObject>, Integer> getOrders(Pair<OrderObject, UserObject> infos) {
		ArrayList<OrderObject> itemOrders = new ArrayList<>();
		OrderObject itemOrder = null;
		
		ArrayList<ResultSet> res = this.c.getOrders(infos);

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
					itemOrders.add(itemOrder);
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
		
		// Lay ID cart - fill detail Order
		rs = res.get(3);	
		ArrayList<CartObject> itemCartIdOrdereds = new ArrayList<>();
		CartObject itemCart = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemCart = new CartObject();					
					itemCart.setCart_id(rs.getInt("cart_id"));

					itemCartIdOrdereds.add(itemCart);
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
		
		// Lay danh sach san pham trong moi don hang
		rs = res.get(5);	
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
		
		// Gia chiet khau don hang hien tai (6)
		rs = res.get(6);
		int orderDiscountPrice = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					orderDiscountPrice = rs.getInt("orderDiscountPrice");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new Septet(itemOrders, total, orderPrice, itemCartIdOrdereds, itemCartOrdereds, itemProductOrdereds, orderDiscountPrice);
	}
	
}
