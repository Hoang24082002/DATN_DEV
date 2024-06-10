<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
	
  <!-- ======= Footer ======= -->
  <footer id="footer">
    <div class="footer-top">
      <div class="container">
        <div class="row">

          <div class="col-lg-3 col-md-6 footer-info">
            <h3>Việt-H</h3>
            <p>Tạo ra các sản phẩm du lịch phong phú, nhân văn cho mọi gia đình Việt.</p>
            <p>Trở thành Thương hiệu du lịch được yêu thích nhất của người Việt Nam và vươn ra tầm thế giới.</p>
          </div>

          <div class="col-lg-3 col-md-6 footer-links">
            <h4>Liên kết hữu ích</h4>
            <ul>
              <li><i class="bi bi-chevron-right"></i> <a href="#">Trang chủ</a></li>
              <li><i class="bi bi-chevron-right"></i> <a href="#">Giới thiệu</a></li>
              <li><i class="bi bi-chevron-right"></i> <a href="#">Dịch vụ</a></li>
              <li><i class="bi bi-chevron-right"></i> <a href="#">Điều khoản và dịch vụ</a></li>
              <li><i class="bi bi-chevron-right"></i> <a href="#">Chính sách bảo mật</a></li>
            </ul>
          </div>

          <div class="col-lg-3 col-md-6 footer-contact">
            <h4>Liên hệ</h4>
            <p>
              Phố Duy Tân <br>
              Hà Nội, Việt Nam <br>
              <strong>SĐT:</strong> +1 5589 55488 55<br>
              <strong>Email:</strong> info@example.com<br>
            </p>

            <div class="social-links">
              <a href="#" class="twitter"><i class="bi bi-twitter"></i></a>
              <a href="#" class="facebook"><i class="bi bi-facebook"></i></a>
              <a href="#" class="instagram"><i class="bi bi-instagram"></i></a>
              <a href="#" class="instagram"><i class="bi bi-instagram"></i></a>
              <a href="#" class="linkedin"><i class="bi bi-linkedin"></i></a>
            </div>

          </div>

          <div class="col-lg-3 col-md-6 footer-newsletter">
            <h4>Đăng ký nhận thông tin khuyến mãi</h4>
            <p>Nhập email để có cơ hội giảm 50% cho chuyến đi tiếp theo của Quý khách.</p>
            <form action="" method="post">
              <input type="email" name="email"><input type="submit" value="Subscribe">
            </form>
          </div>

        </div>
      </div>
    </div>

    <div class="container">
      <div class="copyright">
        &copy; Copyright <strong>Việt-H</strong>. Ghi rõ nguồn khi sử dụng thông tin từ website này
      </div>
      <div class="credits">
        Thiết kế bởi <a href="#">ĐVH</a>
      </div>
    </div>
  </footer><!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
      class="bi bi-arrow-up-short"></i></a>
  <!-- Uncomment below i you want to use a preloader -->
  <!-- <div id="preloader"></div> -->

  <!-- Vendor JS Files -->
  <script src="/datn/vendor/purecounter/purecounter_vanilla.js"></script>
  <script src="/datn/vendor/aos/aos.js"></script>
  <script src="/datn/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="/datn/vendor/glightbox/js/glightbox.min.js"></script>
  <script src="/datn/vendor/isotope-layout/isotope.pkgd.min.js"></script>
  <script src="/datn/vendor/swiper/swiper-bundle.min.js"></script>
  <script src="/datn/vendor/waypoints/noframework.waypoints.js"></script>
  <script src="/datn/vendor/apexcharts/apexcharts.min.js"></script>
  <script src="/datn/vendor/echarts/echarts.min.js"></script>

  <!-- Template Main JS File -->
  <script src="/datn/adjs/main.js"></script>

</body>

        <button type="button" class="btn btn-primary" id="liveToastBtn">Show live toast</button>

        <div class="toast-container position-fixed top-0 start-50 translate-middle-x p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <strong class="me-auto">Đặt hàng</strong>
                    <small>11 mins ago</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"><a href="index.jsp">Limtstore</a></button>
                </div>
                <div class="toast-body">
					  <%
					  String mess = request.getParameter("mess");
						  if(mess != null && !mess.equalsIgnoreCase("")) {
								if(mess.equalsIgnoreCase("orderdone")) {
					  %>
						    Đặt hàng thành công
					        <script>
					            const toastTrigger = document.getElementById('liveToastBtn')
					            const toastLiveExample = document.getElementById('liveToast')
					            if (toastTrigger) {
					                toastTrigger.addEventListener('click', () => {
					                    const toast = new bootstrap.Toast(toastLiveExample)
					
					                    toast.show()
					                })
					            }
					            document.addEventListener('DOMContentLoaded', function() {
					            var buttonElement = document.getElementById('liveToastBtn');
					            buttonElement.click();
					            });
					        </script>
					  <%
								}
						  }
					  %>
                </div>
            </div>
        </div>


</html>