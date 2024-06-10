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
 * Servlet implementation class ProductCategotyList
 */
@WebServlet("/productCategory/list")
public class ProductCategoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductCategoryList() {
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
		ProductCategoryControl pcc = new ProductCategoryControl(cp);

		ProductCategoryObject similar = new ProductCategoryObject();
		similar.setPc_created_author_id(user.getUser_id());
		similar.setPc_name(saveKey);
		
		// lay id productGroup
		int idProductGroupSelected = Utilities.getIntParam(request, "pgid");

		ProductGroupObject selectProductCategoty = new ProductGroupObject();
		if(idProductGroupSelected > 0) {
			selectProductCategoty.setPg_id(idProductGroupSelected);
			similar.setPc_pg_id(idProductGroupSelected);
		}

		// tham so xac dinh loai danh sach
		String trash = request.getParameter("trash");
		String titleList, pos;
		if (trash == null) {
			similar.setPc_delete(false);
			pos = "pclist";
			titleList = "Danh sách danh mục sản phẩm";
		} else {
			similar.setPc_delete(true);
			pos = "pctrash";
			titleList = "Danh sách danh mục sản phẩm bị xóa";
		}
		
		int page = Utilities.getIntParam(request, "page");
		if(page<1) {
			page = 0;
		}

		// lay cau truc
		Quintet<ProductCategoryObject, Short, Byte, UserObject, ProductGroupObject> infos = new Quintet<>(similar, (short)page, (byte) 15, user, selectProductCategoty);
		ArrayList<String> viewList = pcc.viewProductCategory(infos, new Triplet<>(PRODUCTCATEGORY_SOFT.ID, ORDER.DESC, ADD_END_UPDATE.ADD));

		// tra ve ket not
		pcc.releaseConnection();

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
		out.append("<li class=\"breadcrumb-item\">Danh mục sản phẩm</li>");
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
			
			out.append("<button type=\"button\" id=\"btnModalAddProductCategoty\" class=\"btn btn-primary btn-sm my-2\" data-bs-toggle=\"modal\" data-bs-target=\"#addProductCategoty\">");
			out.append("<i class=\"bi bi-plus-lg\"></i> Thêm mới");
			out.append("</button>");
			
			if(idProductGroupSelected > 0) {
				out.append("<script language=\"javascript\">");
				out.append("document.addEventListener('DOMContentLoaded', function() {");
				out.append("var buttonElement = document.getElementById('btnModalAddProductCategoty');");
				out.append("buttonElement.click();");
				out.append("});");
				out.append("</script>");
			}
			
			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2 ms-3\">");
			out.append("<a href=\"/datn/productCategory/list?trash\" class=\"text-light\">");
			out.append("<i class=\"bi bi-trash3\"></i> <span>Thùng rác</span>");
			out.append("</a>");
			out.append("</button>");
			
			out.append("</div>");
			
			out.append("<div class=\"modal fade\" id=\"addProductCategoty\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
			out.append("<div class=\"modal-dialog modal-lg\">");

			out.append("<form method=\"post\" action=\"/datn/productCategory/list\" class=\"needs-validation\" novalidate>");
			out.append("<div class=\"modal-content\">");
			out.append("<div class=\"modal-header\">");
			out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\"><i class=\"bi bi-plus-lg\"></i> Thêm mới danh mục sản phẩm</h1>");
			out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
			out.append("</div>");
			out.append("<div class=\"modal-body\">");

			out.append("<div class=\"row mb-3\">");
			out.append("<div class=\"col-lg-6\">");	
			out.append("<label for=\"slcProductGroup\" class=\"form-label\">Nhóm sản phẩm</label>");
			out.append(viewList.get(2)); // PRODUCT GROUP OPTION
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6\">");
			out.append("<label for=\"productSectionChoose\" class=\"form-label\">Thành phần sản phẩm</label>");
			out.append("<select class=\"form-select\" id=\"productSectionChoose\" name=\"slcProductSectionId\" required>");			
			out.append(viewList.get(3));
			out.append("</select>");
			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6\">");
			out.append("<label for=\"productGroupName\" class=\"form-label\">Tên danh mục sản phẩm</label>");
			out.append("<input type=\"text\" class=\"form-control\" id=\"productGroupName\" name=\"txtProductCategotyName\" required >");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào tên của danh mục sản phẩm</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6\">");
			out.append("<label for=\"manager\" class=\"form-label\">Người quản lý danh mục sản phẩm</label>");
			out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
			out.append(viewList.get(1));
			out.append("</select>");
			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
			out.append("</div>");
			
			out.append("</div>"); // end row mb-3

			out.append("<div class=\"row mb-3\">");			
			out.append("<div class=\"col-lg-12\">");
			out.append("<label for=\"productGroupNotes\" class=\"form-label\">Chú thích danh mục sản phẩm</label>");
			out.append("<textarea row=\"8\" class=\"form-control\" id=\"productGroupNotes\" name=\"txtProductCategotyNotes\" required ></textarea>");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của danh mục sản phẩm</div>");
			out.append("</div>"); // end col-lg-12			
			out.append("</div>");  // end row mb-3

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
		
		// lay id ProductGroup
		int idProductGroupSelect = Utilities.getIntParam(request, "pgid");
		if(idProductGroupSelect > 0 ) {
			doGet(request, response);
		} else {
			// lấy thông tin
			String pc_name = request.getParameter("txtProductCategotyName");
			int pc_pg_id = jsoft.library.Utilities.getShortParam(request, "slcProductGroupId");
			int pc_ps_id = jsoft.library.Utilities.getShortParam(request, "slcProductSectionId");
			int ps_manager_id = jsoft.library.Utilities.getIntParam(request, "slcManager");
			String pc_note = request.getParameter("txtProductCategotyNotes");
			// img
			
			// kiểm tra dữ liệu
			if (pc_name != null && !pc_name.equalsIgnoreCase("") 
					&& pc_note != null && !pc_note.equalsIgnoreCase("")
					&& pc_pg_id > 0
					&& pc_ps_id > 0
					&& ps_manager_id > 0) { 
				
				// khởi tạo đối tượng
				ProductCategoryObject npc = new ProductCategoryObject();
				
				npc.setPc_name(jsoft.library.Utilities.encode(pc_name));
				npc.setPc_pg_id(pc_pg_id);
				npc.setPc_ps_id(pc_ps_id);
				npc.setPc_manager_id(ps_manager_id);
				npc.setPc_notes(jsoft.library.Utilities.encode(pc_note));
				npc.setPc_created_date(jsoft.library.Utilities_date.getDate());
				npc.setPc_created_author_id(user.getUser_id());
				
				// tim bo quan ly ket noi
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				ProductCategoryControl pcc = new ProductCategoryControl(cp);
				
				// thuc hien them moi
				boolean result = pcc.addProductCategory(npc);
				
				// tra ve ket noi
				pcc.releaseConnection();
				
				if(result) {
					response.sendRedirect("/datn/productCategory/list");
				} else {
					response.sendRedirect("/datn/productCategory/list?err=add");
				}
				
			} else {
				String key = request.getParameter("keyword");
				response.sendRedirect("/datn/productCategory/list?key=" + jsoft.library.Utilities.encode(key));
			}
			
		}
		
		
	}

}
