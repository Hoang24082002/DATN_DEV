package jsoft.ads.image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.ImageObject;
import jsoft.objects.UserObject;

public class ImageImpl extends BasicImpl implements Image {

	public ImageImpl(ConnectionPool cp) {
		super(cp, "Image");
	}

	@Override
	public boolean addImage(ImageObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblimage(");
		sql.append("i_name, i_url, user_image, product_image, i_manager_id, ");
		sql.append("i_notes, i_created_date, i_created_author_id ");
		sql.append(")");
		sql.append("VALUE(?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getI_name());
			pre.setString(2, item.getI_url());
			pre.setInt(3, item.getUser_image());
			pre.setInt(4, item.getProduct_image());
			pre.setInt(5, item.getI_manager_id());
			pre.setString(6, item.getI_notes());
			pre.setString(7, item.getI_created_date());
			pre.setInt(8, item.getI_created_author_id());

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
	public boolean editImage(ImageObject item, IMAGE_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblimage SET ");

		switch (et) {
		case GENERAL:
			sql.append("i_name=?, i_url=?, user_image=?, product_image=?, i_manager_id=?, ");
			sql.append("i_notes=?, i_modified_date=?, i_enable=? ");
			break;
		case TRASH:
			sql.append("i_delete=1, i_deleted_date=?, i_deleted_author=? ");
			break;
		case RESTORE:
			sql.append("i_delete=0 ");
			break;
		}
		sql.append(" WHERE i_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getI_name());
				pre.setString(2, item.getI_url());
				pre.setInt(3, item.getUser_image());
				pre.setInt(4, item.getProduct_image());
				pre.setInt(5, item.getI_manager_id());
				pre.setString(6, item.getI_notes());
				pre.setString(7, item.getI_modified_date());
				pre.setBoolean(8, item.isI_enable());

				pre.setInt(9, item.getI_id());

				break;
			case TRASH:
				pre.setString(1, item.getI_deleted_date());
				pre.setString(2, item.getI_deleted_author());

				pre.setInt(3, item.getI_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getI_id());
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
	public boolean delImage(ImageObject item) {
//		if (!this.isEmpty(item)) {
//			return false;
//		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblimage WHERE (i_id=?) AND ((i_created_author_id=?) OR (i_manager_id=?))");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getI_id());
			pre.setInt(2, item.getI_created_author_id());
			pre.setInt(3, item.getI_manager_id());

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

	private boolean isEmpty(ImageObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT pi_id FROM tblproductsize WHERE i_id = ?");
		ResultSet rs = this.get(sql.toString(), item.getI_id());

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
	public ResultSet getImage(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblimage AS s ");
		sql.append("LEFT JOIN tbluser AS u ON s.i_manager_id = u.user_id ");
		sql.append("WHERE s.i_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getImages(Quartet<ImageObject, Short, Byte, UserObject> infos,
			Pair<IMAGE_SOFT, ORDER> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		ImageObject similar = infos.getValue0();

		// số bản ghi đc lấy trong 1 lần
		byte total = infos.getValue2();

		// vị trí bắt đầu lấy bản ghi
		int at = (infos.getValue1() - 1) * total;

		// tai khoan dang nhap
		UserObject user = infos.getValue3();
		
		StringBuffer sql = new StringBuffer();
		
		// danh sách Product group
		sql.append("SELECT * FROM tblimage AS i ");
		sql.append("LEFT JOIN tbluser AS u ON i.i_manager_id = u.user_id ");
		sql.append(this.createConditions(similar));
		sql.append("");
		switch (so.getValue0()) {
		case NAME:
			sql.append("ORDER BY i.i_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY i.i_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY i.i_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group
		sql.append("SELECT COUNT(i_id) AS total FROM tblimage;");

		// danh sach quyen, phu thuoc vao nguoi dang nhap
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");

		System.out.print(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(ImageObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isI_delete()) {
				conds.append("(i_delete=1) ");
			} else {
				conds.append("(i_delete=0) ");
			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
	}

}
