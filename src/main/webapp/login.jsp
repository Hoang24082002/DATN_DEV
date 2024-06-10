<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" flush="true"></jsp:include>

<main id="main">

   <section class="" id="">
   
<%
String message = request.getParameter("err");
if (message != null) {
	out.println("EROR");
%>
<div class="alert text-center text-danger alert-dismissible fade show" role="alert">
Đăng nhập thất bại, vui lòng kiểm tra thông tin tài khoản của bạn!
<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<%
}
%>

       <div class="container" data-aos="fade-up">
           <div class="row">
               <div class="col-xxl-6 offset-xxl-3"> <!-- offset-3: ko có nội dung <=> col-3: ko có nội dung ( đỡ phải tạo div col-3) -->
                   <div class="loginTitle text-bg-primary py-3 mt-5">
                       <h3 class="text-center fw-bold text-uppercase"><i class="fa-solid fa-user-tie space"></i>&nbsp;Đăng nhập</h3>
                   </div>
               </div>
           </div>
   
           <div class="row">
               <div class="col-xxl-6 offset-xxl-3">
                   <div class="loginForm text-bg-light py-2">
                       <form class="px-3" action="user/login" method="post">
                           <div class="row py-2">
                               <div class="col-sm-4 text-end" >Tên người dùng</div>
                               <div class="col-sm-6">
                                   <input type="text" class="form-control" name="txtusername" id="name" />
                               </div>
                           </div>
                           <div class="row py-2">
                               <div class="col-sm-4 text-end">Mật khẩu</div>
                               <div class="col-sm-6">
                                   <input type="password" class="form-control" name="txtuserpass" id="pass" />
                               </div>
                           </div>	
                           <div class="row py-3">
                               <div class="col-sm-12 text-center">
                                   <button type="submit" class="btn btn-primary fw-semibold" ><i class="fa-solid fa-right-to-bracket"></i>&nbsp;Đăng nhập</button>
                                   <button type="button" class="btn btn-secondary fw-semibold" ><i class="fa-regular fa-circle-xmark"></i>&nbsp;Thoát</button>
                               </div>
                           </div>
                           <div class="row py-3">
                               <div class="col-sm-11 text-end">
                                   <a href="register.jsp" class="text-decoration-none">Bạn chưa có tài khoản, hãy đăng ký</a>
                               </div>
                           </div>
                       </form>
                   </div>
               </div>
           </div>
       </div>
     

   </section>

</main>
<!-- End #main -->

<jsp:include page="footer.jsp" flush="true"></jsp:include>
