package jsoft.ads.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.objects.ColorObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductColorObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductSizeObject;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class ProductModel {

	private Product p;

	public ProductModel(ConnectionPool cp) {
		this.p = new ProductImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.p.getCP();
	}

	public void releaseConnection() {
		this.p.releaseConnection();
	}

//	----------------------------------
	public boolean addProduct(ProductObject item) {
		return this.p.addProduct(item);
	}

	public boolean editProduct(ProductObject item, PRODUCT_EDIT_TYPE et) {
		return this.p.editProduct(item, et);
	}

	public boolean delProduct(ProductObject item) {
		return this.p.delProduct(item);
	}

//	----------------------------------
	public boolean addProductColor(ProductColorObject item) {
		return this.p.addProductColor(item);
	}

	public boolean addProductSize(ProductSizeObject item) {
		return this.p.addProductSize(item);
	}


//	----------------------------------
	public ProductObject getProduct(int id) {
		ProductObject item = null;
		ResultSet rs = this.p.getProduct(id);

		if (rs != null) {
			try {
				if (rs.next()) {
					item = new ProductObject();

					item.setProduct_id(rs.getInt("product_id"));
					item.setProduct_name(rs.getString("product_name"));
					item.setProduct_image(rs.getString("product_image"));
					item.setProduct_price(rs.getInt("product_price"));
					item.setProduct_discount_price(rs.getInt("product_discount_price"));
					item.setProduct_enable(rs.getBoolean("product_enable"));
					item.setProduct_delete(rs.getBoolean("product_delete"));
					item.setProduct_visited(rs.getShort("product_visited"));
					item.setProduct_total(rs.getShort("product_total"));
					item.setProduct_manager_id(rs.getShort("product_manager_id"));
					item.setProduct_intro(rs.getString("product_intro"));
					item.setProduct_notes(rs.getString("product_notes"));
					item.setProduct_created_date(rs.getString("product_created_date"));
					item.setProduct_modified_date(rs.getString("product_modified_date"));
					item.setProduct_pg_id(rs.getInt("product_pg_id"));
					item.setProduct_ps_id(rs.getInt("product_ps_id"));
					item.setProduct_pc_id(rs.getInt("product_pc_id"));
					item.setProduct_is_detail(rs.getBoolean("product_is_detail"));
					item.setProduct_deleted_date(rs.getString("product_deleted_date"));
					item.setProduct_deleted_author(rs.getString("product_deleted_author"));
					item.setProduct_promotion_price(rs.getInt("product_promotion_price"));
					item.setProduct_sold(rs.getShort("product_sold"));
					item.setProduct_best_seller(rs.getBoolean("product_best_seller"));
					item.setProduct_promotion(rs.getBoolean("product_promotion"));
					item.setProduct_style(rs.getString("product_style"));
					item.setProduct_created_author_id(rs.getInt("product_created_author_id"));

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return item;
	}

	public Decade<ArrayList<ProductObject>, Short, HashMap<Integer, String>, ArrayList<UserObject>, ArrayList<ProductGroupObject>, ArrayList<ProductSectionObject>, ArrayList<ProductCategoryObject>, ArrayList<ColorObject>, ArrayList<SizeObject>, Pair<ArrayList<ColorObject>, ArrayList<SizeObject>>> getProducts(
			Sextet<ProductObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject> infos,
			Triplet<PRODUCT_SOFT, ORDER, ADD_END_UPDATE> so) {

		HashMap<Integer, String> managerName = new HashMap<>();

		ArrayList<ProductObject> items = new ArrayList<>();

		ProductObject item = null;

		ArrayList<ResultSet> res = this.p.getProducts(infos, so);

		ResultSet rs = res.get(0);

		if (rs != null) {
			try {
				while (rs.next()) {
					item = new ProductObject();

					item.setProduct_id(rs.getInt("product_id"));
					item.setProduct_name(rs.getString("product_name"));
					item.setProduct_image(rs.getString("product_image"));
					item.setProduct_price(rs.getInt("product_price"));
					item.setProduct_discount_price(rs.getInt("product_discount_price"));
					item.setProduct_enable(rs.getBoolean("product_enable"));
					item.setProduct_delete(rs.getBoolean("product_delete"));
					item.setProduct_visited(rs.getShort("product_visited"));
					item.setProduct_total(rs.getShort("product_total"));
					item.setProduct_manager_id(rs.getShort("product_manager_id"));
					item.setProduct_intro(rs.getString("product_intro"));
					item.setProduct_notes(rs.getString("product_notes"));
					item.setProduct_created_date(rs.getString("product_created_date"));
					item.setProduct_modified_date(rs.getString("product_modified_date"));
					item.setProduct_pg_id(rs.getInt("product_pg_id"));
					item.setProduct_ps_id(rs.getInt("product_ps_id"));
					item.setProduct_pc_id(rs.getInt("product_pc_id"));
					item.setProduct_is_detail(rs.getBoolean("product_is_detail"));
					item.setProduct_deleted_date(rs.getString("product_deleted_date"));
					item.setProduct_deleted_author(rs.getString("product_deleted_author"));
					item.setProduct_promotion_price(rs.getInt("product_promotion_price"));
					item.setProduct_sold(rs.getShort("product_sold"));
					item.setProduct_best_seller(rs.getBoolean("product_best_seller"));
					item.setProduct_promotion(rs.getBoolean("product_promotion"));
					item.setProduct_style(rs.getString("product_style"));
					item.setProduct_created_author_id(rs.getInt("product_created_author_id"));

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
		if (rs != null) {
			try {
				while (rs.next()) {
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

		// danh sach nhom san pham
		rs = res.get(3);
		ArrayList<ProductGroupObject> productGroups = new ArrayList<>();
		ProductGroupObject productGroup = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					productGroup = new ProductGroupObject();
					productGroup.setPg_id(rs.getInt("pg_id"));
					productGroup.setPg_name(rs.getString("pg_name"));

					productGroups.add(productGroup);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// danh sach thanh phan san pham theo nhom san pham
		rs = res.get(4);
		ArrayList<ProductSectionObject> productSections = new ArrayList<>();
		ProductSectionObject itemProductSection = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemProductSection = new ProductSectionObject();

					itemProductSection.setPs_id(rs.getShort("ps_id"));
					itemProductSection.setPs_name(rs.getString("ps_name"));

					productSections.add(itemProductSection);

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// danh sach danh muc san pham theo thanh phan san pham
		rs = res.get(5);
		ArrayList<ProductCategoryObject> productCategorys = new ArrayList<>();
		ProductCategoryObject itemProductCategory = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemProductCategory = new ProductCategoryObject();

					itemProductCategory.setPc_id(rs.getShort("pc_id"));
					itemProductCategory.setPc_name(rs.getString("pc_name"));

					productCategorys.add(itemProductCategory);

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// danh sach mau sac
		rs = res.get(6);
		ArrayList<ColorObject> colors = new ArrayList<>();
		ColorObject itemColor = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemColor = new ColorObject();

					itemColor.setC_id(rs.getInt("c_id"));
					itemColor.setC_name(rs.getString("c_name"));

					colors.add(itemColor);

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// danh sach kich co
		rs = res.get(7);
		ArrayList<SizeObject> sizes = new ArrayList<>();
		SizeObject itemSize = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					itemSize = new SizeObject();

					itemSize.setS_id(rs.getInt("s_id"));
					itemSize.setS_name(rs.getString("s_name"));

					sizes.add(itemSize);

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach mau sac cua san pham
		rs = res.get(8);
		ArrayList<ColorObject> productColors = new ArrayList<>();
		ColorObject productColor = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					productColor = new ColorObject();

					productColor.setC_id(rs.getInt("c_id"));
					productColor.setC_name(rs.getString("c_name"));
					productColor.setC_manager_id(rs.getInt("p.product_id"));

					productColors.add(productColor);

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach kich co cua san pham
		rs = res.get(9);
		ArrayList<SizeObject> productSizes = new ArrayList<>();
		SizeObject productSize = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					productSize = new SizeObject();

					productSize.setS_id(rs.getInt("s_id"));
					productSize.setS_name(rs.getString("s_name"));
					productSize.setS_manager_id(rs.getInt("p.product_id"));

					productSizes.add(productSize);

				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new Decade(items, total, managerName, users, productGroups, productSections, productCategorys, colors, sizes, new Pair(productColors, productSizes) );
	}
}
