package jsoft.ads.charts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jsoft.library.Utilities;
import jsoft.objects.ProductObject;

public class ChartLibrary {
	public static ArrayList<String> viewChart(
			HashMap<String, Integer> statisticOrder,
			HashMap<String, Float> statisticRevenue, 
			ArrayList<ProductObject> ProductSolds,
			HashMap<String, Short> stastusOrderCount,
			HashMap<String, Float> statisticProfit,
			String statisticDYM) {

		ArrayList<String> view = new ArrayList<String>();

		view.add(ChartLibrary.salesCard(statisticOrder, statisticDYM)); // (0)
		view.add(ChartLibrary.revenueCard(statisticRevenue, statisticDYM)); // (1)
		view.add(ChartLibrary.userCard(stastusOrderCount.get("user_count"))); // (2)
		view.add(ChartLibrary.userReports(statisticOrder, statisticRevenue, statisticProfit)); // (3)
		view.add(ChartLibrary.statusOrderChart(stastusOrderCount)); // (4)
		view.add(ChartLibrary.topSellingChart(ProductSolds)); // (5)
		view.add(ChartLibrary.profitCard(statisticProfit, statisticDYM)); // (6)

		return view;
	}

	public static String salesCard(HashMap<String, Integer> statisticOrder, String statisticDYM) {
		StringBuffer tmp = new StringBuffer();

		String date_now = jsoft.library.Utilities_date.getDate();
		String[] dYM = date_now.split("/");

		int order_day = 0, order_month = 0, order_year = 0;
		int day = 0, month = 0, year = 0;
		if (dYM.length > 0) {
			day = Utilities.convertStringToInt(dYM[0]);
			month = Utilities.convertStringToInt(dYM[1]);
			year = Utilities.convertStringToInt(dYM[2]);
		}

		// Sử dụng vòng lặp for-each để in ra key và value
		for (Map.Entry<String, Integer> entry : statisticOrder.entrySet()) {
			String key = entry.getKey();
			String[] key_dYM = key.split("/");
			Integer value = entry.getValue();
			if (date_now.equalsIgnoreCase(key)) {
				order_day = value;
			}

			if (month == Utilities.convertStringToInt(key_dYM[1]) && year == Utilities.convertStringToInt(key_dYM[2])) {
				order_month += value;
			}

			if (year == Utilities.convertStringToInt(key_dYM[2])) {
				order_year += value;
			}
		}
		tmp.append("<!-- Sales Card -->");
		tmp.append("<div class=\"col-xxl-3 col-md-6\">");
		tmp.append("<div class=\"card info-card sales-card\">");
		tmp.append("");
		tmp.append("<div class=\"filter\">");
		tmp.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		tmp.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		tmp.append("<li class=\"dropdown-header text-start\">");
		tmp.append("<h6>Lọc</h6>");
		tmp.append("</li>");
		tmp.append("");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view\">Hôm nay</a></li>");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view?om\">Tháng này</a></li>");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view?oy\">Năm nay</a></li>");
		tmp.append("</ul>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Đơn hàng <span>| ");
		if(statisticDYM.equalsIgnoreCase("om")) {
			tmp.append("Tháng nay");		
		} else if(statisticDYM.equalsIgnoreCase("oy")){			 
			tmp.append("Năm này");		
		} else {
			tmp.append("Hôm nay");			
		}		
		tmp.append("</span></h5>");
		tmp.append("");
		tmp.append("<div class=\"d-flex align-items-center\">");
		tmp.append("<div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\">");
		tmp.append("<i class=\"bi bi-cart\"></i>");
		tmp.append("</div>");
		tmp.append("<div class=\"ps-3\">");
		if(statisticDYM.equalsIgnoreCase("om")) {
			tmp.append("<h6>" + order_month + "</h6>");			
		} else if(statisticDYM.equalsIgnoreCase("oy")){			 
			tmp.append("<h6>" + order_year + "</h6>");		
		} else {
			tmp.append("<h6>" + order_day + "</h6>");			
		}
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div><!-- End Sales Card -->");
		tmp.append("");

		return tmp.toString();
	}

	public static String revenueCard(HashMap<String, Float> statisticRevenue, String statisticDYM) {
		StringBuffer tmp = new StringBuffer();

		String date_now = jsoft.library.Utilities_date.getDate();
		String[] dYM = date_now.split("/");

		float order_day = 0, order_month = 0, order_year = 0;
		int day = 0, month = 0, year = 0;
		if (dYM.length > 0) {
			day = Utilities.convertStringToInt(dYM[0]);
			month = Utilities.convertStringToInt(dYM[1]);
			year = Utilities.convertStringToInt(dYM[2]);
		}

		// Sử dụng vòng lặp for-each để in ra key và value
		for (Entry<String, Float> entry : statisticRevenue.entrySet()) {
			String key = entry.getKey();
			String[] key_dYM = key.split("/");
			Float value = entry.getValue();
			if (date_now.equalsIgnoreCase(key)) {
				order_day = value;
			}

			if (month == Utilities.convertStringToInt(key_dYM[1]) && year == Utilities.convertStringToInt(key_dYM[2])) {
				order_month += value;
			}

			if (year == Utilities.convertStringToInt(key_dYM[2])) {
				order_year += value;
			}
		}
		tmp.append("<!-- Revenue Card -->");
		tmp.append("<div class=\"col-xxl-3 col-md-6\">");
		tmp.append("<div class=\"card info-card revenue-card\">");
		tmp.append("");
		tmp.append("<div class=\"filter\">");
		tmp.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		tmp.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		tmp.append("<li class=\"dropdown-header text-start\">");
		tmp.append("<h6>Lọc</h6>");
		tmp.append("</li>");
		tmp.append("");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view\">Hôm nay</a></li>");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view?rm\">Tháng này</a></li>");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view?ry\">Năm nay</a></li>");
		tmp.append("</ul>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Doanh thu <span>| ");
		if(statisticDYM.equalsIgnoreCase("rm")) {
			tmp.append("Tháng nay");		
		} else if(statisticDYM.equalsIgnoreCase("ry")){			 
			tmp.append("Năm này");		
		} else {
			tmp.append("Hôm nay");			
		}		
		tmp.append("</span></h5>");
		tmp.append("");;
		tmp.append("<div class=\"d-flex align-items-center\">");
		tmp.append("<div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\">");
		tmp.append("<i class=\"bi bi-currency-dollar\"></i>");
		tmp.append("</div>");
		tmp.append("<div class=\"ps-3\">");
		if(statisticDYM.equalsIgnoreCase("rm")) {
			tmp.append("<h6>" + order_month + " tr</h6>");			
		} else if(statisticDYM.equalsIgnoreCase("ry")){			 
			tmp.append("<h6>" + order_year + " tr</h6>");		
		} else {
			tmp.append("<h6>" + order_day + " tr</h6>");			
		}
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div><!-- End Revenue Card -->");
		tmp.append("");

		return tmp.toString();
	}
	
	public static String profitCard(HashMap<String, Float> statisticProfit, String statisticDYM) {
		StringBuffer tmp = new StringBuffer();

		String date_now = jsoft.library.Utilities_date.getDate();
		String[] dYM = date_now.split("/");

		float order_day = 0, order_month = 0, order_year = 0;
		int day = 0, month = 0, year = 0;
		if (dYM.length > 0) {
			day = Utilities.convertStringToInt(dYM[0]);
			month = Utilities.convertStringToInt(dYM[1]);
			year = Utilities.convertStringToInt(dYM[2]);
		}

		// Sử dụng vòng lặp for-each để in ra key và value
		for (Entry<String, Float> entry : statisticProfit.entrySet()) {
			String key = entry.getKey();
			String[] key_dYM = key.split("/");
			Float value = entry.getValue();
			if (date_now.equalsIgnoreCase(key)) {
				order_day = value;
			}

			if (month == Utilities.convertStringToInt(key_dYM[1]) && year == Utilities.convertStringToInt(key_dYM[2])) {
				order_month += value;
			}

			if (year == Utilities.convertStringToInt(key_dYM[2])) {
				order_year += value;
			}
		}
		tmp.append("<!-- Revenue Card -->");
		tmp.append("<div class=\"col-xxl-3 col-md-6\">");
		tmp.append("<div class=\"card info-card revenue-card\">");
		tmp.append("");
		tmp.append("<div class=\"filter\">");
		tmp.append("<a class=\"icon\" href=\"#\" data-bs-toggle=\"dropdown\"><i class=\"bi bi-three-dots\"></i></a>");
		tmp.append("<ul class=\"dropdown-menu dropdown-menu-end dropdown-menu-arrow\">");
		tmp.append("<li class=\"dropdown-header text-start\">");
		tmp.append("<h6>Lọc</h6>");
		tmp.append("</li>");
		tmp.append("");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view\">Hôm nay</a></li>");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view?pm\">Tháng này</a></li>");
		tmp.append("<li><a class=\"dropdown-item\" href=\"/datn/view?py\">Năm nay</a></li>");
		tmp.append("</ul>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Lợi nhuận <span>| ");
		if(statisticDYM.equalsIgnoreCase("pm")) {
			tmp.append("Tháng nay");		
		} else if(statisticDYM.equalsIgnoreCase("py")){			 
			tmp.append("Năm này");		
		} else {
			tmp.append("Hôm nay");			
		}		
		tmp.append("</span></h5>");
		tmp.append("");
		tmp.append("<div class=\"d-flex align-items-center\">");
		tmp.append("<div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\">");
		tmp.append("<i class=\"bi bi-currency-dollar\"></i>");
		tmp.append("</div>");
		tmp.append("<div class=\"ps-3\">");
		if(statisticDYM.equalsIgnoreCase("pm")) {
			tmp.append("<h6>" + order_month + " tr</h6>");			
		} else if(statisticDYM.equalsIgnoreCase("py")){			 
			tmp.append("<h6>" + order_year + " tr</h6>");		
		} else {
			tmp.append("<h6>" + order_day + " tr</h6>");			
		}
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div><!-- End Revenue Card -->");
		tmp.append("");

		return tmp.toString();
	}

	public static String userCard(short countUser) {
		StringBuffer tmp = new StringBuffer();

		tmp.append("<!-- Customers Card -->");
		tmp.append("<div class=\"col-xxl-3 col-xl-12\">");
		tmp.append("");
		tmp.append("<div class=\"card info-card customers-card\">");
		tmp.append("");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Khách hàng <span>| Tổng số</span></h5>");
		tmp.append("");
		tmp.append("<div class=\"d-flex align-items-center\">");
		tmp.append("<div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\">");
		tmp.append("<i class=\"bi bi-people\"></i>");
		tmp.append("</div>");
		tmp.append("<div class=\"ps-3\">");
		tmp.append("<h6>" + countUser + "</h6>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("</div><!-- End Customers Card -->");
		tmp.append("");

		return tmp.toString();
	}

	public static String userReports(HashMap<String, Integer> statisticOrder, HashMap<String, Float> statisticRevenue, HashMap<String, Float> statisticProfit) {
		StringBuffer tmp = new StringBuffer();

		StringBuffer data_sales = new StringBuffer();
		StringBuffer data_revenue = new StringBuffer();
		StringBuffer data_profit = new StringBuffer();

		String date_now = jsoft.library.Utilities_date.getDate();
		String[] dYM = date_now.split("/");
		int day = 0, month = 0, year = 0;
		if (dYM.length > 0) {
			day = Utilities.convertStringToInt(dYM[0].trim());
			month = Utilities.convertStringToInt(dYM[1].trim());
			year = Utilities.convertStringToInt(dYM[2].trim());
		}

		StringBuffer categories = new StringBuffer();
		int count_day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			for(int i = 1; i <=30; i++) {
				if(month<10 || i<10) {
					categories.append("\""+year+"-0"+month+"-0"+i+"T12:00:00.000Z\", ");									
				} else {
					categories.append("\""+year+"-"+month+"-"+i+"T12:00:00.000Z\", ");
				}
			}
			categories.append("\""+year+"-"+month+"-"+31+"T12:00:00.000Z\" ");
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
			for(int i = 1; i <=29; i++) {
				if(month<10 || i<10) {
					categories.append("\""+year+"-0"+month+"-0"+i+"T12:00:00.000Z\", ");									
				} else {
					categories.append("\""+year+"-"+month+"-"+i+"T12:00:00.000Z\", ");
				}
			}
			if(month<10) {
				categories.append("\""+year+"-0"+month+"-"+30+"T12:00:00.000Z\" ");				
			} else {
				categories.append("\""+year+"-"+month+"-"+30+"T12:00:00.000Z\" ");	
			}
			count_day = 30;
			break;
		}

		for (int i = 1; i <= count_day; i++) {
			boolean flag_sales = false;
			boolean flag_revenue = false;
			boolean flag_profit = false;
			for (Map.Entry<String, Integer> entry : statisticOrder.entrySet()) {
				String key = entry.getKey();
				String[] key_dYM = key.split("/");
				Integer value = entry.getValue();

				if (i == Utilities.convertStringToInt(key_dYM[0])) {
					data_sales.append(value + " ");
					flag_sales = true;
					break;
				}
			}

			for (Map.Entry<String, Float> entry : statisticRevenue.entrySet()) {
				String key = entry.getKey();
				String[] key_dYM = key.split("/");
				Float value = entry.getValue();

				if (i == Utilities.convertStringToInt(key_dYM[0])) {
					data_revenue.append(value + " ");
					flag_revenue = true;
					break;
				}
			}
			
			for (Map.Entry<String, Float> entry : statisticProfit.entrySet()) {
				String key = entry.getKey();
				String[] key_dYM = key.split("/");
				Float value = entry.getValue();

				if (i == Utilities.convertStringToInt(key_dYM[0])) {
					data_profit.append(value + " ");
					flag_profit = true;
					break;
				}
			}

			if (!flag_sales) {
				data_sales.append("0 ");
			}
			if (!flag_revenue) {
				data_revenue.append("0 ");
			}
			if (!flag_profit) {
				data_profit.append("0 ");
			}
		}
		String[] element_data_sales = data_sales.toString().trim().split(" ");
		String data_sales_result = String.join(", ", element_data_sales);

		String[] element_data_revenue = data_revenue.toString().trim().split(" ");
		String data_revenue_result = String.join(", ", element_data_revenue);
		
		String[] element_data_profit = data_profit.toString().trim().split(" ");
		String data_profit_result = String.join(", ", element_data_profit);

		tmp.append("<!-- Reports -->");
		tmp.append("<div class=\"col-12\">");
		tmp.append("<div class=\"card\">");
		tmp.append("");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Đơn hàng / Doanh thu / Lợi nhuận <span>/tháng này</span></h5>");
		tmp.append("");
		tmp.append("<!-- Line Chart -->");
		tmp.append("<div id=\"reportsChart\"></div>");
		tmp.append("");
		tmp.append("<script>");
		tmp.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		tmp.append("new ApexCharts(document.querySelector(\"#reportsChart\"), {");		
		tmp.append("series: [{");
		tmp.append("name: 'Số đơn hàng',");
		tmp.append("data: ["+data_sales_result+"],");
		tmp.append("}, {");
		tmp.append("name: 'Doanh thu (triệu đồng)',");
		tmp.append("data: ["+data_revenue_result+"]");
		tmp.append("}, {");
		tmp.append("name: 'Lợi nhuận (triệu đồng)',");
		tmp.append("data: ["+data_profit_result+"]");
		tmp.append("}],");
		tmp.append("chart: {");
		tmp.append("height: 350,");
		tmp.append("type: 'area',");
		tmp.append("toolbar: {");
		tmp.append("show: false");
		tmp.append("},");
		tmp.append("},");
		tmp.append("markers: {");
		tmp.append("size: 4");
		tmp.append("},");
		tmp.append("colors: ['#4154f1', '#2eca6a', '#ff771d'],");
		tmp.append("fill: {");
		tmp.append("type: \"gradient\",");
		tmp.append("gradient: {");
		tmp.append("shadeIntensity: 1,");
		tmp.append("opacityFrom: 0.3,");
		tmp.append("opacityTo: 0.4,");
		tmp.append("stops: [0, 90, 100]");
		tmp.append("}");
		tmp.append("},");
		tmp.append("dataLabels: {");
		tmp.append("enabled: false");
		tmp.append("},");
		tmp.append("stroke: {");
		tmp.append("curve: 'smooth',");
		tmp.append("width: 2");
		tmp.append("},");
		tmp.append("xaxis: {");
		tmp.append("type: 'datetime',");
		tmp.append("categories: ["+categories+"]");
		tmp.append("},");
		tmp.append("tooltip: {");
		tmp.append("x: {");
		tmp.append("format: 'dd/MM/yy HH:mm'");
		tmp.append("},");
		tmp.append("}");
		tmp.append("}).render();");
		tmp.append("});");
		tmp.append("</script>");
		tmp.append("<!-- End Line Chart -->");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div><!-- End Reports -->");
		tmp.append("");

		return tmp.toString();
	}
	
	public static String statusOrderChart(HashMap<String, Short> stastusOrderCount) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<div class=\"col-lg-12\">");
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");
		tmp.append("<h5 class=\"card-title\">Trạng thái đơn hàng</h5>");
		tmp.append("");
		tmp.append("<!-- Trạng thái đơn hàng -->");
		tmp.append("<div id=\"pieChart\"></div>");
		tmp.append("");
		tmp.append("<script>");
		tmp.append("document.addEventListener(\"DOMContentLoaded\", () => {");
		tmp.append("new ApexCharts(document.querySelector(\"#pieChart\"), {");
		tmp.append("series: ["+stastusOrderCount.get("handling")+", "+stastusOrderCount.get("handled")+", "+stastusOrderCount.get("delivering")+", "+stastusOrderCount.get("delivered")+", "+stastusOrderCount.get("destroied")+"],");
		tmp.append("chart: {");
		tmp.append("height: 350,");
		tmp.append("type: 'pie',");
		tmp.append("toolbar: {");
		tmp.append("show: true");
		tmp.append("}");
		tmp.append("},");
		tmp.append("labels: ['Đang xử lý', 'Đã xử lý', 'Đang giao', 'Đã giao', 'Bị hủy']");
		tmp.append("}).render();");
		tmp.append("});");
		tmp.append("</script>");
		tmp.append("<!-- End Pie Chart -->");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		
		return tmp.toString();	
	}

	public static String topSellingChart(ArrayList<ProductObject> ProductSolds) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<!-- Top Selling -->");
		tmp.append("<div class=\"col-12\">");
		tmp.append("<div class=\"card top-selling overflow-auto\">");
		tmp.append("");
		tmp.append("<div class=\"card-body pb-0\">");
		tmp.append("<h5 class=\"card-title\">Top 5 sản phẩm bán chạy</h5>");
		tmp.append("");
		tmp.append("<table class=\"table table-borderless\">");
		tmp.append("<thead>");
		tmp.append("<tr>");
		tmp.append("<th scope=\"col\">Ảnh</th>");
		tmp.append("<th scope=\"col\">Tên sản phẩm</th>");
		tmp.append("<th scope=\"col\">Giá nhập</th>");
		tmp.append("<th scope=\"col\">Lượt bán</th>");
		tmp.append("<th scope=\"col\">Giá bán</th>");
		tmp.append("</tr>");
		tmp.append("</thead>");
		tmp.append("<tbody>");
		ProductSolds.forEach(item -> {
			tmp.append("<tr>");
			tmp.append("<th scope=\"row\"><a href=\"#\"><img src=\""+item.getProduct_image()+"\" alt=\"\"></a></th>");
			tmp.append("<td><a href=\"#\" class=\"text-primary fw-bold\">"+item.getProduct_name()+"</a></td>");
			tmp.append("<td>"+item.getProduct_discount_price()+"</td>");
			tmp.append("<td class=\"fw-bold\">"+item.getProduct_sold()+"</td>");
			tmp.append("<td>"+item.getProduct_price()+"</td>");
			tmp.append("</tr>");			
		});
		
		tmp.append("</tbody>");
		tmp.append("</table>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("");
		tmp.append("</div>");
		tmp.append("</div><!-- End Top Selling -->");
		
		return tmp.toString();
	}

	
	
}
