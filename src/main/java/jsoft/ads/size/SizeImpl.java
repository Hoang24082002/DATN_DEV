package jsoft.ads.size;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;
import jsoft.library.ORDER;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class SizeImpl extends BasicImpl implements Size {

	public SizeImpl(ConnectionPool cp) {
		super(cp, "Size");
	}

	@Override
	public boolean addSize(SizeObject item) {
		// câu lệnh SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tblsize(");
		sql.append("s_name, s_manager_id, s_notes, s_created_date, s_created_author_id ");
		sql.append(")");
		sql.append("VALUE(?, ?, ?, ?, ?)");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getS_name());
			pre.setInt(2, item.getS_manager_id());
			pre.setString(3, item.getS_notes());
			pre.setString(4, item.getS_created_date());
			pre.setInt(5, item.getS_created_author_id());

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
	public boolean editSize(SizeObject item, SIZE_EDIT_TYPE et) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE tblsize SET ");

		switch (et) {
		case GENERAL:
			sql.append("s_name=?,  s_manager_id=?,  s_notes=?,  s_modified_date=? ");
			break;
		case TRASH:
			sql.append("s_delete=1, s_deleted_date=?, s_deleted_author=? ");
			break;
		case RESTORE:
			sql.append("s_delete=0 ");
			break;
		}
		sql.append(" WHERE s_id=?");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			switch (et) {
			case GENERAL:
				pre.setString(1, item.getS_name());
				pre.setInt(2, item.getS_manager_id());
				pre.setString(3, item.getS_notes());
				pre.setString(4, item.getS_modified_date());

				pre.setInt(5, item.getS_id());

				break;
			case TRASH:
				pre.setString(1, item.getS_deleted_date());
				pre.setString(2, item.getS_deleted_author());

				pre.setInt(3, item.getS_id());

				break;
			case RESTORE:
				pre.setInt(1, item.getS_id());
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
	public boolean delSize(SizeObject item) {
		if (!this.isEmpty(item)) {
			return false;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM tblsize WHERE (s_id=?) AND ((s_created_author_id=?) OR (s_manager_id=?))");
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, item.getS_id());
			pre.setInt(2, item.getS_created_author_id());
			pre.setInt(3, item.getS_manager_id());

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

	private boolean isEmpty(SizeObject item) {
		boolean flag = true;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ps_id FROM tblproductsize WHERE s_id = ?");
		ResultSet rs = this.get(sql.toString(), item.getS_id());

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
	public ResultSet getSize(int id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tblsize AS s ");
		sql.append("LEFT JOIN tbluser AS u ON s.s_manager_id = u.user_id ");
		sql.append("WHERE s.s_id = ?");
		return this.get(sql.toString(), id);
	}

	@Override
	public ArrayList<ResultSet> getSizes(Quartet<SizeObject, Short, Byte, UserObject> infos,
			Pair<SIZE_SOFT, ORDER> so) {
		// đối tượng lưu chữ thông tin lọc kết quả
		SizeObject similar = infos.getValue0();

		// số bản ghi đc lấy trong 1 lần
		byte total = infos.getValue2();

		// vị trí bắt đầu lấy bản ghi
		int at = (infos.getValue1() - 1) * total;

		// tai khoan dang nhap
		UserObject user = infos.getValue3();
		
		StringBuffer sql = new StringBuffer();
		
		// danh sách Product group
		sql.append("SELECT * FROM tblsize AS s ");
		sql.append("LEFT JOIN tbluser AS u ON s.s_manager_id = u.user_id ");
		sql.append(this.createConditions(similar));
		sql.append("");
		switch (so.getValue0()) {
		case NAME:
			sql.append("ORDER BY s.s_name ").append(so.getValue1().name());
			break;
		case MANAGER:
			sql.append("ORDER BY s.s_manager_id  ").append(so.getValue1().name());
			break;
		default:
			sql.append("ORDER BY s.s_id DESC ");
		}
		sql.append(" LIMIT ").append(at).append(", ").append(total).append("; ");

		// tong so Product group
		sql.append("SELECT COUNT(s_id) AS total FROM tblsize;");

		// danh sach quyen, phu thuoc vao nguoi dang nhap
		sql.append("SELECT * FROM tbluser WHERE ");
		sql.append("(user_permission<=").append(user.getUser_permission()).append(") AND (");
		sql.append("(user_parent_id=").append(user.getUser_id()).append(") OR (user_id=").append(user.getUser_id()).append(")");
		sql.append(");");

		System.out.print(sql.toString());

		return this.getMR(sql.toString());
	}

	private String createConditions(SizeObject similar) {
		StringBuilder conds = new StringBuilder();

		if (similar != null) {

			// System.out.println(similar.isUser_delete());
			if (similar.isS_delete()) {
				conds.append("(s_delete=1) ");
			} else {
				conds.append("(s_delete=0) ");
			}
		}

		if (!conds.toString().equalsIgnoreCase("")) {
			conds.insert(0, " WHERE ");
		}

		return conds.toString();
	}

}
