<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Đơn hàng -->
<div class="tab-pane" id="order" role="tabpanel" aria-labelledby="order-tab" tabindex="0">  
    <div class="local-infor-details border p-4 ">
        <div class="local-infor-heading mb-3">
            <i class="fa-solid fa-location-dot"></i> <strong>Đơn hàng</strong>
        </div>
        <div class="shoping-cart-account-list">

	        <%
			//lay cau truc hien thi trong phien lam viec
			String viewListOrder = (String) session.getAttribute("viewListOrder");
			if(viewListOrder != null && !viewListOrder.equalsIgnoreCase("")) {
				out.append(viewListOrder);
			} else {
				out.append("Ko ton tai viewListOrder");
			}
			%> 

        </div>

        <!-- Paging -->
        <div class="card-body">
            <nav aria-label="...">
                <ul class="pagination justify-content-center">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1" aria-disabled="true">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item active" aria-current="page">
                        <a class="page-link" href="#">2</a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item">
                        <a class="page-link" href="#">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        
    </div>
</div>