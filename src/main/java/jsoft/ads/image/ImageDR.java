package jsoft.ads.image;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.library.Utilities;
import jsoft.library.Utilities_date;
import jsoft.objects.ImageObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ImageDR
 */
@WebServlet("/image/dr")
public class ImageDR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageDR() {
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
			ImageControl sc = new ImageControl(cp);
			
			ImageObject d_i = new ImageObject();
			d_i.setI_id(id);
			d_i.setI_created_author_id(pId);
			d_i.setI_manager_id(user.getUser_id());
			d_i.setI_deleted_date(Utilities_date.getDate());
			d_i.setI_deleted_author(user.getUser_name());
			
			ImageObject r_i = new ImageObject();
			r_i.setI_id(id);
			
			// tim tham so xac dinh xoa
			String trash = request.getParameter("t");

			// tim tham so xac dinh phuc hoi
			String restore = request.getParameter("r");
			
			String url = "/datn/image/list";
			boolean result;
			if (trash == null) {
				if (restore == null) {
					result = sc.delImage(d_i);
					url += "?trash";
				} else {
					result = sc.editImage(r_i, IMAGE_EDIT_TYPE.RESTORE);
				}
			} else {
				result = sc.editImage(d_i, IMAGE_EDIT_TYPE.TRASH);
			}
			
			// tra ve ket noi
			sc.releaseConnection();

			if (result) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(url + "&?err=notok");
			}
			
		} else {
			response.sendRedirect("/datn/image/list?err=del");
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
