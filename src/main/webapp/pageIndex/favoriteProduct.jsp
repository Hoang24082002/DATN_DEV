<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jsoft.*,jsoft.objects.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.*, org.javatuples.*"%>
<%@ page import="jsoft.home.product.ProductControl"%>


<%
// lay cau truc hien thi trong phien lam viec
String viewTopFavorite = (String) session.getAttribute("viewTopFavorite");
%>
	
<!-- ======= Portfolio Section ======= -->
<section id="portfolio" class="portfolio sections-bg">
  <div class="container" data-aos="fade-up">

    <div class="section-header">
      <h3>Sản phẩm bán chạy</h3>
      <p>Top các sản phẩm được khách hàng mua nhiều nhất</p>
    </div>
		<%
		if(viewTopFavorite != null && !viewTopFavorite.equalsIgnoreCase("")) {
			out.append(viewTopFavorite);	
		}
		%>
  </div>
</section><!-- End Portfolio Section -->
