package jsoft.ads.productGroup;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public class ProductGroupImpl extends BasicImpl implements ProductGroup {

	public ProductGroupImpl(ConnectionPool cp) {
		super(cp, "ProductGroup");
	}

	@Override
	public boolean addProductGroup(ProductGroupObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblproductgroup(");
		sql.append("pg_name, pg_manager_id, pg_notes, pg_created_date, pg_created_author_id ");
		sql.append(")");
		sql.append("VALUE(?, ?, ?, ?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getPg_name());
			pre.setInt(2, item.getPg_manager_id());
			pre.setString(3, item.getPg_notes());
			pre.setString(4, item.getPg_created_date());
			pre.setInt(5, item.getPg_created_author_id());

			return this.add(pre);

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

	@Override
	public boolean editProductGroup(ProductGroupObject item, PRODUCTGROUP_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblproductgroup SET ");

		switch (et) {
		case GENERAL:
			sql.append("pg_name=?,  pg_manager_id=?,  pg_notes=?,  pg_modified_date=?,  pg_enable=? ");
			break;
		case TRASH:
			sql.append("pg_delete=1, pg_deleted_date=?, pg_deleted_author=? ");
			break;
		case RESTORE:
			sql.append("pg_delete=0 ");
			break;
		}
		sql.append(" WHERE pg_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getPg_name());
				pre.setInt(2, item.getPg_manager_id());
				pre.setString(3, item.getPg_notes());
				pre.setString(4, item.getPg_modified_date());
				pre.setBoolean(5, item.isPg_enable());

				pre.setInt(6, item.getPg_id());

				break;
			case TRASH:
				pre.setString(1, item.getPg_deleted_date());
				pre.setString(2, item.getPg_deleted_author());

				pre.setInt(3, item.getPg_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getPg_id());
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

	@Override
	public boolean delProductGroup(ProductGroupObject item) {
		if (!this.isEmpty(item)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblproductgroup WHERE (pg_id=?) AND ((pg_created_author_id=?) OR (pg_manager_id=?))");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getPg_id());
			pre.setInt(2, item.getPg_created_author_id());
			pre.setInt(3, item.getPg_manager_id());

			return this.del(pre);
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

	private boolean isEmpty(ProductGroupObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ps_id FROM tblproductsection WHERE ps_pg_id = ").append(item.getPg_id()).append("; ");
		sql.append("SELECT pc_id FROM tblproductcategory WHERE pc_pg_id = ").append(item.getPg_id()).append("; ");
		sql.append("SELECT product_id FROM tblproduct WHERE product_pg_id = ").append(item.getPg_id()).append("; ");
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
		return flag;
	}

	@Override
	public ResultSet getProductGroup(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblproductgroup AS pg ");
		sql.append("LEFT JOIN tbluser AS u ON pg.pg_manager_id = u.user_id ");
		sql.append("WHERE pg.pg_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getProductGroups(Quartet<ProductGroupObject, Short, Byte, UserObject> infos,
			Pair<PRODUCTGROUP_SOFT, ORDER> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		ProductGroupObject similar = infos.getValue0();

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
		
		// danh sách Product group
		sql.append("SELECT * FROM tblproductgroup AS pg ");
		sql.append("LEFT JOIN tbluser AS u ON pg.pg_manager_id = u.user_id ");
		sql.append(this.createConditions(similar));
		sql.append("");
		switch (so.getValue0()) {
		case NAME:
			sql.append("ORDER BY pg.pg_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY pg.pg_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY pg.pg_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group
		sql.append("SELECT COUNT(pg_id) AS total FROM tblproductgroup ");
		sql.append(this.createConditions(similar));
		sql.append(";");

		// danh sach quyen, phu thuoc vao nguoi dang nhap
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");

		System.out.print(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(ProductGroupObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isPg_delete()) {
				conds.append("(pg_delete=1) ");
			} else {
				conds.append("(pg_delete=0) ");
			}
			
			// tu khoa tim kiem
			String key = similar.getPg_name();
			if(key != null && !key.equalsIgnoreCase("")) {
				conds.append(" AND (");
				conds.append(" (pg_name LIKE '%"+key+"%') OR");
				conds.append(" (pg_notes LIKE '%"+key+"%')");
				conds.append(" )");
			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
	}

}
