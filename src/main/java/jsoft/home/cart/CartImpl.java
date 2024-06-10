package jsoft.home.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.objects.CartObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public class CartImpl extends BasicImpl implements Cart {

	public CartImpl(ConnectionPool cp) {
		super(cp, "Cart-Home");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addOrUpdateCart(CartObject item) {
		System.out.println(this.checkEmptyCart(item));
		StringBuffer sql = new StringBuffer();
		if(!this.checkEmptyCart(item)) {
			sql.append("UPDATE tblcart AS c SET c.product_quantity = c.product_quantity + 1, c.product_price = c.product_price +").append(item.getProduct_price()).append(", c.product_discount_price = c.product_discount_price +").append(item.getProduct_discount_price());
			sql.append(" WHERE c.product_id = ").append(item.getProduct_id());
			sql.append(" AND c.product_color LIKE '").append(item.getProduct_color()).append("' ");
			sql.append(" AND c.product_size LIKE '").append(item.getProduct_size()).append("' ");
			System.out.println(item.getProduct_color() + item.getProduct_size());
			
			try {
				PreparedStatement pre = this.con.prepareStatement(sql.toString());
				return this.edit(pre);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
		} else {
			// câu lệnh SQL
//			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO tblcart(");
			sql.append("user_id, product_id, product_color, product_size, product_price, product_discount_price ");
			sql.append(")");
			sql.append("VALUE(?, ?, ?, ?, ?, ?)");
			try {
				PreparedStatement pre = this.con.prepareStatement(sql.toString());
				pre.setInt(1, item.getUser_id());
				pre.setInt(2, item.getProduct_id());
				pre.setString(3, item.getProduct_color());
				pre.setString(4, item.getProduct_size());
				pre.setInt(5, item.getProduct_price());
				pre.setInt(6, item.getProduct_discount_price());
				
				return this.add(pre);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(sql.toString());
			return false;			
		}
	}

	@Override
	public boolean editCart(CartObject item) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblcart AS c SET c.product_quantity = c.product_quantity + 1, c.product_price = c.product_price +").append(item.getProduct_price());
		sql.append(" WHERE c.product_id = ").append(item.getProduct_id());
		sql.append(" AND c.product_color == ").append(item.getProduct_color());
		sql.append(" AND c.product_size == ").append(item.getProduct_size());
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			return this.edit(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delCart(CartObject item) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblcart WHERE cart_id=? AND user_id=?");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getCart_id());
			pre.setInt(2, item.getUser_id());
			
			return this.del(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResultSet getCart(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblcart AS c ");
		sql.append("LEFT JOIN tblproduct AS p ON c.product_id = p.product_id ");
		sql.append("WHERE c.cart_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getCarts(Pair<CartObject, UserObject> infos) {
		// đối tượng lưu chữ thông tin lọc kết quả
		CartObject similar = infos.getValue0();
		
		// tai khoan dang nhap
		UserObject user = infos.getValue1();
		
		// danh sach san pham trong gio hang (0)
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblcart AS c ");
		sql.append("LEFT JOIN tblproduct AS p ON c.product_id = p.product_id ");
		sql.append("WHERE c.is_product_ordered = 0 AND c.user_id = ").append(user.getUser_id());
		sql.append(" ORDER BY c.cart_id; ");
		
		// so san pham trong gio hang (1)
		sql.append("SELECT SUM(product_quantity) AS total FROM tblcart WHERE is_product_ordered = 0 AND user_id = ").append(user.getUser_id()).append("; ");
		
		
		// Gia don hang hien tai (2)
		sql.append("SELECT SUM(product_price) AS orderPrice FROM tblcart WHERE is_product_ordered = 0 AND user_id = ").append(user.getUser_id()).append("; ");
				
		System.out.println(sql.toString());
		return this.getMR(sql.toString());
	}

	private boolean checkEmptyCart(CartObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblcart AS c ");		
		sql.append(" WHERE c.is_product_ordered = 0 AND c.product_id = ").append(item.getProduct_id());
		sql.append(" AND c.product_color LIKE '").append(item.getProduct_color()).append("'");
		sql.append(" AND c.product_size LIKE '").append(item.getProduct_size()).append("'");
		ArrayList<ResultSet> res = this.getMR(sql.toString());
		for (ResultSet rs : res) {
			try {
				if (rs.next()) {
					flag = false;
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(sql.toString());
		return flag;
	}
	
}
