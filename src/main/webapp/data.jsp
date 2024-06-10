<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jsoft.*,jsoft.objects.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.*, org.javatuples.*"%>
<%@ page import="jsoft.home.product.ProductControl"%>
<%@ page import="jsoft.home.cart.CartControl"%>
<%@ page import="jsoft.home.order.OrderControl"%>
<%@ page import="jsoft.home.product.PRODUCT_SORT"%>
<%@ page import="jsoft.library.*"%>

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

//lay thong tin sap xep
String sortID = request.getParameter("m");
String sortPriceCT = request.getParameter("gct");
String sortPriceTC = request.getParameter("gtc");

//lay ID product detail
int id = Utilities.getIntParam(request, "id");

int getPage = Utilities.getIntParam(request, "page");
if(getPage<1) {
	getPage = 0;
}

//Lấy URL của yêu cầu hiện tại
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

// lay cau truc ALL PRODUCT
Quintet<ProductObject, Short, Byte, UserObject, String> infos = new Quintet<>(similar, (short)getPage, (byte)4, user, fullUrl);
ArrayList<String> viewProduct;

// PRODUCT relate
ProductObject productRelate = null;

// check id
if(id <= 0) {
	viewProduct = pc.viewProduct(infos, so, false);
	pc.releaseConnection();
} else {
	productRelate = pc.getProduct(id);
	similar.setProduct_id(id);
	similar.setProduct_pc_id(productRelate.getProduct_pc_id());
	viewProduct = pc.viewProduct(infos, so, true);
	pc.releaseConnection();
}
if(viewProduct.size() > 0) {
	//gui cau truc hien thi vao phien lam viec
	session.setAttribute("viewTopFavorite", viewProduct.get(0));
	session.setAttribute("viewProductNew", viewProduct.get(1));
	session.setAttribute("viewProductSupperDiscount", viewProduct.get(2));
	session.setAttribute("viewSidebarSectionFilterProduct", viewProduct.get(3));
	session.setAttribute("viewSidebarSizeFilterProduct", viewProduct.get(4));
	session.setAttribute("viewSidebarColorFilterProduct", viewProduct.get(5));
	session.setAttribute("viewSidebarCategoryFilterProduct", viewProduct.get(6));
	session.setAttribute("viewFilterProductResult", viewProduct.get(7));
	session.setAttribute("viewSortProdduct", viewProduct.get(8));	
	session.setAttribute("viewDetailProduct", viewProduct.get(9));
	session.setAttribute("viewProductRelated", viewProduct.get(10));
}
if(user != null) {
	CartObject similarCartObject = new CartObject();
	//lay cau truc CART
	Pair<CartObject, UserObject> infosCartObject = new Pair<>(similarCartObject, user);
	CartControl cc = new CartControl(cp);
	if(cp==null) {
		application.setAttribute("CPool", pc.getCP());
	}
	ArrayList<String> viewCart;
	viewCart = cc.viewCart(infosCartObject);
	cc.releaseConnection();
	if(viewCart.size() > 0) {
		//gui cau truc hien thi vao phien lam viec
		session.setAttribute("viewCartList", viewCart.get(0));
		//gui cau truc hien thi vao phien lam viec
		session.setAttribute("viewDelItemCart", viewCart.get(1));
		//gui cau truc hien thi vao phien lam viec
		session.setAttribute("viewSumItemCart", viewCart.get(2));
		//gui cau truc hien thi vao phien lam viec
		session.setAttribute("viewpriceCart", viewCart.get(3));
	}
	
	
	// ORDER
	OrderObject similarOrderObject = new OrderObject();
	Pair<OrderObject, UserObject> infosOrder = new Pair<>(similarOrderObject, user);
	OrderControl oc = new OrderControl(cp);
	if(cp==null) {
		application.setAttribute("CPool", pc.getCP());
	}
	ArrayList<String> viewOrder = oc.viewOrder(infosOrder);
	oc.releaseConnection();
	if(viewOrder.size() > 0) {
		//gui cau truc hien thi vao phien lam viec
		session.setAttribute("viewFormOrder", viewOrder.get(0));
		//gui cau truc hien thi vao phien lam viec
		session.setAttribute("viewListOrder", viewOrder.get(1));
	}
}

%>