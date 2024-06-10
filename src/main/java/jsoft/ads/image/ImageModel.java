package jsoft.ads.image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ImageObject;
import jsoft.objects.UserObject;

public class ImageModel {

	private Image i;

	public ImageModel(ConnectionPool cp) {
		this.i = new ImageImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.i.getCP();
	}

	public void releaseConnection() {
		this.i.releaseConnection();
	}

//	----------------------------------
	public boolean addImage(ImageObject item) {
		return this.i.addImage(item);
	}

	public boolean editImage(ImageObject item, IMAGE_EDIT_TYPE et) {
		return this.i.editImage(item, et);
	}

	public boolean delImage(ImageObject item) {
		return this.i.delImage(item);
	}

//	----------------------------------
	public ImageObject getImage(int id) {
		ImageObject item = null;
		ResultSet rs = this.i.getImage(id);
		
		if(rs!=null) {
			try {
				if(rs.next()) {
					item = new ImageObject();
					item.setI_id(rs.getInt("i_id"));
					item.setI_name(rs.getString("i_name"));
					item.setUser_image(rs.getInt("user_image"));
					item.setProduct_image(rs.getInt("product_image"));
					item.setI_manager_id(rs.getInt("i_manager_id"));
					item.setI_notes(rs.getString("i_notes"));
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Quartet<ArrayList<ImageObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>> getImages(
			Quartet<ImageObject, Short, Byte, UserObject> infos, Pair<IMAGE_SOFT, ORDER> so) {

		HashMap<Integer, String> managerName = new HashMap<>();

		ArrayList<ImageObject> items = new ArrayList<>();

		ImageObject item = null;

		ArrayList<ResultSet> res = this.i.getImages(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new ImageObject();
					item.setI_id(rs.getShort("i_id"));
					item.setI_name(rs.getString("i_name"));
					item.setUser_image(rs.getInt("user_image"));
					item.setProduct_image(rs.getInt("product_image"));
					item.setI_manager_id(rs.getInt("i_manager_id"));
					item.setI_notes(rs.getString("i_notes"));
					item.setI_delete(rs.getBoolean("i_delete"));
					item.setI_created_date(rs.getString("i_created_date"));
					item.setI_deleted_date(rs.getString("i_deleted_date"));
					item.setI_modified_date(rs.getString("i_modified_date"));
					item.setI_deleted_author(rs.getString("i_deleted_author"));
					item.setI_created_author_id(rs.getInt("i_created_author_id"));
					
					items.add(item);

					managerName.put(rs.getInt("user_id"),
							rs.getString("user_fullname") + "(" + rs.getString("user_name") + ")");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		rs = res.get(1);
		short total = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					total = rs.getShort("total");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		rs = res.get(2);
		ArrayList<UserObject> users = new ArrayList<>();
		UserObject user = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					user = new UserObject();
					user.setUser_id(rs.getInt("user_id"));
					user.setUser_name(rs.getString("user_name"));
					user.setUser_fullname(rs.getString("user_fullname"));
					
					users.add(user);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new Quartet<>(items, total, managerName, users);
	}
}
