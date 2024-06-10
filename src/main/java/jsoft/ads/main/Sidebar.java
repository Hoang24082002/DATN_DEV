package jsoft.ads.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Sidebar
 */
@WebServlet("/sidebar")
public class Sidebar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sidebar() {
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
		PrintWriter out = response.getWriter();

		HashMap<String, String> collapsed = new HashMap<>();
		HashMap<String, String> show = new HashMap<>();
		HashMap<String, String> actives = new HashMap<>();

		// tìm tham số vị trí
		String pos = request.getParameter("pos");
		if (pos != null) {
			String menu = pos.substring(0, 2);
			String act = pos.substring(2);

			switch (menu) {
			case "ur":
				collapsed.put("user", "");
				show.put("user", "show"); // tim dc la show ko dc thi la ko co gi
				switch (act) {
				case "list":
					actives.put("list", "class=\"active\"");
					break;
				case "upd":
					break;
				case "trash": // thung rac
					actives.put("utrash", "class=\"active\"");
					break;
				case "log":
					break;
				}
				break;
			case "ar":
				collapsed.put("article", "");
				show.put("article", "show"); // tim dc la show ko dc thi la ko co gi
				switch (act) {
				case "list":
					actives.put("list", "class=\"active\"");
					break;
				case "add":
					actives.put("aadd", "class=\"active\"");
					break;
				case "scall":
					actives.put("scall", "class=\"active\"");
					break;
				case "ctall":
					actives.put("ctall", "class=\"active\"");
					break;
				case "trash": // thung rac
					actives.put("atrash", "class=\"active\"");
					break;
				case "log":
					break;
				}
				break;
			}
			
		}

		out.append("<!-- ======= Sidebar ======= -->");
		out.append("<aside id=\"sidebar\" class=\"sidebar\">");

		out.append("<ul class=\"sidebar-nav\" id=\"sidebar-nav\">");

		out.append("<li class=\"nav-item\">");
		out.append("<a class=\"nav-link " + collapsed.getOrDefault("Dashboard", "collapsed") + "\" href=\"/datn/view\">");
		out.append("<i class=\"bi bi-house\"></i>");
		out.append("<span>Dashboard</span>");
		out.append("</a>");
		out.append("</li><!-- End Dashboard Nav -->");

		out.append("<li class=\"nav-item\">");
		out.append("<a class=\"nav-link " + collapsed.getOrDefault("user", "collapsed")+ "\" data-bs-target=\"#user-nav\" data-bs-toggle=\"collapse\" href=\"#\">");
		out.append("<i class=\"bi bi-people\"></i><span>Người sử dụng</span><i class=\"bi bi-chevron-down ms-auto\"></i>");
		out.append("</a>");
		out.append("<ul id=\"user-nav\" class=\"nav-content collapse " + show.getOrDefault("user", "")+ " \" data-bs-parent=\"#sidebar-nav\">");
		out.append("<li>");
		out.append("<a href=\"/datn/user/list\"" + actives.getOrDefault("list", "") + ">");
		out.append("<i class=\"bi bi-list-ul\"></i><span>Danh sách</span>");
		out.append("</a>");
		out.append("</li>");

		out.append("<li>");
		out.append("<a href=\"/datn/user/list?trash\" " + actives.getOrDefault("utrash", "") + ">");
		out.append("<i class=\"bi bi-trash3\"></i><span>Thùng rác</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("</ul>");
		out.append("</li><!-- End User Nav -->");
		
		
		out.append("<li class=\"nav-item\"><!-- Start Product Nav -->");
		out.append("<a class=\"nav-link "+collapsed.getOrDefault("product", "collapsed")+"\" data-bs-target=\"#product-nav\" data-bs-toggle=\"collapse\" href=\"#\">");
		out.append("<i class=\"bi bi-box-seam\"></i><span>Sản phẩm</span><i class=\"bi bi-chevron-down ms-auto\"></i>");
		out.append("</a>");
		out.append("<ul id=\"product-nav\" class=\"nav-content collapse"+show.getOrDefault("product", "")+"\" data-bs-parent=\"#sidebar-nav\">");
		out.append("<li>");
		out.append("<a href=\"/datn/product/list\" "+actives.getOrDefault("list", "")+">");
		out.append("<i class=\"bi bi-circle\"></i><span>Danh sách</span>");
		out.append("</a>");
		
		out.append("<a href=\"/datn/product/list?trash\" "+actives.getOrDefault("atrash", "")+">");
		out.append("<i class=\"bi bi-circle\"></i><span>Thùng rác</span>");
		out.append("</a>");
		out.append("</li>");

		out.append("<li>");
		out.append("<a href=\"/datn/productGroup/list\" "+actives.getOrDefault("", "")+">");
		out.append("<i class=\"bi bi-circle\"></i><span>Nhóm sản phẩm</span>");
		out.append("</a>");
		out.append("</li>");
		
		out.append("<li>");
		out.append("<a href=\"/datn/productSection/list\" "+actives.getOrDefault("", "")+">");
		out.append("<i class=\"bi bi-circle\"></i><span>Thành phần sản phẩm</span>");
		out.append("</a>");
		out.append("</li>");
		
		out.append("<li>");
		out.append("<a href=\"/datn/productCategory/list\" "+actives.getOrDefault("", "")+">");
		out.append("<i class=\"bi bi-circle\"></i><span>Danh mục sản phẩm</span>");
		out.append("</a>");
		out.append("</li>");
		
		out.append("<li>");
		out.append("<a href=\"/datn/color/list\" "+actives.getOrDefault("", "")+">");
		out.append("<i class=\"bi bi-circle\"></i><span>Màu sắc sản phẩm</span>");
		out.append("</a>");
		out.append("</li>");
		
		out.append("<li>");
		out.append("<a href=\"/datn/size/list\" "+actives.getOrDefault("", "")+">");
		out.append("<i class=\"bi bi-circle\"></i><span>Kích cỡ sản phẩm</span>");
		out.append("</a>");
		out.append("</li>");
		
		out.append("</ul>");
		out.append("</li><!-- End Product Nav -->");
		
		
		out.append("<li class=\"nav-item\">");
		out.append("<a class=\"nav-link collapsed\" data-bs-target=\"#icons-nav\" data-bs-toggle=\"collapse\" href=\"#\">");
		out.append("<i class=\"bi bi-basket\"></i>Đơn hàng</span><i class=\"bi bi-chevron-down ms-auto\"></i>");
		out.append("</a>");
		out.append("<ul id=\"icons-nav\" class=\"nav-content collapse \" data-bs-parent=\"#sidebar-nav\">");
		out.append("<li>");
		out.append("<a href=\"/datn/order/list\">");
		out.append("<i class=\"bi bi-circle\"></i><span>Danh sách</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("<li>");
		out.append("<a href=\"/datn/order/list?trash\">");
		out.append("<i class=\"bi bi-circle\"></i><span>Danh sách đơn hàng bị hủy</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("<li>");
		out.append("<a href=\"icons-boxicons.html\">");
		out.append("<i class=\"bi bi-circle\"></i><span>Boxicons</span>");
		out.append("</a>");
		out.append("</li>");
		out.append("</ul>");
		out.append("</li><!-- End Image Nav -->");
		

//		out.append("<li class=\"nav-item\">");
//		out.append("<a class=\"nav-link collapsed\" data-bs-target=\"#icons-nav\" data-bs-toggle=\"collapse\" href=\"#\">");
//		out.append("<i class=\"bi bi-gem\"></i><span>Icons</span><i class=\"bi bi-chevron-down ms-auto\"></i>");
//		out.append("</a>");
//		out.append("<ul id=\"icons-nav\" class=\"nav-content collapse \" data-bs-parent=\"#sidebar-nav\">");
//		out.append("<li>");
//		out.append("<a href=\"icons-bootstrap.html\">");
//		out.append("<i class=\"bi bi-circle\"></i><span>Bootstrap Icons</span>");
//		out.append("</a>");
//		out.append("</li>");
//		out.append("<li>");
//		out.append("<a href=\"icons-remix.html\">");
//		out.append("<i class=\"bi bi-circle\"></i><span>Remix Icons</span>");
//		out.append("</a>");
//		out.append("</li>");
//		out.append("<li>");
//		out.append("<a href=\"icons-boxicons.html\">");
//		out.append("<i class=\"bi bi-circle\"></i><span>Boxicons</span>");
//		out.append("</a>");
//		out.append("</li>");
//		out.append("</ul>");
//		out.append("</li><!-- End Icons Nav -->");

//		out.append("<li class=\"nav-heading\">Pages</li>");

//		out.append("<li class=\"nav-item\">");
//		out.append("<a class=\"nav-link collapsed\" href=\"users-profile.html\">");
//		out.append("<i class=\"bi bi-person\"></i>");
//		out.append("<span>Profile</span>");
//		out.append("</a>");
//		out.append("</li><!-- End Profile Page Nav -->");
//
//		out.append("<li class=\"nav-item\">");
//		out.append("<a class=\"nav-link collapsed\" href=\"pages-faq.html\">");
//		out.append("<i class=\"bi bi-question-circle\"></i>");
//		out.append("<span>F.A.Q</span>");
//		out.append("</a>");
//		out.append("</li><!-- End F.A.Q Page Nav -->");
//
//		out.append("<li class=\"nav-item\">");
//		out.append("<a class=\"nav-link collapsed\" href=\"pages-contact.html\">");
//		out.append("<i class=\"bi bi-envelope\"></i>");
//		out.append("<span>Contact</span>");
//		out.append("</a>");
//		out.append("</li><!-- End Contact Page Nav -->");
//
//		out.append("<li class=\"nav-item\">");
//		out.append("<a class=\"nav-link collapsed\" href=\"pages-register.html\">");
//		out.append("<i class=\"bi bi-card-list\"></i>");
//		out.append("<span>Register</span>");
//		out.append("</a>");
//		out.append("</li><!-- End Register Page Nav -->");
//
//		out.append("<li class=\"nav-item\">");
//		out.append("<a class=\"nav-link collapsed\" href=\"pages-login.html\">");
//		out.append("<i class=\"bi bi-box-arrow-in-right\"></i>");
//		out.append("<span>Login</span>");
//		out.append("</a>");
//		out.append("</li><!-- End Login Page Nav -->");
//
//		out.append("<li class=\"nav-item\">");
//		out.append("<a class=\"nav-link collapsed\" href=\"pages-error-404.html\">");
//		out.append("<i class=\"bi bi-dash-circle\"></i>");
//		out.append("<span>Error 404</span>");
//		out.append("</a>");
//		out.append("</li><!-- End Error 404 Page Nav -->");

		out.append("</ul>");

		out.append("</aside><!-- End Sidebar-->");
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
