<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jsoft.*,jsoft.objects.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.*, org.javatuples.*"%>
<%@ page import="jsoft.library.*"%>
<%@ page import="jsoft.home.product.ProductControl"%>
<%@ page import="jsoft.home.product.PRODUCT_SORT"%>

<%
//xac dinh tap ki tu can lay
request.setCharacterEncoding("utf-8");

//tìm thông tin đăng nhập
UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

//tim bo quan ly ket noi
ConnectionPool cp = (ConnectionPool)application.getAttribute("CPool");

ProductControl pc = new ProductControl(cp);
if(cp==null) {
	application.setAttribute("CPool", pc.getCP());
}

//lay id pg, ps, pc
int pg_id = Utilities.getIntParam(request, "pgid");
int ps_id = Utilities.getIntParam(request, "psid");
int pc_id = Utilities.getIntParam(request, "pcid");

int getPage = Utilities.getIntParam(request, "page");
if(getPage<1) {
	getPage = 0;
}

// Lấy URL của yêu cầu hiện tại
StringBuffer url = request.getRequestURL();
String queryString = request.getQueryString();
if (queryString != null) {
    url.append("?").append(queryString);
}
String fullUrl = url.toString();

//lay cau truc
ProductObject similar = new ProductObject();

if(pg_id > 0) {
similar.setProduct_pg_id(pg_id);
}

if(ps_id > 0) {
similar.setProduct_ps_id(ps_id);	
}

if(pc_id > 0) {
similar.setProduct_pc_id(pc_id);	
}

// lay thong tin sap xep
String sortID = request.getParameter("m");
String sortPriceCT = request.getParameter("gct");
String sortPriceTC = request.getParameter("gtc");

Pair<PRODUCT_SORT, ORDER> so;
if(sortID != null) {
	so = new Pair<>(PRODUCT_SORT.ID, ORDER.DESC);
} else {
	if(sortPriceCT != null) {
		so = new Pair<>(PRODUCT_SORT.PRICE, ORDER.DESC);
	} else {
		if(sortPriceTC != null) {
			so = new Pair<>(PRODUCT_SORT.PRICE, ORDER.ASC);
		} else {
			so = new Pair<>(PRODUCT_SORT.ID, ORDER.ASC);
		}
	}
}

Quintet<ProductObject, Short, Byte, UserObject, String> infos = new Quintet<>(similar, (short)getPage, (byte)4, user, fullUrl);
ArrayList<String> viewProduct = pc.viewProduct(infos, so, false);
if(viewProduct.size() > 0) {
	//gui cau truc hien thi vao phien lam viec
	session.setAttribute("viewSidebarSectionFilterProduct", viewProduct.get(3));
	session.setAttribute("viewSidebarSizeFilterProduct", viewProduct.get(4));
	session.setAttribute("viewSidebarColorFilterProduct", viewProduct.get(5));
	session.setAttribute("viewSidebarCategoryFilterProduct", viewProduct.get(6));
	session.setAttribute("viewFilterProductResult", viewProduct.get(7));
	session.setAttribute("viewSortProdduct", viewProduct.get(8));
}


%>