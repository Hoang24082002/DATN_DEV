package jsoft.ads.productCategory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.library.Utilities;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupProfiles
 */
@WebServlet("/productCategory/profiles")
public class ProductCategoryProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// dinh nghia kieu noi dung xuat ve trinh khach
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductCategoryProfiles() {
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

		// Tìm id của ProductCategory để cập nhật
		int id = jsoft.library.Utilities.getIntParam(request, "id");
//		System.out.println("id = " + id);

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();
		ProductCategoryObject e_pc = null;

		// tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		// tạo đối tượng thực thi chức năng
		ProductCategoryControl psc = new ProductCategoryControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}

		if (id > 0) {
			e_pc = psc.getProductCategory(id);
		}

		// các thông tin sửa
		String pc_name = "", pc_notes = "", pc_image = "";
		boolean pc_enable = false;
		int pc_manager_id = 0, pc_pg_id = 0, pc_ps_id = 0;
		boolean isEdit = false;

		if (e_pc != null) {
			pc_name = e_pc.getPc_name();
			pc_pg_id = e_pc.getPc_pg_id();
			pc_ps_id = e_pc.getPc_ps_id();
			pc_manager_id = e_pc.getPc_manager_id();
			pc_notes = e_pc.getPc_notes();
			pc_image = e_pc.getPc_image();
			pc_enable = e_pc.isPc_enable();

			isEdit = true;
		}

		// lay cau truc
		ProductCategoryObject similar = new ProductCategoryObject();
		similar.setPc_id(e_pc.getPc_id());
		similar.setPc_created_author_id(user.getUser_id());
		similar.setPc_manager_id(pc_manager_id);
		similar.setPc_ps_id(e_pc.getPc_ps_id());
		
		
		// Tìm id của ProductGroup để show option ProductSection
		int idProductGroupSelected = Utilities.getIntParam(request, "pgid");

		ProductGroupObject selectProductGroup = new ProductGroupObject();
		if(idProductGroupSelected > 0) {
			selectProductGroup.setPg_id(idProductGroupSelected);
			similar.setPc_pg_id(idProductGroupSelected);
		} else {
			similar.setPc_pg_id(e_pc.getPc_pg_id());
		}
		
		Quintet<ProductCategoryObject, Short, Byte, UserObject, ProductGroupObject> infos = new Quintet<>(similar, (short)1, (byte) 15, user, selectProductGroup);
		ArrayList<String> viewList = psc.viewProductCategory(infos, new Triplet<>(PRODUCTCATEGORY_SOFT.ID, ORDER.DESC, ADD_END_UPDATE.UPDATE));
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
		out.append("<h1>Thông tin danh mục sản phẩm</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/datn/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Danh mục sản phẩm</li>");
		out.append("<li class=\"breadcrumb-item active\">Chỉnh chửa</li>");
		out.append("</ol>");
		out.append("</nav>");
		out.append("</div><!-- End Page Title -->");

		out.append("<section class=\"section profile\">");
		out.append("<div class=\"row\">");

		out.append("<div class=\"col-xl-12\">");

		out.append("<div class=\"card\">");
		out.append("<div class=\"card-body pt-3\">");
		out.append("<!-- Bordered Tabs -->");
		out.append("<ul class=\"nav nav-tabs nav-tabs-bordered\">");

		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#overview\"><i class=\"bi bi-info-square\"></i> Tổng quát</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"bi bi-pencil-square\"></i> Chỉnh sửa</button>");
		out.append("</li>");

		out.append("</ul> ");

		out.append("<div class=\"tab-content pt-2\">");

		out.append("<div class=\"tab-pane fade profile-overview\" id=\"overview\">");
		out.append("<h5 class=\"card-title\">Tóm tắt</h5>");
		out.append("<p class=\"small fst-italic\"> Do " + user.getUser_name() + " tạo</p>");

		out.append("<h5 class=\"card-title\">Chi tiết</h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên Danh mục sản phẩm</div>");
		out.append("<div class=\"col-lg-9 col-md-5\">" + pc_name + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Chú thích</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + pc_notes + "</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("<div class=\"tab-pane fade show active profile-edit pt-3\" id=\"edit\">");

		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"post\" action=\"/datn/productCategory/profiles\">");
		out.append("<div>");
		
		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"slcProductGroup\" class=\"col-md-3 col-lg-2 form-label\">Nhóm sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4 mb-3 text-start\">");
		out.append(viewList.get(2));
		out.append("</div>");

		out.append("<label for=\"productSectionChoose\" class=\"col-md-3 col-lg-2 form-label\">Thành phần sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4 mb-3 text-start\">");
		out.append("<select class=\"form-select\" id=\"productSectionChoose\" name=\"slcProductSectionId\" required>");
		out.append(viewList.get(3));
		out.append("</select>");
		out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
		out.append("</div>");
		out.append("</div>"); // end row mb-3 row align-items-center text-end

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"pc_name\" class=\"col-md-3 col-lg-2 col-form-label\">Tên Danh mục sản phẩm</label>");
		out.append("<div class=\"col-md-3 col-lg-4 mb-3\">");
		out.append("<input name=\"txtProductGroupName\" type=\"text\" class=\"form-control\" id=\"pc_name\" value=\""+pc_name+"\">");
		out.append("</div>");

		out.append("<label for=\"manager\" class=\"col-md-3 col-lg-2 col-form-label\">Người quản lý chuyên mục</label>");
		out.append("<div class=\"col-md-3 col-lg-4\">");
		out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
		out.append(viewList.get(1));
		out.append("</select>");
		out.append("</div>");
		out.append("</div>"); // end row mb-3 row align-items-center text-end

		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"pc_notes\" class=\"col-md-3 col-lg-2 col-form-label\">Chú thích</label>");
		out.append("<div class=\"col-md-9 col-lg-10\">");
		out.append("<textarea name=\"txtProductGroupNotes\" class=\"form-control\" id=\"pc_notes\" style=\"height: 100px\">"+ pc_notes + "</textarea>");
		out.append("</div>");
		out.append("</div>"); // end row mb-3 row align-items-center text-end
		
		out.append("<div class=\"row mb-3 row align-items-center text-end\">");
		out.append("<label for=\"pc_table\" class=\"col-md-3 col-lg-2 col-form-label\">pc_enable</label>");
		out.append("<div class=\"col-md-3 col-lg-4 text-start\">");
		
		if (pc_enable) {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isPg_enable\" value=\"no\" id=\"noPg_enable\">");
			out.append("<label class=\"form-check-label\" for=\"noPg_enable\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isPg_enable\" value=\"yes\" id=\"yesPg_enable\" checked>");
			out.append("<label class=\"form-check-label\" for=\"noPg_enable\">Có</label>");
		} else {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isPg_enable\" value=\"no\" id=\"noPg_enable\" checked>");
			out.append("<label class=\"form-check-label\" for=\"noPg_enable\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isPg_enable\" value=\"yes\" id=\"yesPg_enable\">");
			out.append("<label class=\"form-check-label\" for=\"yesPg_enable\">Có</label>");
		}
		
		out.append("</div>");
		out.append("</div>");
		out.append("<div class=\"text-center mt-3\">");
		out.append("<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-save\"></i> Lưu thay đổi</button>");
		out.append("</div>");
		if (isEdit) {
			out.append("<input type=\"hidden\" name=\"idPgEdit\" value=\""+id+"\">");
		}
		out.append("</div>");
		out.append("</form><!-- End Profile Edit Form -->");

		out.append("</div>");

		out.append("</div>");

		out.append("</div><!-- End Bordered Tabs -->");

		out.append("</div>");
		out.append("</div>");

		out.append("</div>");

		out.append("</div> ");
		out.append("</div> ");

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

		
		// lay id ProductGroup
		int idProductGroupSelect = Utilities.getIntParam(request, "pgid");

		if(idProductGroupSelect > 0) {
			doGet(request, response);			
		} else {
			// lấy id của chuyen muc để chỉnh sửa
			int id = jsoft.library.Utilities.getIntParam(request, "idPgEdit");
			if (id > 0) {
				// lấy thông tin
				String pc_name = request.getParameter("txtProductGroupName");
				int pc_pg_id = jsoft.library.Utilities.getShortParam(request, "slcProductGroupId");
				int pc_ps_id = jsoft.library.Utilities.getShortParam(request, "slcProductSectionId");
				int manager = jsoft.library.Utilities.getIntParam(request, "slcManager");
				String pc_note = request.getParameter("txtProductGroupNotes");
				String pc_enable = request.getParameter("isPg_enable");
				
				System.out.println(pc_name+ " - " + pc_pg_id + " - " + manager +" - " + pc_note + pc_enable);
				
				// kiểm tra dữ liệu
				if (pc_name != null && !pc_name.equalsIgnoreCase("") 
						&& pc_note != null && !pc_note.equalsIgnoreCase("")
						&& pc_pg_id > 0
						&& pc_ps_id > 0
						&& manager > 0) {
					
					// khởi tạo đối tượng
					ProductCategoryObject epc = new ProductCategoryObject();
					
					epc.setPc_id(id);
					epc.setPc_name(jsoft.library.Utilities.encode(pc_name));
					epc.setPc_pg_id(pc_pg_id);
					epc.setPc_ps_id(pc_ps_id);
					epc.setPc_manager_id(manager);
					epc.setPc_notes(jsoft.library.Utilities.encode(pc_note));
					epc.setPc_modified_date(jsoft.library.Utilities_date.getDate());
					epc.setPc_enable(("yes".equals(pc_enable)? true : false));
					
					// tim bo quan ly ket noi
					ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
					
					ProductCategoryControl psc = new ProductCategoryControl(cp);
					if (cp == null) {
						getServletContext().setAttribute("CPool", psc.getCP());
					}
					
					// thuc hien cap nhat
					boolean result = psc.editProductCategory(epc, PRODUCTCATEGORY_EDIT_TYPE.GENERAL);
					
					// tra ve ket noi
					psc.releaseConnection();
					
					if (result) {
						response.sendRedirect("/datn/productCategory/list");
					} else {
						response.sendRedirect("/datn/productCategory/list?err=edit");
					}
				}
			} else {
				// id ko ton tai
				response.sendRedirect("/datn/productCategory/list?err=profiles");
			}
		}
	}

}
