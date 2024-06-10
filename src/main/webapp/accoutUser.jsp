<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<jsp:include page="header.jsp" flush="true"></jsp:include>

<main id="main">

  <section class="portfolio">
      <div class="container">
          <div class="account-container row">

              <div class="col-lg-3">

                  <div class="nav-account border p-4 ">
                      <div class="account-avata">
                          <!-- <img src="./assets/img/avt/avt2.jpg" alt=""> -->
                      </div>

                      <ul class="nav nav-tabs d-flex flex-column" id="myTab" role="tablist">
                          <li class="mb-3">
                              <a href="#" class="active" id="account-edit-tab" data-bs-toggle="tab" data-bs-target="#account-edit" type="button" role="tab" aria-controls="account-edit" aria-selected="true"><i class="fa-solid fa-circle-dollar-to-slot me-2"></i> Tài khoản</a>
                          </li>
                          <li class="mb-3">
                              <a href="#" class="" id="order-tab" data-bs-toggle="tab" data-bs-target="#order" type="button" role="tab" aria-controls="order" aria-selected="false"><i class="fa-solid fa-bag-shopping me-2"></i> Đơn hàng</a>
                          </li>
                          <li class="mb-3">
                              <a href="user/logout" class=""><i class="fa-solid fa-right-from-bracket me-2"></i> Đăng xuất</a>
                          </li>
                      </ul>

                  </div>

              </div>

			<div class="tab-content col-lg-9">				
				<jsp:include page="/accoutUser/editAccout.jsp" flush="true"></jsp:include>
			
				<jsp:include page="/accoutUser/discoutAccout.jsp" flush="true"></jsp:include>
			
				<jsp:include page="/accoutUser/orderAccout.jsp" flush="true"></jsp:include>
			
				<jsp:include page="/accoutUser/productFavouriteAccout.jsp" flush="true"></jsp:include>
			
				<jsp:include page="/accoutUser/recentlyViewedAccout.jsp" flush="true"></jsp:include>
			</div>
		</div>
      </div>
  </section>
        
</main>
<!-- End #main -->

<jsp:include page="footer.jsp" flush="true"></jsp:include>