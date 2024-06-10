package jsoft.ads.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import jsoft.ConnectionPool;
import jsoft.ads.productGroup.ProductGroupControl;
import jsoft.library.ORDER;
import jsoft.library.Utilities;
import jsoft.objects.OrderObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class OrderList
 */
@WebServlet("/order/list")
public class OrderList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// tìm thông tin đăng nhập
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		if (user != null) {
			view(request, response, user);
		} else {
			response.sendRedirect("/datn");
		}
	}

	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user)
			throws ServletException, IOException {
		// xac dinh kieu noi dung xuat ve trinh khach
		response.setContentType(CONTENT_TYPE);

		// tao doi tuong thuc thi xuat noi dung
		PrintWriter out = response.getWriter();

		// tim bo quan ly ket noi
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}

		// lay tu khoa tim kiem
		String key = request.getParameter("key");
		String saveKey = (key != null && !key.equalsIgnoreCase("")) ? key.trim() : "";

		// tao doi tuong thuc thi chuc nang
		OrderControl pgc = new OrderControl(cp);

		OrderObject similar = new OrderObject();

		// tham so xac dinh loai danh sach
		String trash = request.getParameter("trash");
		String titleList, pos;
		if (trash == null) {
			similar.setOrder_destroy(false);
			pos = "olist";
			titleList = "Danh sách đơn hàng";
		} else {
			similar.setOrder_destroy(true);
			pos = "otrash";
			titleList = "Danh sách đơn hàng bị hủy";
		}

		int page = Utilities.getIntParam(request, "page");
		if (page < 1) {
			page = 0;
		}

		// lay cau truc
		Quartet<OrderObject, Short, Byte, UserObject> infos = new Quartet<>(similar, (short) page, (byte) 5,user);
		ArrayList<String> viewList = pgc.viewOrderAdmin(infos, new Pair<>(ORDER_SOFT.ID, ORDER.DESC));

		// tra ve ket not
		pgc.releaseConnection();

		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header?pos=" + pos);
		if (h != null) {
			h.include(request, response);
		}

		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>" + titleList + "</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/datn/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Đơn hàng</li>");
		if (trash == null) {
			out.append("<li class=\"breadcrumb-item active\">Danh sách</li>");
		} else {
			out.append("<li class=\"breadcrumb-item active\">Danh sách bị hủy</li>");
		}
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section\">");
		out.append("<div class=\"row\">");

		out.append("<div class=\"col-lg-12\">");

//				out.append("<div class=\"card\">");
//				out.append("<div class=\"card-body\">");
//		if (trash == null) {
//			out.append("<div class=\"d-flex justify-content-start\">");
//
//			out.append(
//					"<button type=\"button\" class=\"btn btn-primary btn-sm my-2\" data-bs-toggle=\"modal\" data-bs-target=\"#addOrder\">");
//			out.append("<i class=\"bi bi-plus-lg\"></i> Thêm mới");
//			out.append("</button>");
//
//			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2 ms-3\">");
//			out.append("<a href=\"/datn/productGroup/list?trash\" class=\"text-light\">");
//			out.append("<i class=\"bi bi-trash3\"></i> <span>Thùng rác</span>");
//			out.append("</a>");
//			out.append("</button>");
//
//			out.append("</div>");
//
//			out.append(
//					"<div class=\"modal fade\" id=\"addOrder\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
//			out.append("<div class=\"modal-dialog modal-lg\">");
//
//			out.append(
//					"<form method=\"post\" action=\"/datn/productGroup/list\" class=\"needs-validation\" novalidate>");
//			out.append("<div class=\"modal-content\">");
//			out.append("<div class=\"modal-header\">");
//			out.append(
//					"<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\"><i class=\"bi bi-plus-lg\"></i> Thêm mới nhóm sản phẩm</h1>");
//			out.append(
//					"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
//			out.append("</div>");
//			out.append("<div class=\"modal-body\">");
//
//			out.append("<div class=\"row mb-3\">");
//			out.append("<div class=\"col-lg-6\">");
//			out.append("<label for=\"productGroupName\" class=\"form-label\">Tên nhóm sản phẩm</label>");
//			out.append(
//					"<input type=\"text\" class=\"form-control\" id=\"productGroupName\" name=\"txtOrderName\" required >");
//			out.append("<div class=\"invalid-feedback\">Hãy nhập vào tên của nhóm sản phẩm</div>");
//			out.append("</div>");
//
//			out.append("<div class=\"col-lg-6\">");
//			out.append("<label for=\"manager\" class=\"form-label\">Người quản lý nhóm sản phẩm</label>");
//			out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
//			out.append(viewList.get(1));
//			out.append("</select>");
//			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
//			out.append("</div>");
//
//			out.append("</div>"); // end row mb-3
//
//			out.append("<div class=\"row mb-3\">");
//			out.append("<div class=\"col-lg-12\">");
//			out.append("<label for=\"productGroupNotes\" class=\"form-label\">Chú thích nhóm sản phẩm</label>");
//			out.append(
//					"<textarea row=\"8\" class=\"form-control\" id=\"productGroupNotes\" name=\"txtOrderNotes\" required ></textarea>");
//			out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của nhóm sản phẩm</div>");
//			out.append("</div>");
//			out.append("</div>");
//
//			out.append("</div>");
//			out.append("<div class=\"modal-footer\">");
//			out.append(
//					"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-plus-lg\"></i> Thêm mới</button>");
//			out.append(
//					"<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\"><i class=\"bi bi-x-lg\"></i> Thoát</button>");
//
//			out.append("</div>");
//			out.append("</div>"); // modal-content
//			out.append("</form>");
//			out.append("</div>");
//			out.append("</div>");
//		}
		out.append(viewList.get(0));

//				out.append("</div>"); // end card-body
//				out.append("</div>"); // end card

		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-12\">");
		// chart
		out.append("</div>");
		out.append("</div>");
		out.append("</section>");

		out.append("</main><!-- End #main -->");

		// tham chiếu tìm footer
		RequestDispatcher f = request.getRequestDispatcher("/footer");
		if (f != null) {
			f.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// tim thong tin dang nhap
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		// thiết lập tập ký tự cần lấy
		request.setCharacterEncoding("utf-8");
		
		// lấy thông tin
		int order_id = Utilities.getIntParam(request, "oi");
		int order_status = Utilities.getIntParam(request, "os");
		
		if(order_id > 0 && order_status > 0) {
			OrderObject eOrder = new OrderObject();
			eOrder.setOrder_id(order_id);
			eOrder.setOrder_status(order_status);
			
			// tim bo quan ly ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			OrderControl pgc = new OrderControl(cp);
			
			// thuc hien them moi
			boolean result = pgc.editOrder(eOrder, ORDER_EDIT_TYPE.GENERAL);
			// tra ve ket noi
			pgc.releaseConnection();
			
			if(result) {
				response.sendRedirect("/datn/order/list");
			} else {
				response.sendRedirect("/datn/order/list?err=edit");
			}
		} else {
			response.sendRedirect("/datn/order/list?err=profiles");
		}
		
		
		
	}

}
