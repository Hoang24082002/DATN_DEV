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
 * Servlet implementation class ProductGroupProfiles
 */
@WebServlet("/product/profiles")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	    maxFileSize = 1024 * 1024 * 10,      // 10MB
	    maxRequestSize = 1024 * 1024 * 50    // 50MB
	)
public class ProductProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// dinh nghia kieu noi dung xuat ve trinh khach
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductProfiles() {
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
		int id = jsoft.library.Utilities.getIntParam(request, "id");
		System.out.println("id = " + id);

		// tạo đối tượng thực hiện xuất nội dung
		PrintWriter out = response.getWriter();
		ProductObject e_p = null;

		// tìm bộ quản lý kết nối
		ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");

		// tạo đối tượng thực thi chức năng
		ProductControl psc = new ProductControl(cp);
		if (cp == null) {
			getServletContext().setAttribute("CPool", cp);
		}

		if (id > 0) {
			e_p = psc.getProduct(id);
		}

		// các thông tin sửa
		String product_name = "", product_image = "", product_modified_date = "", product_style = "", product_intro = "", product_notes = "";
		boolean product_enable = false, product_best_seller = false, product_promotion = false, product_is_detail = false;
		int product_manager_id = 0, product_price = 0, product_discount_price = 0, product_promotion_price = 0, product_pg_id = 0, product_ps_id = 0, product_pc_id = 0;
		short product_total = 0;
		boolean isEdit = false;

		if (e_p != null) {
			product_name = e_p.getProduct_name();
			product_image = e_p.getProduct_image();
			product_price = e_p.getProduct_price();
			product_discount_price = e_p.getProduct_discount_price();
			product_enable = e_p.isProduct_enable();
			
			product_total = e_p.getProduct_total();
			product_manager_id = e_p.getProduct_manager_id();
			product_intro = e_p.getProduct_intro();
			product_notes = e_p.getProduct_notes();
			
			
			product_pg_id = e_p.getProduct_pg_id();
			product_ps_id = e_p.getProduct_ps_id();
			product_pc_id = e_p.getProduct_pc_id();
			product_is_detail = e_p.isProduct_is_detail();
			product_promotion_price = e_p.getProduct_promotion_price();
			
			product_best_seller = e_p.isProduct_best_seller();
			product_promotion = e_p.isProduct_promotion();
			product_style = e_p.getProduct_style();

			isEdit = true;
		}

		// lay cau truc
		ProductObject similar = new ProductObject();
		similar.setProduct_id(e_p.getProduct_id());
		similar.setProduct_created_author_id(user.getUser_id());
		similar.setProduct_manager_id(product_manager_id);
		similar.setProduct_pc_id(e_p.getProduct_pc_id());
		
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
		
		// lay cau truc
		Sextet<ProductObject, Short, Byte, UserObject, ProductGroupObject, ProductSectionObject> infos = new Sextet<>(similar, (short)1, (byte) 15, user, selectProductCategoty, selectProductSection);
		ArrayList<String> viewList = psc.viewProduct(infos, new Triplet<>(PRODUCT_SOFT.ID, ORDER.DESC, ADD_END_UPDATE.UPDATE));
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
		out.append("<h1>Thông tin sản phẩm</h1>");
		out.append("<nav class=\"ms-auto\">");
		out.append("<ol class=\"breadcrumb\">");
		out.append("<li class=\"breadcrumb-item\"><a href=\"/datn/view\"><i class=\"bi bi-house\"></i></a></li>");
		out.append("<li class=\"breadcrumb-item\">Sản phẩm</li>");
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
		out.append("<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#editColorAndSize\"><i class=\"bi bi-plus-square\"></i> Chỉnh sửa màu sắc và kích cỡ</button>");
		out.append("</li>");
		
		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link\" data-bs-toggle=\"tab\" data-bs-target=\"#addMoreImageProduct\"><i class=\"bi bi-plus-square\"></i> Thêm ảnh cho sản phẩm</button>");
		out.append("</li>");

		out.append("<li class=\"nav-item\">");
		out.append("<button class=\"nav-link active\" data-bs-toggle=\"tab\" data-bs-target=\"#edit\"><i class=\"bi bi-pencil-square\"></i> Chỉnh sửa</button>");
		out.append("</li>");

		out.append("</ul>"); // end nav nav-tabs nav-tabs-bordered

		out.append("<div class=\"tab-content pt-2\">"); // start tab-content pt-2 overview
		out.append("<div class=\"tab-pane fade profile-overview\" id=\"overview\">");
		out.append("<h5 class=\"card-title\">Tóm tắt</h5>");
		out.append("<p class=\"small fst-italic\"> Do " + user.getUser_name() + " tạo</p>");

		out.append("<h5 class=\"card-title\">Chi tiết</h5>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label \">Tên sản phẩm</div>");
		out.append("<div class=\"col-lg-9 col-md-5\">" + product_name + "</div>");
		out.append("</div>");

		out.append("<div class=\"row\">");
		out.append("<div class=\"col-lg-3 col-md-4 label\">Chú thích</div>");
		out.append("<div class=\"col-lg-9 col-md-8\">" + product_notes + "</div>");
		out.append("</div>");
		out.append("</div>"); // end tab-content pt-2 overview
		
		out.append("<div class=\"tab-content pt-2\">"); // start tab-content pt-2 editColorAndSize
		out.append("<div class=\"tab-pane fade profile-editColorAndSize\" id=\"editColorAndSize\">");
		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"post\" action=\"/datn/product/colorAndSize\"\" class=\"needs-validation\" novalidate>");
		out.append("<input type=\"hidden\" name=\"idProduct\" value=\"" + id + "\" >");
		out.append("<div class=\"col-lg-12 mb-3\">");
		out.append(viewList.get(4));
		out.append("</div>"); // end col-lg-12
		out.append("<div class=\"text-center mt-3\">");
		out.append("<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-person-plus\"></i> Thêm mới</button>");
		out.append("</div>");
		out.append("</form><!-- End Profile Edit Form -->");
		out.append("</div>");
		out.append("</div>"); // end tab-content pt-2 editColorAndSize
		
		out.append("<div class=\"tab-content pt-2\">"); // start tab-content pt-2 addMoreImageProduct
		out.append("<div class=\"tab-pane fade profile-addMoreImageProduct\" id=\"addMoreImageProduct\">");
		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"post\" action=\"/datn/product/colorAndSize\"\" class=\"needs-validation\" novalidate>");
		out.append("<input type=\"hidden\" name=\"idProduct\" value=\"" + id + "\" >");
		out.append("<div class=\"\">"); // div
		out.append("Thêm ảnh sản phẩm");
		out.append("");
		out.append("");
		out.append("");
		out.append("</div>"); // end div
		out.append("<div class=\"text-center mt-3\">");
		out.append("<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-person-plus\"></i> Thêm mới</button>");
		out.append("</div>");
		out.append("</form><!-- End Profile Edit Form -->");
		out.append("</div>");
		out.append("</div>"); // end tab-content pt-2 addMoreImageProduct

		out.append("<div class=\"tab-pane fade show active profile-edit pt-3\" id=\"edit\">");
		out.append("<!-- Profile Edit Form -->");
		out.append("<form method=\"post\" action=\"/datn/product/profiles\" class=\"needs-validation\" enctype=\"multipart/form-data\" novalidate>");
		out.append("<div class=\"modal-content\">");
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
		out.append("<input type=\"text\" class=\"form-control\" id=\"productName\" name=\"txtProductName\" value=\""+product_name+"\" required >");
		out.append("<div class=\"invalid-feedback\">Hãy nhập vào tên của sản phẩm</div>");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-3 mb-3\">");
		out.append("<label for=\"productImage\" class=\"form-label\">Ảnh sản phẩm</label>");
		out.append("<input type=\"file\" class=\"form-control\" id=\"productImage\" name=\"productImage\" required >");
		out.append("</div>"); 	
		
		out.append("<div class=\"col-lg-3 mb-3\">");
		out.append("<div class=\"\">");
		out.append("<img src=\""+product_image+"\" alt=\""+product_image+"\">");
		out.append("</div>"); 
		out.append("</div>"); 
		out.append("</div>"); // end row mb-3
		
		out.append("<div class=\"row mb-3 mb-3\">");			
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productPrice\" class=\"form-label\">Giá sản phẩm</label>");
		out.append("<input type=\"number\" class=\"form-control\" id=\"productPrice\" name=\"txtProductPrice\" value=\""+product_price+"\" required >");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productDiscountPrice\" class=\"form-label\">Giá chiếu khấu sản phẩm</label>");
		out.append("<input type=\"number\" class=\"form-control\" id=\"productDiscountPrice\" name=\"txtProductDiscountPrice\" value=\""+product_discount_price+"\" required >");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productPromotionPrice\" class=\"form-label\">Giá khuyến mãi sản phẩm</label>");
		out.append("<input type=\"number\" class=\"form-control\" id=\"productPromotionPrice\" name=\"txtProductPromotionPrice\" value=\""+product_promotion_price+"\" required >");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productTotal\" class=\"form-label\">Số lượng sản phẩm</label>");
		out.append("<input type=\"number\" class=\"form-control\" id=\"productTotal\" name=\"txtProductTotal\" value=\""+product_total+"\" required >");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productStyle\" class=\"form-label\">Kiểu sản phẩm</label>");
		out.append("<input type=\"text\" class=\"form-control\" id=\"productStyle\" name=\"txtProductStyle\" value=\""+product_style+"\" required >");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productBestSeller\" class=\"col-form-label\">Siêu giảm giá</label>");
		out.append("<div class=\"text-start\">");
		if(product_best_seller) {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isProduct_best_seller\" value=\"no\" id=\"noProduct_best_seller\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_best_seller\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isProduct_best_seller\" value=\"yes\" checked id=\"yesProduct_best_seller\">");
			out.append("<label class=\"form-check-label\" for=\"yesProduct_best_seller\">Có</label>");						
		} else {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isProduct_best_seller\" value=\"no\" checked id=\"noProduct_best_seller\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_best_seller\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isProduct_best_seller\" value=\"yes\" id=\"yesProduct_best_seller\">");
			out.append("<label class=\"form-check-label\" for=\"yesProduct_best_seller\">Có</label>");
		}
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productBestSeller\" class=\"col-form-label\">Hiển thị không</label>");
		out.append("<div class=\"text-start\">");
		if(product_enable) {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isProduct_enable\" value=\"no\" id=\"noProduct_enable\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_enable\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isProduct_enable\" value=\"yes\" checked id=\"yesProduct_enable\">");
			out.append("<label class=\"form-check-label\" for=\"yesProduct_enable\">Có</label>");						
		} else {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isProduct_enable\" value=\"no\" checked id=\"noProduct_enable\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_enable\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isProduct_enable\" value=\"yes\" id=\"yesProduct_enable\">");
			out.append("<label class=\"form-check-label\" for=\"yesProduct_enable\">Có</label>");
		}
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-4 mb-3\">");
		out.append("<label for=\"productBestSeller\" class=\"col-form-label\">Khuyến mãi không</label>");
		out.append("<div class=\"text-start\">");
		if(product_promotion) {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isProduct_promotion\" value=\"no\" id=\"noProduct_promotion\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_promotion\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isProduct_promotion\" value=\"yes\" checked id=\"yesProduct_promotion\">");
			out.append("<label class=\"form-check-label\" for=\"yesProduct_promotion\">Có</label>");						
		} else {
			out.append("<input class=\"me-1\" type=\"radio\" name=\"isProduct_promotion\" value=\"no\" checked id=\"noProduct_promotion\">");
			out.append("<label class=\"form-check-label\" for=\"noProduct_promotion\">Không</label>");
			out.append("<input class=\"ms-4\" type=\"radio\" name=\"isProduct_promotion\" value=\"yes\" id=\"yesProduct_promotion\">");
			out.append("<label class=\"form-check-label\" for=\"yesProduct_promotion\">Có</label>");
		}
		out.append("</div>");
		out.append("</div>");
		
		out.append("<div class=\"col-lg-12 mb-3\">");
		out.append(viewList.get(4));
		out.append("</div>"); // end col-lg-12
		
		out.append("<div class=\"col-lg-12 mb-3\">");
		out.append("<label for=\"productNotes\" class=\"form-label\">Chú thích sản phẩm</label>");
		out.append("<textarea row=\"8\" class=\"form-control\" id=\"productNotes\" name=\"txtProductNotes\" required >"+product_notes+"</textarea>");
		out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của sản phẩm</div>");
		out.append("</div>"); // end col-lg-12
		
		out.append("<div class=\"col-lg-12 mb-3\">");
		out.append("<label for=\"productIntro\" class=\"form-label\">Giới thiệu chung sản phẩm</label>");
		out.append("<textarea row=\"8\" class=\"form-control\" id=\"productIntro\" name=\"txtProductIntro\" required >"+product_intro+"</textarea>");
		out.append("<div class=\"invalid-feedback\">Hãy nhập vào chú thích của sản phẩm</div>");
		out.append("</div>"); // end col-lg-12	
		out.append("</div>"); // end row mb-3
		
		// Truyền id theo cơ thế biến form ẩn để thực hiện edit
		if (isEdit) {
			out.append("<input type=\"hidden\" name=\"idForPost\" value=\"" + id + "\" >");
		}

		out.append("</div>");
		out.append("<div class=\"text-center mt-3\">");
		out.append("<button type=\"submit\" class=\"btn btn-primary\"><i class=\"bi bi-save\"></i> Lưu thay đổi</button>");
		out.append("</div>");
		out.append("</div>"); // modal-content

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
			String product_enable = request.getParameter("isProduct_enable");
			String product_promotion = request.getParameter("isProduct_promotion");
			
			// img-upload
			String product_image = "";
			Part filePart = request.getPart("productImage");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			InputStream io = filePart.getInputStream();
			String path = request.getServletContext().getRealPath("/") + "uploads" + File.separator + "products" + File.separator + fileName;
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
					&& product_enable != null && !product_enable.equalsIgnoreCase("")
					&& product_promotion != null && !product_promotion.equalsIgnoreCase("")
					&& product_pg_id > 0
					&& product_ps_id > 0
					&& product_pc_id > 0
					&& id > 0
					&& product_manager_id > 0
					&& jsoft.library.Utilities.saveFile(io, path)) { 
				
				product_image = "/datn/uploads/products/" + fileName;
				
//				System.out.println("id: " + id + ", product_manager_id: " + product_manager_id);
//				System.out.println("product_name: " + product_name + ", product_image:" + product_image + ", product_discout_price:" + product_discout_price + ", product_total: " + product_total);
//				System.out.println("product_manager_id: " + product_manager_id + ", product_intro:" + product_intro + ", product_note:" + product_note + ", product_pg_id: " + product_pg_id);
//				System.out.println("product_ps_id: " + product_ps_id + ", product_pc_id:" + product_pc_id + ", product_promotion_price:" + product_promotion_price + ", product_style: " + product_style);
//				System.out.println("product_best_seller: " + product_best_seller + ", product_price: " + product_price + ", product_enable + product_promotion: " + product_enable + "+" + product_promotion);
				
				// khởi tạo đối tượng
				ProductObject ep = new ProductObject();
				
				ep.setProduct_id(id);
				ep.setProduct_name(jsoft.library.Utilities.encode(product_name));
				ep.setProduct_image(product_image);
				ep.setProduct_price(product_price);
				ep.setProduct_discount_price(product_discout_price);
				ep.setProduct_enable(("yes".equals(product_enable)? true : false));
				
				ep.setProduct_total(product_total);				
				ep.setProduct_manager_id(product_manager_id);
				ep.setProduct_intro(jsoft.library.Utilities.encode(product_intro));
				ep.setProduct_notes(jsoft.library.Utilities.encode(product_note));
				ep.setProduct_modified_date(jsoft.library.Utilities_date.getDate());
				
				ep.setProduct_pg_id(product_pg_id);				
				ep.setProduct_ps_id(product_ps_id);
				ep.setProduct_pc_id(product_pc_id);		
				ep.setProduct_is_detail(true);
				ep.setProduct_promotion_price(product_promotion_price);	
				
				ep.setProduct_best_seller(("yes".equals(product_best_seller)? true : false));
				ep.setProduct_promotion(("yes".equals(product_promotion)? true : false));
				ep.setProduct_style(jsoft.library.Utilities.encode(product_style));
				
				// tim bo quan ly ket noi
				ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
				
				ProductControl pc = new ProductControl(cp);
				if (cp == null) {
					getServletContext().setAttribute("CPool", pc.getCP());
				}
				
				// thuc hien cap nhat
				boolean result = pc.editProduct(ep, PRODUCT_EDIT_TYPE.GENERAL);
				
				// tra ve ket noi
				pc.releaseConnection();
				
				if (result) {
					response.sendRedirect("/datn/product/list");
				} else {
					response.sendRedirect("/datn/product/list?err=edit");
				}
			} else {
			// id ko ton tai
			response.sendRedirect("/datn/product/list?err=profiles");
			}
		}
	}

}
