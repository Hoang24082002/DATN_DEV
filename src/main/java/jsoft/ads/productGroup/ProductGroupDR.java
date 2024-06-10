package jsoft.ads.productGroup;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.library.Utilities_date;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupDR
 */
@WebServlet("/productGroup/dr")
public class ProductGroupDR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductGroupDR() {
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
			ProductGroupControl pgc = new ProductGroupControl(cp);
			
			ProductGroupObject d_pg = new ProductGroupObject();
			d_pg.setPg_id(id);
			d_pg.setPg_created_author_id(pId);
			d_pg.setPg_manager_id(user.getUser_id());
			d_pg.setPg_deleted_date(Utilities_date.getDate());
			d_pg.setPg_deleted_author(user.getUser_name());
			
			ProductGroupObject r_pg = new ProductGroupObject();
			r_pg.setPg_id(id);
			
			// tim tham so xac dinh xoa
			String trash = request.getParameter("t");

			// tim tham so xac dinh phuc hoi
			String restore = request.getParameter("r");
			
			String url = "/datn/productGroup/list";
			boolean result;
			if (trash == null) {
				if (restore == null) {
					result = pgc.delProductGroup(d_pg);
					url += "?trash";
				} else {
					result = pgc.editProductGroup(r_pg, PRODUCTGROUP_EDIT_TYPE.RESTORE);
				}
			} else {
				result = pgc.editProductGroup(d_pg, PRODUCTGROUP_EDIT_TYPE.TRASH);
			}
			
			// tra ve ket noi
			pgc.releaseConnection();

			if (result) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(url + "&?err=notok");
			}
			
		} else {
			response.sendRedirect("/datn/productGroup/list?err=del");
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
