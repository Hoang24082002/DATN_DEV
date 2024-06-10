package jsoft.ads.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.ProductColorObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductSizeObject;
import jsoft.objects.UserObject;

public class ProductImpl extends BasicImpl implements Product {

	public ProductImpl(ConnectionPool cp) {
		super(cp, "Product");
	}

	@Override
	public boolean addProduct(ProductObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblproduct(");
		sql.append("product_name, product_image, product_price, product_discount_price, product_total, ");
		sql.append("product_manager_id, product_intro, product_notes, product_created_date, product_pg_id, ");
		sql.append("product_ps_id, product_pc_id, product_promotion_price, product_best_seller, product_style, ");
		sql.append("product_created_author_id ");
		sql.append(" )");
		sql.append("VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getProduct_name());
			pre.setString(2, item.getProduct_image());
			pre.setInt(3, item.getProduct_price());
			pre.setInt(4, item.getProduct_discount_price());
			pre.setShort(5, item.getProduct_total());
			pre.setInt(6, item.getProduct_manager_id());
			pre.setString(7, item.getProduct_intro());
			pre.setString(8, item.getProduct_notes());
			pre.setString(9, item.getProduct_created_date());
			pre.setInt(10, item.getProduct_pg_id());
			pre.setInt(11, item.getProduct_ps_id());
			pre.setInt(12, item.getProduct_pc_id());
			pre.setInt(13, item.getProduct_promotion_price());
			pre.setBoolean(14, item.isProduct_best_seller());
			pre.setString(15, item.getProduct_style());
			pre.setInt(16, item.getProduct_created_author_id());

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
	public boolean editProduct(ProductObject item, PRODUCT_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblproduct SET ");

		switch (et) {
		case GENERAL:
			sql.append("product_name=?, product_image=?, product_price=?, product_discount_price=?, product_enable=?,  ");
			sql.append("product_total=?, product_manager_id=?, product_intro=?, product_notes=?, product_modified_date=?, ");
			sql.append("product_pg_id=?, product_ps_id=?, product_pc_id=?, product_is_detail=?, product_promotion_price=?, ");
			sql.append("product_best_seller=?, product_promotion=?, product_style=? ");
			break;
		case TRASH:
			sql.append("product_delete=1, product_deleted_date=?, product_deleted_author=? ");
			break;
		case RESTORE:
			sql.append("product_delete=0 ");
			break;
		}
		sql.append(" WHERE product_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getProduct_name());
				pre.setString(2, item.getProduct_image());
				pre.setInt(3, item.getProduct_price());
				pre.setInt(4, item.getProduct_discount_price());
				pre.setBoolean(5, item.isProduct_enable());
				
				pre.setShort(6, item.getProduct_total());
				pre.setInt(7, item.getProduct_manager_id());
				pre.setString(8, item.getProduct_intro());
				pre.setString(9, item.getProduct_notes());
				pre.setString(10, item.getProduct_modified_date());
				
				pre.setInt(11, item.getProduct_pg_id());
				pre.setInt(12, item.getProduct_ps_id());
				pre.setInt(13, item.getProduct_pc_id());
				pre.setBoolean(14, item.isProduct_is_detail());
				pre.setInt(15, item.getProduct_promotion_price());
				
				pre.setBoolean(16, item.isProduct_best_seller());
				pre.setBoolean(17, item.isProduct_promotion());
				pre.setString(18, item.getProduct_style());

				pre.setInt(19, item.getProduct_id());

				break;
			case TRASH:
				pre.setString(1, item.getProduct_deleted_date());
				pre.setString(2, item.getProduct_deleted_author());

				pre.setInt(3, item.getProduct_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getProduct_id());
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
	public boolean delProduct(ProductObject item) {
		if (!this.isEmpty(item)) {
			System.out.println("ProductImpl-delProduct"+this.isEmpty(item));
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblproduct WHERE (product_id=?) AND ((product_created_author_id=?) OR (product_manager_id=?))");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getProduct_id());
			pre.setInt(2, item.getProduct_created_author_id());
			pre.setInt(3, item.getProduct_manager_id());

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

	private boolean isEmpty(ProductObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ps_id FROM tblproductsize WHERE product_id = ").append(item.getProduct_id()).append("; ");
		sql.append("SELECT pc_id FROM tblproductcolor WHERE product_id = ").append(item.getProduct_id()).append("; ");
		sql.append("SELECT i_id FROM tblimage WHERE product_image = ").append(item.getProduct_id()).append("; ");
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
	public ResultSet getProduct(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblproduct AS p ");
		sql.append("LEFT JOIN tbluser AS u ON p.product_manager_id = u.user_id ");
		sql.append("WHERE p.product_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getProducts(Sextet<ProductObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject> infos, Triplet<PRODUCT_SOFT, ORDER, ADD_END_UPDATE> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		ProductObject similar = infos.getValue0();

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
		
		// danh muc san pham khi select item thanh phan san pham
		ProductSectionObject productSection = infos.getValue5();
		
		StringBuffer sql = new StringBuffer();
		
		// danh sách Product group
		sql.append("SELECT * FROM tblproduct AS p ");
		sql.append("LEFT JOIN tbluser AS u ON p.product_manager_id = u.user_id ");
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
			sql.append("ORDER BY p.product_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group (1)
		sql.append("SELECT COUNT(product_id) AS total FROM tblproduct;");

		// danh sach quyen, phu thuoc vao nguoi dang nhap (2)
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");
		
		// danh sach nhom san pham (3)
		sql.append("SELECT * FROM tblproductgroup ORDER BY pg_id DESC ;");
		
		// danh sach thanh phan theo nhom san pham (4)
		if(productGroup.getPg_id() > 0) {
			sql.append("SELECT * FROM tblproductsection WHERE ").append("ps_pg_id = ").append(productGroup.getPg_id()).append(" ORDER BY ps_id; ");	
		} else {
			sql.append("SELECT * FROM tblproductsection ORDER BY ps_id; ");	
		}
		
		// danh sach danh muc theo thanh phan san pham (5)
		if(productGroup.getPg_id() > 0) {
			sql.append("SELECT * FROM tblproductcategory WHERE ").append("pc_ps_id = ").append(productSection.getPs_id()).append(" ORDER BY pc_id; ");			
		} else {
			sql.append("SELECT * FROM tblproductcategory ORDER BY pc_id; ");	
		}
		
		// danh sach mau sac (6)
		sql.append("SELECT * FROM tblcolor WHERE c_delete = 0 ORDER BY c_id ASC; ");
		
		// danh sach kich co (7)
		sql.append("SELECT * FROM tblsize WHERE s_delete = 0 ORDER BY s_id ASC; ");
		
		// danh sach mau sac cua san pham (8)
		sql.append("SELECT * FROM tblcolor AS c ");
		sql.append("LEFT JOIN tblproductcolor AS pc ON c.c_id = pc.c_id ");
		sql.append("LEFT JOIN tblproduct AS p ON pc.product_id = p.product_id WHERE c.c_delete = 0; ");
		
		// danh sach kich co san pham (9)
		sql.append("SELECT * FROM tblsize AS s ");
		sql.append("LEFT JOIN tblproductsize AS ps ON s.s_id = ps.s_id ");
		sql.append("LEFT JOIN tblproduct AS p ON ps.product_id = p.product_id WHERE s.s_delete = 0; ");
		
		System.out.println(sql.toString());
		return this.getMR(sql.toString());
	}

	private String createConditions(ProductObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isProduct_delete()) {
				conds.append("(product_delete=1) ");
			} else {
				conds.append("(product_delete=0) ");
			}
			
			// tu khoa tim kiem
			String key = similar.getProduct_name();
			if(key != null && !key.equalsIgnoreCase("")) {
				conds.append(" AND (");
				conds.append(" (product_name LIKE '%"+key+"%') OR");
				conds.append(" (product_notes LIKE '%"+key+"%')");
				conds.append(" )");
			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
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
	public boolean addProductSize(ProductSizeObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblproductsize(");
		sql.append("s_id, product_id ");
		sql.append(")");
		sql.append("VALUE(?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getS_id());
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
	public boolean editProductColor(ProductColorObject pco, PRODUCTCOLORSIZE_EDIT_TYPE et) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editProductSize(ProductSizeObject pso, PRODUCTCOLORSIZE_EDIT_TYPE et) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delProductColor(ProductColorObject item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delProductSize(ProductSizeObject item) {
		// TODO Auto-generated method stub
		return false;
	}	

}
