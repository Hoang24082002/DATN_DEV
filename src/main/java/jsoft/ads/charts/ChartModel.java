package jsoft.ads.charts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import jsoft.ConnectionPool;
import jsoft.ads.image.IMAGE_SOFT;
import jsoft.library.ORDER;
import jsoft.objects.ImageObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class ChartModel {
	private Chart i;

	public ChartModel(ConnectionPool cp) {
		this.i = new ChartImpl(cp);
	}

	public ConnectionPool getCP() {
		return this.i.getCP();
	}

	public void releaseConnection() {
		this.i.releaseConnection();
	}
	
//	------------------------------------------------------
	public Quintet<
			HashMap<String, Integer>,
			HashMap<String, Float>, 
			ArrayList<ProductObject>, 
			HashMap<String, Short>,
			HashMap<String, Float>> getCharts(int month, int year, String statisticDYM) {


		ArrayList<ResultSet> res = this.i.getCharts(month, year, statisticDYM);

		// số đơn hàng theo ngày (0)
		ResultSet rs = res.get(0);
		HashMap<String, Integer> statisticOrder = new HashMap<>();
		if (rs != null) {
			try {
				while (rs.next()) {
					statisticOrder.put(rs.getString("order_date"), rs.getInt("order_count"));
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// doanh thu theo ngày (1)
		rs = res.get(1);
		HashMap<String, Float> statisticRevenue = new HashMap<>();
		if (rs != null) {
			try {
				while (rs.next()) {
					float revenue = (float) (rs.getInt("order_revenue") * 1.0 / 1000000);
					statisticRevenue.put(rs.getString("order_date"), revenue);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Top 10 san pham dc ban nhieu nhat (2)
		ArrayList<ProductObject> ProductSolds = new ArrayList<>();
		ProductObject ProductSold = null;
		rs = res.get(2);
		if (rs != null) {
			try {
				while (rs.next()) {
					ProductSold = new ProductObject();
					ProductSold.setProduct_id(rs.getInt("product_id"));
					ProductSold.setProduct_name(rs.getString("product_name"));
					ProductSold.setProduct_image(rs.getString("product_image"));
					ProductSold.setProduct_price(rs.getInt("product_price"));
					ProductSold.setProduct_discount_price(rs.getInt("product_discount_price"));
					ProductSold.setProduct_enable(rs.getBoolean("product_enable"));
					ProductSold.setProduct_delete(rs.getBoolean("product_delete"));
					ProductSold.setProduct_visited(rs.getShort("product_visited"));
					ProductSold.setProduct_total(rs.getShort("product_total"));
					ProductSold.setProduct_manager_id(rs.getShort("product_manager_id"));
					ProductSold.setProduct_intro(rs.getString("product_intro"));
					ProductSold.setProduct_notes(rs.getString("product_notes"));
					ProductSold.setProduct_created_date(rs.getString("product_created_date"));
					ProductSold.setProduct_modified_date(rs.getString("product_modified_date"));
					ProductSold.setProduct_pg_id(rs.getInt("product_pg_id"));
					ProductSold.setProduct_ps_id(rs.getInt("product_ps_id"));
					ProductSold.setProduct_pc_id(rs.getInt("product_pc_id"));
					ProductSold.setProduct_is_detail(rs.getBoolean("product_is_detail"));
					ProductSold.setProduct_deleted_date(rs.getString("product_deleted_date"));
					ProductSold.setProduct_deleted_author(rs.getString("product_deleted_author"));
					ProductSold.setProduct_promotion_price(rs.getInt("product_promotion_price"));
					ProductSold.setProduct_sold(rs.getShort("product_sold"));
					ProductSold.setProduct_best_seller(rs.getBoolean("product_best_seller"));
					ProductSold.setProduct_promotion(rs.getBoolean("product_promotion"));
					ProductSold.setProduct_style(rs.getString("product_style"));
					ProductSold.setProduct_created_author_id(rs.getInt("product_created_author_id"));

					ProductSolds.add(ProductSold);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// All trang thai don hang
		HashMap<String, Short> stastusOrderCount = new HashMap<>();
		
		// So don hang dang xu ly (3)
		rs = res.get(3);
		short handling = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					handling = rs.getShort("handling");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stastusOrderCount.put("handling", handling);
		
		// So don hang da xu ly (4)
		rs = res.get(4);
		short handled = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					handled = rs.getShort("handled");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stastusOrderCount.put("handled", handled);
		
		// So don hang dang giao (5)
		rs = res.get(5);
		short delivering = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					delivering = rs.getShort("delivering");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stastusOrderCount.put("delivering", delivering);
		
		// So don hang da giao (6)
		rs = res.get(6);
		short delivered = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					delivered = rs.getShort("delivered");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stastusOrderCount.put("delivered", delivered);
		
		// So don bị huy (7)
		rs = res.get(7);
		short destroied = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					destroied = rs.getShort("destroied");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stastusOrderCount.put("destroied", destroied);
		
		// tong so khach hang (8)
		rs = res.get(8);
		short userCount = 0;
		if (rs != null) {
			try {
				if (rs.next()) {
					userCount = rs.getShort("user_count");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stastusOrderCount.put("user_count", userCount);
		
		// loi nhuan theo ngày (9)
		rs = res.get(9);
		HashMap<String, Float> statisticProfit = new HashMap<>();
		if (rs != null) {
			try {
				while (rs.next()) {
					float profit = (float) (rs.getInt("order_profit") * 1.0 / 1000000);
					statisticProfit.put(rs.getString("order_date"), profit);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return new Quintet<>(statisticOrder, statisticRevenue, ProductSolds, stastusOrderCount, statisticProfit);
	}
}
