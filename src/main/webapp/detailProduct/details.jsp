<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
// lay cau truc hien thi trong phien lam viec
String viewDetailProduct = (String) session.getAttribute("viewDetailProduct");
if(viewDetailProduct != null && !viewDetailProduct.equalsIgnoreCase("")) {
	out.append(viewDetailProduct);	
}
%>