package jsoft.ads.productGroup;

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
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupProfiles
 */
@WebServlet("/productGroup/profiles")
public class ProductGroupProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// dinh nghia kieu noi dung xuat ve trinh khach
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductGroupProfiles() {
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

	public void view(HttpServletRequest request, HttpServletResponse response, UserObject user)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// xác định kiểu nội dung xuất về trình khách
		response.setContentType(CONTENT_TYPE);

		// Tìm id của người sử dụng để cập nhật
		Short id = jsoft.library.Utilities.getShortParam(request, "id");

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();
		ProductGroupObject e_pg = null;

		// tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		// tạo đối tượng thực thi chức năng
		ProductGroupControl pgc = new ProductGroupControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}

		if (id > 0) {
			e_pg = pgc.getProductGroup(id);
		}

		// các thông tin sửa
		String pg_name = "", pg_notes = "", pg_modified_date = "";
		boolean pg_enable = false;
		int pg_manager_id = 0;
		boolean isEdit = false;

		if (e_pg != null) {
			pg_name = e_pg.getPg_name();
			pg_notes = e_pg.getPg_notes();
			pg_enable = e_pg.isPg_enable();
			pg_manager_id = e_pg.getPg_manager_id();

			isEdit = true;
		}

		// lay cau truc
		ProductGroupObject similar = new ProductGroupObject();
		similar.setPg_created_author_id(user.getUser_id());
		similar.setPg_manager_id(pg_manager_id);
		Quartet<ProductGroupObject, Short, Byte, UserObject> infos = new Quartet<>(similar, (short) 1, (byte) 15, user);
		ArrayList<String> viewList = pgc.viewProductGroup(infos, new Pair<>(PRODUCTGROUP_SOFT.ID, ORDER.DESC));
		// trả về kết nối
		pgc.releaseConnection();

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
		out.append("<li class=\"breadcrumb-item\">Nhóm sản phẩm</li>");
		out.append("<li class=\"breadcrumb-item active\">Chỉnh sửa</li>");
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
		out.append("<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#overview\"><i class=\"bi bi-info-square\"></i> Tổng quát</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"bi bi-pencil-square\"></i> Chỉnh sửa</button>");
		out.append("</li>");

		out.append("</ul>"); // end nav nav-tabs nav-tabs-bordered

		out.append("<div class=\"tab-content pt-2\">");

		out.append("<div class=\"tab-pane fade profile-overview\" id=\"overview\">");
		out.append("<h5 class=\"card-title\">Tóm tắt</h5>");
		out.append("<p class=\"small fst-italic\"> Do " + user.getUser_name() + " tạo</p>");

		out.append("<h5 class=\"card-title\">Chi tiết</h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên nhóm sản phẩm</div>");
		out.append("<div class=\"col-lg-9 col-md-5\">" + pg_name + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Chú thích</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + pg_notes + "</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("<div class=\"tab-pane fade show active profile-edit pt-3\" id=\"edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"post\" action=\"/datn/productGroup/profiles\">");

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_name\" class=\"col-md-3 col-lg-2 col-form-label\">Tên nhóm sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4 mb-3\">");
		out.append("<input name=\"txtProductGroupName\" type=\"text\" class=\"form-control\" id=\"ps_name\" value=\""
				+ pg_name + "\">");
		out.append("</div>");
		out.append("</div>");

//		out.append("<div class=\"row mb-3 row align-items-center text-end\">"); 
//		out.append("<label for=\"ps_name_en\" class=\"col-md-3 col-lg-2 col-form-label\">Tên nhóm sản phẩm(En)</label>");
//		out.append("<div class=\"col-md-3 col-lg-4\">");
//		out.append("<input name=\"txtProductGroupTable\" type=\"text\" class=\"form-control\" id=\"ps_name_en\" value=\""+pg_name_en+"\">");
//		out.append("</div>");
//		out.append("</div>");

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_notes\" class=\"col-md-3 col-lg-2 col-form-label\">Chú thích</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append(
				"<textarea name=\"txtProductGroupNotes\" class=\"form-control\" id=\"ps_notes\" style=\"height: 100px\">"
						+ pg_notes + "</textarea>");
		out.append("</div>");
		out.append("</div>");

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append(
				"<label for=\"manager\" class=\"col-md-3 col-lg-2 col-form-label\">Người quản lý chuyên mục</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
		out.append(viewList.get(1));
		out.append("</select>");
		out.append("</div>");
		out.append("</div>");

//		out.append("<div class=\"row mb-3 row align-items-center text-end\">"); 
//		out.append("<label for=\"ps_table\" class=\"col-md-3 col-lg-2 col-form-label\">ps_table</label>");
//		out.append("<div class=\"col-md-3 col-lg-4\">");
//		out.append("<input name=\"txtProductGroupTable\" type=\"text\" class=\"form-control\" id=\"ps_table\" value=\""+ps_table+"\">");
//		out.append("</div>");
//		out.append("</div>");

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_table\" class=\"col-md-3 col-lg-2 col-form-label\">ps_enable</label>");
		out.append("<div class=\"col-md-3 col-lg-4 text-start\">");
		if (pg_enable) {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isPg_enable\" value=\"no\" id=\"noPg_enable\">");
			out.append("<label class=\"form-check-label\" for=\"noPg_enable\">Không</label>");
			out.append(
					"<input class=\"ms-4\" type=\"radio\" name=\"isPg_enable\" value=\"yes\" id=\"yesPg_enable\" checked>");
			out.append("<label class=\"form-check-label\" for=\"noPg_enable\">Có</label>");
		} else {
			out.append(
					"<input class=\"me-1\" type=\"radio\" name=\"isPg_enable\" value=\"no\" id=\"noPg_enable\" checked>");
			out.append("<label class=\"form-check-label\" for=\"noPg_enable\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isPg_enable\" value=\"yes\" id=\"yesPg_enable\">");
			out.append("<label class=\"form-check-label\" for=\"yesPg_enable\">Có</label>");
		}
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
			String pg_name = request.getParameter("txtProductGroupName");
			int manager = jsoft.library.Utilities.getIntParam(request, "slcManager");
			String pg_note = request.getParameter("txtProductGroupNotes");
			String pg_enable = request.getParameter("isPg_enable");
			
			// kiểm tra dữ liệu
			if (pg_name != null && !pg_name.equalsIgnoreCase("") 
					&& pg_note != null && !pg_note.equalsIgnoreCase("")
					&& manager > 0) { 
				
				// khởi tạo đối tượng
				ProductGroupObject epg = new ProductGroupObject();
				
				epg.setPg_id(id);
				epg.setPg_name(jsoft.library.Utilities.encode(pg_name));
				epg.setPg_manager_id(manager);
				epg.setPg_notes(jsoft.library.Utilities.encode(pg_note));
				epg.setPg_modified_date(jsoft.library.Utilities_date.getDate());
				epg.setPg_enable(("yes".equals(pg_enable)? true : false));
				
				// tim bo quan ly ket noi
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				
				ProductGroupControl pgc = new ProductGroupControl(cp);
				if (cp == null) {
					getServletContext().setAttribute("CPool", pgc.getCP());
				}
				
				// thuc hien cap nhat
				boolean result = pgc.editProductGroup(epg, PRODUCTGROUP_EDIT_TYPE.GENERAL);
				
				// tra ve ket noi
				pgc.releaseConnection();
				
				if (result) {
					response.sendRedirect("/datn/productGroup/list");
				} else {
					response.sendRedirect("/datn/productGroup/list?err=edit");
				}
			}
		} else {
			// id ko ton tai
			response.sendRedirect("/datn/productGroup/list?err=profiles");
		}
	}

}
