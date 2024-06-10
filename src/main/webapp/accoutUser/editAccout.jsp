<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jsoft.*,jsoft.objects.*"%>
<%
//tìm thông tin đăng nhập
UserObject user = (UserObject) request.getSession().getAttribute("userLogined");
%>

<!-- Edit accout -->
<div class="tab-pane active" id="account-edit" role="tabpanel" aria-labelledby="account-edit-tab" tabindex="0">
    <form action="user/profiles" method="post">
        
        <div class="local-infor-details border p-4 ">
            <div class="local-infor-heading mb-3">
                <i class="fa-solid fa-location-dot"></i> <strong>Thông tin tài khoản</strong>
            </div>
            <div class="mb-3">
                <label for="userName">Tên người dùng</label>
                <div class="">
                    <input type="text" class="form-control" name="userName" id="userName" value="<%= user.getUser_name() %>" disabled>
                </div>
            </div>
            <div class="mb-3">
                <label for="userFullName">Họ và tên</label>
                <div class="">
                    <input type="text" class="form-control" name=txtUserFullname id="userFullName" value="<%= user.getUser_fullname() %>">
                </div>
            </div>
            <div class="mb-3">
                <label for="userPhoneNumber">Số điện thoại</label>
                <div class="">
                    <input type="text" class="form-control" name="txtPhone" id="userPhoneNumber" value="<%= user.getUser_mobilephone() %>">
                </div>
            </div>
            <div class="mb-3">
                <label for="userEmail">Email</label>
                <div class="">
                    <input type="email" class="form-control" name="txtEmail" id="userEmail" value="<%= user.getUser_email() %>">
                </div>
            </div>
            <div class="mb-3">
                <label for="userBirthday">Sinh nhật</label>
                <div class="">
                    <input type="date" class="form-control" name="txtBirthday" id="userBirthday" value="<%= user.getUser_birthday() %>">
                </div>
            </div>
            <div class="mb-3">
                <label for="userAddress">Địa chỉ</label>
                <div class="">
                    <input type="text" class="form-control" name="txtAddress" id="userAddress" value="<%= user.getUser_address() %>">
                </div>
            </div>
            <div class="mb-3">
                <label for="notes">Chú thích</label>
                <div class="">
                    <textarea name="txtNotes" class="form-control" id="notes" style="height: 100px"><%= user.getUser_notes() %></textarea>
                </div>
            </div>
            <div class="mb-3 text-center">
            	<input type="hidden" name="idForPost" value="<%= user.getUser_id() %>" >
            	<input type="hidden" name="act" value="edit" >
            	<input type="hidden" name="page" value="home" >
                <button type="submit" class="btn btn-danger border border-0">Lưu thay đổi</button>
            </div>
        </div>

    </form>

</div>