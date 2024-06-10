package jsoft.ads.productSection;

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
import jsoft.library.ORDER;
import jsoft.library.Utilities;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupList
 */
@WebServlet("/productSection/list")
public class ProductSectionList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductSectionList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
		ProductSectionControl psc = new ProductSectionControl(cp);

		ProductSectionObject similar = new ProductSectionObject();
		similar.setPs_created_author_id(user.getUser_id());
		similar.setPs_name(saveKey);

		// tham so xac dinh loai danh sach
		String trash = request.getParameter("trash");
		String titleList, pos;
		if (trash == null) {
			similar.setPs_delete(false);
			pos = "pslist";
			titleList = "Danh sách thành phần sản phẩm";
		} else {
			similar.setPs_delete(true);
			pos = "pstrash";
			titleList = "Danh sách thành phần sản phẩm bị xóa";
		}
		
		int page = Utilities.getIntParam(request, "page");
		if(page<1) {
			page = 0;
		}

		// lay cau truc
		Quartet<ProductSectionObject, Short, Byte, UserObject> infos = new Quartet<>(similar, (short)page, (byte) 15, user);
		ArrayList<String> viewList = psc.viewProductSection(infos, new Pair<>(PRODUCTSECTION_SOFT.ID, ORDER.DESC));

		// tra ve ket not
		psc.releaseConnection();

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
		out.append("<li class=\"breadcrumb-item\">Thành phần sản phẩm</li>");
		if (trash == null) {
			out.append("<li class=\"breadcrumb-item active\">Danh sách</li>");			
		} else {
			out.append("<li class=\"breadcrumb-item active\">Danh sách xóa</li>");
		}
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section\">");
		out.append("<div class=\"row\">");

		out.append("<div class=\"col-lg-12\">");

//		out.append("<div class=\"card\">");
//		out.append("<div class=\"card-body\">");
		if (trash == null) {
			out.append("<div class=\"d-flex justify-content-start\">");
			
			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2\" data-bs-toggle=\"modal\" data-bs-target=\"#addProductGroup\">");
			out.append("<i class=\"bi bi-plus-lg\"></i> Thêm mới");
			out.append("</button>");
			
			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2 ms-3\">");
			out.append("<a href=\"/datn/productSection/list?trash\" class=\"text-light\">");
			out.append("<i class=\"bi bi-trash3\"></i> <span>Thùng rác</span>");
			out.append("</a>");
			out.append("</button>");
			
			out.append("</div>");
			
			out.append("<div class=\"modal fade\" id=\"addProductGroup\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
			out.append("<div class=\"modal-dialog modal-lg\">");

			out.append("<form method=\"post\" action=\"/datn/productSection/list\" class=\"needs-validation\" novalidate>");
			out.append("<div class=\"modal-content\">");
			out.append("<div class=\"modal-header\">");
			out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\"><i class=\"bi bi-plus-lg\"></i> Thêm mới thành phần sản phẩm</h1>");
			out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
			out.append("</div>");
			out.append("<div class=\"modal-body\">");

			out.append("<div class=\"row mb-3\">");
			out.append("<div class=\"col-lg-12\">");
			out.append("<label for=\"productGroupChoose\" class=\"form-label\">Nhóm sản phẩm</label>");
			out.append("<select class=\"form-select\" id=\"productGroupChoose\" name=\"slcProductGroup\" required>");			
			out.append(viewList.get(2));
			out.append("</select>");
			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6\">");
			out.append("<label for=\"productGroupName\" class=\"form-label\">Tên thành phần sản phẩm</label>");
			out.append("<input type=\"text\" class=\"form-control\" id=\"productGroupName\" name=\"txtProductGroupName\" required >");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào tên của thành phần sản phẩm</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6\">");
			out.append("<label for=\"manager\" class=\"form-label\">Người quản lý thành phần sản phẩm</label>");
			out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
			out.append(viewList.get(1));
			out.append("</select>");
			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
			out.append("</div>");			
			out.append("</div>"); // end row mb-3

			out.append("<div class=\"row mb-3\">");			
			out.append("<div class=\"col-lg-12\">");
			out.append("<label for=\"productGroupNotes\" class=\"form-label\">Chú thích thành phần sản phẩm</label>");
			out.append("<textarea row=\"8\" class=\"form-control\" id=\"productGroupNotes\" name=\"txtProductGroupNotes\" required ></textarea>");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của thành phần sản phẩm</div>");
			out.append("</div>"); // end col-lg-12			
			out.append("</div>");  // end row mb-3
			
//			out.append("<div class=\"row mb-3\">"); // row mb-3
//			
//			out.append("<label for=\"\" class=\"col-md-3 col-lg-2 col-form-label\">Hiển thị ở trang chủ</label>");
//			out.append("<div class=\"col-md-3 col-lg-4 text-start\">");
//			out.append("<input class=\"\" type=\"radio\" name=\"isPs_enable\" value=\"no\" id=\"noPs_enable\">");
//			out.append("<label class=\"form-check-label\" for=\"noPs_enable\">Không</label>");
//			out.append("<input class=\"\" type=\"radio\" name=\"isPs_enable\" value=\"yes\" id=\"yesPs_enable\">");
//			out.append("<label class=\"form-check-label\" for=\"yesPs_enable\">Có</label>");
//			out.append("</div>");
//			
//			out.append("</div>"); // end row mb-3

			out.append("</div>");
			out.append("<div class=\"modal-footer\">");
			out.append("<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-plus-lg\"></i> Thêm mới</button>");
			out.append("<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\"><i class=\"bi bi-x-lg\"></i> Thoát</button>");

			out.append("</div>");
			out.append("</div>"); // modal-content
			out.append("</form>");
			out.append("</div>");
			out.append("</div>");
		}
		out.append(viewList.get(0));

//		out.append("<p>This is an examle page with no contrnt. You can use it as a starter for your custom pages.</p>");

//		out.append("</div>"); // end card-body
//		out.append("</div>"); // end card

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// tim thong tin dang nhap
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		// thiết lập tập ký tự cần lấy
		request.setCharacterEncoding("utf-8");
		
		// lấy thông tin
		String pg_name = request.getParameter("txtProductGroupName");
		int ps_pg_id = jsoft.library.Utilities.getShortParam(request, "slcProductGroup");
		int ps_manager_id = jsoft.library.Utilities.getIntParam(request, "slcManager");
		String pg_note = request.getParameter("txtProductGroupNotes");
		// img
		
		
		// kiểm tra dữ liệu
		if (pg_name != null && !pg_name.equalsIgnoreCase("") 
				&& pg_note != null && !pg_note.equalsIgnoreCase("")
				&& ps_pg_id >0
				&& ps_manager_id > 0) { 
			
			// khởi tạo đối tượng
			ProductSectionObject nps = new ProductSectionObject();
			
			nps.setPs_name(jsoft.library.Utilities.encode(pg_name));
			nps.setPs_pg_id(ps_pg_id);
			nps.setPs_manager_id(ps_manager_id);
			nps.setPs_notes(jsoft.library.Utilities.encode(pg_note));
			nps.setPs_created_date(jsoft.library.Utilities_date.getDate());
			nps.setPs_created_author_id(user.getUser_id());
			
			// tim bo quan ly ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ProductSectionControl psc = new ProductSectionControl(cp);
			
			// thuc hien them moi
			boolean result = psc.addProductSection(nps);
			
			// tra ve ket noi
			psc.releaseConnection();
			
			if(result) {
				response.sendRedirect("/datn/productSection/list");
			} else {
				response.sendRedirect("/datn/productSection/list?err=add");
			}
			
		} else {
			String key = request.getParameter("keyword");
			response.sendRedirect("/datn/productSection/list?key=" + jsoft.library.Utilities.encode(key));
//			response.sendRedirect("/datn/productSection/list?key=" + key);
		}
		
	}

}
