package jsoft.ads.color;

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
import jsoft.objects.ColorObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ColorList
 */
@WebServlet("/color/list")
public class ColorList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ColorList() {
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
		ColorControl pgc = new ColorControl(cp);

		ColorObject similar = new ColorObject();
		similar.setC_created_author_id(user.getUser_id());

		// tham so xac dinh loai danh sach
		String trash = request.getParameter("trash");
		String titleList, pos;
		if (trash == null) {
			similar.setC_delete(false);
			pos = "pglist";
			titleList = "Danh sách màu sắc sản phẩm";
		} else {
			similar.setC_delete(true);
			pos = "pgtrash";
			titleList = "Danh sách màu sắc sản phẩm bị xóa";
		}

		// lay cau truc
		Quartet<ColorObject, Short, Byte, UserObject> infos = new Quartet<>(similar, (short)1, (byte) 15, user);
		ArrayList<String> viewList = pgc.viewColor(infos, new Pair<>(COLOR_SOFT.ID, ORDER.DESC));

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
		out.append("<li class=\"breadcrumb-item\">Màu sắc sản phẩm</li>");
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
			
			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2\" data-bs-toggle=\"modal\" data-bs-target=\"#addColor\">");
			out.append("<i class=\"bi bi-plus-lg\"></i> Thêm mới");
			out.append("</button>");
			
			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2 ms-3\">");
			out.append("<a href=\"/datn/color/list?trash\" class=\"text-light\">");
			out.append("<i class=\"bi bi-trash3\"></i> <span>Thùng rác</span>");
			out.append("</a>");
			out.append("</button>");
			
			out.append("</div>");
			
			out.append("<div class=\"modal fade\" id=\"addColor\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
			out.append("<div class=\"modal-dialog modal-lg\">");

			out.append("<form method=\"post\" action=\"/datn/color/list\" class=\"needs-validation\" novalidate>");
			out.append("<div class=\"modal-content\">");
			out.append("<div class=\"modal-header\">");
			out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\"><i class=\"bi bi-plus-lg\"></i> Thêm mới màu sắc sản phẩm</h1>");
			out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
			out.append("</div>");
			out.append("<div class=\"modal-body\">");

			out.append("<div class=\"row mb-3\">");
			out.append("<div class=\"col-lg-6\">");
			out.append("<label for=\"productGroupName\" class=\"form-label\">Màu sắc sản phẩm</label>");
			out.append("<input type=\"text\" class=\"form-control\" id=\"productGroupName\" name=\"txtColorName\" required >");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào tên của màu sắc sản phẩm</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6\">");
			out.append("<label for=\"manager\" class=\"form-label\">Người quản lý màu sắc sản phẩm</label>");
			out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
			out.append(viewList.get(1));
			out.append("</select>");
			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
			out.append("</div>");
			
			out.append("</div>"); // end row mb-3

			out.append("<div class=\"row mb-3\">");
			out.append("<div class=\"col-lg-12\">");
			out.append("<label for=\"productGroupNotes\" class=\"form-label\">Chú thích màu sắc sản phẩm</label>");
			out.append("<textarea row=\"8\" class=\"form-control\" id=\"productGroupNotes\" name=\"txtColorNotes\" required ></textarea>");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của màu sắc sản phẩm</div>");
			out.append("</div>");
			out.append("</div>");

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
		String pg_name = request.getParameter("txtColorName");
		int manager = jsoft.library.Utilities.getIntParam(request, "slcManager");
		String pg_note = request.getParameter("txtColorNotes");
		
		// kiểm tra dữ liệu
		if (pg_name != null && !pg_name.equalsIgnoreCase("") 
				&& pg_note != null && !pg_note.equalsIgnoreCase("")
				&& manager > 0) { 
			
			// khởi tạo đối tượng
			ColorObject npg = new ColorObject();
			
			npg.setC_name(jsoft.library.Utilities.encode(pg_name));
			npg.setC_manager_id(manager);
			npg.setC_notes(jsoft.library.Utilities.encode(pg_note));
			npg.setC_created_date(jsoft.library.Utilities_date.getDate());
			npg.setC_created_author_id(user.getUser_id());
			
			// tim bo quan ly ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ColorControl pgc = new ColorControl(cp);
			
			// thuc hien them moi
			boolean result = pgc.addColor(npg);
			
			// tra ve ket noi
			pgc.releaseConnection();
			
			if(result) {
				response.sendRedirect("/datn/color/list");
			} else {
				response.sendRedirect("/datn/color/list?err=add");
			}
			
		} else {
			response.sendRedirect("/datn/color/list?err=valueadd");
		}
		
	}

}
