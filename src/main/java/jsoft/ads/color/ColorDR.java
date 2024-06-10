package jsoft.ads.color;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.library.Utilities_date;
import jsoft.objects.ColorObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ColorDR
 */
@WebServlet("/color/dr")
public class ColorDR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ColorDR() {
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
			ColorControl pgc = new ColorControl(cp);
			
			ColorObject d_c = new ColorObject();
			d_c.setC_id(id);
			d_c.setC_created_author_id(pId);
			d_c.setC_manager_id(user.getUser_id());
			d_c.setC_deleted_date(Utilities_date.getDate());
			d_c.setC_deleted_author(user.getUser_name());
			
			ColorObject r_c = new ColorObject();
			r_c.setC_id(id);
			
			// tim tham so xac dinh xoa
			String trash = request.getParameter("t");

			// tim tham so xac dinh phuc hoi
			String restore = request.getParameter("r");
			
			String url = "/datn/color/list";
			boolean result;
			if (trash == null) {
				if (restore == null) {
					result = pgc.delColor(d_c);
					url += "?trash";
				} else {
					result = pgc.editColor(r_c, COLOR_EDIT_TYPE.RESTORE);
				}
			} else {
				result = pgc.editColor(d_c, COLOR_EDIT_TYPE.TRASH);
			}
			
			// tra ve ket noi
			pgc.releaseConnection();

			if (result) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(url + "&?err=notok");
			}
			
		} else {
			response.sendRedirect("/datn/color/list?err=del");
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
