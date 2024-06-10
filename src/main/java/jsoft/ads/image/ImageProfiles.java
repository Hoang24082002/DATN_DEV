package jsoft.ads.image;

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
import jsoft.objects.ImageObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ImageProfiles
 */
@WebServlet("/image/profiles")
public class ImageProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// dinh nghia kieu noi dung xuat ve trinh khach
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageProfiles() {
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
			response.sendRedirect("/datn/user/login");
		}
	}

	public void view(HttpServletRequest request, HttpServletResponse response, UserObject user)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// xác định kiểu nội dung xuất về trình khách
		response.setContentType(CONTENT_TYPE);

		// Tìm id của người sử dụng để cập nhật
		Short id = jsoft.library.Utilities.getShortParam(request, "id");

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();
		ImageObject e_i = null;

		// tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		// tạo đối tượng thực thi chức năng
		ImageControl sc = new ImageControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}

		if (id > 0) {
			e_i = sc.getImage(id);
		}

		// các thông tin sửa
		String s_name = "", s_notes = "", s_modified_date = "";
		boolean s_enable = false;
		int s_manager_id = 0;
		boolean isEdit = false;

		if (e_i != null) {
			s_name = e_i.getI_name();
			s_notes = e_i.getI_notes();
			s_manager_id = e_i.getI_manager_id();

			isEdit = true;
		}

		// lay cau truc
		ImageObject similar = new ImageObject();
		similar.setI_created_author_id(user.getUser_id());
		similar.setI_manager_id(s_manager_id);
		Quartet<ImageObject, Short, Byte, UserObject> infos = new Quartet<>(similar, (short) 1, (byte) 15, user);
		ArrayList<String> viewList = sc.viewImage(infos, new Pair<>(IMAGE_SOFT.ID, ORDER.DESC));
		// trả về kết nối
		sc.releaseConnection();

		// tham so xac dinh loai danh sach
		String trash = request.getParameter("trash");

		// tham chiếu tìm header
		RequestDispatcher h = request.getRequestDispatcher("/header?pos=snlist");
		if (h != null) {
			h.include(request, response);
		}

		out.append("<main id=\"main\" class=\"main\">");

		RequestDispatcher error = request.getRequestDispatcher("/error");
		if (error != null) {
			error.include(request, response);
		}

		out.append("<div class=\"pagetitle d-flex\">");
		out.append("<h1>Thông tin nhóm sản phẩm</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/datn/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Hệ thống sản phẩm</li>");
		if (trash == null) {
			out.append("<li class=\"breadcrumb-item active\">Danh sách</li>");
		} else {
			out.append("<li class=\"breadcrumb-item active\">Danh sách xóa</li>");
		}
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section profile\">");
		out.append("<div class=\"row\">");

		out.append("<div class=\"col-xl-12\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body pt-3\">");
		out.append("<!-- Bordered Tabs -->");
		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered\">");// start nav nav-tabs nav-tabs-bordered

		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#overview\"><i class=\"bi bi-info-square\"></i> Tổng quát</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append(
				"<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"bi bi-pencil-square\"></i> Chỉnh sửa</button>");
		out.append("</li>");

		out.append("</ul>"); // end nav nav-tabs nav-tabs-bordered

		out.append("<div class=\"tab-content pt-2\">");

		out.append("<div class=\"tab-pane fade show active profile-overview\" id=\"overview\">");
		out.append("<h5 class=\"card-title\">Tóm tắt</h5>");
		out.append("<p class=\"small fst-italic\"> Do " + user.getUser_name() + " tạo</p>");

		out.append("<h5 class=\"card-title\">Chi tiết</h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Màu sắc sản phẩm</div>");
		out.append("<div class=\"col-lg-9 col-md-5\">" + s_name + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Chú thích</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + s_notes + "</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("<div class=\"tab-pane fade profile-edit pt-3\" id=\"edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"post\" action=\"/datn/image/profiles\">");

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_name\" class=\"col-md-3 col-lg-2 col-form-label\">Màu sắc sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4 mb-3\">");
		out.append("<input name=\"txtImageName\" type=\"text\" class=\"form-control\" id=\"ps_name\" value=\""
				+ s_name + "\">");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_notes\" class=\"col-md-3 col-lg-2 col-form-label\">Chú thích</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append(
				"<textarea name=\"txtImageNotes\" class=\"form-control\" id=\"ps_notes\" style=\"height: 100px\">"
						+ s_notes + "</textarea>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append(
				"<label for=\"manager\" class=\"col-md-3 col-lg-2 col-form-label\">Người quản lý màu sắc</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
		out.append(viewList.get(1));
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");


		out.append("<div class=\"text-center mt-3\">");
		out.append(
				"<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-save\"></i> Lưu thay đổi</button>");
		out.append("</div>");

		// Truyền id theo cơ thế biến form ẩn để thực hiện edit
		if (isEdit) {
			out.append("<input type=\"hidden\" name=\"idForPost\" value=\"" + id + "\" >");
//			out.append("<input type=\"hidden\" name=\"act\" value=\"edit\" >");
		}

		out.append("</form><!-- End Profile Edit Form -->");

		out.append("</div>");

		out.append("</div>");

		out.append("</div><!-- End Bordered Tabs -->");

		out.append("</div>");
		out.append("</div>");

		out.append("</div>");

//		out.append("</div>"); // end card-body
//		out.append("</div>"); // end card

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
		// TODO Auto-generated method stub
		// tim thong tin dang nhap
		UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

		// thiết lập tập ký tự cần lấy
		request.setCharacterEncoding("utf-8");

		// lấy id của chuyen muc để chỉnh sửa
		int id = jsoft.library.Utilities.getShortParam(request, "idForPost");

		if (id > 0) {
			// lấy thông tin
			// lấy thông tin
			String s_name = request.getParameter("txtImageName");
			int manager = jsoft.library.Utilities.getIntParam(request, "slcManager");
			String s_note = request.getParameter("txtImageNotes");
			
			// kiểm tra dữ liệu
			if (s_name != null && !s_name.equalsIgnoreCase("") 
					&& s_note != null && !s_note.equalsIgnoreCase("")
					&& manager > 0) { 
				
				// khởi tạo đối tượng
				ImageObject e_i = new ImageObject();
				
				e_i.setI_id(id);
				e_i.setI_name(jsoft.library.Utilities.encode(s_name));
				e_i.setI_manager_id(manager);
				e_i.setI_notes(jsoft.library.Utilities.encode(s_note));
				e_i.setI_modified_date(jsoft.library.Utilities_date.getDate());
				
				// tim bo quan ly ket noi
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				
				ImageControl sc = new ImageControl(cp);
				if (cp == null) {
					getServletContext().setAttribute("CPool", sc.getCP());
				}
				
				// thuc hien cap nhat
				boolean result = sc.editImage(e_i, IMAGE_EDIT_TYPE.GENERAL);
				
				// tra ve ket noi
				sc.releaseConnection();
				
				if (result) {
					response.sendRedirect("/datn/image/list");
				} else {
					response.sendRedirect("/datn/image/list?err=edit");
				}
			}
		} else {
			// id ko ton tai
			response.sendRedirect("/datn/image/list?err=profiles");
		}
	}

}
