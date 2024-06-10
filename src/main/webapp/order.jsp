<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" flush="true"></jsp:include>

<main id="main">
    <section id="info-delivery" class="info-delivery">
        <div class="container">
            <div class="shipment-details">

                <div class="local-infor">

                    <div class="">

                        <%
						//lay cau truc hien thi trong phien lam viec
						String viewFormOrder = (String) session.getAttribute("viewFormOrder");
						if(viewFormOrder != null && !viewFormOrder.equalsIgnoreCase("")) {
							out.append(viewFormOrder);
						} else {
							out.append("Ko ton tai viewFormOrder");
						}
						%> 

                        <div class="list-product-in-cart col-lg-8 border p-4">
                            <div class="mb-3">
                                <i class="fa-solid fa-bag-shopping"></i> 
                                <strong>
                                	Sản phẩm 
									<%
					            	// lay cau truc hien thi trong phien lam viec
					            	String viewSumItemCart = (String) session.getAttribute("viewSumItemCart");
					            	if(viewSumItemCart != null && !viewSumItemCart.equalsIgnoreCase("")) {
					            		out.append(viewSumItemCart);	
					            	}
									%>
                                </strong>
                            </div>
			              <!-- cart  -->
			       	   		<div class="card card-body">
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
				             </div> 
                        </div>

                    </div>

                </div>

            </div>
        </div>
    </section>

</main><!-- End #main -->

<!-- ======= Footer ======= -->
<jsp:include page="footer.jsp" flush="true"></jsp:include>
 