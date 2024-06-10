<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- ======= Our Clients Section sp moi ======= -->

<%
// lay cau truc hien thi trong phien lam viec
String viewProductNew = (String) session.getAttribute("viewProductNew");
%>

<section id="clients">
	<div class="container" data-aos="zoom-in">
		<div class="section-header">
			<h3>Sản phẩm mới</h3>
		</div>

		<div class="clients-slider swiper">
			<div class="swiper-wrapper align-items-center">
				<%
				if(viewProductNew != null && !viewProductNew.equalsIgnoreCase("")) {
					out.append(viewProductNew);	
				}
				%>
			</div>
			<div class="swiper-pagination"></div>
		</div>

	</div>
</section>
<!-- End Our Clients Section -->
