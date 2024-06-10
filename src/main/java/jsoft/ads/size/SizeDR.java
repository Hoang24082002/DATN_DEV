package jsoft.ads.size;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.library.Utilities_date;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class SizeDR
 */
@WebServlet("/size/dr")
public class SizeDR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SizeDR() {
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
			SizeControl sc = new SizeControl(cp);
			
			SizeObject d_s = new SizeObject();
			d_s.setS_id(id);
			d_s.setS_created_author_id(pId);
			d_s.setS_manager_id(user.getUser_id());
			d_s.setS_deleted_date(Utilities_date.getDate());
			d_s.setS_deleted_author(user.getUser_name());
			
			SizeObject r_c = new SizeObject();
			r_c.setS_id(id);
			
			// tim tham so xac dinh xoa
			String trash = request.getParameter("t");

			// tim tham so xac dinh phuc hoi
			String restore = request.getParameter("r");
			
			String url = "/datn/size/list";
			boolean result;
			if (trash == null) {
				if (restore == null) {
					result = sc.delSize(d_s);
					url += "?trash";
				} else {
					result = sc.editSize(r_c, SIZE_EDIT_TYPE.RESTORE);
				}
			} else {
				result = sc.editSize(d_s, SIZE_EDIT_TYPE.TRASH);
			}
			
			// tra ve ket noi
			sc.releaseConnection();

			if (result) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(url + "&?err=notok");
			}
			
		} else {
			response.sendRedirect("/datn/size/list?err=del");
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
