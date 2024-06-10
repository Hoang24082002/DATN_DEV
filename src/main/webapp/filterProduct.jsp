<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" flush="true"></jsp:include>
<jsp:include page="/pageIndex/heroSlider.jsp" flush="true"></jsp:include>

<section>
    <div class="container row m-auto">
		<jsp:include page="/filterProduct/sidebar.jsp" flush="true"></jsp:include>	
		<jsp:include page="/filterProduct/filterResult.jsp" flush="true"></jsp:include>

    </div>
</section>


<jsp:include page="footer.jsp" flush="true"></jsp:include>