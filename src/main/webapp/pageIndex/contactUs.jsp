<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- ======= Contact Section ======= -->
<section id="contact" class="contact">
  <div class="container">

    <header class="section-header">
      <h3>Liên hệ</h3>
      <p>Nếu quý khách có thắc mắc hay đóng góp xin vui lòng điền vào Form dưới đây và gửi cho chúng tôi. Xin chân thành cảm ơn!</p>
    </header>

    <div class="row" data-aos="fade-up">
      <div class="col-lg-6">
        <div class="info-box mb-4">
          <i class="bi bi-geo-alt"></i>
          <h3>Địa chỉ</h3>
          <p>TP Hải Dương, Hải Dương, Việt Nam</p>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="info-box  mb-4">
          <i class="bi bi-envelope"></i>
          <h3>Email</h3>
          <p>contact@example.com</p>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="info-box  mb-4">
          <i class="bi bi-telephone-outbound"></i>
          <h3>Điện thoại</h3>
          <p>+84 123456789</p>
        </div>
      </div>

    </div>

    <div class="row" data-aos="fade-up">

      <div class="col-lg-6 ">
        <iframe class="mb-4 mb-lg-0" src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12097.433213460943!2d-74.0062269!3d40.7101282!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0xb89d1fe6bc499443!2sDowntown+Conference+Center!5e0!3m2!1smk!2sbg!4v1539943755621" frameborder="0" style="border:0; width: 100%; height: 384px;" allowfullscreen></iframe>
      </div>

      <div class="col-lg-6">
        <form action="forms/contact.php" method="post" role="form" class="php-email-form">
          <div class="row">
            <div class="col-md-6 form-group">
              <input type="text" name="name" class="form-control" id="name" placeholder="Nhập tên của bạn" required>
            </div>
            <div class="col-md-6 form-group mt-3 mt-md-0">
              <input type="email" class="form-control" name="email" id="email" placeholder="Nhập email của bạn" required>
            </div>
          </div>
          <div class="form-group mt-3">
            <input type="text" class="form-control" name="subject" id="subject" placeholder="Nhập tiêu đề" required>
          </div>
          <div class="form-group mt-3">
            <textarea class="form-control" name="message" rows="5" placeholder="Nhập nội dung" required></textarea>
          </div>
          <div class="my-3">
            <div class="loading">Loading</div>
            <div class="error-message"></div>
            <div class="sent-message">Tin nhắn của bạn đã được gửi. Cảm ơn!</div>
          </div>
          <div class="text-center"><button type="submit">Gửi</button></div>
        </form>
      </div>

    </div>

  </div>
</section><!-- End Contact Section -->
