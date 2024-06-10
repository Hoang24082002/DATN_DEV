package jsoft.ads.product;

import java.util.ArrayList;
import java.util.HashMap;

import jsoft.ads.product.ADD_END_UPDATE;
import jsoft.ads.productCategory.ProductCategoryLibrary;
import jsoft.ads.productGroup.ProductGroupLibrary;
import jsoft.library.Utilities;
import jsoft.objects.ColorObject;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.ProductSizeObject;
import jsoft.objects.SizeObject;
import jsoft.objects.UserObject;

public class ProductLibrary {
	public static ArrayList<String> viewProduct(
					ArrayList<ProductObject> items, 
					ProductObject product, 
					UserObject user,
					HashMap<Integer, String> manager_name, 
					ArrayList<UserObject> datas, 
					ArrayList<ProductGroupObject> productGroups,
					ArrayList<ProductSectionObject> productSections,
					ArrayList<ProductCategoryObject> productCategories, 
					ADD_END_UPDATE aOru,
					ArrayList<ColorObject> colors,
					ArrayList<SizeObject> sizes,
					ArrayList<ColorObject> productColors, 
					ArrayList<SizeObject> productSizes,
					int total,
					int page,
					byte totalPage) {

		ArrayList<String> view = new ArrayList<>();

		StringBuffer tmp = new StringBuffer();

		tmp.append("<div class=\"card\">");
		tmp.append("<div class=\"card-body\">");
//		tmp.append("<h5 class=\"card-title\">Table with stripped rows</h5>");

		tmp.append("<table class=\"table table-striped\">");
		tmp.append("<thead>");
		tmp.append("<tr>");
		tmp.append("<th scope=\"col\">STT</th>");
		tmp.append("<th scope=\"col\">Ngày tạo</th>");
		tmp.append("<th scope=\"col\">Tên</th>");
		tmp.append("<th scope=\"col\">Thuộc nhóm sản phẩm</th>");

		if (product.isProduct_delete()) {
			tmp.append("<th scope=\"col\">Ngày xóa</th>");
			tmp.append("<th scope=\"col\" colspan=\"2\" class=\"text-center\">Thực hiện</th>");
			tmp.append("<th scope=\"col\">#</th>");
		} else {
			tmp.append("<th scope=\"col\">Màu sắc</th>");
			tmp.append("<th scope=\"col\">Kích cỡ</th>");
			tmp.append("<th scope=\"col\">Người quản lý</th>");
			tmp.append("<th scope=\"col\" colspan=\"3\">Thực hiện</th>");
			tmp.append("<th scope=\"col\">#</th>");
		}
		tmp.append("</tr>");
		tmp.append("</thead>");

		tmp.append("<tbody>");

		items.forEach(item -> {
			tmp.append("<tr>");
			tmp.append("<th scope=\"row\">" + (items.indexOf(item) + 1) + "</th>");
			tmp.append("<td>" + item.getProduct_created_date() + "</td>");
			tmp.append("<td>" + item.getProduct_name() + "</td>");
				
			productGroups.forEach(productGroup -> {
				productSections.forEach(productSection -> {
					productCategories.forEach(productCategory -> {
						if(productGroup.getPg_id() == item.getProduct_pg_id() && productSection.getPs_id() == item.getProduct_ps_id() && productCategory.getPc_id() == item.getProduct_pc_id()) {
							tmp.append("<td>" + productGroup.getPg_name() + "/" + productSection.getPs_name() + "/" + productCategory.getPc_name() + "</td>");
						}						
					});
				});
			});
			

//			System.out.println("----" + section.isSection_delete() + "----");

			if (product.isProduct_delete()) {
				tmp.append("<td>" + item.getProduct_deleted_date() + "</td>");
				tmp.append(
						"<td class=\"align-middle\"><a href=\"/datn/product/dr?id="+item.getProduct_id()+"&r\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-reply\"></i></a></td>");
				tmp.append(
						"<td class=\"align-middle\"><button class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delproductGroup"
								+ item.getProduct_id() + "\"><i class=\"bi bi-trash\"></i></button></td>");
				tmp.append(ProductLibrary.viewDelProductSection(item));
				tmp.append("<th scope=\"row\">" + item.getProduct_id() + "</th>");
			} else {
				// mau sac
				tmp.append("<td>");
				productColors.forEach(productColor -> {
					if(productColor.getC_manager_id() == item.getProduct_id()) {
						tmp.append(productColor.getC_name() + " ");
					}
				});
				tmp.append("</td>");
				// kich co
				tmp.append("<td>");
				productSizes.forEach(productSize -> {
					if(productSize.getS_manager_id() == item.getProduct_id()) {
						tmp.append(productSize.getS_name() + " ");
					}
				});
				tmp.append("</td>");
				tmp.append("<td>" + manager_name.get(item.getProduct_manager_id()) + "</td>");
				tmp.append(
						"<td class=\"align-middle\"><a href=\"#\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-eye\"></i></a></td>");
				tmp.append("<td class=\"align-middle\"><a href=\"/datn/product/profiles?id=" + item.getProduct_id() + "&pgid="+item.getProduct_pg_id() + "&psid="+item.getProduct_ps_id()
						+ "\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
				tmp.append(
						"<td class=\"align-middle\"><button class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delproductGroup"
								+ item.getProduct_id() + "\"><i class=\"bi bi-trash\"></i></button></td>");
				tmp.append(ProductLibrary.viewDelProductSection(item));

				tmp.append("<th scope=\"row\">" + item.getProduct_id() + "</th>");
			}

			tmp.append("</tr>");
		});

		tmp.append("</tbody>");
		tmp.append("</table>");

		tmp.append("</div>");
		tmp.append("</div>");
		if(product.getProduct_name() != null) {
			tmp.append(ProductLibrary.getPaging("/datn/product/list?key="+product.getProduct_name()+"&", page, total, totalPage));
		} else {
			tmp.append(ProductLibrary.getPaging("/datn/product/list?", page, total, totalPage));			
		}
		view.add(tmp.toString());

		// view 1
		view.add(ProductLibrary.viewManagerOption(datas, product.getProduct_manager_id()));
		
		// danh sach nhom san pham 2
		view.add(ProductLibrary.viewProductGroupOptionEndProductSectionOption(productGroups,productSections, product.getProduct_pg_id(), product.getProduct_ps_id(), aOru, product.getProduct_id()));
		
		// danh sach danh muc san pham theo thanh phan san pham 3
		view.add(ProductLibrary.viewProducCategorytOptionByProductSection(productCategories, product.getProduct_pc_id()));
		
		// danh sach mau sac va kich co (4)
		view.add(ProductLibrary.viewProductColorAndSize(colors, sizes, productColors, productSizes, product.getProduct_id()));

		return view;
	}

	public static StringBuilder viewDelProductSection(ProductObject item) {
		StringBuilder tmp = new StringBuilder();

		String title;

		if (item.isProduct_delete()) {
			title = "Xóa vĩnh viễn";
		} else {
			title = "Xóa thành phần";
		}

		tmp.append("<div class=\"modal fade\" id=\"delproductGroup" + item.getProduct_id()
				+ "\" tabindex=\"-1\" aria-labelledby=\"productGroupLabel" + item.getProduct_id()
				+ "\" aria-hidden=\"true\">");
		tmp.append("<div class=\"modal-dialog modal-lg\">");
		tmp.append("<div class=\"modal-content\">");
		tmp.append("<div class=\"modal-header\">");
		tmp.append("<h1 class=\"modal-title fs-5\" id=\"productGroupLabel" + item.getProduct_id() + "\">" + title + "</h1>");
		tmp.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-body\">");

		if (item.isProduct_delete()) {
			tmp.append("Bạn sẽ xóa vĩnh viễn thành phần <b>").append(item.getProduct_name())
					.append("</b> <br>");
			tmp.append("Thành phần không thể phục hồi được nữa.");
		} else {
			tmp.append("Bạn có chắc chắn xóa tài khoản <b>").append(item.getProduct_name())
					.append("</b> <br>");
			tmp.append("Hệ thống tạm thời lưu vào thùng rác, thành phần có thể phục hồi trong vòng 30 ngày.");
		}
		tmp.append("</div>");
		tmp.append("<div class=\"modal-footer\">");
		if (item.isProduct_delete()) {
			tmp.append("<a href=\"/datn/product/dr?id=" + item.getProduct_id() + "&pid="
					+ item.getProduct_created_author_id() + "\" class=\"btn btn-danger\">Xóa vĩnh viễn</a>");
		} else {
			tmp.append("<a href=\"/datn/product/dr?id=" + item.getProduct_id() + "&t&pid="
					+ item.getProduct_created_author_id() + "\" class=\"btn btn-danger\">Xóa</a>"); // dr = delete +
																									// restore
		}
		tmp.append("<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Hủy</button>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</div>");

		return tmp;
	}

	public static String viewManagerOption(ArrayList<UserObject> users, int selectedId) {
		StringBuilder tmp = new StringBuilder();

		users.forEach(item -> {
			if (item.getUser_id() == selectedId) {
				tmp.append("<option value=\"").append(item.getUser_id()).append("\" selected>");
			} else {
				tmp.append("<option value=\"").append(item.getUser_id()).append("\">");
			}
			tmp.append(Utilities.decode(item.getUser_fullname())).append("(").append(item.getUser_name()).append(")");
//			tmp.append("aaaaaa");
			tmp.append("</option>");
		});

		return tmp.toString();
	}
	
	public static String viewProductGroupOptionEndProductSectionOption(ArrayList<ProductGroupObject> productGroups, ArrayList<ProductSectionObject> productSections, int selectedPGId, int selectedPSId, ADD_END_UPDATE aOru, int id) {
		StringBuilder tmp = new StringBuilder();
		
		System.out.println("selectedPGId - " + selectedPGId +", selectedPSId - " + selectedPSId);
		
		tmp.append("<div class=\"row\">");
		
		// form select ProductGroup
		tmp.append("<form method=\"\" action=\"\" class=\"\">");
		tmp.append("<div class=\"col-lg-6\">");
		tmp.append("<label for=\"slcProductGroup\" class=\"form-label\">Nhóm sản phẩm</label>");
		tmp.append("<div class=\"mb-3\">");
		tmp.append("<select class=\"form-select\" name=\"slcProductGroupId\" onchange=\"refreshCate(this.form)\" >");
		tmp.append("<option value=\"0\"> --- </option>");
		productGroups.forEach(item -> {
			if (item.getPg_id() == selectedPGId) {
				tmp.append("<option value=\"").append(item.getPg_id()).append("\" selected>");
			} else {
				tmp.append("<option value=\"").append(item.getPg_id()).append("\">");
			}
			tmp.append(Utilities.decode(item.getPg_name()));
			tmp.append("</option>");
		});
		
		tmp.append("</select>");
		tmp.append("</div>");
		tmp.append("</div>");
//		tmp.append("</form>");
		
		
		// form select ProductSection
//		tmp.append("<form method=\"\" action=\"\" class=\"col-lg-6 \">");
		tmp.append("<div class=\"col-lg-6\">");
		tmp.append("<label for=\"slcProductSection\" class=\"form-label\">Thành phần sản phẩm</label>");
		tmp.append("<div class=\"mb-3\">");
		tmp.append("<select class=\"form-select\" name=\"slcProductSectionId\" onchange=\"refreshCate(this.form)\" >");
		tmp.append("<option value=\"0\"> --- </option>");
		productSections.forEach(item -> {
			if (item.getPs_id() == selectedPSId) {
				tmp.append("<option value=\"").append(item.getPs_id()).append("\" selected>");
			} else {
				tmp.append("<option value=\"").append(item.getPs_id()).append("\">");
			}
			tmp.append(Utilities.decode(item.getPs_name()));
			tmp.append("</option>");
		});
		
		tmp.append("</select>");
		tmp.append("</div>");
		tmp.append("</div>");
		tmp.append("</form>");
		
		tmp.append("<script language=\"javascript\">");
		
		// JS su ly Form ProductGroup
		tmp.append("function refreshCate(fn) {");
		tmp.append("let pg_id = fn.slcProductGroupId.value;");
		tmp.append("let ps_id = fn.slcProductSectionId.value;");
		tmp.append("fn.method = 'post';");
		tmp.append("if(ps_id == 0 ) {");
		switch (aOru) {
		case ADD:
			tmp.append("fn.action = `/datn/product/list?pgid=${pg_id}`;");
			break;
		case UPDATE:
			tmp.append("fn.action = `/datn/product/profiles?id=${"+id+"}&pgid=${pg_id}`;");
			break;
		}
		tmp.append("} else {");
		switch (aOru) {
		case ADD:
			tmp.append("fn.action = `/datn/product/list?pgid=${pg_id}&psid=${ps_id}`;");
			break;
		case UPDATE:
			tmp.append("fn.action = `/datn/product/profiles?id=${"+id+"}&pgid=${pg_id}&psid=${ps_id}`;");
			break;
		}
		tmp.append("}");

		
		tmp.append("fn.submit();");
		tmp.append("}");

		tmp.append("</script>");
		
		tmp.append("</div>");

		return tmp.toString();
	}
	
	public static String viewProductSectionOptionByProductGroup(ArrayList<ProductSectionObject> productSections, int selectedId, ADD_END_UPDATE aOru, int id) {
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("<form method=\"\" action=\"\">");
		tmp.append("<label for=\"slcProductSection\" class=\"form-label\">Thành phần sản phẩm</label>");
		tmp.append("<div class=\"\">");
		tmp.append("<select class=\"form-select\" name=\"slcProductSectionId\" onchange=\"refreshCate2(this.form)\" >");
		tmp.append("<option value=\"\"> --- </option>");
		productSections.forEach(item -> {
			if (item.getPs_id() == selectedId) {
				tmp.append("<option value=\"").append(item.getPs_id()).append("\" selected>");
			} else {
				tmp.append("<option value=\"").append(item.getPs_id()).append("\">");
			}
			tmp.append(Utilities.decode(item.getPs_name()));
			tmp.append("</option>");
		});
		
		tmp.append("</select>");
		tmp.append("</div>");
		tmp.append("</form>");
		
		tmp.append("<script language=\"javascript\">");
		tmp.append("function refreshCate2(fn) {");
		tmp.append("let pg_id = document.getElementById('slcProductGroupId').value;");
		tmp.append("console.log(pg_id);");
		tmp.append("let ps_id = fn.slcProductSectionId.value;");
		tmp.append("fn.method = 'post';");
		switch (aOru) {
		case ADD:
			tmp.append("fn.action = `/datn/product/list?psid=${ps_id}`;");
			break;
		case UPDATE:
			tmp.append("fn.action = `/datn/product/profiles?id=${"+id+"}&psid=${ps_id}`;");
			break;
		}
		
		tmp.append("fn.submit();");
		tmp.append("}");
		tmp.append("</script>");

		return tmp.toString();
	}
	
	public static String viewProducCategorytOptionByProductSection(ArrayList<ProductCategoryObject> productCategories, int selectedId) {
		StringBuilder tmp = new StringBuilder();
//		System.out.println("CateLib - selectUserManager = " + selectedId);
		productCategories.forEach(item -> {
			if (item.getPc_id() == selectedId) {
				tmp.append("<option value=\"").append(item.getPc_id()).append("\" selected>");
			} else {
				tmp.append("<option value=\"").append(item.getPc_id()).append("\">");
			}
			tmp.append(Utilities.decode(item.getPc_name()));
			tmp.append("</option>");
		});

		return tmp.toString();
	}
	
	public static String viewProductColorAndSize(ArrayList<ColorObject> colors, ArrayList<SizeObject> sizes, ArrayList<ColorObject> productColors, ArrayList<SizeObject> productSizes, int id) {
		StringBuilder tmp = new StringBuilder();
		
		// checkbox ColorOptions
		tmp.append("<div class=\"\">");
		tmp.append("<label for=\"productColor\" class=\"col-form-label\">Màu sắc</label>");
		tmp.append("<div class=\"d-flex justify-content-start flex-wrap\">");
		colors.forEach(item -> {
			boolean checked = false;
			   for (ColorObject pc : productColors) {
			        if (pc.getC_manager_id() == id && item.getC_id() == pc.getC_id()) {
			        	checked = true;
			            break;  // Exit the inner loop since the condition is met
			        }
			    }
			tmp.append("<div class=\"form-check ms-4\">");
			tmp.append("<input class=\"form-check-input\" type=\"checkbox\" name=\"colorOptions\" value=\""+item.getC_id()+"\" id=\"colorID"+item.getC_id()+"\"").append(checked? "checked": " ").append(">");
			tmp.append("<label class=\"form-check-label\" for=\"colorID"+item.getC_id()+"\">"+item.getC_name()+"</label>");
			tmp.append("</div>");
		});		
		tmp.append("</div>");
		tmp.append("</div>");
		
		// checkbox sizeOptions
		tmp.append("<div class=\"\">");
		tmp.append("<label for=\"productSize\" class=\"col-form-label\">Kích cỡ</label>");
		tmp.append("<div class=\"d-flex justify-content-start flex-wrap\">");
		sizes.forEach(item -> {
			boolean checked = false;
			   for (SizeObject ps : productSizes) {
			        if (ps.getS_manager_id() == id && item.getS_id() == ps.getS_id()) {
			        	checked = true;
			            break;  // Exit the inner loop since the condition is met
			        }
			    }
			tmp.append("<div class=\"form-check ms-4\">");
			tmp.append("<input class=\"form-check-input\" type=\"checkbox\" name=\"sizeOptions\" value=\""+item.getS_id()+"\" id=\"sizeID"+item.getS_id()+"\"").append(checked? "checked": " ").append(">");
			tmp.append("<label class=\"form-check-label\" for=\"sizeID"+item.getS_id()+"\">"+item.getS_name()+"</label>");
			tmp.append("</div>");					
		});
		tmp.append("</div>");
		tmp.append("</div>");
		
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
			leftCurrent = "<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"page="+i+"\">"+i+"</a></li>" + leftCurrent;
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
			rightCurrent += "<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"page="+i+"\">"+i+"</a></li>";
			if(++count >= 2) {
				break;
			}
		}
		if(countPage > page + 4) {
			rightCurrent += "<li class=\"page-item disabled\"><a class=\"page-link\" href=\"#\">...</a></li>";
		}
		tmp.append(rightCurrent);
		
		tmp.append("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"page="+countPage+"\" tabindex=\"-1\" aria-disabled=\"true\" ><span aria-hidden=\"true\">&raquo;</span></a></li>");
		tmp.append("</ul>");
		tmp.append("</nav>");
		
		return tmp.toString();
	}
	
	
}
