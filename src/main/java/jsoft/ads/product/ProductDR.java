package jsoft.ads.product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.library.Utilities_date;
import jsoft.objects.ProductObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupDR
 */
@WebServlet("/product/dr")
public class ProductDR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductDR() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// tim thong tin dang nhap
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
		
		int id = Utilities.getIntParam(request, "id");
		int pId = Utilities.getIntParam(request, "pid"); //  create author ID
		
		if(user != null && id > 0) {
			// lay ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ProductControl pc = new ProductControl(cp);
			
			ProductObject d_p = new ProductObject();
			d_p.setProduct_id(id);
			d_p.setProduct_created_author_id(pId);
			d_p.setProduct_manager_id(user.getUser_id());
			d_p.setProduct_deleted_date(Utilities_date.getDate());
			d_p.setProduct_deleted_author(user.getUser_name());
			
			ProductObject r_p = new ProductObject();
			r_p.setProduct_id(id);
			
			// tim tham so xac dinh xoa
			String trash = request.getParameter("t");

			// tim tham so xac dinh phuc hoi
			String restore = request.getParameter("r");
			
			String url = "/datn/product/list";
			boolean result;
			if (trash == null) {
				if (restore == null) {
					result = pc.delProduct(d_p);
					url += "?trash";
				} else {
					result = pc.editProduct(r_p, PRODUCT_EDIT_TYPE.RESTORE);
				}
			} else {
				result = pc.editProduct(d_p, PRODUCT_EDIT_TYPE.TRASH);
			}
			
			// tra ve ket noi
			pc.releaseConnection();

			if (result) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(url + "&?err=notok");
			}
			
		} else {
			response.sendRedirect("/datn/product/list?err=del");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
