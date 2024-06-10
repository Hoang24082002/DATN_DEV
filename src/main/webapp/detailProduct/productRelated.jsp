<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!-- ======= Recommend Product ======= -->
<%
// lay cau truc hien thi trong phien lam viec
String viewProductRelated = (String) session.getAttribute("viewProductRelated");
if(viewProductRelated != null && !viewProductRelated.equalsIgnoreCase("")) {
	out.append(viewProductRelated);	
} else {
	out.append("NULL");	
}
%>
