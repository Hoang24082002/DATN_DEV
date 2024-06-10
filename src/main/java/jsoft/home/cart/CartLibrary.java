package jsoft.home.cart;

import java.util.ArrayList;

import jsoft.objects.CartObject;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

public class CartLibrary {
	public static ArrayList<String> viewCart(
			ArrayList<CartObject> cartItems,
			ArrayList<ProductObject> productItems,
			short total,
			CartObject similar,
			UserObject userLogined,
			int orderPrice) {
		ArrayList<String> view = new ArrayList<>();

		StringBuffer tmp = new StringBuffer();
				
		if(total <= 0 ) {
			tmp.append("Không có sản phẩm nào trong giỏ hàng");									
		} else {			
			cartItems.forEach(cartItem -> {
				tmp.append("<div class=\"cart-shopping-list-item d-flex justify-content-around align-items-center mb-4 border-bottom pb-2 position-relative\">");
				tmp.append("<div class=\"cart-shopping-list-item-img\">");
				for (ProductObject productItem : productItems) {
					if (productItem.getProduct_id() == cartItem.getProduct_id()) {
						tmp.append("<img src=\""+productItem.getProduct_image()+"\" alt=\"\">");
						break;
					}
				}
				tmp.append("<!-- Button trigger modal -->");
				tmp.append("<button type=\"button\" class=\"btn btn-light btn-sm position-absolute top-0 start-0\" data-bs-toggle=\"modal\" data-bs-target=\"#delete-item-shopping-cart-id"+cartItem.getCart_id()+"\">");
				tmp.append("<i class=\"fa-solid fa-xmark\"></i>");
				tmp.append("</button>");
				tmp.append("</div>");
				tmp.append("<div class=\"cart-shopping-list-item-content\">");
				tmp.append("<div class=\"cart-shopping-list-item-content-name\">");
				for (ProductObject productItem : productItems) {
					if (productItem.getProduct_id() == cartItem.getProduct_id()) {
						tmp.append("<span>" + productItem.getProduct_name() + "</span>");	
						break;
					}
				}					
				tmp.append("</div>");
				tmp.append("<div class=\"cart-shopping-list-item-content-color-size\">");
				tmp.append("<span>"+cartItem.getProduct_color()+"</span>");
				tmp.append("<span> | </span>");
				tmp.append("<span>"+cartItem.getProduct_size()+"</span>");
				tmp.append("</div>");
				tmp.append("<div class=\"cart-shopping-list-item-content-price\">");
				tmp.append("<span>"+cartItem.getProduct_price()+" đ</span> ");
				tmp.append("</div>");
				tmp.append("</div>");
				tmp.append("<div class=\"input-group mb-3 cart-shopping-list-item-quantity\">");
				tmp.append("<button class=\"input-group-text\">+</button>");
				tmp.append("<input type=\"text\" class=\"form-control\" value=\""+cartItem.getProduct_quantity()+"\" readonly>");
				tmp.append("<button class=\"input-group-text\">-</button>");
				for (ProductObject productItem : productItems) {
					if (productItem.getProduct_id() == cartItem.getProduct_id()) {
						tmp.append("<input type=\"text\" class=\"form-control\" value=\""+productItem.getProduct_total()+"\" readonly>");	
						break;
					}
				}
				
				tmp.append("</div>");
				tmp.append("</div>");		
				
			});	
		}
		
		view.add(tmp.toString());
		
//		(1)
		view.add(CartLibrary.viewDelItemCart(cartItems));
		
//		(2)
		view.add(CartLibrary.viewSumItemCart(total));
		
//		(3)
		view.add(CartLibrary.priceCart(orderPrice));
		
		return view;
	}
	
	public static String viewDelItemCart(ArrayList<CartObject> cartItems ) {
		StringBuffer tmp = new StringBuffer();
		
		cartItems.forEach(item -> {
			tmp.append("<div class=\"modal fade\" id=\"delete-item-shopping-cart-id"+item.getCart_id()+"\" tabindex=\"-1\" aria-labelledby=\"delete-item-shopping-cart-label\" aria-hidden=\"true\">");
			tmp.append("<div class=\"modal-dialog\">");
			tmp.append("<div class=\"modal-content\">");
			tmp.append("<div class=\"modal-header\">");
			tmp.append("<h1 class=\"modal-title fs-5\" id=\"delete-item-shopping-cart-label\">Xóa sản phẩm khỏi giỏ hàng</h1>");
			tmp.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
			tmp.append("</div>");
			tmp.append("<div class=\"modal-body\">");
			tmp.append("Bạn có chắc chắn muốn xóa sản phẩm khỏi giỏ hàng không");
			tmp.append("</div>");
			tmp.append("<div class=\"modal-footer\">");
			tmp.append("<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Không</button>");
			tmp.append("<a href=\"/datn/cart/delete?id=" + item.getCart_id() + "\" class=\"btn btn-danger\">Có</a>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");			
		});
		
		return tmp.toString();
	}
	
	public static String viewSumItemCart(short total) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append(total);
		
		return tmp.toString();
	}
	
	public static String priceCart(int total) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append(total);
		
		return tmp.toString();
	}
	
}
