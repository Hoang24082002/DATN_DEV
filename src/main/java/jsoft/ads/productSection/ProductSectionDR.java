package jsoft.ads.productSection;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.library.Utilities_date;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupDR
 */
@WebServlet("/productSection/dr")
public class ProductSectionDR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductSectionDR() {
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
		
		short id = Utilities.getShortParam(request, "id");
		int pId = Utilities.getIntParam(request, "pid"); //  create author ID
		
		if(user != null && id > 0) {
			// lay ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ProductSectionControl psc = new ProductSectionControl(cp);
			
			ProductSectionObject d_ps = new ProductSectionObject();
			d_ps.setPs_id(id);
			d_ps.setPs_created_author_id(pId);
			d_ps.setPs_manager_id(user.getUser_id());
			d_ps.setPs_deleted_date(Utilities_date.getDate());
			d_ps.setPs_deleted_author(user.getUser_name());
			
			ProductSectionObject r_ps = new ProductSectionObject();
			r_ps.setPs_id(id);
			
			// tim tham so xac dinh xoa
			String trash = request.getParameter("t");

			// tim tham so xac dinh phuc hoi
			String restore = request.getParameter("r");
			
			String url = "/datn/productSection/list";
			boolean result;
			if (trash == null) {
				if (restore == null) {
					result = psc.delProductSection(d_ps);
					url += "?trash";
				} else {
					result = psc.editProductSection(r_ps, PRODUCTSECTION_EDIT_TYPE.RESTORE);
				}
			} else {
				result = psc.editProductSection(d_ps, PRODUCTSECTION_EDIT_TYPE.TRASH);
			}
			
			// tra ve ket noi
			psc.releaseConnection();

			if (result) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(url + "&?err=notok");
			}
			
		} else {
			response.sendRedirect("/datn/productSection/list?err=del");
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
