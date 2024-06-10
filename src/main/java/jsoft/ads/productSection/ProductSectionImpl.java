package jsoft.ads.productSection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

public class ProductSectionImpl extends BasicImpl implements ProductSection {

	public ProductSectionImpl(ConnectionPool cp) {
		super(cp, "ProductSection");
	}

	@Override
	public boolean addProductSection(ProductSectionObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblproductsection(");
		sql.append("ps_name, ps_pg_id, ps_manager_id, ps_notes, ps_created_date, ");
		sql.append("ps_image, ps_created_author_id )");
		sql.append("VALUE(?, ?, ?, ?, ?, ?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getPs_name());
			pre.setInt(2, item.getPs_pg_id());
			pre.setInt(3, item.getPs_manager_id());
			pre.setString(4, item.getPs_notes());
			pre.setString(5, item.getPs_created_date());
			pre.setString(6, item.getPs_image());
			pre.setInt(7, item.getPs_created_author_id());

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
	public boolean editProductSection(ProductSectionObject item, PRODUCTSECTION_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblproductsection SET ");

		switch (et) {
		case GENERAL:
			sql.append("ps_name=?,  ps_pg_id=?,  ps_manager_id=?,  ps_notes=?,  ps_modified_date=?, ");
			sql.append("ps_image=?,  ps_enable=? ");
			break;
		case TRASH:
			sql.append("ps_delete=1, ps_deleted_date=?, ps_deleted_author=? ");
			break;
		case RESTORE:
			sql.append("ps_delete=0 ");
			break;
		}
		sql.append(" WHERE ps_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getPs_name());
				pre.setInt(2, item.getPs_pg_id());
				pre.setInt(3, item.getPs_manager_id());
				pre.setString(4, item.getPs_notes());
				pre.setString(5, item.getPs_modified_date());
				pre.setString(6, item.getPs_image());
				pre.setBoolean(7, item.isPs_enable());

				pre.setInt(8, item.getPs_id());

				break;
			case TRASH:
				pre.setString(1, item.getPs_deleted_date());
				pre.setString(2, item.getPs_deleted_author());

				pre.setInt(3, item.getPs_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getPs_id());
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
	public boolean delProductSection(ProductSectionObject item) {
		if (!this.isEmpty(item)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblproductsection WHERE (ps_id=?) AND ((ps_created_author_id=?) OR (ps_manager_id=?))");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getPs_id());
			pre.setInt(2, item.getPs_created_author_id());
			pre.setInt(3, item.getPs_manager_id());

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

	private boolean isEmpty(ProductSectionObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT pc_id FROM tblproductcategory WHERE pc_pg_id = ").append(item.getPs_id()).append("; ");
		sql.append("SELECT product_id FROM tblproduct WHERE product_pg_id = ").append(item.getPs_id()).append("; ");
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
	public ResultSet getProductSection(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblproductsection AS ps ");
		sql.append("LEFT JOIN tbluser AS u ON ps.ps_manager_id = u.user_id ");
		sql.append("WHERE ps.ps_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getProductSections(Quartet<ProductSectionObject, Short, Byte, UserObject> infos,
			Pair<PRODUCTSECTION_SOFT, ORDER> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		ProductSectionObject similar = infos.getValue0();

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
		sql.append("SELECT * FROM tblproductsection AS ps ");
		sql.append("LEFT JOIN tbluser AS u ON ps.ps_manager_id = u.user_id ");
		sql.append(this.createConditions(similar));
		sql.append("");
		switch (so.getValue0()) {
		case NAME:
			sql.append("ORDER BY ps.ps_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY ps.ps_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY ps.ps_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group
		sql.append("SELECT COUNT(ps_id) AS total FROM tblproductsection ");
		sql.append(this.createConditions(similar));
		sql.append(";");

		// danh sach quyen, phu thuoc vao nguoi dang nhap
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");
		
		// danh sach nhom san pham
		sql.append("SELECT * FROM tblproductgroup ORDER BY pg_id DESC ;");

		System.out.print(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(ProductSectionObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isPs_delete()) {
				conds.append("(ps_delete=1) ");
			} else {
				conds.append("(ps_delete=0) ");
			}
			
			// tu khoa tim kiem
			String key = jsoft.library.Utilities.decode(similar.getPs_name());
			System.out.println("productImpl - key: " + similar.getPs_name());
			if(key != null && !key.equalsIgnoreCase("")) {
				conds.append(" AND (");
				conds.append(" (ps_name LIKE '%"+key+"%') OR");
				conds.append(" (ps_notes LIKE '%"+key+"%')");
				conds.append(" )");
			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
	}

}
