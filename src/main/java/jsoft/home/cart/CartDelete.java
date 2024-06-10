package jsoft.home.cart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.ads.productGroup.ProductGroupControl;
import jsoft.library.Utilities;
import jsoft.objects.CartObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class CartDelete
 */
@WebServlet("/cart/delete")
public class CartDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// tim thong tin dang nhap
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
		
		int id = Utilities.getIntParam(request, "id");
		
		if(user != null && id > 0) { 
			// lay ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			CartControl cc = new CartControl(cp);
			
			CartObject d_c = new CartObject();
			d_c.setCart_id(id);
			d_c.setUser_id(user.getUser_id());
			
			boolean result = cc.delCart(d_c);
			
			if(result) {
				response.sendRedirect(request.getContextPath());
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
