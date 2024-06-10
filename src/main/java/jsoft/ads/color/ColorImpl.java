package jsoft.ads.color;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.ColorObject;
import jsoft.objects.UserObject;

public class ColorImpl extends BasicImpl implements Color {

	public ColorImpl(ConnectionPool cp) {
		super(cp, "Color");
	}

	@Override
	public boolean addColor(ColorObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblcolor(");
		sql.append("c_name, c_manager_id, c_notes, c_created_date, c_created_author_id ");
		sql.append(")");
		sql.append("VALUE(?, ?, ?, ?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getC_name());
			pre.setInt(2, item.getC_manager_id());
			pre.setString(3, item.getC_notes());
			pre.setString(4, item.getC_created_date());
			pre.setInt(5, item.getC_created_author_id());

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
	public boolean editColor(ColorObject item, COLOR_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblcolor SET ");

		switch (et) {
		case GENERAL:
			sql.append("c_name=?,  c_manager_id=?,  c_notes=?,  c_modified_date=? ");
			break;
		case TRASH:
			sql.append("c_delete=1, c_deleted_date=?, c_deleted_author=? ");
			break;
		case RESTORE:
			sql.append("c_delete=0 ");
			break;
		}
		sql.append(" WHERE c_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getC_name());
				pre.setInt(2, item.getC_manager_id());
				pre.setString(3, item.getC_notes());
				pre.setString(4, item.getC_modified_date());

				pre.setInt(5, item.getC_id());

				break;
			case TRASH:
				pre.setString(1, item.getC_deleted_date());
				pre.setString(2, item.getC_deleted_author());

				pre.setInt(3, item.getC_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getC_id());
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
		System.out.println("ColorImpl - " + sql.toString());

		return false;
	}

	@Override
	public boolean delColor(ColorObject item) {
		if (!this.isEmpty(item)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblcolor WHERE (c_id=?) AND ((c_created_author_id=?) OR (c_manager_id=?))");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getC_id());
			pre.setInt(2, item.getC_created_author_id());
			pre.setInt(3, item.getC_manager_id());

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

	private boolean isEmpty(ColorObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT pc_id FROM tblproductcolor WHERE c_id = ?");
		ResultSet rs = this.get(sql.toString(), item.getC_id());

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
	public ResultSet getColor(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblcolor AS c ");
		sql.append("LEFT JOIN tbluser AS u ON c.c_manager_id = u.user_id ");
		sql.append("WHERE c.c_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getColors(Quartet<ColorObject, Short, Byte, UserObject> infos,
			Pair<COLOR_SOFT, ORDER> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		ColorObject similar = infos.getValue0();

		// số bản ghi đc lấy trong 1 lần
		byte total = infos.getValue2();

		// vị trí bắt đầu lấy bản ghi
		int at = (infos.getValue1() - 1) * total;

		// tai khoan dang nhap
		UserObject user = infos.getValue3();
		
		StringBuffer sql = new StringBuffer();
		
		// danh sách Product group
		sql.append("SELECT * FROM tblcolor AS c ");
		sql.append("LEFT JOIN tbluser AS u ON c.c_manager_id = u.user_id ");
		sql.append(this.createConditions(similar));
		sql.append("");
		switch (so.getValue0()) {
		case NAME:
			sql.append("ORDER BY c.c_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY c.c_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY c.c_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group
		sql.append("SELECT COUNT(c_id) AS total FROM tblcolor;");

		// danh sach quyen, phu thuoc vao nguoi dang nhap
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");

		System.out.print(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(ColorObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isC_delete()) {
				conds.append("(c_delete=1) ");
			} else {
				conds.append("(c_delete=0) ");
			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
	}

}
