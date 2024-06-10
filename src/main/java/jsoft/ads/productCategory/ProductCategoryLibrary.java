package jsoft.ads.productCategory;

import java.util.ArrayList;
import java.util.HashMap;

import jsoft.ads.productGroup.ProductGroupLibrary;
import jsoft.library.Utilities;
import jsoft.objects.ProductCategoryObject;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.ProductSectionObject;
import jsoft.objects.UserObject;

public class ProductCategoryLibrary {
	public static ArrayList<String> viewProductCategory(
			ArrayList<ProductCategoryObject> items, 
			ProductCategoryObject productCategory, 
			UserObject user,
			HashMap<Integer, String> manager_name, 
			ArrayList<UserObject> datas, 
			ArrayList<ProductGroupObject> productGroups, 
			ArrayList<ProductSectionObject> productSections, 
			ADD_END_UPDATE aOru, 
			ProductGroupObject productGroupObject,
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

		if (productCategory.isPc_delete()) {
			tmp.append("<th scope=\"col\">Ngày xóa</th>");
			tmp.append("<th scope=\"col\" colspan=\"2\" class=\"text-center\">Thực hiện</th>");
			tmp.append("<th scope=\"col\">#</th>");
		} else {
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
			tmp.append("<td>" + item.getPc_created_date() + "</td>");
			tmp.append("<td>" + item.getPc_name() + "</td>");
				
			productGroups.forEach(productGroup -> {
				if(productGroup.getPg_id() == item.getPc_pg_id()) {
					tmp.append("<td>" + productGroup.getPg_name() + "</td>");
				}
			});
			

//			System.out.println("----" + section.isSection_delete() + "----");

			if (productCategory.isPc_delete()) {
				tmp.append("<td>" + item.getPc_deleted_date() + "</td>");
				tmp.append(
						"<td class=\"align-middle\"><a href=\"/datn/productCategory/dr?id="+item.getPc_id()+"&r\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-reply\"></i></a></td>");
				tmp.append(
						"<td class=\"align-middle\"><button class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delproductGroup"
								+ item.getPc_id() + "\"><i class=\"bi bi-trash\"></i></button></td>");
				tmp.append(ProductCategoryLibrary.viewDelProductCategory(item));
				tmp.append("<th scope=\"row\">" + item.getPc_id() + "</th>");
			} else {
				tmp.append("<td>" + manager_name.get(item.getPc_manager_id()) + "</td>");
				tmp.append(
						"<td class=\"align-middle\"><a href=\"#\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-eye\"></i></a></td>");
				tmp.append("<td class=\"align-middle\"><a href=\"/datn/productCategory/profiles?id=" + item.getPc_id()+ "&pgid="+item.getPc_pg_id() + "\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
				tmp.append(
						"<td class=\"align-middle\"><button class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delproductGroup"
								+ item.getPc_id() + "\"><i class=\"bi bi-trash\"></i></button></td>");
				tmp.append(ProductCategoryLibrary.viewDelProductCategory(item));

				tmp.append("<th scope=\"row\">" + item.getPc_id() + "</th>");
			}

			tmp.append("</tr>");
		});

		tmp.append("</tbody>");
		tmp.append("</table>");

		tmp.append("</div>");
		tmp.append("</div>");
		if(productCategory.getPc_name() != null) {
			tmp.append(ProductCategoryLibrary.getPaging("/datn/productCategory/list?key="+productCategory.getPc_name()+"&", page, total, totalPage));
		} else {
			tmp.append(ProductCategoryLibrary.getPaging("/datn/productCategory/list?", page, total, totalPage));	
		}

		view.add(tmp.toString());

		// view 1
		view.add(ProductCategoryLibrary.viewManagerOption(datas, productCategory.getPc_manager_id()));
		
		// danh sach nhom san pham 2
		view.add(ProductCategoryLibrary.viewProductGroupOption(productGroups, productCategory.getPc_pg_id(), aOru, productCategory.getPc_id()));
		
		// danh sach thanh phan san pham theo nhom san pham 3
		view.add(ProductCategoryLibrary.viewProductSectionOptionByProductGroup(productSections, productCategory.getPc_ps_id()));

		return view;
	}

	public static StringBuilder viewDelProductCategory(ProductCategoryObject item) {
		StringBuilder tmp = new StringBuilder();

		String title;

		if (item.isPc_delete()) {
			title = "Xóa vĩnh viễn";
		} else {
			title = "Xóa thành phần";
		}

		tmp.append("<div class=\"modal fade\" id=\"delproductGroup" + item.getPc_id()
				+ "\" tabindex=\"-1\" aria-labelledby=\"productGroupLabel" + item.getPc_id()
				+ "\" aria-hidden=\"true\">");
		tmp.append("<div class=\"modal-dialog modal-lg\">");
		tmp.append("<div class=\"modal-content\">");
		tmp.append("<div class=\"modal-header\">");
		tmp.append("<h1 class=\"modal-title fs-5\" id=\"productGroupLabel" + item.getPc_id() + "\">" + title + "</h1>");
		tmp.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-body\">");

		if (item.isPc_delete()) {
			tmp.append("Bạn sẽ xóa vĩnh viễn thành phần <b>").append(item.getPc_name())
					.append("</b> <br>");
			tmp.append("Thành phần không thể phục hồi được nữa.");
		} else {
			tmp.append("Bạn có chắc chắn xóa tài khoản <b>").append(item.getPc_name())
					.append("</b> <br>");
			tmp.append("Hệ thống tạm thời lưu vào thùng rác, thành phần có thể phục hồi trong vòng 30 ngày.");
		}
		tmp.append("</div>");
		tmp.append("<div class=\"modal-footer\">");
		if (item.isPc_delete()) {
			tmp.append("<a href=\"/datn/productCategory/dr?id=" + item.getPc_id() + "&pid="
					+ item.getPc_created_author_id() + "\" class=\"btn btn-danger\">Xóa vĩnh viễn</a>");
		} else {
			tmp.append("<a href=\"/datn/productCategory/dr?id=" + item.getPc_id() + "&t&pid="
					+ item.getPc_created_author_id() + "\" class=\"btn btn-danger\">Xóa</a>"); // dr = delete +
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
	
	public static String viewProductGroupOption(ArrayList<ProductGroupObject> productGroups, int selectedId, ADD_END_UPDATE aOru, int id) {
		StringBuilder tmp = new StringBuilder();
		
		tmp.append("<form method=\"\" action=\"\">");		
		tmp.append("<div class=\"\">");
		tmp.append("<select class=\"form-select\" name=\"slcProductGroupId\" onchange=\"refreshCate(this.form)\" >");
		tmp.append("<option value=\"\"> --- </option>");
		productGroups.forEach(item -> {
			if (item.getPg_id() == selectedId) {
				tmp.append("<option value=\"").append(item.getPg_id()).append("\" selected>");
			} else {
				tmp.append("<option value=\"").append(item.getPg_id()).append("\">");
			}
			tmp.append(Utilities.decode(item.getPg_name()));
			tmp.append("</option>");
		});
		
		tmp.append("</select>");
		tmp.append("</div>");
		tmp.append("</form>");
		
		tmp.append("<script language=\"javascript\">");
		tmp.append("function refreshCate(fn) {");
		tmp.append("let pg_id = fn.slcProductGroupId.value;");
		tmp.append("fn.method = 'post';");
		switch (aOru) {
		case ADD:
			tmp.append("fn.action = `/datn/productCategory/list?pgid=${pg_id}`;");
			break;
		case UPDATE:
			tmp.append("fn.action = `/datn/productCategory/profiles?id=${"+id+"}&pgid=${pg_id}`;");
			break;
		}
		
		tmp.append("fn.submit();");
		tmp.append("}");
		tmp.append("</script>");

		return tmp.toString();
	}
	
	public static String viewProductSectionOptionByProductGroup(ArrayList<ProductSectionObject> productSections, int selectedId) {
		StringBuilder tmp = new StringBuilder();
//		System.out.println("CateLib - selectUserManager = " + selectedId);
		productSections.forEach(item -> {
			if (item.getPs_id() == selectedId) {
				tmp.append("<option value=\"").append(item.getPs_id()).append("\" selected>");
			} else {
				tmp.append("<option value=\"").append(item.getPs_id()).append("\">");
			}
			tmp.append(Utilities.decode(item.getPs_name()));
			tmp.append("</option>");
		});

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
