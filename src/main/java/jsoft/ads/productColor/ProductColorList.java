//package jsoft.ads.productColor;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.javatuples.Pair;
//import org.javatuples.Quartet;
//import jsoft.ConnectionPool;
//import jsoft.library.ORDER;
//import jsoft.objects.ProductGroupObject;
//import jsoft.objects.UserObject;
//
///**
// * Servlet implementation class ProductGroupList
// */
//@WebServlet("/productGroup/list")
//public class ProductColorList extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//	// định nghĩa kiểu nội dung xuất về trình khách
//	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
//	
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public ProductColorList() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//		// tìm thông tin đăng nhập
//		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
//
//		if (user != null) {
//			view(request, response, user);
//		} else {
//			response.sendRedirect("/datn/user/login");
//		}
//	}
//	
//	protected void view(HttpServletRequest request, HttpServletResponse response, UserObject user)
//			throws ServletException, IOException {
//
//		// xac dinh kieu noi dung xuat ve trinh khach
//		response.setContentType(CONTENT_TYPE);
//
//		// tao doi tuong thuc thi xuat noi dung
//		PrintWriter out = response.getWriter();
//
//		// tim bo quan ly ket noi
//		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
//		if (cp == null) {
//			getServletContext().setAttribute("CPool", cp);
//		}
//
//		// lay tu khoa tim kiem
//		String key = request.getParameter("key");
//		String saveKey = (key != null && !key.equalsIgnoreCase("")) ? key.trim() : "";
//
//		// tao doi tuong thuc thi chuc nang
//		ProductColorControl pgc = new ProductColorControl(cp);
//
//		ProductGroupObject similar = new ProductGroupObject();
//		similar.setPg_created_author_id(user.getUser_id());
//
//		// tham so xac dinh loai danh sach
//		String trash = request.getParameter("trash");
//		String titleList, pos;
//		if (trash == null) {
//			similar.setPg_delete(false);
//			pos = "pglist";
//			titleList = "Danh sách nhóm sản phẩm";
//		} else {
//			similar.setPg_delete(true);
//			pos = "pgtrash";
//			titleList = "Danh sách nhóm sản phẩm bị xóa";
//		}
//
//		// lay cau truc
//		Quartet<ProductGroupObject, Short, Byte, UserObject> infos = new Quartet<>(similar, (short)1, (byte) 15, user);
//		ArrayList<String> viewList = pgc.viewProductGroup(infos, new Pair<>(PRODUCTCOLOR_SOFT.ID, ORDER.DESC));
//
//		// tra ve ket not
//		pgc.releaseConnection();
//
//		// tham chiếu tìm header
//		RequestDispatcher h = request.getRequestDispatcher("/header?pos=" + pos);
//		if (h != null) {
//			h.include(request, response);
//		}
//
//		out.append("<main id=\"main\" class=\"main\">");
//
//		RequestDispatcher error = request.getRequestDispatcher("/error");
//		if (error != null) {
//			error.include(request, response);
//		}
//
//		out.append("<div class=\"pagetitle d-flex\">");
//		out.append("<h1>" + titleList + "</h1>");
//		out.append("<nav class=\"ms-auto\">");
//		out.append("<ol class=\"breadcrumb\">");
//		out.append("<li class=\"breadcrumb-item\"><a href=\"/datn/view\"><i class=\"bi bi-house\"></i></a></li>");
//		out.append("<li class=\"breadcrumb-item\">Nhóm sản phẩm</li>");
//		if (trash == null) {
//			out.append("<li class=\"breadcrumb-item active\">Danh sách</li>");			
//		} else {
//			out.append("<li class=\"breadcrumb-item active\">Danh sách xóa</li>");
//		}
//		out.append("</ol>");
//		out.append("</nav>");
//		out.append("</div><!-- End Page Title -->");
//
//		out.append("<section class=\"section\">");
//		out.append("<div class=\"row\">");
//
//		out.append("<div class=\"col-lg-12\">");
//
////		out.append("<div class=\"card\">");
////		out.append("<div class=\"card-body\">");
//		if (trash == null) {
//			out.append("<div class=\"d-flex justify-content-start\">");
//			
//			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2\" data-bs-toggle=\"modal\" data-bs-target=\"#addProductGroup\">");
//			out.append("<i class=\"bi bi-person-plus\"></i> Thêm mới");
//			out.append("</button>");
//			
//			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2 ms-3\">");
//			out.append("<a href=\"/datn/productGroup/list?trash\" class=\"text-light\">");
//			out.append("<i class=\"bi bi-trash3\"></i><span>Thùng rác</span>");
//			out.append("</a>");
//			out.append("</button>");
//			
//			out.append("</div>");
//			
//			out.append(
//					"<div class=\"modal fade\" id=\"addProductGroup\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
//			out.append("<div class=\"modal-dialog modal-lg\">");
//
//			out.append("<form method=\"post\" action=\"/datn/productGroup/list\" class=\"needs-validation\" novalidate>");
//			out.append("<div class=\"modal-content\">");
//			out.append("<div class=\"modal-header\">");
//			out.append(
//					"<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\"><i class=\"bi bi-person-plus\"></i>Thêm mới nhóm sản phẩm</h1>");
//			out.append(
//					"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
//			out.append("</div>");
//			out.append("<div class=\"modal-body\">");
//
//			out.append("<div class=\"row mb-3\">");
//			out.append("<div class=\"col-lg-6\">");
//			out.append("<label for=\"productGroupName\" class=\"form-label\">Tên nhóm sản phẩm</label>");
//			out.append(
//					"<input type=\"text\" class=\"form-control\" id=\"productGroupName\" name=\"txtProductGroupName\" required >");
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
//					"<textarea row=\"8\" class=\"form-control\" id=\"productGroupNotes\" name=\"txtProductGroupNotes\" required ></textarea>");
//			out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của nhóm sản phẩm</div>");
//			out.append("</div>");
//			out.append("</div>");
//			
////			out.append("<div class=\"row mb-3\">"); // row mb-3
////			
////			out.append("<label for=\"\" class=\"col-md-3 col-lg-2 col-form-label\">Hiển thị ở trang chủ</label>");
////			out.append("<div class=\"col-md-3 col-lg-4 text-start\">");
////			out.append("<input class=\"\" type=\"radio\" name=\"isPs_enable\" value=\"no\" id=\"noPs_enable\">");
////			out.append("<label class=\"form-check-label\" for=\"noPs_enable\">Không</label>");
////			out.append("<input class=\"\" type=\"radio\" name=\"isPs_enable\" value=\"yes\" id=\"yesPs_enable\">");
////			out.append("<label class=\"form-check-label\" for=\"yesPs_enable\">Có</label>");
////			out.append("</div>");
////			
////			out.append("</div>"); // end row mb-3
//
//			out.append("</div>");
//			out.append("<div class=\"modal-footer\">");
//			out.append(
//					"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-person-plus\"></i>Thêm mới</button>");
//			out.append(
//					"<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\"><i class=\"bi bi-x-lg\"></i>Thoát</button>");
//
//			out.append("</div>");
//			out.append("</div>"); // modal-content
//			out.append("</form>");
//			out.append("</div>");
//			out.append("</div>");
//		}
//		out.append(viewList.get(0));
//
////		out.append("<p>This is an examle page with no contrnt. You can use it as a starter for your custom pages.</p>");
//
////		out.append("</div>"); // end card-body
////		out.append("</div>"); // end card
//
//		out.append("</div>");
//		out.append("</div>");
//
//		out.append("<div class=\"row\">");
//		out.append("<div class=\"col-lg-12\">");
//		// chart
//		out.append("</div>");
//		out.append("</div>");
//		out.append("</section>");
//
//		out.append("</main><!-- End #main -->");
//
//		// tham chiếu tìm footer
//		RequestDispatcher f = request.getRequestDispatcher("/footer");
//		if (f != null) {
//			f.include(request, response);
//		}
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		// tim thong tin dang nhap
//		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
//
//		// thiết lập tập ký tự cần lấy
//		request.setCharacterEncoding("utf-8");
//		
//		// lấy thông tin
//		String pg_name = request.getParameter("txtProductGroupName");
//		int manager = jsoft.library.Utilities.getIntParam(request, "slcManager");
//		String pg_note = request.getParameter("txtProductGroupNotes");
//		
//		// kiểm tra dữ liệu
//		if (pg_name != null && !pg_name.equalsIgnoreCase("") 
//				&& pg_note != null && !pg_note.equalsIgnoreCase("")
//				&& manager > 0) { 
//			
//			// khởi tạo đối tượng
//			ProductGroupObject npg = new ProductGroupObject();
//			
//			npg.setPg_name(jsoft.library.Utilities.encode(pg_name));
//			npg.setPg_manager_id(manager);
//			npg.setPg_notes(jsoft.library.Utilities.encode(pg_note));
//			npg.setPg_created_date(jsoft.library.Utilities_date.getDate());
////			npg.setPg_enable(("yes".equals(pg_enable)? true : false));
//			npg.setPg_created_author_id(user.getUser_id());
//			
//			// tim bo quan ly ket noi
//			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
//			ProductColorControl pgc = new ProductColorControl(cp);
//			
//			// thuc hien them moi
//			boolean result = pgc.addProductGroup(npg);
//			
//			// tra ve ket noi
//			pgc.releaseConnection();
//			
//			if(result) {
//				response.sendRedirect("/datn/productGroup/list");
//			} else {
//				response.sendRedirect("/datn/productGroup/list?err=add");
//			}
//			
//		} else {
//			response.sendRedirect("/datn/productGroup/list?err=valueadd");
//		}
//		
//	}
//
//}
