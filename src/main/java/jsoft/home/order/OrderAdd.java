package jsoft.home.order;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.objects.OrderObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class OrderAdd
 */
@WebServlet("/order/add")
public class OrderAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderAdd() {
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
		
		// lay thong tin
		String order_address = request.getParameter("userOrderAddress");
		int order_price = Utilities.getIntParam(request, "orderPrice");
		int orderDiscountPrice = Utilities.getIntParam(request, "orderDiscountPrice");
		int order_payment_method = Utilities.getIntParam(request, "payment-method-type");
		String order_detail_cartId = request.getParameter("orderDetailCartId");
	
		
		if(order_address != null && !order_address.equalsIgnoreCase("")
				&& order_price > 0
				&& orderDiscountPrice > 0
				&& order_payment_method > 0
				&& order_detail_cartId != null && !order_detail_cartId.equalsIgnoreCase("")) {
			
			// doi tuong
			OrderObject norder = new OrderObject();
			norder.setUser_id(user.getUser_id());
			norder.setOrder_date(jsoft.library.Utilities_date.getDate());
			norder.setOrder_price(order_price);
			norder.setOrder_discount_price(orderDiscountPrice);
			norder.setOrder_address(jsoft.library.Utilities.encode(order_address));
			norder.setOrder_payment_method(order_payment_method);
			norder.setOrder_detail_cartId(order_detail_cartId);
			
			// tim bo quan ly ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			OrderControl oc = new OrderControl(cp);
			
			// thuc hien them moi don hang
			boolean result = oc.addOrder(norder);
			
			// thuc hien edit edit Product Ordered tblcart				       
	        String[] numbers = order_detail_cartId.split("-");// Tách chuỗi thành mảng các số        
	        ArrayList<Integer> resultTransfer = new ArrayList<>();// Chuyển mảng thành ArrayList<Integer>
	        for (String number : numbers) {
	            if (!number.isEmpty()) {
	            	resultTransfer.add(Integer.parseInt(number));
	            }
	        }
	        boolean resultEdit = false;
	        boolean resultEditProductSold = false;
	        for (Integer integer : resultTransfer) {
	        	resultEdit = oc.editProductOrdered(integer);
	        	resultEditProductSold = oc.editProductSold(integer);
	        	if(!resultEdit) {
	        		break;
	        	}
			}
			
			// tra ve ket noi
			oc.releaseConnection();
						
			if(result && resultEdit && resultEditProductSold) {
				response.sendRedirect(request.getContextPath() + "?mess=orderdone");
			} else {
				response.sendRedirect(request.getContextPath() + "/order.jsp?err=add");
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/order.jsp?err=valueadd");
		}
		
		
	}

}
