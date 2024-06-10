package jsoft.home.order;

import java.util.ArrayList;

import jsoft.objects.CartObject;
import jsoft.objects.OrderObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class OrderLibrary {
	public static ArrayList<String> viewOrder(
			ArrayList<OrderObject> orderItems,
			short total,
			int orderPrice, 
			ArrayList<CartObject> itemCartIdOrdereds,
			ArrayList<CartObject> itemCartOrdereds,
			UserObject userLogined,
			ArrayList<ProductObject> itemProductOrdereds,
			int orderDiscountPrice) {
		ArrayList<String> view = new ArrayList<>();
		StringBuffer tmp = new StringBuffer();
		
		view.add(OrderLibrary.viewFormOrder(userLogined, orderPrice, itemCartIdOrdereds, orderDiscountPrice));
		
		view.add(OrderLibrary.viewListOrder(orderItems, userLogined, itemCartOrdereds, itemProductOrdereds));
		
		return view;
	}
	
	public static String viewFormOrder(UserObject user, int orderPrice, ArrayList<CartObject> itemCartIdOrdereds, int orderDiscountPrice) {
		StringBuffer tmp = new StringBuffer();
		String order_detail_cartId = "";

		for(CartObject item : itemCartIdOrdereds) {
			order_detail_cartId += "" + item.getCart_id() + "-";
		}
		
		tmp.append("<form action=\"/datn/order/add\" method=\"post\" class=\"row\">");
		tmp.append("<div class=\"col-lg-8\">");
		tmp.append("<div class=\"local-infor-details border p-4\">");
		tmp.append("<div class=\"local-infor-heading mb-3\">");
		tmp.append("<i class=\"fa-solid fa-location-dot\"></i> <strong>Thông tin giao hàng</strong>");
		tmp.append("</div>");
		tmp.append("<div class=\"mb-3\">");
		tmp.append("<label for=\"userName\">Họ và tên</label>");
		tmp.append("<div class=\"\">");
		tmp.append("<input type=\"text\" class=\"form-control\" name=\"userName\" id=\"userName\" value=\""+user.getUser_fullname()+"\" readonly>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("<div class=\"mb-3\">");
		tmp.append("<label for=\"\">Số điện thoại</label>");
		tmp.append("<div class=\"\">");
		tmp.append("<input type=\"text\" class=\"form-control\" name=\"\" id=\"\" value=\""+user.getUser_mobilephone()+"\" readonly>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("<div class=\"mb-3\">");
		tmp.append("<label for=\"\">Email</label>");
		tmp.append("<div class=\"\">");
		tmp.append("<input type=\"email\" class=\"form-control\" name=\"\" id=\"\" value=\""+user.getUser_email()+"\" readonly>");
		tmp.append("</div>");
		tmp.append("</div>");
//		tmp.append("<div class=\"mb-3\">");
//		tmp.append("<label for=\"userCityName\">Tỉnh / Thành phố</label>");
//		tmp.append("<div class=\"\">");
//		tmp.append("<select id=\"userCityName\" class=\"form-select\">");
//		tmp.append("<option selected>Choose...</option>");
//		tmp.append("<option>...</option>");
//		tmp.append("<option>...</option>");
//		tmp.append("</select>");
//		tmp.append("</div>");
//		tmp.append("</div>");
//		tmp.append("<div class=\"mb-3\">");
//		tmp.append("<label for=\"userDistrictName\">Quận / Huyện</label>");
//		tmp.append("<div class=\"\">");
//		tmp.append("<select id=\"userDistrictName\" class=\"form-select\">");
//		tmp.append("<option selected>Choose...</option>");
//		tmp.append("<option>...</option>");
//		tmp.append("<option>...</option>");
//		tmp.append("</select>");
//		tmp.append("</div>");
//		tmp.append("</div>");
//		tmp.append("<div class=\"mb-3\">");
//		tmp.append("<label for=\"userWardsName\">Phường / Xã</label>");
//		tmp.append("<div class=\"\">");
//		tmp.append("<select id=\"userWardsName\" class=\"form-select\">");
//		tmp.append("<option selected>Choose...</option>");
//		tmp.append("<option>...</option>");
//		tmp.append("<option>...</option>");
//		tmp.append("</select>");
//		tmp.append("</div>");
//		tmp.append("</div>");
		tmp.append("<div class=\"mb-3\">");
		tmp.append("<label for=\"userOrderAddress\">Nhập địa chỉ</label>");
		tmp.append("<div class=\"\">");
		tmp.append("<input type=\"text\" class=\"form-control\" name=\"userOrderAddress\" id=\"userOrderAddress\" value=\""+user.getUser_address()+"\">");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("<div class=\"payment-methods my-5 border p-4\">");
		tmp.append("<div class=\"local-infor-heading mb-3\">");
		tmp.append("<i class=\"fa-regular fa-credit-card\"></i> <strong>Phương thức thanh toán</strong>");
		tmp.append("</div>");
		tmp.append("<div class=\"d-flex justify-content-start align-items-center mb-3\">");
		tmp.append("<div class=\"me-3\">");
		tmp.append("<input type=\"radio\" class=\"\" name=\"payment-method-type\" id=\"payment-on-delivery\" value=\"1\">");
		tmp.append("</div>");
		tmp.append("<label for=\"payment-on-delivery\"><i class=\"fa-solid fa-money-bill-1\"></i>  Thanh toán khi nhận hàng</label>");
		tmp.append("</div>");
		tmp.append("<div class=\"d-flex justify-content-start align-items-center mb-3\">");
		tmp.append("<div class=\"me-3\">");
		tmp.append("<input type=\"radio\" class=\"\" name=\"payment-method-type\" id=\"payment-on-vnpay\" value=\"2\">");
		tmp.append("</div>");
		tmp.append("<label for=\"payment-on-vnpay\"><i class=\"fa-brands fa-cc-amazon-pay\"></i>  Thanh toán bằng VNPAY</label>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div> <!-- END col-lg-8 -->");
		tmp.append("<div class=\"col-lg-4\">");
		tmp.append("<div class=\"use-discount-code border p-4\">");
		tmp.append("<div class=\"\">");
		tmp.append("<!-- discount-code -->");
		tmp.append("<button class=\"btn btn-primary\" type=\"button\" data-bs-toggle=\"modal\"");
		tmp.append("data-bs-target=\"#discount-code\">");
		tmp.append("Chọn mã giảm giá");
		tmp.append("</button>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("<div class=\"payment-result my-5 border p-4\">");
		tmp.append("<div class=\"payment-result-details-order\">");
		tmp.append("<h4><strong>Chi tiết đơn hàng</strong> </h4>");
//		tmp.append("<p>Giá trị đơn hàng <span>"+orderPrice+" đ</span></p>");
//		tmp.append("<p>Ưu đãi chương trình <span>- xxxxx đ</span></p>");
		tmp.append("</div>");
		tmp.append("<div class=\"payment-result-price\">");
		tmp.append("<input type=\"text\" class=\"form-control\" name=\"orderPrice\" value=\""+orderPrice+"\" id=\"orderPrice\" hidden>");
		tmp.append("<input type=\"text\" class=\"form-control\" name=\"orderDiscountPrice\" value=\""+orderDiscountPrice+"\" id=\"orderPrice\" hidden>");
		tmp.append("<input type=\"text\" class=\"form-control\" name=\"orderDetailCartId\" value=\""+order_detail_cartId+"\" id=\"orderPrice\" hidden>");
		tmp.append("<p>Tổng tiền thanh toán <span>"+orderPrice+" đ</span></p>");
		tmp.append("</div>");
		tmp.append("<button type=\"submit\" class=\"btn btn-danger border border-0\">Thanh toán</button>");
		tmp.append("</div>");
		tmp.append("</div> <!-- END col-lg-4 -->");
		tmp.append("");
		tmp.append("");
		tmp.append("</form>");
		
		return tmp.toString();
	}
	
	public static String viewListOrder(ArrayList<OrderObject> orderItems, UserObject userLogined, ArrayList<CartObject> itemCartOrdereds, ArrayList<ProductObject> itemProductOrdereds) {
		StringBuffer tmp = new StringBuffer();
		
		orderItems.forEach(item -> {
			tmp.append("<div class=\"shoping-cart-account-item p-4 border border-1 mb-4\">");
			tmp.append("<div class=\"\">");
			tmp.append("");
			tmp.append("<div class=\"shoping-cart-account-item-id\">");
			tmp.append("<span><strong>Mã đơn:</strong></span>");
			tmp.append("<span>"+item.getOrder_id()+"</span>");
			tmp.append("</div>");
			tmp.append("<div class=\"shoping-cart-account-item-dateOrder\">");
			tmp.append("<span><strong>Ngày đặt:</strong></span>");
			tmp.append("<span>"+item.getOrder_date()+"</span>");
			tmp.append("</div>");
			tmp.append("<div class=\"shoping-cart-account-item-price\">");
			tmp.append("<span><strong>Tổng tiền:</strong></span>");
			tmp.append("<span>"+item.getOrder_price()+" đ</span>");
			tmp.append("</div>");
			tmp.append("");
			tmp.append("</div>");
			tmp.append("");
			tmp.append("<div class=\"shoping-cart-account-item-btn d-flex justify-content-between align-items-center mt-3\">");
			tmp.append("<button type=\"button\" class=\"btn btn-danger border border-0\" data-bs-toggle=\"collapse\" data-bs-target=\"#order-details-"+item.getOrder_id()+"\" aria-expanded=\"false\" aria-controls=\"order-details-"+item.getOrder_id()+"\">Chi tiết</button>");
			if(item.getOrder_status() == 1) {
				tmp.append("<button type=\"button\" class=\"btn btn-danger border border-0\" data-bs-toggle=\"modal\" data-bs-target=\"#OrderDestroy"+item.getOrder_id()+"\">Hủy đơn</button>");
				
				tmp.append("<div class=\"modal fade\" id=\"OrderDestroy"+item.getOrder_id()+"\" tabindex=\"-1\" aria-labelledby=\"OrderDestroyLabel\" aria-hidden=\"true\">");
				tmp.append("<div class=\"modal-dialog\">");
				tmp.append("<div class=\"modal-content\">");
				tmp.append("<div class=\"modal-header\">");
				tmp.append("<h1 class=\"modal-title fs-5\" id=\"delete-item-shopping-cart-label\">Hủy đơn hàng</h1>");
				tmp.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
				tmp.append("</div>");
				tmp.append("<div class=\"modal-body\">");
				tmp.append("Bạn có chắc chắn muốn hủy đơn hàng này không");
				tmp.append("</div>");
				tmp.append("<div class=\"modal-footer\">");
				tmp.append("<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Không</button>");
				tmp.append("<a href=\"/datn/order/edit?id=" + item.getOrder_id() + "\" class=\"btn btn-danger\">Có</a>");
				tmp.append("</div>");
				tmp.append("</div>");
				tmp.append("</div>");
				tmp.append("</div>");
			}			
			tmp.append("</div>");
			tmp.append("");			
			tmp.append("");
			tmp.append("<!-- #order-details -->");
			tmp.append("<div class=\"collapse\" id=\"order-details-"+item.getOrder_id()+"\">");
			tmp.append("<div class=\"card card-body\">");
			tmp.append("<div class=\"shoping-cart-account-item-userReceive\">");
			tmp.append("<span><strong>Nguời nhận:</strong></span>");
			tmp.append("<span>"+userLogined.getUser_fullname()+"</span>");
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
			tmp.append("<div class=\"list-product-in-cart col-lg-8 border p-4\">");
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
	    	        			tmp.append("<div class=\"cart-shopping-list-item-img\">");
	    	        			tmp.append("<img src=\""+po.getProduct_image()+"\" alt=\"\">");
	    	        			tmp.append("</div>");
	    	        			tmp.append("<div class=\"cart-shopping-list-item-content\">");
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
	        			tmp.append("<div class=\"input-group mb-3 cart-shopping-list-item-quantity\">");
	        			tmp.append("<span class=\"input-group-text\">+</span>");
	        			tmp.append("<input type=\"text\" class=\"form-control\" value=\""+ico.getProduct_quantity()+"\" readonly>");
	        			tmp.append("<span class=\"input-group-text\">-</span>");
	        			tmp.append("</div>");
	        			tmp.append("</div>");
	        		}
	        	});
	        });	        
			// end for
			
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("<!-- end #order-details -->");
			tmp.append("</div>");
			
		});
		
		return tmp.toString();		
	}
	
	
}
