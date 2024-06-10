package jsoft.ads.productColor;

import java.util.ArrayList;
import java.util.HashMap;
import jsoft.library.Utilities;
import jsoft.objects.ProductGroupObject;
import jsoft.objects.UserObject;

public class ProductColorLibrary {
	public static ArrayList<String> viewProductGroup(ArrayList<ProductGroupObject> items, ProductGroupObject productGroup, UserObject user,
			HashMap<Integer, String> manager_name, ArrayList<UserObject> datas) {

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

		if (productGroup.isPg_delete()) {
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
			tmp.append("<td>" + item.getPg_created_date() + "</td>");
			tmp.append("<td>" + item.getPg_name() + "</td>");

//			System.out.println("----" + section.isSection_delete() + "----");

			if (productGroup.isPg_delete()) {
				tmp.append("<td>" + item.getPg_modified_date() + "</td>");
				tmp.append(
						"<td class=\"align-middle\"><a href=\"/datn/productGroup/dr?id="+item.getPg_id()+"&r\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-reply\"></i></a></td>");
				tmp.append(
						"<td class=\"align-middle\"><button class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delproductGroup"
								+ item.getPg_id() + "\"><i class=\"bi bi-trash\"></i></button></td>");
				tmp.append(ProductColorLibrary.viewDelProductGroup(item));
				tmp.append("<th scope=\"row\">" + item.getPg_id() + "</th>");
			} else {
				tmp.append("<td>" + manager_name.get(item.getPg_manager_id()) + "</td>");
				tmp.append(
						"<td class=\"align-middle\"><a href=\"#\" class=\"btn btn-primary btn-sm\"><i class=\"bi bi-eye\"></i></a></td>");
				tmp.append("<td class=\"align-middle\"><a href=\"/datn/productGroup/profiles?id=" + item.getPg_id()
						+ "\" class=\"btn btn-secondary btn-sm\"><i class=\"bi bi-pencil-square\"></i></a></td>");
				tmp.append(
						"<td class=\"align-middle\"><button class=\"btn btn-danger btn-sm\" data-bs-toggle=\"modal\" data-bs-target=\"#delproductGroup"
								+ item.getPg_id() + "\"><i class=\"bi bi-trash\"></i></button></td>");
				tmp.append(ProductColorLibrary.viewDelProductGroup(item));

				tmp.append("<th scope=\"row\">" + item.getPg_id() + "</th>");
			}

			tmp.append("</tr>");
		});

		tmp.append("</tbody>");
		tmp.append("</table>");

		tmp.append("</div>");
		tmp.append("</div>");

		view.add(tmp.toString());
//		System.out.println(section.getSection_manager_id());
		view.add(viewManagerOption(datas, productGroup.getPg_manager_id()));

		return view;
	}

	public static StringBuilder viewDelProductGroup(ProductGroupObject item) {
		StringBuilder tmp = new StringBuilder();

		String title;

		if (item.isPg_delete()) {
			title = "Xóa vĩnh viễn";
		} else {
			title = "Xóa thành phần";
		}

		tmp.append("<div class=\"modal fade\" id=\"delproductGroup" + item.getPg_id()
				+ "\" tabindex=\"-1\" aria-labelledby=\"productGroupLabel" + item.getPg_id()
				+ "\" aria-hidden=\"true\">");
		tmp.append("<div class=\"modal-dialog modal-lg\">");
		tmp.append("<div class=\"modal-content\">");
		tmp.append("<div class=\"modal-header\">");
		tmp.append("<h1 class=\"modal-title fs-5\" id=\"productGroupLabel" + item.getPg_id() + "\">" + title + "</h1>");
		tmp.append(
				"<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>");
		tmp.append("</div>");
		tmp.append("<div class=\"modal-body\">");

		if (item.isPg_delete()) {
			tmp.append("Bạn sẽ xóa vĩnh viễn thành phần <b>").append(item.getPg_name())
					.append("</b> <br>");
			tmp.append("Thành phần không thể phục hồi được nữa.");
		} else {
			tmp.append("Bạn có chắc chắn xóa tài khoản <b>").append(item.getPg_name())
					.append("</b> <br>");
			tmp.append("Hệ thống tạm thời lưu vào thùng rác, thành phần có thể phục hồi trong vòng 30 ngày.");
		}
		tmp.append("</div>");
		tmp.append("<div class=\"modal-footer\">");
		if (item.isPg_delete()) {
			tmp.append("<a href=\"/datn/productGroup/dr?id=" + item.getPg_id() + "&pid="
					+ item.getPg_created_author_id() + "\" class=\"btn btn-danger\">Xóa vĩnh viễn</a>");
		} else {
			tmp.append("<a href=\"/datn/productGroup/dr?id=" + item.getPg_id() + "&t&pid="
					+ item.getPg_created_author_id() + "\" class=\"btn btn-danger\">Xóa</a>"); // dr = delete +
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
}
