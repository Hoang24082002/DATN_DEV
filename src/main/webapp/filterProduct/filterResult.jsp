<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="" class="col-10">
    <!-- ======= filterNewProductByGroupPoduct Section ======= -->
    <div id="filterNewProductByGroupPoduct" class="filterNewProductByGroupPoduct portfolio sections-bg">
        <div class="container" data-aos="fade-up">

            <!-- NAV LINK -->
<%
//lay cau truc hien thi trong phien lam viec
String viewSidebarCategoryFilterProduct = (String) session.getAttribute("viewSidebarCategoryFilterProduct");
if(viewSidebarCategoryFilterProduct != null && !viewSidebarCategoryFilterProduct.equalsIgnoreCase("")) {
	out.append(viewSidebarCategoryFilterProduct);
}
%> 

            <!-- FILTER -->
<%
//lay cau truc hien thi trong phien lam viec
String viewSortProdduct = (String) session.getAttribute("viewSortProdduct");
if(viewSortProdduct != null && !viewSortProdduct.equalsIgnoreCase("")) {
	out.append(viewSortProdduct);
}
%> 

            <!-- SESULT FILTER -->
            <div class="row gy-4 portfolio-container">

<%
//lay cau truc hien thi trong phien lam viec
String viewFilterProductResult = (String) session.getAttribute("viewFilterProductResult");
if(viewFilterProductResult != null && !viewFilterProductResult.equalsIgnoreCase("")) {
	out.append(viewFilterProductResult);
}
%> 

            </div>
        </div>
    </div><!-- End filterNewProductByGroupPoduct Section -->
</div><!-- End div col-lg-9 -->