<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- ======= Our Clients Section sp sieu GG ======= -->

<%
// lay cau truc hien thi trong phien lam viec
String viewProductSupperDiscount = (String) session.getAttribute("viewProductSupperDiscount");
%>

<section id="clients">
	<div class="container" data-aos="zoom-in">
		<div class="section-header">
			<h3>Siêu giảm giá</h3>
		</div>

		<div class="clients-slider swiper">
			<div class="swiper-wrapper align-items-center">
				<%
				if(viewProductSupperDiscount != null && !viewProductSupperDiscount.equalsIgnoreCase("")) {
					out.append(viewProductSupperDiscount);	
				}
				%>
			</div>
			<div class="swiper-pagination"></div>
		</div>

	</div>
</section>
<!-- End Our Clients Section -->
