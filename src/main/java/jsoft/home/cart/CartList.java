package jsoft.home.cart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.objects.CartObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class CartList
 */
@WebServlet("/cart/list")
public class CartList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
		if(user == null) {
			response.sendRedirect("/datn");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// tim thong tin dang nhap
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
		
		// thiết lập tập ký tự cần lấy
		request.setCharacterEncoding("utf-8");
		
		// lay id san pham
		int id = jsoft.library.Utilities.getIntParam(request, "product_id_cart");
		
		String product_color = request.getParameter("product-color-choose");
		String product_size = request.getParameter("product-size-choose");
		int product_price = jsoft.library.Utilities.getIntParam(request, "product_price_cart");
		int product_discount_price = jsoft.library.Utilities.getIntParam(request, "product_discount_price_cart");
		if(id > 0) {
			if(product_color != null && !product_color.equalsIgnoreCase("")
			&& product_size != null && !product_size.equalsIgnoreCase("")
			&& product_price > 0
			&& product_discount_price > 0) {
				
				// doi tuong
				CartObject nc = new CartObject();
				nc.setProduct_id(id);
				nc.setUser_id(user.getUser_id());
				nc.setProduct_color(jsoft.library.Utilities.encode(product_color));
				nc.setProduct_size(jsoft.library.Utilities.encode(product_size));
				nc.setProduct_price(product_price);
				nc.setProduct_discount_price(product_discount_price);
				
				// tim bo quan ly ket noi
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				CartControl cc = new CartControl(cp);
				
				boolean result = cc.addOrUpdateCart(nc);
				
				cc.releaseConnection();
				
				if(result) {
					response.sendRedirect("/datn/detailProduct.jsp?id="+id+"");
				} else {
					response.sendRedirect("/datn/detailProduct.jsp?id="+id+"&err=add");
				}
			} else {
				response.sendRedirect("/datn/detailProduct.jsp?id="+id+"&err=valueadd");
			}
		} else {
			System.out.println(" ko ton tai ID ");
		}
		
	}

}
