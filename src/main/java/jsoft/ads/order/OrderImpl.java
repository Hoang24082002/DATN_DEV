package jsoft.ads.order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.OrderObject;
import jsoft.objects.UserObject;

public class OrderImpl extends BasicImpl implements Order {

	public OrderImpl(ConnectionPool cp) {
		super(cp, "Order");
	}
	
	@Override
	public boolean editOrder(OrderObject item, ORDER_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblorder	 SET ");

		switch (et) {
		case GENERAL:
			sql.append("order_status=? ");
			break;
		case TRASH:
			sql.append("order_destroy=1 ");
			break;
		case RESTORE:
			sql.append("order_destroy=0 ");
			break;
		}
		sql.append(" WHERE order_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setInt(1, item.getOrder_status());

				pre.setInt(2, item.getOrder_id());

				break;
			case TRASH:
				pre.setInt(1, item.getOrder_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getOrder_id());
				break;
			}

			return this.edit(pre);

		} catch (SQLException e) {
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return false;
	}

//	@Override
//	public boolean delOrder(OrderObject item) {
//		if (!this.isEmpty(item)) {
//			return false;
//		}
//
//		StringBuffer sql = new StringBuffer();
//		sql.append("DELETE FROM tblproductgroup WHERE (pg_id=?) AND ((pg_created_author_id=?) OR (pg_manager_id=?))");
//		try {
//			PreparedStatement pre = this.con.prepareStatement(sql.toString());
//			pre.setInt(1, item.getPg_id());
//			pre.setInt(2, item.getPg_created_author_id());
//			pre.setInt(3, item.getPg_manager_id());
//
//			return this.del(pre);
//		} catch (SQLException e) {
//			try {
//				this.con.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		}
//		return false;
//	}

//	private boolean isEmpty(OrderObject item) {
//		boolean flag = true;
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT ps_id FROM tblproductsection WHERE ps_pg_id = ").append(item.getPg_id()).append("; ");
//		sql.append("SELECT pc_id FROM tblproductcategory WHERE pc_pg_id = ").append(item.getPg_id()).append("; ");
//		sql.append("SELECT product_id FROM tblproduct WHERE product_pg_id = ").append(item.getPg_id()).append("; ");
//		ArrayList<ResultSet> res = this.getMR(sql.toString());
//
//		for (ResultSet rs : res) {
//			try {
//				if (rs.next()) {
//					flag = false;
//					break;
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return flag;
//	}

	@Override
	public ResultSet getOrder(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblorder AS o ");
		sql.append("LEFT JOIN tbluser AS u ON o.user_id = u.user_id ");
		sql.append("WHERE o.order_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getOrders(Quartet<OrderObject, Short, Byte, UserObject> infos,
			Pair<ORDER_SOFT, ORDER> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		OrderObject similar = infos.getValue0();

		// số bản ghi đc lấy trong 1 lần
		byte total = infos.getValue2();

		// vị trí bắt đầu lấy bản ghi
		int at = (infos.getValue1() - 1) * total;
		if(at<0) {
			at = 0;
		}

		// tai khoan dang nhap
		UserObject user = infos.getValue3();
		
		StringBuffer sql = new StringBuffer();
		
		// danh sach don hang (0)
		sql.append("SELECT * FROM tblorder AS o ");
		sql.append("LEFT JOIN tbluser AS u ON o.user_id = u.user_id ");
		sql.append(this.createConditions(similar));
		sql.append(" ORDER BY o.order_id DESC ");
		sql.append(" LIMIT " + at + ", " + total + ";");
		
		// Tong so don hang (1)
		sql.append("SELECT COUNT(order_id) AS total FROM tblorder; ");
		
		// Gia don hang hien tai (2)
		sql.append("SELECT SUM(product_price) AS orderPrice FROM tblcart WHERE is_product_ordered = 0 AND user_id = ").append(user.getUser_id()).append("; ");
		
		// Lay danh sach san pham trong moi don hang (3)
		sql.append("SELECT * FROM tblcart AS c LEFT JOIN tblproduct AS p ON c.product_id = p.product_id WHERE is_product_ordered = 1; ");
		
		// Lay danh cart - da dat hang theo don (4)
		sql.append("SELECT * FROM tblcart WHERE is_product_ordered = 1; ");

		System.out.print(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(OrderObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isOrder_destroy()) {
				conds.append("(order_destroy=1) ");
			} else {
				conds.append("(order_destroy=0) ");
			}
			
			// tu khoa tim kiem
//			String key = similar.getPg_name();
//			if(key != null && !key.equalsIgnoreCase("")) {
//				conds.append(" AND (");
//				conds.append(" (pg_name LIKE '%"+key+"%') OR");
//				conds.append(" (pg_notes LIKE '%"+key+"%')");
//				conds.append(" )");
//			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
	}

}
