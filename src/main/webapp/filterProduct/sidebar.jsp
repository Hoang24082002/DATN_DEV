<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<aside id="sidebar" class="sidebar col-2">
    <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#components-nav-categories" data-bs-toggle="collapse" href="#">
                <span>Danh mục</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
<%
//lay cau truc hien thi trong phien lam viec
String viewSidebarSectionFilterProduct = (String) session.getAttribute("viewSidebarSectionFilterProduct");
if(viewSidebarSectionFilterProduct != null && !viewSidebarSectionFilterProduct.equalsIgnoreCase("")) {
	out.append(viewSidebarSectionFilterProduct);
}
%> 			
        </li><!-- End Components Nav categories -->

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#components-nav-size" data-bs-toggle="collapse" href="#">
                <span>Kích cỡ</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
<%
//lay cau truc hien thi trong phien lam viec
String viewSidebarSizeFilterProduct = (String) session.getAttribute("viewSidebarSizeFilterProduct");
if(viewSidebarSizeFilterProduct != null && !viewSidebarSizeFilterProduct.equalsIgnoreCase("")) {
	out.append(viewSidebarSizeFilterProduct);
}
%> 	
        </li><!-- End Components Nav size -->

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#components-nav-color" data-bs-toggle="collapse" href="#">
                <span>Màu sắc</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
<%
//lay cau truc hien thi trong phien lam viec
String viewSidebarColorFilterProduct = (String) session.getAttribute("viewSidebarColorFilterProduct");
if(viewSidebarColorFilterProduct != null && !viewSidebarColorFilterProduct.equalsIgnoreCase("")) {
	out.append(viewSidebarColorFilterProduct);
}
%> 	
        </li><!-- End Components Nav color -->

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#components-nav-price" data-bs-toggle="collapse" href="#">
                <span>Khoảng giá</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="components-nav-price" class="nav-content collapse ">
                <li>
                    <a href="charts-chartjs.html">
                        <i class="bi bi-circle"></i><span>Từ đến</span>
                    </a>
                </li>
                <li>
                    <a href="charts-apexcharts.html">
                        <i class="bi bi-circle"></i><span>Từ đến</span>
                    </a>
                </li>
                <li>
                    <a href="charts-echarts.html">
                        <i class="bi bi-circle"></i><span>Từ đến</span>
                    </a>
                </li>
            </ul>
        </li><!-- End Components Nav price -->

    </ul>
</aside><!-- End Sidebar-->