<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" flush="true"></jsp:include>
<jsp:include page="/pageIndex/heroSlider.jsp" flush="true"></jsp:include>

<main id="main">
		<jsp:include page="/detailProduct/details.jsp" flush="true"></jsp:include>	
		<jsp:include page="/detailProduct/productRelated.jsp" flush="true"></jsp:include>
</main><!-- End #main -->


<jsp:include page="footer.jsp" flush="true"></jsp:include>