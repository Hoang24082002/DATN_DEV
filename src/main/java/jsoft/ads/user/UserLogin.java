package jsoft.ads.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsoft.ConnectionPool;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class UserLogin
 */
@WebServlet("/user/login")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLogin() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// xác định kiểu nội dung xuất về trình khách
		response.setContentType(CONTENT_TYPE);
		
		// xác định kiểu nội dung xuất về trình khách
		StringBuffer out = new StringBuffer();
		
		// tìm tham số báo lỗi nếu có
		String error = request.getParameter("err");
		if (error != null) {
			out.append("<div class=\"alert border-secondary alert-dismissible fade show\" role=\"alert\">");

			switch (error) {
			case "param":
				out.append("Tham số lấy giá trị không chính xác");
				break;
			case "value":
				out.append("Không tồn tại giá trị cho tài khoản");
				break;
			case "notok":
				out.append("Đăng nhập không thành công");
				break;
			default:
				out.append("Có lỗi trong quá trình đăng nhập");
			}
			out.append(
					"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>");
			out.append("</div>");
		}
		
		// lấy dữ liệu từ login.jsp
		String username = request.getParameter("txtusername");
        String userpass = request.getParameter("txtuserpass");
        
        if (username != null && userpass != null) {
			username = username.trim();
			userpass = userpass.trim();

			if (!username.equalsIgnoreCase("") && !userpass.equalsIgnoreCase("")) {
				// Tham chiếu ngữ cảnh ứng dụng
				ServletContext application = getServletConfig().getServletContext();

				// Tìm bộ quản lý kết nối trong không gian ngữ cảnh
				ConnectionPool cp = (ConnectionPool) application.getAttribute("CPool");

				// Tạo đối tượng thực thi chúc năng
				UserControl uc = new UserControl(cp);
				if (cp == null) {
					application.setAttribute("CPool", uc.getCP());
				}

				// Thực hiện đăng nhập
				UserObject user = uc.getUserObject(username, userpass);

				// Trả về kết nối
				uc.releaseConnection();

				if (user != null) {
					// Tham chiếu phiên làm việc
					HttpSession session = request.getSession(true);

					// Đưa thông tin đăng nhập vào phiên
					session.setAttribute("userLogined", user);

					// Trở về giao diện chính
					if(user.getUser_permission() == 1 ) {
						// Chuyển hướng đến trang JSP sau khi đăng nhập thành công
						response.sendRedirect(request.getContextPath());
					} else {
						response.sendRedirect("/datn/view");						
					}

				} else {
					response.sendRedirect(request.getContextPath() + "/login.jsp?err=notok");
				}

			} else {
				response.sendRedirect("/datn/user/login?err=value");
			}

		} else {
			response.sendRedirect("/datn/user/login?err=param");

		}
	}

}
