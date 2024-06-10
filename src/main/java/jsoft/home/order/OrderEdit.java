package jsoft.home.order;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.ads.productGroup.ProductGroupControl;
import jsoft.library.Utilities;
import jsoft.library.Utilities_date;
import jsoft.objects.OrderObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class OrderEdit
 */
@WebServlet("/order/edit")
public class OrderEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		// xac dinh kieu noi dung xuat ve trinh khach
		response.setContentType(CONTENT_TYPE);

		// tao doi tuong thuc thi xuat noi dung
		PrintWriter out = response.getWriter();

		// tim bo quan ly ket noi
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}
		
		// lay id order:
		int order_id = Utilities.getIntParam(request, "id");
		if(order_id > 0) {
			OrderControl pgc = new OrderControl(cp);
			
			OrderObject eOrder = new OrderObject();
			eOrder.setOrder_id(order_id);
			eOrder.setOrder_delete_date(Utilities_date.getDate());
			
			boolean result = pgc.editOrder(eOrder);
			
			if(result) {
				response.sendRedirect("/datn");
			} else {
				response.sendRedirect("/datn?err=notok");
			}
		} else {
			response.sendRedirect("/datn?err=del");
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
