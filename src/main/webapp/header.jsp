<%@page import="java.util.ArrayList"%>
<%@page import="jsoft.home.productGroup.*"%>
<%@page import="jsoft.home.productSection.*"%>
<%@page import="jsoft.home.productCategory.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@ page import="jsoft.*,jsoft.objects.*"%>

<jsp:include page="/data.jsp" flush="true"></jsp:include>

<%

// tìm thông tin đăng nhập
UserObject user = (UserObject) request.getSession().getAttribute("userLogined");

//tim bo quan ly ket noi
ConnectionPool cp = (ConnectionPool)application.getAttribute("CPool");

// danh sach nhom san pham
ProductGroupControl pgc = new ProductGroupControl(cp);
if(cp==null) {
	application.setAttribute("CPool", pgc.getCP());
}
ArrayList<ProductGroupObject> pgos = pgc.getProductGroupObjects();
pgc.releaseConnection();

//danh sach thanh phan san pham
ProductSectionControl psc = new ProductSectionControl(cp);
if(cp==null) {
	application.setAttribute("CPool", psc.getCP());
}
ArrayList<ProductSectionObject> psos = psc.getProductSectionObjects();
psc.releaseConnection();

//danh sach nhom san pham
ProductCategoryControl pcc = new ProductCategoryControl(cp);
if(cp==null) {
	application.setAttribute("CPool", pcc.getCP());
}
ArrayList<ProductCategoryObject> pcos = pcc.getProductCategoryObjects();
pcc.releaseConnection();

%>	
	
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Home</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <!-- <link href="assets/img/favicon.png" rel="icon">
  <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon"> -->

  <!-- Google Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Barlow:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Odibee+Sans&family=Raleway:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
		rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="/datn/vendor/animate.css/animate.min.css" rel="stylesheet">
  <link href="/datn/vendor/aos/aos.css" rel="stylesheet">
  <link href="/datn/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/datn/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="/datn/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="/datn/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link rel="stylesheet" href="/datn/adcss/all.min.css">
  <link href="/datn/adcss/variables.css" rel="stylesheet">
  <link href="/datn/adcss/style.css" rel="stylesheet">

</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="fixed-top d-flex align-items-center header-transparent">
    <div class="container-fluid position-relative">

      <div class="row justify-content-center align-items-center">
        <div class="col-xl-11 d-flex align-items-center justify-content-between">
          <div class="d-flex align-items-center justify-content-start">
            <!-- <a href="index.html" class="logo"><img src="assets/img/about.jpg" alt="" class="img-fluid"></a> -->
            <h1 class="logo"><a href="index.jsp">H-Shop</a></h1>
            <nav id="navbar" class="navbar">
              <ul class="">
                <li><a class="nav-link scrollto" href="#portfolio">Sản phẩm bán chạy</a></li>
					<%
					    for(ProductGroupObject pgo : pgos) {
					%>       
					<li class="dropdown"><a href="./filterProduct.jsp?pgid=<%= pgo.getPg_id()%>"><span><%= pgo.getPg_name() %></span> <i class="bi bi-chevron-down"></i></a>
					  <ul class="edit_dropdown">    		
					<%
					        for(ProductSectionObject pso : psos) {
					            if(pso.getPs_pg_id() == pgo.getPg_id()) {
					%>
					    <li class="dropdown"><a href="./filterProduct.jsp?pgid=<%= pgo.getPg_id()%>&psid=<%= pso.getPs_id()%>"><span><%= pso.getPs_name() %></span> <i class="bi bi-chevron-right"></i></a>
					      <ul>
					<%
					                for(ProductCategoryObject pco : pcos) {
					                    if(pco.getPc_pg_id() == pgo.getPg_id() && pco.getPc_ps_id() == pso.getPs_id()) {
					%>
					        <li><a href="./filterProduct.jsp?pgid=<%= pgo.getPg_id()%>&psid=<%= pso.getPs_id()%>&pcid=<%= pco.getPc_id()%>"><%= pco.getPc_name() %></a></li>
					<%
					                    }
					                }
					%>
					      </ul>
					    </li>
					<%
					            }
					        }
					%>
					  </ul>
					</li>
					<%
					    }
					%>                
                <li><a class="nav-link scrollto" href="#about">Giới thiệu</a></li>
                <!-- <li><a class="nav-link scrollto" href="#team">Đội ngũ phát triển</a></li>
                <li><a class="nav-link" href="blog.html">Tin tức</a></li>
                <li><a class="nav-link scrollto" href="#contact">Liên hệ</a></li> -->
                <form class="input-group" role="search" name="" id="">
                  <input onClick="" onBlur="" class="form-control" type="search" name="txtKeyword"  id="txtKeyword" aria-label="Search">
                  <button onClick="" type="button" class="btn btn-primary" id="btnSearch"><i class="fa-solid fa-magnifying-glass"></i></button>
                </form>
              </ul>
              <i class="bi bi-list mobile-nav-toggle"></i>
            </nav><!-- .navbar -->
          </div>
          <div class="navbar-group-icon d-flex justify-content-end text-dark">
            
              <% 
              	if(user == null ) {
              %>
            <div class="navbar-account ps-4 d-flex flex-column align-items-center">
              <!-- Sử lý USER -->
              <a class="d-flex text-dark" href="/datn/login.jsp"><i class="fa-regular fa-user"></i></a>
            </div>
              <%
              	} else {
              %>
            <div class="navbar-account ps-4 d-flex flex-column align-items-center">
              <!-- Sử lý USER -->
              <a class="d-flex text-dark" href="/datn/accoutUser.jsp"><%= user.getUser_name() %></a>
            </div>
              <%
              	}
      		  %>   
      		          
            
            <div class="navbar-cart ps-4 d-flex flex-column align-items-center position-relative" data-bs-toggle="collapse" href="#cart-shopping-show" role="button" aria-expanded="false" aria-controls="cart-shopping-show">
              <i class="fa-solid fa-cart-shopping"></i>
	              <%
	              if(user != null) {
            	  %>
	              	<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-light text-danger" >           	  
            	  <%
	            	// lay cau truc hien thi trong phien lam viec
	            	String viewSumItemCart = (String) session.getAttribute("viewSumItemCart");
	            	if(viewSumItemCart != null && !viewSumItemCart.equalsIgnoreCase("")) {
	            		out.append(viewSumItemCart);	
	            	}
	            	%>
		              </span>	            	
	            	<%
	              }
	              %>
            </div>

            <!-- Show cart  -->
            <div class="collapse position-absolute top-100 end-0 show-cart bg-white text-dark" id="cart-shopping-show">
              <% 
              	if(user == null ) {
              %>
              <!-- login  -->
			  <div class="card card-body">
			    <div class="cart-shopping-freeShip bg-white text-dark">
			      <a class="d-flex" href="/datn/login.jsp">Vui lòng đăng nhập</a>
                </div>
			  </div>
              <%
              	} else {
              %>
              <!-- cart  -->
       	   		<div class="card card-body">
	               <div class="cart-shopping-freeShip bg-white text-dark">
	                 <h4 class="cart-shopping-title m-0 py-2">Giỏ hàng</h4>
	               </div>
	               <!-- danh sach san pham trong gio hang -->
	               <div class="cart-shopping-list my-3">	                  
	       				<%
						// lay cau truc hien thi trong phien lam viec
						String viewCartList = (String) session.getAttribute("viewCartList");
						if(viewCartList != null && !viewCartList.equalsIgnoreCase("")) {
							out.append(viewCartList);	
						} else {
							out.append("");	
						}
						%>
	               </div>
	               <div class="cart-shopping-payment bg-white text-dark py-3">
	                 <div class="cart-shopping-payment-button d-flex justify-content-between flex-column">
	                   <div class="cart-shopping-payment-button-price">
	                   
	                     <span class="" >
	                    <%
						// lay cau truc hien thi trong phien lam viec
						String viewpriceCart = (String) session.getAttribute("viewpriceCart");
						if(viewpriceCart != null && !viewpriceCart.equalsIgnoreCase("")) {
							out.append(viewpriceCart);	
						} else {
							out.append("");	
						}
						%>
	                     <i class="fa-solid fa-chevron-down"></i></span>
	                   </div>
	                   <button type="button" class="btn btn-danger border border-0"><a class="text-white" href="./order.jsp">Đặt hàng</a></button>
	                 </div>
	               </div>
	             </div>      
              <%
              	}
      		  %>
      		           
            </div>

          </div>
        </div>
      </div>

    </div>
  </header><!-- End Header -->

  <!-- Modal delete item shopping cart -->
<%
// lay cau truc hien thi trong phien lam viec
String viewDelItemCart = (String) session.getAttribute("viewDelItemCart");
if(viewDelItemCart != null && !viewDelItemCart.equalsIgnoreCase("")) {
	out.append(viewDelItemCart);	
}
%>

  <!-- Modal discount code -->
  <div class="modal fade" id="discount-code" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Mã giảm giá</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
		  <div class="discount-code-list row d-flex flex-wrap justify-content-around">
			<div class="discount-code-item col-lg-12 row border border-1 mb-4">
				<div class="discount-code-item-content col-lg-8">
					<div class="discount-code-item-name">
						<span><strong>Mã GG 1</strong></span>                                        
					</div>
					<div class="discount-code-item-type">
						<span>Thể loại 1</span>
					</div>
					<div class="discount-code-item-dueDate">
						<span>HSD: xx-xx-xxx</span>
					</div>
				</div>

				<div class="discount-code-item-btn col-lg-4 d-flex align-items-center">
					<button type="button" class="btn btn-danger border border-0"><a class="text-white" href="#">Áp dụng</a></button>
				</div>
			</div>

			<div class="discount-code-item col-lg-12 row border border-1 mb-4">
				<div class="discount-code-item-content col-lg-8">

					<div class="discount-code-item-name">
						<span><strong>Mã GG 1</strong></span>                                        
					</div>
					<div class="discount-code-item-type">
						<span>Thể loại 1</span>
					</div>
					<div class="discount-code-item-dueDate">
						<span>HSD: xx-xx-xxx</span>
					</div>

				</div>

				<div class="discount-code-item-btn col-lg-4 d-flex align-items-center">
				  <button type="button" class="btn btn-danger border border-0"><a class="text-white" href="#">Áp dụng</a></button>
				</div>
			</div>

			<div class="discount-code-item col-lg-12 row border border-1 mb-4">
				<div class="discount-code-item-content col-lg-8">

					<div class="discount-code-item-name">
						<span><strong>Mã GG 1</strong></span>                                        
					</div>
					<div class="discount-code-item-type">
						<span>Thể loại 1</span>
					</div>
					<div class="discount-code-item-dueDate">
						<span>HSD: xx-xx-xxx</span>
					</div>

				</div>

				<div class="discount-code-item-btn col-lg-4 d-flex align-items-center">
				  <button type="button" class="btn btn-danger border border-0"><a class="text-white" href="#">Áp dụng</a></button>
				</div>
			</div>

			<div class="discount-code-item col-lg-12 row border border-1 mb-4">
				<div class="discount-code-item-content col-lg-8">

					<div class="discount-code-item-name">
						<span><strong>Mã GG 1</strong></span>                                        
					</div>
					<div class="discount-code-item-type">
						<span>Thể loại 1</span>
					</div>
					<div class="discount-code-item-dueDate">
						<span>HSD: xx-xx-xxx</span>
					</div>

				</div>

				<div class="discount-code-item-btn col-lg-4 d-flex align-items-center"">
				  <button type="button" class="btn btn-danger border border-0"><a class="text-white" href="#">Áp dụng</a></button>
				</div>
			</div>
		  </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Thoát</button>
        </div>
      </div>
    </div>
  </div>
