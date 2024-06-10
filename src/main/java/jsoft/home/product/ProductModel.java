package jsoft.home.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Sextet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.library.Utilities;
import jsoft.objects.ColorObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class ProductModel {
	private Product pm;
	
	public ProductModel(ConnectionPool cp) {
		this.pm = new ProductImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.pm.getCP();
	}

	public void releaseConnection() {
		this.pm.releaseConnection();
	}
	
	public ProductObject getProduct(int id) {
		ProductObject item = null;
		ResultSet rs = this.pm.getProduct(id);

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
	
	// lay danh sach RES san pham
	public Decade<
		ArrayList<ProductGroupObject>, 
		ArrayList<ProductObject>, 
		ArrayList<ProductObject>, 
		ArrayList<ProductObject>,
		ArrayList<ProductSectionObject>,
		ArrayList<ProductCategoryObject>, 
		ArrayList<SizeObject>, 
		ArrayList<ColorObject>, 
		ArrayList<ProductObject>, 
		Integer> getProductObjects(
				Quintet<ProductObject, Short, Byte, UserObject, String> infos, 
				Pair<PRODUCT_SORT, ORDER> so,
				boolean isDetail) {
		
		ArrayList<ResultSet> res = this.pm.getProductObjects(infos, so, isDetail);		
		// danh sach nhom san pham
		ResultSet rs = res.get(0);
		ArrayList<ProductGroupObject> productGroups = new ArrayList<>();
		ProductGroupObject productGroup = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					productGroup = new ProductGroupObject();
					productGroup.setPg_id(rs.getInt("pg_id"));
					productGroup.setPg_name(Utilities.decode(rs.getString("pg_name")));

					productGroups.add(productGroup);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach san pham yeu thich
		rs = res.get(1);
		ArrayList<ProductObject> productFavorites = new ArrayList<>();
		ProductObject productFavorite = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					productFavorite = new ProductObject();
					productFavorite.setProduct_id(rs.getInt("product_id"));
					productFavorite.setProduct_name(Utilities.decode(rs.getString("product_name")));
					productFavorite.setProduct_image(rs.getString("product_image"));
					productFavorite.setProduct_pg_id(rs.getInt("product_pg_id"));
					productFavorite.setProduct_price(rs.getInt("product_price"));
					productFavorite.setProduct_best_seller(rs.getBoolean("product_best_seller"));
					productFavorite.setProduct_promotion_price(rs.getInt("product_promotion_price"));
					
					productFavorites.add(productFavorite);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach san pham moi
		rs = res.get(2);
		ArrayList<ProductObject> productNews = new ArrayList<>();
		ProductObject productNew = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					productNew = new ProductObject();
					productNew.setProduct_id(rs.getInt("product_id"));
					productNew.setProduct_name(Utilities.decode(rs.getString("product_name")));
					productNew.setProduct_image(rs.getString("product_image"));
					productNew.setProduct_pg_id(rs.getInt("product_pg_id"));
					productNew.setProduct_price(rs.getInt("product_price"));
					productNew.setProduct_best_seller(rs.getBoolean("product_best_seller"));
					productNew.setProduct_promotion_price(rs.getInt("product_promotion_price"));
					
					productNews.add(productNew);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach san pham sieu giam gia
		rs = res.get(3);
		ArrayList<ProductObject> productSupperDiscounts = new ArrayList<>();
		ProductObject productSupperDiscount = null;
		if(rs!=null) {
			try {
				while(rs.next()) {
					productSupperDiscount = new ProductObject();
					productSupperDiscount.setProduct_id(rs.getInt("product_id"));
					productSupperDiscount.setProduct_name(Utilities.decode(rs.getString("product_name")));
					productSupperDiscount.setProduct_image(rs.getString("product_image"));
					productSupperDiscount.setProduct_pg_id(rs.getInt("product_pg_id"));
					productSupperDiscount.setProduct_price(rs.getInt("product_price"));
					productSupperDiscount.setProduct_best_seller(rs.getBoolean("product_best_seller"));
					productSupperDiscount.setProduct_promotion_price(rs.getInt("product_promotion_price"));
					
					productSupperDiscounts.add(productSupperDiscount);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach thanh phan san pham
		rs = res.get(4);
		ArrayList<ProductSectionObject> productSections = new ArrayList<>();
		ProductSectionObject productSection = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					productSection = new ProductSectionObject();
					productSection.setPs_id(rs.getInt("ps_id"));
					productSection.setPs_name(Utilities.decode(rs.getString("ps_name")));

					productSections.add(productSection);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach danh muc san pham
		rs = res.get(5);
		ArrayList<ProductCategoryObject> productCategorys = new ArrayList<>();
		ProductCategoryObject productCategory = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					productCategory = new ProductCategoryObject();
					productCategory.setPc_id(rs.getInt("pc_id"));
					productCategory.setPc_name(Utilities.decode(rs.getString("pc_name")));

					productCategorys.add(productCategory);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach kich co san pham
		rs = res.get(6);
		ArrayList<SizeObject> sizes = new ArrayList<>();
		SizeObject size = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					size = new SizeObject();
					size.setS_id(rs.getInt("s_id"));
					size.setS_name(Utilities.decode(rs.getString("s_name")));

					sizes.add(size);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// danh sach mau sac san pham
		rs = res.get(7);
		ArrayList<ColorObject> colors = new ArrayList<>();
		ColorObject color = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					color = new ColorObject();
					color.setC_id(rs.getInt("c_id"));
					color.setC_name(Utilities.decode(rs.getString("c_name")));

					colors.add(color);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<ProductObject> filterProducts = new ArrayList<>();
		if(!isDetail) {
			// danh sach san pham loc
			rs = res.get(8);
			ProductObject filterProduct = null;
			if(rs!=null) {
				try {
					while(rs.next()) {
						filterProduct = new ProductObject();
						filterProduct.setProduct_id(rs.getInt("product_id"));
						filterProduct.setProduct_name(Utilities.decode(rs.getString("product_name")));
						filterProduct.setProduct_image(rs.getString("product_image"));
						filterProduct.setProduct_pg_id(rs.getInt("product_pg_id"));
						filterProduct.setProduct_price(rs.getInt("product_price"));
						filterProduct.setProduct_promotion_price(rs.getInt("product_promotion_price"));
						filterProduct.setProduct_best_seller(rs.getBoolean("product_best_seller"));
						
						filterProducts.add(filterProduct);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		} else {
			rs = res.get(8);
			ProductObject detailProduct = null;
			if(rs!=null) {
				try {
					while(rs.next()) {
						detailProduct = new ProductObject();
						detailProduct.setProduct_id(rs.getInt("product_id"));
						detailProduct.setProduct_name(Utilities.decode(rs.getString("product_name")));
						detailProduct.setProduct_image(rs.getString("product_image"));
						detailProduct.setProduct_pg_id(rs.getInt("product_pg_id"));
						detailProduct.setProduct_price(rs.getInt("product_price"));
						detailProduct.setProduct_discount_price(rs.getInt("product_discount_price"));
						detailProduct.setProduct_promotion_price(rs.getInt("product_promotion_price"));
						detailProduct.setProduct_best_seller(rs.getBoolean("product_best_seller"));
						detailProduct.setProduct_intro(Utilities.decode(rs.getString("product_intro")));
						
						filterProducts.add(detailProduct);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return new Decade(productGroups, productFavorites, productNews,productSupperDiscounts, productSections, productCategorys, sizes, colors, filterProducts, filterProducts.size() + 1);
	}
}
