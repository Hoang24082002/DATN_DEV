package jsoft.home.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jsoft.ConnectionPool;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class UserRegister
 */
@WebServlet("/user/register")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// dinh nghia kieu noi dung xuat ve trinh khach
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// tim thong tin dang nhap
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		// thiết lập tập ký tự cần lấy
		request.setCharacterEncoding("utf-8");
		
		// lay thong tin
		String user_name = request.getParameter("txtUserName");
		String user_pass = request.getParameter("txtUserPass");
		String user_pass_conf = request.getParameter("txtUserPassConf");
		String user_fullname = request.getParameter("txtUserFullName");
		String user_birthday = request.getParameter("txtUserBirthday");
		String user_mobilephone = request.getParameter("txtUserPhone");
		String user_email = request.getParameter("txtUserEmail");
		String user_address = request.getParameter("txtUserAddress");
		
		if(user_name != null && !user_name.equalsIgnoreCase("") 
				&& user_pass != null && !user_pass.equalsIgnoreCase("")
				&& user_pass_conf != null && !user_pass_conf.equalsIgnoreCase("")
				&& user_fullname != null && !user_fullname.equalsIgnoreCase("")
				&& user_birthday != null && !user_birthday.equalsIgnoreCase("")
				&& user_mobilephone != null && !user_mobilephone.equalsIgnoreCase("")
				&& user_email != null && !user_email.equalsIgnoreCase("")
				&& user_address != null && !user_address.equalsIgnoreCase("")
				&& user_pass_conf.equalsIgnoreCase(user_pass)) {
			
			UserObject nUser = new UserObject();
			nUser.setUser_name(user_name);
			nUser.setUser_pass(user_pass);
			nUser.setUser_fullname(jsoft.library.Utilities.encode(user_fullname));
			nUser.setUser_birthday(user_birthday);
			nUser.setUser_mobilephone(user_mobilephone);
			nUser.setUser_email(user_email);
			nUser.setUser_address(user_address);
			nUser.setUser_permission((byte)1);
			nUser.setUser_created_date(jsoft.library.Utilities_date.getDate());
			nUser.setUser_last_logined(jsoft.library.Utilities_date.getDate());
			nUser.setUser_parent_id(0);
			
			ConnectionPool cp = (ConnectionPool)getServletContext().getAttribute("CPool");
			UserControl uc = new UserControl(cp);
			if(cp==null) {
				getServletContext().setAttribute("CPool", uc.getCP());
			}
			
			// thuc hien them moi
			boolean result = uc.addUser(nUser);
			
			if(result) {
				if(user == null) {
					// Tham chiếu phiên làm việc
					HttpSession session = request.getSession(true);
	
					// Đưa thông tin đăng nhập vào phiên
					session.setAttribute("userLogined", nUser);					
				}

				response.sendRedirect(request.getContextPath());
			} else {
				response.sendRedirect("/datn/register.jsp?err=add");
			}
		} else {
			response.sendRedirect("/datn/register.jsp?err=valueadd");
		}
						
	}

}
