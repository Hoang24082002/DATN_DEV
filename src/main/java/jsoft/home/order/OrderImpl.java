package jsoft.home.order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.javatuples.Pair;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.objects.OrderObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public class OrderImpl extends BasicImpl implements Order {

	public OrderImpl(ConnectionPool cp) {
		super(cp, "Order-Home");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addOrder(OrderObject item) {
		StringBuffer sql = new StringBuffer();
		// câu lệnh SQL
		sql.append("INSERT INTO tblorder(");
		sql.append("user_id, order_date, order_price, order_address, order_payment_method, order_detail_cartId, order_discount_price ");
		sql.append(")");
		sql.append("VALUE(?, ?, ?, ?, ?, ?, ?)");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getUser_id());
			pre.setString(2, item.getOrder_date());
			pre.setInt(3, item.getOrder_price());
			pre.setString(4, item.getOrder_address());
			pre.setInt(5, item.getOrder_payment_method());
			pre.setString(6, item.getOrder_detail_cartId());
			pre.setInt(7, item.getOrder_discount_price());
			
			return this.add(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sql.toString());
		return false;			
		
	}

	@Override
	public boolean editOrder(OrderObject item) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblorder AS c SET c.order_destroy = 1, c.order_delete_date = ?");
		sql.append(" WHERE c.order_id = ?");
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getOrder_delete_date());
			pre.setInt(2, item.getOrder_id());
			return this.edit(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delOrder(OrderObject item) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblorder WHERE order_id=? AND user_id=?");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getOrder_id());
			pre.setInt(2, item.getUser_id());
			
			return this.del(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean editProductOrdered(int cart_id) {
	    StringBuffer sql = new StringBuffer();
	    sql.append("UPDATE tblcart AS c SET c.is_product_ordered = 1 ");
	    sql.append(" WHERE c.cart_id = ").append(cart_id);

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			return this.edit(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("editProductOrdered: " + sql.toString());
	    return false;
	}

	@Override
	public ResultSet getOrder(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblorder AS o ");
		sql.append("LEFT JOIN tbluser AS u ON o.user_id = u.user_id ");
		sql.append("WHERE o.order_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getOrders(Pair<OrderObject, UserObject> infos) {
		// đối tượng lưu chữ thông tin lọc kết quả
		OrderObject similar = infos.getValue0();
		
		// tai khoan dang nhap
		UserObject user = infos.getValue1();
		
		// danh sach don hang (0)
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblorder AS o ");
		sql.append("LEFT JOIN tbluser AS u ON o.user_id = u.user_id ");
		sql.append("WHERE o.order_destroy = 0 AND o.user_id = ").append(user.getUser_id());
		sql.append(" ORDER BY o.order_id; ");
		
		// Tong so don hang (1)
		sql.append("SELECT COUNT(order_id) AS total FROM tblorder WHERE order_destroy = 0 AND user_id = ").append(user.getUser_id()).append("; ");
		
		// Gia don hang hien tai (2)
		sql.append("SELECT SUM(product_price) AS orderPrice FROM tblcart WHERE is_product_ordered = 0 AND user_id = ").append(user.getUser_id()).append("; ");
		
		// Lay ID cart - fill detail Order (3)
		sql.append("SELECT * FROM tblcart WHERE is_product_ordered = 0 AND user_id = ").append(user.getUser_id()).append("; ");
		
		// Lay danh cart - da dat hang theo don (4)
		sql.append("SELECT * FROM tblcart WHERE is_product_ordered = 1 AND user_id = ").append(user.getUser_id()).append("; ");
		
		// Lay danh sach san pham trong moi don hang (5)
		sql.append("SELECT * FROM tblcart AS c LEFT JOIN tblproduct AS p ON c.product_id = p.product_id WHERE is_product_ordered = 1 AND c.user_id = ").append(user.getUser_id()).append(";");
		
		// Gia chiet khau don hang hien tai (6)
		sql.append("SELECT SUM(product_discount_price) AS orderDiscountPrice FROM tblcart WHERE is_product_ordered = 0 AND user_id = ").append(user.getUser_id()).append("; ");
		
		System.out.println(sql.toString());
		return this.getMR(sql.toString());
	}

	@Override
	public boolean editProductSold(int cart_id) {
	    StringBuffer sql = new StringBuffer();
	    sql.append("UPDATE tblcart AS c LEFT JOIN tblproduct AS p ON c.product_id = p.product_id SET product_sold = product_sold + product_quantity ");
	    sql.append("WHERE c.cart_id = ?;");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, cart_id);
			return this.edit(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("editProductOrdered: " + sql.toString());
	    return false;
	}

	
}
