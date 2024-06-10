<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" flush="true"></jsp:include>

<main id="main">
        <section class="" id="">

            <div class="container" data-aos="fade-up">
    
                <form action="user/register" method="post" class="p-5" id="formRegisAccount" >
                    <h2 class="text-uppercase fw-bold  text-center">
                        Đăng ký tài khoản của bạn
                    </h2>
                    <div class="">
                    	<label class="form-label" for="userName">Tên người dùng</label>
                        <input type="text" class="mb-3 form-control" id="userName" name="txtUserName" value="" placeholder="Nhập tên người dùng" required>
                        <label class="form-label" for="userFullName">Họ và tên</label>
                        <input type="text" class="mb-3 form-control" id="userFullName" name="txtUserFullName" value="" placeholder="Nhập họ và tên" required>
                        <label class="form-label" for="userBirthday">Ngày sinh</label>
                        <input type="date" class="mb-3 form-control" id="userBirthday" name="txtUserBirthday" value="" required>
                        <label class="form-label" for="userPhone">Số điện thoại</label>
                        <input type="text" class="mb-3 form-control" id="userPhone" name="txtUserPhone" value="" placeholder="Nhập số điện thoại" required>
                        <label class="form-label" for="userAddress">Địa chỉ</label>
                        <input type="text" class="mb-3 form-control" id="userAddress" name="txtUserAddress" value="" placeholder="Nhập địa chỉ" required>
                    </div>
                    <div class="">
                        <label class="form-label" for="userEmail">Địa chỉ email</label>
                        <input type="email" class="mb-3 form-control" id="userEmail" name="txtUserEmail" value="" placeholder="Nhập email" required>
                        <label class="form-label" for="userPass">Mật khẩu</label>
                        <input type="password" class="mb-3 form-control" id="userPass" name="txtUserPass" value="" placeholder="Nhập mật khẩu" required>   
                        <label class="form-label" for="userPassConf">Mật lại khẩu</label>
                        <input type="password" class="mb-3 form-control" id="userPassConf" name="txtUserPassConf" value="" placeholder="Nhập lại mật khẩu" required>                  
                    </div>
                    <div class="text-center py-3">
                        <button class="btn btn-primary" type="submit">Đăng ký</button>
                        <!-- <button class="btn btn-primary" type="button">Đăng ký</button> -->
                    </div>
                </form>
        
            </div>

        </section>


</main>
<!-- End #main -->

<jsp:include page="footer.jsp" flush="true"></jsp:include>