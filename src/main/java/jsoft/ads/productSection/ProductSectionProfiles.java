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
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupProfiles
 */
@WebServlet("/productSection/profiles")
public class ProductSectionProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// dinh nghia kieu noi dung xuat ve trinh khach
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductSectionProfiles() {
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
		ProductSectionObject e_pg = null;

		// tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		// tạo đối tượng thực thi chức năng
		ProductSectionControl psc = new ProductSectionControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}

		if (id > 0) {
			e_pg = psc.getProductSection(id);
		}

		// các thông tin sửa
		String ps_name = "", ps_notes = "", ps_modified_date = "";
		boolean ps_enable = false;
		int ps_manager_id = 0;
		boolean isEdit = false;

		if (e_pg != null) {
			ps_name = e_pg.getPs_name();
			ps_notes = e_pg.getPs_notes();
			ps_enable = e_pg.isPs_enable();
			ps_manager_id = e_pg.getPs_manager_id();

			isEdit = true;
		}

		// lay cau truc
		ProductSectionObject similar = new ProductSectionObject();
		similar.setPs_created_author_id(user.getUser_id());
		similar.setPs_manager_id(ps_manager_id);
		similar.setPs_pg_id(e_pg.getPs_pg_id());
		Quartet<ProductSectionObject, Short, Byte, UserObject> infos = new Quartet<>(similar, (short) 1, (byte) 15, user);
		ArrayList<String> viewList = psc.viewProductSection(infos, new Pair<>(PRODUCTSECTION_SOFT.ID, ORDER.DESC));
		// trả về kết nối
		psc.releaseConnection();

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
		out.append("<h1>Thông tin thành phần sản phẩm</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/datn/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Thành phần sản phẩm</li>");
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
		out.append("<button class=\"nav-link  active\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"bi bi-pencil-square\"></i> Chỉnh sửa</button>");
		out.append("</li>");

		out.append("</ul>"); // end nav nav-tabs nav-tabs-bordered

		out.append("<div class=\"tab-content pt-2\">");

		out.append("<div class=\"tab-pane fade profile-overview\" id=\"overview\">");
		out.append("<h5 class=\"card-title\">Tóm tắt</h5>");
		out.append("<p class=\"small fst-italic\"> Do " + user.getUser_name() + " tạo</p>");

		out.append("<h5 class=\"card-title\">Chi tiết</h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên nhóm sản phẩm</div>");
		out.append("<div class=\"col-lg-9 col-md-5\">" + ps_name + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Chú thích</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + ps_notes + "</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("<div class=\"tab-pane fade show active profile-edit pt-3\" id=\"edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"post\" action=\"/datn/productSection/profiles\">");
		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"productGroupChoose\" class=\"col-md-3 col-lg-2 col-form-label\">Nhóm sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<select class=\"form-select\" id=\"productGroupChoose\" name=\"slcProductGroup\" required>");			
		out.append(viewList.get(2));
		out.append("</select>");
		out.append("</div>");
		out.append("</div>"); // row mb-3 row align-items-center text-end

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_name\" class=\"col-md-3 col-lg-2 col-form-label\">Tên nhóm sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4 mb-3\">");
		out.append("<input name=\"txtProductGroupName\" type=\"text\" class=\"form-control\" id=\"ps_name\" value=\""+ ps_name + "\">");
		out.append("</div>");
		
		out.append("<label for=\"manager\" class=\"col-md-3 col-lg-2 col-form-label\">Người quản lý chuyên mục</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
		out.append(viewList.get(1));
		out.append("</select>");
		out.append("</div>");	
		out.append("</div>"); // end row mb-3 row align-items-center text-end;

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_notes\" class=\"col-md-3 col-lg-2 col-form-label\">Chú thích</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<textarea name=\"txtProductGroupNotes\" class=\"form-control\" id=\"ps_notes\" style=\"height: 100px\">"+ ps_notes + "</textarea>");
		out.append("</div>");
		out.append("</div>"); //row mb-3 row align-items-center text-end

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"ps_table\" class=\"col-md-3 col-lg-2 col-form-label\">ps_enable</label>");
		out.append("<div class=\"col-md-3 col-lg-4 text-start\">");
		if (ps_enable) {
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
		out.append("<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-save\"></i> Lưu thay đổi</button>");
		out.append("</div>");

		// Truyền id theo cơ thế biến form ẩn để thực hiện edit
		if (isEdit) {
			out.append("<input type=\"hidden\" name=\"idForPost\" value=\"" + id + "\" >");
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
			String ps_name = request.getParameter("txtProductGroupName");
			int ps_pg_id = jsoft.library.Utilities.getShortParam(request, "slcProductGroup");
			int manager = jsoft.library.Utilities.getIntParam(request, "slcManager");
			String ps_note = request.getParameter("txtProductGroupNotes");
			String ps_enable = request.getParameter("isPg_enable");
			
			// kiểm tra dữ liệu
			if (ps_name != null && !ps_name.equalsIgnoreCase("") 
					&& ps_note != null && !ps_note.equalsIgnoreCase("")
					&& ps_pg_id > 0
					&& manager > 0) { 
				
				// khởi tạo đối tượng
				ProductSectionObject eps = new ProductSectionObject();
				
				eps.setPs_id(id);
				eps.setPs_name(jsoft.library.Utilities.encode(ps_name));
				eps.setPs_pg_id(ps_pg_id);
				eps.setPs_manager_id(manager);
				eps.setPs_notes(jsoft.library.Utilities.encode(ps_note));
				eps.setPs_modified_date(jsoft.library.Utilities_date.getDate());
				eps.setPs_enable(("yes".equals(ps_enable)? true : false));
				
				// tim bo quan ly ket noi
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				
				ProductSectionControl psc = new ProductSectionControl(cp);
				if (cp == null) {
					getServletContext().setAttribute("CPool", psc.getCP());
				}
				
				// thuc hien cap nhat
				boolean result = psc.editProductSection(eps, PRODUCTSECTION_EDIT_TYPE.GENERAL);
				
				// tra ve ket noi
				psc.releaseConnection();
				
				if (result) {
					response.sendRedirect("/datn/productSection/list");
				} else {
					response.sendRedirect("/datn/productSection/list?err=edit");
				}
			}
		} else {
			// id ko ton tai
			response.sendRedirect("/datn/productSection/list?err=profiles");
		}
	}

}
