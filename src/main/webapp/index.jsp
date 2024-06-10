<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" flush="true"></jsp:include>

<main id="main">
	<jsp:include page="/pageIndex/heroSlider.jsp" flush="true"></jsp:include>

	<jsp:include page="/pageIndex/favoriteProduct.jsp" flush="true"></jsp:include>

	<jsp:include page="/pageIndex/productNew.jsp" flush="true"></jsp:include>

	<jsp:include page="/pageIndex/productSuperDiscount.jsp" flush="true"></jsp:include>
	
	<jsp:include page="/pageIndex/contactUs.jsp" flush="true"></jsp:include>

</main>
<!-- End #main -->

<jsp:include page="footer.jsp" flush="true"></jsp:include>