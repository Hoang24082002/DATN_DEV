package jsoft.ads.productColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Septet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.ads.product.ADD_END_UPDATE;
import jsoft.ads.product.PRODUCT_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductColorObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

public class ProductColorImpl extends BasicImpl implements ProductColor {

	public ProductColorImpl(ConnectionPool cp) {
		super(cp, "ProductColor");
	}

	@Override
	public boolean addProductColor(ProductColorObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblproductcolor(");
		sql.append("c_id, product_id ");
		sql.append(")");
		sql.append("VALUE(?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getC_id());
			pre.setInt(2, item.getProduct_id());

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
	public boolean editProductColor(ProductColorObject item, PRODUCTCOLOR_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblproductcolor SET ");

		switch (et) {
		case GENERAL:
			sql.append("c_id = ?, product_id = ? ");
			break;
		case TRASH:
			sql.append("pg_delete=1 ");
			break;
		case RESTORE:
			sql.append("pg_delete=0 ");
			break;
		}
		sql.append(" WHERE pc_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setInt(1, item.getC_id());
				pre.setInt(2, item.getProduct_id());

				pre.setInt(3, item.getPc_id());

				break;
			case TRASH:
//				pre.setString(1, item.getPg_deleted_date());
//				pre.setString(2, item.getPg_deleted_author());
//
//				pre.setInt(3, item.getPg_id());

				break;
			case RESTORE:
//				pre.setInt(1, item.getPg_id());
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
	public boolean delProductColor(ProductColorObject item) {
//		if (!this.isEmpty(item)) {
//			return false;
//		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblproductcolor WHERE pc_id=?");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getPc_id());

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

//	private boolean isEmpty(ProductColorObject item) {
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
	public ResultSet getProductColor(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblproductcolor AS pc ");
		sql.append("LEFT JOIN tblproduct AS p ON pc.product_id = p.product_id ");
		sql.append("LEFT JOIN tblcolor AS c ON pc.c_id = p.c_id ");
		sql.append("WHERE pc.pc_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getProductColors(Septet<ProductColorObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject, ProductCategoryObject> infos, 
			Triplet<PRODUCT_SOFT, ORDER, ADD_END_UPDATE> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		ProductColorObject similar = infos.getValue0();

		// số bản ghi đc lấy trong 1 lần
		byte total = infos.getValue2();

		// vị trí bắt đầu lấy bản ghi
		int at = (infos.getValue1() - 1) * total;

		// tai khoan dang nhap
		UserObject user = infos.getValue3();
		
		// thanh phan san pham khi select item nhom san pham
		ProductGroupObject productGroup = infos.getValue4();
		
		// danh muc san pham khi select item thanh phan san pham
		ProductSectionObject productSection = infos.getValue5();
		
		// san pham khi select item thanh phan san pham
		ProductCategoryObject productCategory = infos.getValue6();
		
		StringBuffer sql = new StringBuffer();
		
		// danh sách Product color
		sql.append("SELECT * FROM tblproductcolor AS pc ");
		sql.append("LEFT JOIN tblproduct AS p ON pc.product_id = p.product_id ");
		sql.append("LEFT JOIN tblcolor AS c ON pc.c_id = p.c_id ");
		sql.append(this.createConditions(similar));
		sql.append("");
		switch (so.getValue0()) {
		case NAME:
			sql.append("ORDER BY p.product_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY p.product_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY pc.pc_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group
		sql.append("SELECT COUNT(pc_id) AS total FROM tblproductcolor;");

		// danh sach quyen, phu thuoc vao nguoi dang nhap
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");

		// danh sach nhom san pham (3)
		sql.append("SELECT * FROM tblproductgroup ORDER BY pg_id DESC ;");
		
		// danh sach thanh phan theo nhom san pham (4)
		sql.append("SELECT * FROM tblproductsection WHERE ").append("ps_pg_id = ").append(productGroup.getPg_id()).append(" ORDER BY ps_id; ");
		
		// danh sach danh muc theo thanh phan san pham (5)
		sql.append("SELECT * FROM tblproductcategory WHERE ").append("pc_ps_id = ").append(productSection.getPs_id()).append(" ORDER BY pc_id; ");
		
		// danh sach danh muc theo thanh phan san pham (5)
		sql.append("SELECT * FROM tblproductcategory WHERE ").append("pc_ps_id = ").append(productSection.getPs_id()).append(" ORDER BY pc_id; ");

		return this.getMR(sql.toString());
	}

	private String createConditions(ProductColorObject similar) {
		StringBuilder conds = new StringBuilder();

//		if (similar != null) {
//
//			// System.out.println(similar.isUser_delete());
//			if (similar.isPg_delete()) {
//				conds.append("(pg_delete=1) ");
//			} else {
//				conds.append("(pg_delete=0) ");
//			}
//		}
//
//		if (!conds.toString().equalsIgnoreCase("")) {
//			conds.insert(0, " WHERE ");
//		}

		return conds.toString();
	}

}
