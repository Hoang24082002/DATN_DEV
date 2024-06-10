package jsoft.home.product;

import java.util.ArrayList;

import jsoft.library.Utilities;
import jsoft.objects.ColorObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class ProductLibrary {
	public static ArrayList<String> viewProduct(
			ArrayList<ProductGroupObject> productGroups,
			ArrayList<ProductObject> productFavorites, 
			ArrayList<ProductObject> productNews, 
			ArrayList<ProductObject> productSupperDiscounts, 
			ArrayList<ProductSectionObject> productSections,
			ArrayList<ProductCategoryObject> productCategorys,
			ArrayList<SizeObject> sizes,
			ArrayList<ColorObject> colors,
			ArrayList<ProductObject> filterProductResult,
			ProductObject product,
			UserObject user,
			String url,
			int total,
			int page,
			byte totalPage) {
		ArrayList<String> view = new ArrayList<>();

		StringBuffer tmp = new StringBuffer();

		tmp.append("<div class=\"portfolio-isotope\" data-portfolio-filter=\"*\" data-portfolio-layout=\"masonry\" data-portfolio-sort=\"original-order\" data-aos=\"fade-up\" data-aos-delay=\"100\">");

		tmp.append("<div>");
		tmp.append("<ul class=\"portfolio-flters\">");
		tmp.append("<li data-filter=\"*\" class=\"filter-active\">Tất cả</li>");
		productGroups.forEach(pg -> {
			tmp.append("<li data-filter=\".filter-favorite-" + pg.getPg_id() + "\">").append(Utilities.encode(pg.getPg_name())).append("</li>");
		});
		tmp.append("</ul><!-- End Portfolio Filters -->");
		tmp.append("</div>");

		tmp.append("<div class=\"row gy-4 portfolio-container\">");
		productFavorites.forEach(productFavorite -> {
			tmp.append("<div class=\"col-xl-3 col-md-6 portfolio-item filter-favorite-" + productFavorite.getProduct_pg_id()+ "\">");
			tmp.append("<div class=\"portfolio-wrap\">");
			tmp.append("<div class=\"position-relative\">");
			tmp.append("<img src=\"" + productFavorite.getProduct_image() + "\" class=\"img-fluid\" alt=\"\">");
			tmp.append("</div>");
			tmp.append("<div class=\"portfolio-info\">");
			tmp.append("<h4><a href=\"./detailProduct.jsp?id="+productFavorite.getProduct_id()+"\" title=\"More Details\">" + productFavorite.getProduct_name() + "</a></h4>");
			tmp.append("<div class=\"d-flex justify-content-start portfolio-info-price\">");
			if(productFavorite.isProduct_best_seller()) {
				tmp.append("<span class=\"portfolio-info-price-main pe-3 text-decoration-line-through\">"+productFavorite.getProduct_price()+" đ</span>");
				tmp.append("<span class=\"portfolio-info-price-sale text-danger\">"+productFavorite.getProduct_promotion_price()+" đ</span>");				
			} else {
				tmp.append("<span class=\"portfolio-info-price-main pe-3\">"+productFavorite.getProduct_price()+" đ</span>");
			}
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div><!-- End Portfolio Item -->");
		});
		tmp.append("</div><!-- End Portfolio Container -->");

		tmp.append("</div>");

		// (0)
		view.add(tmp.toString());
		
		// (1)
		view.add(ProductLibrary.viewProductNew(productNews));
		
		// (2)
		view.add(ProductLibrary.viewProductSupperDiscount(productSupperDiscounts));
		
		// (3) 
		view.add(ProductLibrary.viewSidebarSectionFilterProduct(productSections, url, product));
		
		// (4)
		view.add(ProductLibrary.viewSidebarSizeFilterProduct(sizes));
		
		// (5)
		view.add(ProductLibrary.viewSidebarColorFilterProduct(colors));
		
		// (6)
		view.add(ProductLibrary.viewSidebarCategoryFilterProduct(productCategorys, product.getProduct_pc_id(), url, product));
		
		// (7)
		view.add(ProductLibrary.viewFilterProductResult(filterProductResult, url,page, total, totalPage));
		
		// (8)
		view.add(ProductLibrary.viewSortProdduct(url));
		
		// (9) detail product 
		view.add(viewDetailProduct(filterProductResult, colors, sizes, user, product));
		
		// (10) product related
		view.add(viewProductRelated(filterProductResult));

		return view;
	}
	
	public static String viewProductNew(ArrayList<ProductObject> productNews) {
		StringBuffer tmp = new StringBuffer();
		
		productNews.forEach(item -> {
			tmp.append("<div class=\"swiper-slide\">");
			tmp.append("<div class=\"swiper-slide-img position-relative\">");
			tmp.append("<a href=\"./detailProduct.jsp?id="+item.getProduct_id()+"\"> ");
			tmp.append("<img src=\""+item.getProduct_image()+"\" class=\"img-fluid\" alt=\"\">");
			tmp.append("<div class=\"swiper-slide-img-title\">");
			tmp.append("<p>"+item.getProduct_name()+"</p>");
			tmp.append("</div>");
			tmp.append("</a>");
			tmp.append("<div class=\"position-absolute z-3 top-0 rounded-3 text-bg-danger p-1 m-1\">Hàng mới</div>");
			tmp.append("</div>");
			tmp.append("<div class=\"swiper-slide-content row\">");
			tmp.append("<div class=\"swiper-slide-content-self col-xl-7 col-md-12 col-sm-12\">");
			tmp.append("<div class=\"swiper-slide-content-self-price portfolio-info-price\">");
			tmp.append("<span class=\"portfolio-info-price-main pe-3\">"+item.getProduct_price()+" đ</span>");
			tmp.append("<!-- <span class=\"portfolio-info-price-sale\">2.000.000 đ</span> -->");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");			
		});
		
		return tmp.toString();
	}
	
	public static String viewProductSupperDiscount(ArrayList<ProductObject> productSupperDiscounts ) {
		StringBuffer tmp = new StringBuffer();
		productSupperDiscounts.forEach(item -> {
			tmp.append("<div class=\"swiper-slide\">");
			tmp.append("<div class=\"swiper-slide-img position-relative\">");
			tmp.append("<a href=\"./detailProduct.jsp?id="+item.getProduct_id()+"\"> ");
			tmp.append("<img src=\""+item.getProduct_image()+"\" class=\"img-fluid\" alt=\"\">");
			tmp.append("<div class=\"swiper-slide-img-title\">");
			tmp.append("<p>"+item.getProduct_name()+"</p>");
			tmp.append("</div>");
			tmp.append("</a>");
			tmp.append("<div class=\"position-absolute z-3 top-0 rounded-3 text-bg-danger p-1 m-1\">").append((int)(((item.getProduct_price() - item.getProduct_promotion_price()) / (double)item.getProduct_price()) * 100)).append("%</div>");
			tmp.append("</div>");
			tmp.append("<div class=\"swiper-slide-content row\">");
			tmp.append("<div class=\"swiper-slide-content-self col-xl-7 col-md-12 col-sm-12\">");
			tmp.append("<div class=\"swiper-slide-content-self-price portfolio-info-price\">");
			if(item.isProduct_best_seller()) {
				tmp.append("<span class=\"portfolio-info-price-main pe-3 text-decoration-line-through\">"+item.getProduct_price()+" đ</span>");
				tmp.append("<span class=\"portfolio-info-price-sale text-danger\">"+item.getProduct_promotion_price()+" đ</span>");				
			} else {
				tmp.append("<span class=\"portfolio-info-price-main pe-3\">"+item.getProduct_price()+" đ</span>");
			}
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");			
		});
		
		return tmp.toString();
	}
	
	public static String viewSidebarSectionFilterProduct(ArrayList<ProductSectionObject> productSections, String url, ProductObject product) {
		StringBuffer tmp = new StringBuffer();
		
		String newURL;
//		String newURL = url.contains("pcid")? url.substring(0, url.indexOf("&psid=")) : url;
		if(url.contains("psid")) {
			newURL = url.substring(0, url.indexOf("&psid="));
		} else {
			newURL = url;
		}
		
		
		tmp.append("<ul id=\"components-nav-categories\" class=\"nav-content collapse \">");
		productSections.forEach(item -> {
			tmp.append("<li>");
			tmp.append("<a href=\""+newURL+"&psid="+item.getPs_id()+"\">");
			tmp.append("<i class=\"bi bi-circle\"></i><span>"+item.getPs_name()+"</span>");
			tmp.append("</a>");
			tmp.append("</li>");			
		});
		tmp.append("</ul>");
		
		return tmp.toString();
	}
	
	public static String viewSidebarSizeFilterProduct(ArrayList<SizeObject> sizes) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<ul id=\"components-nav-size\" class=\"nav-content collapse \">");
		tmp.append("<div class=\"d-flex flex-wrap\">");
		sizes.forEach(item -> {
			tmp.append("<li>");
			tmp.append("<a href=\"forms-elements.html\">");
			tmp.append("<div class=\"components-nav-sizeChoose\">"+item.getS_name()+"</div>");
			tmp.append("</a>");
			tmp.append("</li>");			
		});
		tmp.append("</div>");
		tmp.append("</ul>");
		
		return tmp.toString();
	}
	
	public static String viewSidebarColorFilterProduct(ArrayList<ColorObject> colors) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<ul id=\"components-nav-color\" class=\"nav-content collapse \">");
		tmp.append("<div class=\"d-flex flex-wrap\">");
		colors.forEach(item -> {
			tmp.append("<li>");
			tmp.append("<a href=\"forms-elements.html\">");
			tmp.append("<div class=\"components-nav-colorChoose\">"+item.getC_name()+"</div>");
			tmp.append("</a>");
			tmp.append("</li>");			
		});
		tmp.append("</div>");
		tmp.append("</ul>");
		
		return tmp.toString();
	}
	
	public static String viewSidebarCategoryFilterProduct(ArrayList<ProductCategoryObject> productCategorys, int active, String url, ProductObject product) {
		StringBuffer tmp = new StringBuffer();
		
		String newURL;
		if(url.contains("psid")) {
			newURL = url.substring(0, url.indexOf("&psid="));
		} else {
			newURL = url;
		}
		
		tmp.append("<ul class=\"nav nav-pills nav-fill\">");
		productCategorys.forEach(item -> {
			if(item.getPc_id() == active) {
				tmp.append("<li class=\"nav-item rounded mx-2\">");
				tmp.append("<a class=\"nav-link text-white fw-bolder active\" aria-current=\"page\" href=\""+newURL+ "&psid=" + product.getProduct_ps_id() + "&pcid="+ item.getPc_id() +"\">"+item.getPc_name()+"</a>");
				tmp.append("</li>");
			} else {
				tmp.append("<li class=\"nav-item rounded mx-2\">");
				tmp.append("<a class=\"nav-link text-dark fw-bolder\" aria-current=\"page\" href=\""+newURL+ "&psid=" + product.getProduct_ps_id() + "&pcid="+ item.getPc_id() +"\">"+item.getPc_name()+"</a>");
				tmp.append("</li>");							
			}
		});
		tmp.append("</ul>");;
		
		return tmp.toString();
	}
	
	public static String viewFilterProductResult(ArrayList<ProductObject> filterProductResult, String url, int page, int total, byte totalPage) {
		StringBuffer tmp = new StringBuffer();
		
		filterProductResult.forEach(item -> {
			tmp.append("<div class=\"col-xl-3 col-md-6 portfolio-item filter-do-nam\">");
			tmp.append("<div class=\"portfolio-wrap\">");
			tmp.append("<div class=\"position-relative\">");
			tmp.append("<img src=\""+item.getProduct_image()+"\" class=\"img-fluid\" alt=\"\">");
//			tmp.append("<div class=\"position-absolute z-3 top-0 rounded-3 text-bg-danger p-1 m-1\">Hàng mới</div>");
			tmp.append("</div>");
			tmp.append("<div class=\"portfolio-info\">");
			tmp.append("<h4><a href=\"./detailProduct.jsp?id="+item.getProduct_id()+"\" title=\"More Details\">"+item.getProduct_name()+"</a></h4>");
			tmp.append("<div class=\"d-flex justify-content-start portfolio-info-price\">");
			if(item.isProduct_best_seller()) {
				tmp.append("<span class=\"portfolio-info-price-main pe-3 text-decoration-line-through\">"+item.getProduct_price()+" đ</span>");
				tmp.append("<span class=\"portfolio-info-price-sale text-danger\">"+item.getProduct_promotion_price()+" đ</span>");				
			} else {
				tmp.append("<span class=\"portfolio-info-price-main pe-3\">"+item.getProduct_price()+" đ</span>");
			}
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div><!-- End Portfolio Item -->");			
		});
		tmp.append(ProductLibrary.getPaging(url,page, total, totalPage));
		
		return tmp.toString();
	}
	
	public static String viewSortProdduct(String url) {
		StringBuffer tmp = new StringBuffer();
		
		String newURL;
		if(url.contains("&m")) {
			newURL = url.substring(0, url.indexOf("&m"));
		} else {
			if(url.contains("&gct")) {
				newURL = url.substring(0, url.indexOf("&gct"));
			} else {
				if(url.contains("&gtc")) {
					newURL = url.substring(0, url.indexOf("&gtc"));
				} else {
					newURL = url;
				}
			}
		}
		
		tmp.append("<div class=\"filterNewProductByGroupPoduct-header d-flex flex-wrap justify-content-between align-items-center py-4\">");
		tmp.append("<div class=\"filterNewProductBySort\">");
		tmp.append("<div class=\"dropdown\">");
		tmp.append("<div class=\"\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">");
		tmp.append("<i class=\"fa-solid fa-filter\"></i>");
		tmp.append("Sắp xếp theo");
		tmp.append("</div>");
		tmp.append("<ul class=\"dropdown-menu\">");
		tmp.append("<li class=\"ps-4\"><a href=\""+ newURL + "&m" +"\">Mới nhất</a></li>");
		tmp.append("<li class=\"ps-4\"><a href=\""+ newURL + "&gtc"+"\">Giá từ thấp -> cao</a></li>");
		tmp.append("<li class=\"ps-4\"><a href=\""+ newURL + "&gct"+"\">Giá từ cao -> thấp</a></li>");
		tmp.append("</ul>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		
		return tmp.toString();
	}
	
	public static String viewDetailProduct(ArrayList<ProductObject> products, ArrayList<ColorObject> colors, ArrayList<SizeObject> sizes, UserObject user, ProductObject similer) {
		StringBuffer tmp = new StringBuffer();
		for(ProductObject product : products) {			
			if(product.getProduct_id() == similer.getProduct_id()) {
				tmp.append("<section id=\"portfolio-details\" class=\"portfolio-details\">");
				tmp.append("<div class=\"container text-dark\">");
				
				tmp.append("<div class=\"row gy-4\">");
				
				tmp.append("<div class=\"col-lg-5\">");
				tmp.append("<div class=\"portfolio-details-slider swiper\">");
				tmp.append("<div class=\"swiper-wrapper align-items-center\">");
				
				tmp.append("<div class=\"swiper-slide\">");
				tmp.append("<img src=\""+product.getProduct_image()+"\" alt=\"\">");
				tmp.append("</div>");
				
				tmp.append("<div class=\"swiper-slide\">");
				tmp.append("<img src=\""+product.getProduct_image()+"\" alt=\"\">");
				tmp.append("</div>");
				
				tmp.append("<div class=\"swiper-slide\">");
				tmp.append("<img src=\""+product.getProduct_image()+"\" alt=\"\">");
				tmp.append("</div>");
				
				tmp.append("</div>");
				tmp.append("<div class=\"swiper-pagination\"></div>");
				tmp.append("</div>");
				tmp.append("</div>");
				
				tmp.append("<div class=\"col-lg-7\">");
				
				tmp.append("<form action=\"cart/list\" method=\"post\">");
				tmp.append("<div class=\"portfolio-info\">");
				tmp.append("<h3>"+product.getProduct_name()+"</h3>");
				tmp.append("<ul>");
				tmp.append("<li>");
				tmp.append("<strong>Mã sp: </strong>");
				tmp.append("<input type=\"hidden\" name=\"url-product-image\" value=\"url\">");
				tmp.append("<input class=\"border-0\" type=\"text\" name=\"product_id_cart\" value=\""+product.getProduct_id()+"\" readonly>");
				tmp.append("</li>");
				tmp.append("<li>");
				tmp.append("<strong>Giá: </strong>");
				if(product.isProduct_best_seller()) {
					tmp.append("<input class=\"border-0\" type=\"text\" name=\"product_price_cart\" value=\""+product.getProduct_promotion_price()+"\" readonly>");
					
				} else {
					tmp.append("<input class=\"border-0\" type=\"text\" name=\"product_price_cart\" value=\""+product.getProduct_price()+"\" readonly>");
					
				}
				tmp.append("<input class=\"border-0\" type=\"hidden\" name=\"product_discount_price_cart\" value=\""+product.getProduct_discount_price()+"\">");
				tmp.append("</li>");
				
				tmp.append("<li>");
				tmp.append("<strong>Màu sắc: </strong>");
				tmp.append("<ul class=\"d-flex flex-wrap justify-content-start align-items-center\">");		
				colors.forEach(item -> {
					tmp.append("<li class=\"my-0 me-2 ms-3\">");
					tmp.append("<input type=\"radio\" class=\"\" name=\"product-color-choose\" value=\""+item.getC_name()+"\" id=\"mau-"+item.getC_id()+"\">");
					String color="";
					switch (item.getC_name()) {
					case "Màu đỏ":
						color = "red";
						break;
					case "Màu cam":
						color = "orange";
						break;
					case "Màu vàng":
						color = "yellow";
						break;
					case "Màu xanh lá":
						color = "green";
						break;
					case "Màu xanh da trời":
						color = "blue";
						break;
					case "Màu tím":
						color = "purple";
						break;					
					case "Màu trắng":
						color = "white";
						break;
					default:
						break;
					}
					tmp.append("<label class=\"components-nav-sizeChoose \" for=\"mau-"+item.getC_id()+"\" style=\"width: 30px; height: 30px; border: 1px solid #000; background: "+color+";\" ></label>");
					tmp.append("</li>");			
				});
				tmp.append("</ul>");
				
				tmp.append("</li>");
				
				tmp.append("<li>");
				tmp.append("<strong>Kích cỡ: </strong>");
				tmp.append("<ul class=\"d-flex flex-wrap justify-content-start align-items-center\">");
				sizes.forEach(item -> {
					tmp.append("<li class=\"my-0 me-2 ms-3\">");
					tmp.append("<input type=\"radio\" class=\"\" name=\"product-size-choose\" value=\""+item.getS_name()+"\" id=\"size-"+item.getS_id()+"\">");
					tmp.append("<label class=\"components-nav-sizeChoose\" for=\"size-"+item.getS_id()+"\">"+item.getS_name()+"</label>");
					tmp.append("</li>");			
				});		
				tmp.append("</ul>");
				tmp.append("</li>");
				tmp.append("</ul>");
				tmp.append("<div class=\"add-shopping-cart\">");
				if(user != null) {
					tmp.append("<button class=\"btn btn-danger\" type=\"submit\">Thêm vào giỏ hàng</button>");				
				} else {
					tmp.append("<button class=\"btn btn-danger\" type=\"button\"><a class=\"d-flex text-white\" href=\"/datn/login.jsp\">Đăng nhập để thêm sản phẩm vào giỏ</a></button>");	
				}
				tmp.append("</div>");
				tmp.append("</div>");
				
				tmp.append("<div class=\"portfolio-description\">");
				tmp.append("<h4>Mô tả</h4>");
				tmp.append("<p>");
				tmp.append(product.getProduct_intro());
				tmp.append("</p>");
//			tmp.append("<h4 class=\"\"  data-bs-toggle=\"collapse\" data-bs-target=\"#show-material-product\" aria-expanded=\"false\" aria-controls=\"show-material-product\">");
//			tmp.append("Chất liệu");
//			tmp.append("</h4>");
				tmp.append("</div>");
				tmp.append("</form>");
				
				tmp.append("</div>");
				
				tmp.append("</div>");
				
				tmp.append("</div>");
				tmp.append("</section><!-- End Portfolio Details Section -->");	
				break;
			}
				
		}
		
		return tmp.toString();
	}
	
	
	public static String viewProductRelated(ArrayList<ProductObject> products) {
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<section id=\"clients\">");
		tmp.append("<div class=\"container\" data-aos=\"zoom-in\">");
		tmp.append("<div class=\"section-header\">");
		tmp.append("<h3>Gợi ý sản phẩm</h3>");
		tmp.append("</div>");
		tmp.append("<div class=\"clients-slider swiper\">");
		tmp.append("<div class=\"swiper-wrapper align-items-center\">");
		tmp.append("<!-- FOR -->");
		products.forEach(item -> {
			tmp.append("<div class=\"swiper-slide\">");
			tmp.append("<div class=\"swiper-slide-img position-relative\">");
			tmp.append("<a href=\"./detailProduct.jsp?id="+item.getProduct_id()+"\"> ");
			tmp.append("<img src=\""+item.getProduct_image()+"\" class=\"img-fluid\"");
			tmp.append("alt=\"\">");
			tmp.append("<div class=\"swiper-slide-img-title\">");
			tmp.append("<p>"+item.getProduct_name()+"</p>");
			tmp.append("</div>");
			tmp.append("</a>");
//			tmp.append("<div class=\"position-absolute z-3 top-0 rounded-3 text-bg-danger p-1 m-1\">Hàng mới</div>");
			tmp.append("</div>");
			tmp.append("<div class=\"swiper-slide-content row\">");
			tmp.append("<div class=\"swiper-slide-content-self col-xl-7 col-md-12 col-sm-12\">");
			tmp.append("<div class=\"swiper-slide-content-self-price portfolio-info-price\">");
			tmp.append("<span class=\"portfolio-info-price-main pe-3\">"+item.getProduct_price()+" đ</span>");
			tmp.append("<!-- <span class=\"portfolio-info-price-sale\">2.000.000 đ</span> -->");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");
			tmp.append("</div>");		
		});
		tmp.append("<!-- END FOR -->");
		tmp.append("</div>");
		tmp.append("<div class=\"swiper-pagination\"></div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</section><!-- End Our Clients Section -->");
		
		return tmp.toString();
}
	
	public static String getPaging(String url, int page, int total, byte totalperpage) {
		// tinh toan tong so trang
		int countPage = total / totalperpage;
		if(total % totalperpage != 0) {
			countPage++;
		}
		
		if(page > countPage || page <= 0) {
			page = 1;
		}
		
		
		
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<nav aria-label=\"...\">");
		tmp.append("<ul class=\"pagination justify-content-center\">");
		tmp.append("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
		
		// left 
		String leftCurrent = "";
		int count = 0;
		for(int i = page - 1; i > 0; i--) {
			leftCurrent = "<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"&page="+i+"\">"+i+"</a></li>" + leftCurrent;
			if(++count >= 2) {
				break;
			}
		}
		if(page >= 4) {		
			leftCurrent = "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">...</a></li>" + leftCurrent;
		}
		tmp.append(leftCurrent);
		
		tmp.append("<li class=\"page-item active\" aria-current=\"page\"><a class=\"page-link\" href=\"#\">"+page+"</a></li>");
		
		// right
		String rightCurrent ="";
		count = 0;
		for(int i = page + 1; i <= countPage; i++) {
			rightCurrent += "<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"&page="+i+"\">"+i+"</a></li>";
			if(++count >= 2) {
				break;
			}
		}
		if(countPage > page + 4) {
			rightCurrent += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">...</a></li>";
		}
		tmp.append(rightCurrent);
		
		tmp.append("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"&page="+countPage+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a></li>");
		tmp.append("</ul>");
		tmp.append("</nav>");
		
		return tmp.toString();
	}
	
	
}
