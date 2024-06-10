package jsoft.ads.productCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public class ProductCategoryImpl extends BasicImpl implements ProductCategory {

	public ProductCategoryImpl(ConnectionPool cp) {
		super(cp, "ProductCategory");
	}

	@Override
	public boolean addProductCategory(ProductCategoryObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblproductcategory(");
		sql.append("pc_name, pc_pg_id, pc_ps_id, pc_manager_id, pc_notes, ");
		sql.append("pc_created_date, pc_image, pc_created_author_id )");
		sql.append("VALUE(?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getPc_name());
			pre.setInt(2, item.getPc_pg_id());
			pre.setInt(3, item.getPc_ps_id());
			pre.setInt(4, item.getPc_manager_id());
			pre.setString(5, item.getPc_notes());
			pre.setString(6, item.getPc_created_date());
			pre.setString(7, item.getPc_image());
			pre.setInt(8, item.getPc_created_author_id());

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
	public boolean editProductCategory(ProductCategoryObject item, PRODUCTCATEGORY_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblproductcategory SET ");

		switch (et) {
		case GENERAL:
			sql.append("pc_name=?, pc_pg_id=?, pc_ps_id=?, pc_manager_id=?, pc_notes=?, ");
			sql.append("pc_modified_date=?, pc_image=?, pc_enable=? ");
			break;
		case TRASH:
			sql.append("pc_delete=1, pc_deleted_date=?, pc_deleted_author=? ");
			break;
		case RESTORE:
			sql.append("pc_delete=0 ");
			break;
		}
		sql.append(" WHERE pc_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getPc_name());
				pre.setInt(2, item.getPc_pg_id());
				pre.setInt(3, item.getPc_ps_id());
				pre.setInt(4, item.getPc_manager_id());
				pre.setString(5, item.getPc_notes());
				pre.setString(6, item.getPc_modified_date());
				pre.setString(7, item.getPc_image());
				pre.setBoolean(8, item.isPc_enable());

				pre.setInt(9, item.getPc_id());

				break;
			case TRASH:
				pre.setString(1, item.getPc_deleted_date());
				pre.setString(2, item.getPc_deleted_author());

				pre.setInt(3, item.getPc_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getPc_id());
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
	public boolean delProductCategory(ProductCategoryObject item) {
		if (!this.isEmpty(item)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblproductcategory WHERE (pc_id=?) AND ((pc_created_author_id=?) OR (pc_manager_id=?))");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getPc_id());
			pre.setInt(2, item.getPc_created_author_id());
			pre.setInt(3, item.getPc_manager_id());

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

	private boolean isEmpty(ProductCategoryObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT product_id FROM tblproduct WHERE product_pc_id = ?");
		ResultSet rs = this.get(sql.toString(), item.getPc_id());

		if (rs != null) {
			try {
				if (rs.next()) {
					flag = false;
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public ResultSet getProductCategory(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblproductcategory AS ps ");
		sql.append("LEFT JOIN tbluser AS u ON ps.pc_manager_id = u.user_id ");
		sql.append("WHERE ps.pc_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getProductCategorys(Quintet<ProductCategoryObject, Short, Byte, UserObject, ProductGroupObject> infos,
			Triplet<PRODUCTCATEGORY_SOFT, ORDER, ADD_END_UPDATE> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		ProductCategoryObject similar = infos.getValue0();

		// số bản ghi đc lấy trong 1 lần
		byte total = infos.getValue2();

		// vị trí bắt đầu lấy bản ghi
		int at = (infos.getValue1() - 1) * total;
		if(at<0) {
			at = 0;
		}
		// tai khoan dang nhap
		UserObject user = infos.getValue3();
		
		// thanh phan san pham khi select item nhom san pham
		ProductGroupObject productGroup = infos.getValue4();
		
		StringBuffer sql = new StringBuffer();
		
		// danh sách Product group
		sql.append("SELECT * FROM tblproductcategory AS ps ");
		sql.append("LEFT JOIN tbluser AS u ON ps.pc_manager_id = u.user_id ");
		sql.append(this.createConditions(similar));
		sql.append("");
		switch (so.getValue0()) {
		case NAME:
			sql.append("ORDER BY ps.pc_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY ps.pc_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY ps.pc_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group 1
		sql.append("SELECT COUNT(pc_id) AS total FROM tblproductcategory ");
		sql.append(this.createConditions(similar));
		sql.append(";");

		// danh sach quyen, phu thuoc vao nguoi dang nhap 2
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");
		
		// danh sach nhom san pham 3
		sql.append("SELECT * FROM tblproductgroup WHERE pg_delete = 0 ORDER BY pg_id ASC ;");
		
		// danh sach thanh phan theo nhom san pham 4
		sql.append("SELECT * FROM tblproductsection WHERE ps_delete = 0 AND ").append("ps_pg_id = ").append(productGroup.getPg_id()).append(" ORDER BY ps_id; ");

		System.out.print(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(ProductCategoryObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isPc_delete()) {
				conds.append("(pc_delete=1) ");
			} else {
				conds.append("(pc_delete=0) ");
			}
			
			// tu khoa tim kiem
			String key = similar.getPc_name();
			if(key != null && !key.equalsIgnoreCase("")) {
				conds.append(" AND (");
				conds.append(" (pc_name LIKE '%"+key+"%') OR");
				conds.append(" (pc_notes LIKE '%"+key+"%')");
				conds.append(" )");
			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
	}

}
