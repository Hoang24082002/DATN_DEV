package jsoft.ads.order;

import java.util.ArrayList;

import jsoft.ads.user.UserLibrary;
import jsoft.objects.CartObject;
import jsoft.objects.OrderObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class OrderAdminLibrary {
	public static ArrayList<String> viewOrderAdmin(
			ArrayList<OrderObject> items, 
			OrderObject order, 
			UserObject user,
			int total,
			int page,
			byte totalPage,
			ArrayList<UserObject> userOrdereds,
			ArrayList<CartObject> itemCartOrdereds,
			ArrayList<ProductObject> itemProductOrdereds) {
		ArrayList<String> view = new ArrayList<>();
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");
//		tmp.append("<h5 class=\"card-title\">Table with stripped rows</h5>");

		tmp.append("<table class=\"table table-striped\">");
		tmp.append("<thead>");
		tmp.append("<tr>");
		tmp.append("<th scope=\"col\">STT</th>");

		if (order.isOrder_destroy()) {
			tmp.append("<th scope=\"col\">Ngày hủy</th>");
			tmp.append("<th scope=\"col\">Người đặt</th>");
			tmp.append("<th scope=\"col\">Giá đơn hàng</th>");
			tmp.append("<th scope=\"col\">#</th>");
		} else {			
			tmp.append("<th scope=\"col\">Ngày đặt</th>");
			tmp.append("<th scope=\"col\">Người đặt</th>");
			tmp.append("<th scope=\"col\">Giá đơn hàng</th>");
			tmp.append("<th scope=\"col\">Trạng thái đơn hàng</th>");
			tmp.append("<th scope=\"col\" colspan=\"3\">Thực hiện</th>");
			tmp.append("<th scope=\"col\">#</th>");
		}
		tmp.append("</tr>");
		tmp.append("</thead>");

		tmp.append("<tbody>");

		items.forEach(item -> {
			tmp.append("<tr>");
			tmp.append("<th scope=\"row\">" + (items.indexOf(item) + 1) + "</th>");

			if (order.isOrder_destroy()) {
				tmp.append("<td>" + item.getOrder_delete_date() + "</td>");
				for(UserObject userOrdered : userOrdereds) {
					if(userOrdered.getUser_id() == item.getUser_id()) {
						tmp.append("<td>" + userOrdered.getUser_fullname() + "</td>");
						break;
					}
				}
				tmp.append("<td>" + item.getOrder_price() + "</td>");
				tmp.append("<th scope=\"row\">" + item.getOrder_id() + "</th>");
			} else {
				tmp.append("<td>" + item.getOrder_date() + "</td>");
				for(UserObject userOrdered : userOrdereds) {
					if(userOrdered.getUser_id() == item.getUser_id()) {
						tmp.append("<td>" + userOrdered.getUser_fullname() + "</td>");
						break;
					}
				}
				tmp.append("<td>" + item.getOrder_price() + "</td>");
				tmp.append("<td>" + OrderAdminLibrary.viewOrderStatus(item.getOrder_id(), item.getOrder_status()) + "</td>");
				tmp.append("<td class=\"align-middle\"><button class=\"btn btn-primary\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#order-details-"+item.getOrder_id()+"\" aria-expanded=\"false\" aria-controls=\"order-details-"+item.getOrder_id()+"\">Chi tiết</button></td>");				
				tmp.append("<th scope=\"row\">" + item.getOrder_id() + "</th>");
			}

			tmp.append("</tr>");
		});
		tmp.append("</tbody>");
		tmp.append("</table>");
		tmp.append(OrderAdminLibrary.viewListOrder(items, user, itemCartOrdereds, itemProductOrdereds));

		tmp.append("</div>");
		tmp.append("</div>");	
		tmp.append(UserLibrary.getPaging("/datn/order/list?", page, total, totalPage));
		view.add(tmp.toString());
		
		return view;
	}

	
	public static String viewOrderStatus(int id, int selectedId) {
		StringBuilder tmp = new StringBuilder();

		tmp.append("<form method=\"\" action=\"\">");		
		tmp.append("<div class=\"\">");
		tmp.append("<input type=\"hidden\" name=\"orderID\" value=\""+id+"\">");
		tmp.append("<select class=\"form-select\" name=\"slcOrderStatus\" onchange=\"refreshCate(this.form)\" >");
		if(selectedId == 1) {
			tmp.append("<option value=\"1\" selected> Đang xử lý </option>");			
		} else {
			tmp.append("<option value=\"1\"> Đang xử lý </option>");			
		}
		if(selectedId == 2) {
			tmp.append("<option value=\"2\" selected> Đã xử lý </option>");			
		} else {
			tmp.append("<option value=\"2\"> Đã xử lý </option>");			
		}
		if(selectedId == 3) {
			tmp.append("<option value=\"3\" selected> Đang giao </option>");			
		} else {
			tmp.append("<option value=\"3\"> Đang giao </option>");			
		}
		if(selectedId == 4) {
			tmp.append("<option value=\"4\" selected> Đã giao </option>");			
		} else {
			tmp.append("<option value=\"4\"> Đã giao </option>");			
		}
		tmp.append("</select>");
		tmp.append("</div>");
		tmp.append("</form>");
		
		tmp.append("<script language=\"javascript\">");
		tmp.append("function refreshCate(fn) {");
		tmp.append("let os = fn.slcOrderStatus.value;");
		tmp.append("let oi = fn.orderID.value;");
		tmp.append("fn.method = 'post';");
		tmp.append("fn.action = `/datn/order/list?oi=${oi}&os=${os}`;");	
		tmp.append("fn.submit();");
		tmp.append("}");
		tmp.append("</script>");

		return tmp.toString();
	}
	
	
	public static String viewListOrder(ArrayList<OrderObject> orderItems, UserObject userLogined, ArrayList<CartObject> itemCartOrdereds, ArrayList<ProductObject> itemProductOrdereds) {
		StringBuffer tmp = new StringBuffer();
		
		orderItems.forEach(item -> {
			tmp.append("<!-- #order-details -->");
//			tmp.append("<div class=\"collapse\" id=\"order-details-"+item.getOrder_id()+"\">");
//			tmp.append("<div class=\"card card-body\">");  // Start card card-body
			
			tmp.append("<!-- Modal -->");
			tmp.append("<div class=\"modal fade\" id=\"order-details-"+item.getOrder_id()+"\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
			tmp.append("<div class=\"modal-dialog\">");
			tmp.append("<div class=\"modal-content\" style=\"width: max-content;\">");
			tmp.append("<div class=\"modal-header\">");
			tmp.append("<h1 class=\"modal-title fs-5\" id=\"exampleModalLabel\">Chi tiết đơn hàng</h1>");
			tmp.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
			tmp.append("</div>");
			tmp.append("<div class=\"modal-body\">");
			
			tmp.append("<div class=\"shoping-cart-account-item-order_id\">");
			tmp.append("<span><strong>Mã đơn hàng:</strong></span>");
			tmp.append("<span>"+item.getOrder_id()+"</span>");
			tmp.append("</div>");
			tmp.append("<div class=\"shoping-cart-account-item-userAddress\">");
			tmp.append("<span><strong>Địa chỉ:</strong></span>");
			tmp.append("<span>"+item.getOrder_address()+"</span>");
			tmp.append("</div>");
			tmp.append("<div class=\"shoping-cart-account-item-order-status\">");
			tmp.append("<span><strong>Trạng thái đơn hàng:</strong></span>");
			if(item.getOrder_status() == 1) {
				tmp.append("<span> Đang xử lý</span>");
			} else if(item.getOrder_status() == 2) {
				tmp.append("<span> Đã xử lý</span>");
			} else if(item.getOrder_status() == 3) {
				tmp.append("<span> Đang giao</span>");
			} else if(item.getOrder_status() == 4) {
				tmp.append("<span> Đã giao</span>");
			}			
			tmp.append("</div>");
			tmp.append("<div class=\"list-product-in-cart border p-4\">");
			tmp.append("<div class=\"mb-3\">");
			tmp.append("<i class=\"fa-solid fa-bag-shopping\"></i> <strong>Sản phẩm</strong>");
			tmp.append("</div>");
			tmp.append("<div class=\"cart-shopping-list\">");
			// for
			String order_detail_cartId = item.getOrder_detail_cartId();
	        String[] numbers = order_detail_cartId.split("-");// Tách chuỗi thành mảng các số        
	        ArrayList<Integer> resultTransfer = new ArrayList<>();// Chuyển mảng thành ArrayList<Integer>
	        for (String number : numbers) {
	            if (!number.isEmpty()) {
	            	resultTransfer.add(Integer.parseInt(number));
	            }
	        }
	        resultTransfer.forEach(i -> {
	        	itemCartOrdereds.forEach(ico -> {
	        		if(i == ico.getCart_id()) {
	        			tmp.append("<div class=\"cart-shopping-list-item d-flex justify-content-around align-items-center mb-4 border-bottom pb-2 position-relative\">");
	        			for(ProductObject po : itemProductOrdereds) {
	        				if(ico.getProduct_id() == po.getProduct_id()) {
	        					tmp.append("<div class=\"cart-shopping-list-item-img col-lg-3\" style=\"width: 200px; height:100px\">");
	        					tmp.append("<img src=\""+po.getProduct_image()+"\" alt=\"\" style=\"width: 100%; height:100%; object-fit: contain;\">");
	        					tmp.append("</div>");
	        					tmp.append("<div class=\"cart-shopping-list-item-content  col-lg-6\">");
	        					tmp.append("<div class=\"cart-shopping-list-item-content-name\">");
	        					tmp.append("<span>"+po.getProduct_name()+"</span>");
	        					tmp.append("</div>");
	        					break;
	        				}
	        				
	        			}
	        			tmp.append("<div class=\"cart-shopping-list-item-content-color-size\">");
	        			tmp.append("<span>"+ico.getProduct_color()+"</span>");
	        			tmp.append("<span> | </span>");
	        			tmp.append("<span>"+ico.getProduct_size()+"</span>");
	        			tmp.append("</div>");
	        			tmp.append("<div class=\"cart-shopping-list-item-content-price\">");
	        			tmp.append("<span>"+ico.getProduct_price()+" đ</span>");
	        			tmp.append("</div>");
	        			tmp.append("</div>");
	        			tmp.append("<div class=\"col-lg-2\" >");
	        			tmp.append("<div class=\"input-group mb-3 cart-shopping-list-item-quantity\">");
	        			tmp.append("<span class=\"input-group-text\">+</span>");
	        			tmp.append("<input type=\"text\" class=\"form-control\" value=\""+ico.getProduct_quantity()+"\" readonly>");
	        			tmp.append("<span class=\"input-group-text\">-</span>");
	        			tmp.append("</div>");
	        			tmp.append("</div>");
	        			tmp.append("</div>");
	        		}
	        	});
	        });	        
			// end for
			
			tmp.append("</div>");
			tmp.append("</div>");
			
			tmp.append("</div>");
			tmp.append("<div class=\"modal-footer\">");
			tmp.append("<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			
			
			
//			tmp.append("</div>"); // End card card-body
//			tmp.append("</div>");
			tmp.append("<!-- end #order-details -->");
			
		});
		
		return tmp.toString();		
	}
	
	public static String getPaging(String url, int page, int total, byte totalperpage) {
		// tinh toan tong so trang
		int countPage = total / totalperpage;
		if(total % totalperpage != 0) {
			countPage++;
		}
		
		if(page > countPage || page <= 0) {
			page = 1;
		}
		
		
		
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<nav aria-label=\"...\">");
		tmp.append("<ul class=\"pagination justify-content-center\">");
		tmp.append("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
		
		// left 
		String leftCurrent = "";
		int count = 0;
		for(int i = page - 1; i > 0; i--) {
			leftCurrent = "<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"page="+i+"\">"+i+"</a></li>" + leftCurrent;
			if(++count >= 2) {
				break;
			}
		}
		if(page >= 4) {		
			leftCurrent = "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">...</a></li>" + leftCurrent;
		}
		tmp.append(leftCurrent);
		
		tmp.append("<li class=\"page-item active\" aria-current=\"page\"><a class=\"page-link\" href=\"#\">"+page+"</a></li>");
		
		// right
		String rightCurrent ="";
		count = 0;
		for(int i = page + 1; i <= countPage; i++) {
			rightCurrent += "<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"page="+i+"\">"+i+"</a></li>";
			if(++count >= 2) {
				break;
			}
		}
		if(countPage > page + 4) {
			rightCurrent += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">...</a></li>";
		}
		tmp.append(rightCurrent);
		
		tmp.append("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"page="+countPage+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a></li>");
		tmp.append("</ul>");
		tmp.append("</nav>");
		
		return tmp.toString();
	}
	
}
