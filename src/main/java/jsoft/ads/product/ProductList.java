package jsoft.ads.product;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

import jsoft.ConnectionPool;
import jsoft.library.ORDER;
import jsoft.library.Utilities;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductGroupList
 */
@WebServlet("/product/list")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	    maxFileSize = 1024 * 1024 * 10,      // 10MB
	    maxRequestSize = 1024 * 1024 * 50    // 50MB
	)
public class ProductList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// định nghĩa kiểu nội dung xuất về trình khách
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductList() {
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
		ProductControl psc = new ProductControl(cp);

		ProductObject similar = new ProductObject();
		similar.setProduct_created_author_id(user.getUser_id());
		similar.setProduct_name(saveKey);
		
		// lay id productGroup
		int idProductGroupSelected = Utilities.getIntParam(request, "pgid");
		ProductGroupObject selectProductCategoty = new ProductGroupObject();
		if(idProductGroupSelected > 0) {
			selectProductCategoty.setPg_id(idProductGroupSelected);
			similar.setProduct_pg_id(idProductGroupSelected);
		}
		
		// lay id productSection
		int idProductSectionSelected = Utilities.getIntParam(request, "psid");
		ProductSectionObject selectProductSection = new ProductSectionObject();
		if(idProductSectionSelected > 0) {
			selectProductSection.setPs_id(idProductSectionSelected);
			similar.setProduct_ps_id(idProductSectionSelected);
		}

		// tham so xac dinh loai danh sach
		String trash = request.getParameter("trash");
		String titleList, pos;
		if (trash == null) {
			similar.setProduct_delete(false);
			pos = "plist";
			titleList = "Danh sách sản phẩm";
		} else {
			similar.setProduct_delete(true);
			pos = "ptrash";
			titleList = "Danh sách sản phẩm bị xóa";
		}
		
		int page = Utilities.getIntParam(request, "page");
		if(page<1) {
			page = 0;
		}

		// lay cau truc
		Sextet<ProductObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject> infos = new Sextet<>(similar, (short)page, (byte) 15, user, selectProductCategoty, selectProductSection);
		ArrayList<String> viewList = psc.viewProduct(infos, new Triplet<>(PRODUCT_SOFT.ID, ORDER.DESC, ADD_END_UPDATE.ADD));

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
		out.append("<li class=\"breadcrumb-item\">Sản phẩm</li>");
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
			
			out.append("<button type=\"button\" id=\"btnModalAddProduct\" class=\"btn btn-primary btn-sm my-2\" data-bs-toggle=\"modal\" data-bs-target=\"#addProduct\">");
			out.append("<i class=\"bi bi-plus-lg\"></i> Thêm mới");
			out.append("</button>");
			
			if(idProductGroupSelected > 0 || idProductSectionSelected > 0) {
				out.append("<script language=\"javascript\">");
				out.append("document.addEventListener('DOMContentLoaded', function() {");
				out.append("var buttonElement = document.getElementById('btnModalAddProduct');");
				out.append("buttonElement.click();");
				out.append("});");
				out.append("</script>");
			}
			
			out.append("<button type=\"button\" class=\"btn btn-primary btn-sm my-2 ms-3\">");
			out.append("<a href=\"/datn/product/list?trash\" class=\"text-light\">");
			out.append("<i class=\"bi bi-trash3\"></i><span>Thùng rác</span>");
			out.append("</a>");
			out.append("</button>");
			
			out.append("</div>");
			
			out.append("<div class=\"modal fade\" id=\"addProduct\" data-bs-backdrop=\"static\" data-bs-keyboard=\"false\" tabindex=\"-1\" aria-labelledby=\"staticBackdropLabel\" aria-hidden=\"true\">");
			out.append("<div class=\"modal-dialog modal-lg\">");

			out.append("<form method=\"post\" action=\"/datn/product/list\" class=\"needs-validation\" enctype=\"multipart/form-data\" novalidate>");
			out.append("<div class=\"modal-content\">");
			out.append("<div class=\"modal-header\">");
			out.append("<h1 class=\"modal-title fs-5\" id=\"staticBackdropLabel\"><i class=\"bi bi-plus-lg\"></i> Thêm mới sản phẩm</h1>");
			out.append("<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
			out.append("</div>");
			out.append("<div class=\"modal-body\">");

			out.append("<div class=\"row mb-3\">");			
			out.append("<div class=\"col-lg-12\">");	
			out.append(viewList.get(2)); // PRODUCT GROUP OPTION
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6 mb-3\">");
			out.append("<label for=\"productCategoryChoose\" class=\"form-label\">Danh mục sản phẩm</label>");
			out.append("<select class=\"form-select\" id=\"productCategoryChoose\" name=\"slcProductCategoryId\" required>");			
			out.append(viewList.get(3));
			out.append("</select>");
			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6 mb-3\">");
			out.append("<label for=\"manager\" class=\"form-label\">Người quản lý sản phẩm</label>");
			out.append("<select class=\"form-select\" id=\"manager\" name=\"slcManager\" required>");
			out.append(viewList.get(1));
			out.append("</select>");
			out.append("<div class=\"invalid-feedback\">Nhập hộp thư cho tài khoản</div>");
			out.append("</div>");
			out.append("</div>");  // end row mb-3
			
			out.append("<div class=\"row mb-3\">"); 
			out.append("<div class=\"col-lg-6 mb-3\">");
			out.append("<label for=\"productName\" class=\"form-label\">Tên sản phẩm</label>");
			out.append("<input type=\"text\" class=\"form-control\" id=\"productName\" name=\"txtProductName\" required >");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào tên của sản phẩm</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-6 mb-3\">");
			out.append("<label for=\"productImage\" class=\"form-label\">Ảnh sản phẩm</label>");
			out.append("<input type=\"file\" class=\"form-control\" id=\"productImage\" name=\"productImage\" required >");
			out.append("</div>"); 		
			out.append("</div>"); // end row mb-3
			
			out.append("<div class=\"row mb-3 mb-3\">"); // start row mb-3			
			out.append("<div class=\"col-lg-4 mb-3\">");
			out.append("<label for=\"productPrice\" class=\"form-label\">Giá sản phẩm</label>");
			out.append("<input type=\"number\" class=\"form-control\" id=\"productPrice\" name=\"txtProductPrice\" required >");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-4 mb-3\">");
			out.append("<label for=\"productDiscountPrice\" class=\"form-label\">Giá chiếu khấu sản phẩm</label>");
			out.append("<input type=\"number\" class=\"form-control\" id=\"productDiscountPrice\" name=\"txtProductDiscountPrice\" required >");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-4 mb-3\">");
			out.append("<label for=\"productPromotionPrice\" class=\"form-label\">Giá khuyến mãi sản phẩm</label>");
			out.append("<input type=\"number\" class=\"form-control\" id=\"productPromotionPrice\" name=\"txtProductPromotionPrice\" required >");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-4 mb-3\">");
			out.append("<label for=\"productTotal\" class=\"form-label\">Số lượng sản phẩm</label>");
			out.append("<input type=\"number\" class=\"form-control\" id=\"productTotal\" name=\"txtProductTotal\" required >");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-4 mb-3\">");
			out.append("<label for=\"productStyle\" class=\"form-label\">Kiểu sản phẩm</label>");
			out.append("<input type=\"text\" class=\"form-control\" id=\"productStyle\" name=\"txtProductStyle\" required >");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-4 mb-3\">");
			out.append("<label for=\"productBestSeller\" class=\"col-form-label\">Siêu giảm giá</label>");
			out.append("<div class=\"text-start\">");
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isProduct_best_seller\" value=\"no\" id=\"noProduct_best_seller\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_best_seller\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isProduct_best_seller\" value=\"yes\" id=\"yesProduct_best_seller\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_best_seller\">Có</label>");			
			out.append("</div>");
			out.append("</div>");
			
			out.append("<div class=\"col-lg-12 mb-3\">");
			out.append("<label for=\"productNotes\" class=\"form-label\">Chú thích sản phẩm</label>");
			out.append("<textarea row=\"8\" class=\"form-control\" id=\"productNotes\" name=\"txtProductNotes\" required ></textarea>");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của sản phẩm</div>");
			out.append("</div>"); // end col-lg-12
			
			out.append("<div class=\"col-lg-12 mb-3\">");
			out.append("<label for=\"productIntro\" class=\"form-label\">Giới thiệu chung sản phẩm</label>");
			out.append("<textarea row=\"8\" class=\"form-control\" id=\"productIntro\" name=\"txtProductIntro\" required ></textarea>");
			out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của sản phẩm</div>");
			out.append("</div>"); // end col-lg-12	
			out.append("</div>"); // end row mb-3

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
		int idProductSectionSelect = Utilities.getIntParam(request, "psid");
		if(idProductGroupSelect > 0 || idProductSectionSelect > 0) {
			doGet(request, response);
		} else {
			// lấy thông tin
			int product_pg_id = jsoft.library.Utilities.getIntParam(request, "slcProductGroupId");
			int product_ps_id = jsoft.library.Utilities.getIntParam(request, "slcProductSectionId");
			int product_pc_id = jsoft.library.Utilities.getIntParam(request, "slcProductCategoryId");
			String product_name = request.getParameter("txtProductName");
			int product_manager_id = jsoft.library.Utilities.getIntParam(request, "slcManager");		
			String product_note = request.getParameter("txtProductNotes");
			String product_intro = request.getParameter("txtProductIntro");
			int product_price = jsoft.library.Utilities.convertStringToInt(request.getParameter("txtProductPrice"));
			int product_discout_price = jsoft.library.Utilities.convertStringToInt(request.getParameter("txtProductDiscountPrice"));
			int product_promotion_price= jsoft.library.Utilities.convertStringToInt(request.getParameter("txtProductPromotionPrice"));
			short product_total= jsoft.library.Utilities.convertStringToShort(request.getParameter("txtProductTotal"));
			String product_style= request.getParameter("txtProductStyle");
			String product_best_seller = request.getParameter("isProduct_best_seller");
			
			// img-upload
			String product_image = "";			
	        
			// kiểm tra dữ liệu
			if (product_name != null && !product_name.equalsIgnoreCase("") 
					&& product_note != null && !product_note.equalsIgnoreCase("")
					&& product_intro != null && !product_intro.equalsIgnoreCase("")
					&& product_price > 0
					&& product_discout_price > 0
					&& product_promotion_price > 0
					&& product_total > 0
					&& product_style != null && !product_style.equalsIgnoreCase("")
					&& product_best_seller != null && !product_best_seller.equalsIgnoreCase("")
					&& product_pg_id > 0
					&& product_ps_id > 0
					&& product_pc_id > 0
					&& product_manager_id > 0) { 
				
				Part filePart = request.getPart("productImage");
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				InputStream io = filePart.getInputStream();
				String path = request.getServletContext().getRealPath("/") + "uploads" + File.separator + "products" + File.separator + fileName;
				
				if(jsoft.library.Utilities.saveFile(io, path)) {
					product_image = "/datn/uploads/products/" + fileName;					
				}
//				System.out.println("product_name: " + product_name + ", product_image:" + product_image + ", product_discout_price:" + product_discout_price + ", product_total: " + product_total);
//				System.out.println("product_manager_id: " + product_manager_id + ", product_intro:" + product_intro + ", product_note:" + product_note + ", product_pg_id: " + product_pg_id);
//				System.out.println("product_ps_id: " + product_ps_id + ", product_pc_id:" + product_pc_id + ", product_promotion_price:" + product_promotion_price + ", product_style: " + product_style);
//				System.out.println("product_best_seller: " + product_best_seller);
				
				// khởi tạo đối tượng
				ProductObject np = new ProductObject();
				
				np.setProduct_name(jsoft.library.Utilities.encode(product_name));
				np.setProduct_image(product_image);
				np.setProduct_price(product_price);
				np.setProduct_discount_price(product_discout_price);
				np.setProduct_total(product_total);
				
				np.setProduct_manager_id(product_manager_id);
				np.setProduct_intro(jsoft.library.Utilities.encode(product_intro));
				np.setProduct_notes(jsoft.library.Utilities.encode(product_note));
				np.setProduct_created_date(jsoft.library.Utilities_date.getDate());
				np.setProduct_pg_id(product_pg_id);
				
				np.setProduct_ps_id(product_ps_id);
				np.setProduct_pc_id(product_pc_id);			
				np.setProduct_promotion_price(product_promotion_price);	
				np.setProduct_best_seller(("yes".equals(product_best_seller)? true : false));
				np.setProduct_style(jsoft.library.Utilities.encode(product_style));
				
				np.setProduct_created_author_id(user.getUser_id());
				
				// tim bo quan ly ket noi
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				ProductControl psc = new ProductControl(cp);
				
				// thuc hien them moi san pham
				boolean result = psc.addProduct(np);
				
				// thuc hien them moi mau sac san pham
				
				
				// thuc hien them moi kich co san pham
				
				// tra ve ket noi
				psc.releaseConnection();
				
				if(result) {
					response.sendRedirect("/datn/product/list");
				} else {
					response.sendRedirect("/datn/product/list?err=add");
				}
				
			} else {
				String key = request.getParameter("keyword");
				if(key!=null) {
					response.sendRedirect("/datn/product/list?key=" + jsoft.library.Utilities.encode(key));					
				} else {
					response.sendRedirect("/datn/product/list?err=valueadd");
				}
			}
			
		}
		
		
	}

}
