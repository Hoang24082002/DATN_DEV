package jsoft.ads.charts;

import java.sql.ResultSet;
import java.util.ArrayList;

import jsoft.ConnectionPool;
import jsoft.ads.basic.BasicImpl;

public class ChartImpl extends BasicImpl implements Chart {

	public ChartImpl(ConnectionPool cp) {
		super(cp, "Chart");
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<ResultSet> getCharts(int month, int year, String statisticDYM) {
		StringBuffer sql = new StringBuffer();

		int count_day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			count_day = 31;
			break;
		case 2:
			if (month % 4 == 0) {
				count_day = 29;
			} else {
				count_day = 28;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			count_day = 30;
			break;
		}

		// số đơn hàng theo ngày (0)
		sql.append("SELECT order_date, COUNT(*) AS order_count FROM tblorder WHERE  ");
		for (int i = 1; i < count_day; i++) {
			sql.append("order_date like '%" + i + "/%" + month + "/" + year + "' OR ");
		}
		sql.append("order_date like '%" + count_day + "/%" + month + "/" + year + "' ");
		sql.append("GROUP BY order_date; ");

		// doanh thu theo ngày (1)
		sql.append("SELECT order_date, SUM(order_price) AS order_revenue FROM tblorder WHERE ");
		for (int i = 1; i < count_day; i++) {
			sql.append("order_date like '%" + i + "/%" + month + "/" + year + "' OR ");
		}
		sql.append("order_date like '%" + count_day + "/%" + month + "/" + year + "' ");
		sql.append("GROUP BY order_date;");

		// Top 10 san pham dc ban nhieu nhat (2)
		sql.append("SELECT * FROM tblproduct ORDER BY product_sold DESC LIMIT 5 ;");

		// So don hang dang xu ly (3)
		sql.append("SELECT COUNT(*) AS handling FROM tblorder WHERE order_status = 1 AND order_destroy = 0; ");
		// So don hang da xu ly (4)
		sql.append("SELECT COUNT(*) AS handled FROM tblorder WHERE order_status = 2 AND order_destroy = 0; ");
		// So don hang dang giao (5)
		sql.append("SELECT COUNT(*) AS delivering FROM tblorder WHERE order_status = 3 AND order_destroy = 0; ");
		// So don hang da giao (6)
		sql.append("SELECT COUNT(*) AS delivered FROM tblorder WHERE order_status = 4 AND order_destroy = 0; ");
		// So don bị huy (7)
		sql.append("SELECT COUNT(*) AS destroied FROM tblorder WHERE order_destroy = 1; ");		
		// tong so khach hang (8)
		sql.append("SELECT COUNT(*) AS user_count FROM tbluser WHERE user_permission = 1 AND user_delete = 0; ");
		
		// loi nhuan theo ngày (9)
		sql.append("SELECT order_date, SUM(order_price) - SUM(order_discount_price) AS order_profit FROM tblorder WHERE ");
		for (int i = 1; i < count_day; i++) {
			sql.append("order_date like '%" + i + "/%" + month + "/" + year + "' OR ");
		}
		sql.append("order_date like '%" + count_day + "/%" + month + "/" + year + "' ");
		sql.append("GROUP BY order_date;");

		return this.getMR(sql.toString());
	}

}
