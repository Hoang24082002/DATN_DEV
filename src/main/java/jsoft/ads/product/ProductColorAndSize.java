package jsoft.ads.product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsoft.ConnectionPool;
import jsoft.objects.ProductColorObject;
import jsoft.objects.ProductSizeObject;
import jsoft.objects.UserObject;

/**
 * Servlet implementation class ProductColorAndSize
 */
@WebServlet("/product/colorAndSize")
public class ProductColorAndSize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductColorAndSize() {
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
//			view(request, response, user);
		} else {
			response.sendRedirect("/datn");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// lấy id của chuyen muc để chỉnh sửa
		int id = jsoft.library.Utilities.getShortParam(request, "idProduct");
		
		// Lấy giá trị từ các checkbox color + size
        String[] colorOptions = request.getParameterValues("colorOptions");
        String[] sizeOptions = request.getParameterValues("sizeOptions");
        
        if(colorOptions.length > 0 && sizeOptions.length > 0 && id > 0) {
        	// tim bo quan ly ket noi
			ConnectionPool cp = (ConnectionPool) getServletContext().getAttribute("CPool");
			ProductControl pc = new ProductControl(cp);
			
			boolean result = false;
			
			// thuc hien them moi mau sac san pham
			ProductColorObject pco;
	        for (String color : colorOptions) {
	            pco = new ProductColorObject();
	            pco.setC_id(jsoft.library.Utilities.convertStringToInt(color));
	            pco.setProduct_id(id);
	            
	            result = pc.addProductColor(pco);
	        }
			
			
			// thuc hien them moi kich co san pham
			ProductSizeObject pso;
	        for (String size : sizeOptions) {
	        	pso = new ProductSizeObject();
	        	pso.setS_id(jsoft.library.Utilities.convertStringToInt(size));
	        	pso.setProduct_id(id);
	            
	            result = pc.addProductSize(pso);
	        }
	        
	        if(result) {
	        	response.sendRedirect("/datn/product/list");
	        } else {
	        	response.sendRedirect("/datn/product/list?err=add");
	        }
			
			// tra ve ket noi
			pc.releaseConnection();
        } else {
        	response.sendRedirect("/datn/product/list?err=valueadd");
        }
	}

}
