package jsoft.ads.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.ads.charts.ChartControl;
import jsoft.library.Utilities;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class View
 */
@WebServlet("/view")
public class View extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public View() {
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

		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		if (user != null) {
			view(request, response, user);
		} else {
			response.sendRedirect("/datn");
		}

	}

	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// xác định kiểu nội dung xuất về trình khách
		response.setContentType(CONTENT_TYPE);

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();
		
		// tim bo quan ly ket noi
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}
		
		String statisticDYM = "";
		
		String o_om = request.getParameter("om");
		if(o_om != null) {
			statisticDYM = "om";
		}
		String o_oy = request.getParameter("oy");
		if(o_oy != null) {
			statisticDYM = "oy";
		}
		
		String r_rm = request.getParameter("rm");
		if(r_rm != null) {
			statisticDYM = "rm";
		}
		String r_ry = request.getParameter("ry");
		if(r_ry != null) {
			statisticDYM = "ry";
		}
		
		String p_pm = request.getParameter("pm");
		if(p_pm != null) {
			statisticDYM = "pm";
		}
		String p_py = request.getParameter("py");
		if(p_py != null) {
			statisticDYM = "py";
		}
		
		ChartControl cc = new ChartControl(cp);
		String date_now = jsoft.library.Utilities_date.getDate();
		String[] dYM = date_now.split("/");
		int day = 0, month = 0, year = 0;
		if (dYM.length > 0) {
			day = Utilities.convertStringToInt(dYM[0]);
			month = Utilities.convertStringToInt(dYM[1]);
			year = Utilities.convertStringToInt(dYM[2]);
		}
		
		System.out.println("statisticDYM = " + statisticDYM);
		
		ArrayList<String> viewChart = cc.viewChart(month, year, statisticDYM);
		
		
		cc.releaseConnection();
		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header");
		if(h != null) {
			h.include(request, response);
		}
		
		out.append("<main id=\"main\" class=\"main\">");
		
		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Tổng quan</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/adv/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");
		
		out.append("<section class=\"section dashboard\">");
		out.append("<div class=\"row\">");	
		
		out.append("<div class=\"col-lg-12\">"); // Start between side columns col-lg-12
		out.append("<div class=\"row\">");		
		// code
		out.append(viewChart.get(0));
		out.append(viewChart.get(1));
		out.append(viewChart.get(6));
		out.append(viewChart.get(2));
		out.append(viewChart.get(3));	
		
		out.append("</div>"); 
		out.append("</div>"); // End between side columns col-lg-12
		
		out.append("<div class=\"col-lg-8\">"); // Start Left side columns col-lg-8
		out.append("<div class=\"row\">");		
		// code	
		out.append(viewChart.get(5));
		out.append("</div>"); 
		out.append("</div>"); // End Left side columns col-lg-8
		
		out.append("<div class=\"col-lg-4\">"); // Start Right side columns col-lg-4
		// code
		out.append(viewChart.get(4));
		out.append("</div>"); // Start Right side columns columns col-lg-4
		
		out.append("</div>");
		out.append("</section>");
		
		out.append("</main><!-- End #main -->");
		
		// tham chiếu tìm sidebar
		RequestDispatcher f = request.getRequestDispatcher("/footer");
		if(f != null) {
			f.include(request, response);
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
