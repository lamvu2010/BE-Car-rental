package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.*;
import com.example.be_car_rental.models.Giaypheplaixe;
import com.example.be_car_rental.models.Khachhang;
import com.example.be_car_rental.models.Quyen;
import com.example.be_car_rental.models.Taikhoan;
import com.example.be_car_rental.repositories.GiaypheplaixeRepository;
import com.example.be_car_rental.repositories.KhachhangRepository;
import com.example.be_car_rental.repositories.QuyenRepository;
import com.example.be_car_rental.repositories.TaikhoanRepository;
import com.example.be_car_rental.security.Jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class IdentityService {
    @Autowired
    private TaikhoanRepository taikhoanRepository;

    @Autowired
    private KhachhangRepository khachhangRepository;

    @Autowired
    private QuyenRepository quyenRepository;

    @Autowired
    private GiaypheplaixeRepository giaypheplaixeRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private SmsService smsService;

    @Autowired
    private JwtService jwtService;

    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6; // Độ dài OTP (có thể thay đổi)

    private final SecureRandom random = new SecureRandom();

    public String generateOTP() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            otp.append(CHARACTERS.charAt(index));
        }
        return otp.toString();
    }

    public ApiResponse register(Registerdto registerdto) {
        if (registerdto.getSodienthoai() == null || registerdto.getSodienthoai() == "") {
            return new ApiResponse(false, "Số điện thoại không hợp lệ", null);
        }
        if (registerdto.getEmail() == null || registerdto.getEmail() == "") {
            return new ApiResponse(false, "Email không hợp lệ", null);
        }
        if (registerdto.getMatkhau() == null || registerdto.getMatkhau() == "") {
            return new ApiResponse<>(false, "Mật khẩu không hợp lệ", null);
        }
        if (registerdto.getHovaten() == null) {
            return new ApiResponse<>(false, "Họ và tên không được để trống", null);
        }
        String otp = generateOTP();
        System.out.println(otp);
        Taikhoan taikhoan = new Taikhoan();
        Khachhang khachhang = new Khachhang();
        taikhoan.setSodienthoai(registerdto.getSodienthoai());
        taikhoan.setMatkhau(registerdto.getMatkhau());
        taikhoan.setOtp(otp);
        taikhoan.setThoigiantaoma(Timestamp.valueOf(LocalDateTime.now()));
        taikhoan.setThoigianhethan(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
        taikhoan.setTrangthai(false);
        taikhoan.setIdquyen(3);
        khachhang.setSodienthoai(registerdto.getSodienthoai());
        khachhang.setEmail(registerdto.getEmail());
        khachhang.setHovaten(registerdto.getHovaten());
        khachhang.setNgaysinh(registerdto.getNgaysinh());
        try {
            taikhoanRepository.save(taikhoan);
            khachhangRepository.save(khachhang);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Không thể lưu tài khoản và khách hàng", null);
        }
        try{
            smsService.sendOtp("",otp);
        }catch (Exception e){
            return new ApiResponse<>(false,"Không thể gửi mã otp", null);
        }
        return new ApiResponse<>(true, "Nhận thông tin thành công", null);
    }

    public ApiResponse verifyOtp(Registerdto registerdto) {
        // Kiem tra tai khoan co ton tai khong
        if (!taikhoanRepository.existsById(registerdto.getSodienthoai())) {
            return new ApiResponse(false, "Tài khoản không đúng", null);
        }
        Taikhoan taikhoan = taikhoanRepository.findById(registerdto.getSodienthoai()).get();
        // Kiem tra thoi gian ma xac nhan
        if (taikhoan.getThoigianhethan().toLocalDateTime().isBefore(LocalDateTime.now())) {
            return new ApiResponse<>(false, "Mã xác nhận đã hết hạn", null);
        }
        // So sanh ma otp trong database và ng dung nhap
        if (taikhoan.getOtp().equals(registerdto.getOtp())) {
            taikhoan.setTrangthai(true);
            taikhoanRepository.save(taikhoan);
            return new ApiResponse<>(true, "Đăng ký thành công", null);
        }
        return new ApiResponse(false, "Đăng ký thất bại", null);
    }

    public ApiResponse login(Logindto logindto) {
        if (logindto.getSdt() == null || logindto.getSdt() == "") {
            return new ApiResponse<>(false, "Tên đăng nhập không được bỏ trống", null);
        }
        if (logindto.getPassword() == null || logindto.getPassword() == "") {
            return new ApiResponse<>(false, "Mật khẩu không được bỏ trống", null);
        }
        if (!taikhoanRepository.existsById(logindto.getSdt())) {
            return new ApiResponse(false, "Tên đăng nhập không tồn tại", null);
        }
        Taikhoan taikhoan = taikhoanRepository.findById(logindto.getSdt()).get();
        if (!taikhoan.getMatkhau().equals(logindto.getPassword())) {
            return new ApiResponse<>(false, "Sai mật khẩu", null);
        }

        UserResponse user = new UserResponse();
        if(khachhangRepository.existsById(logindto.getSdt())){
            Khachhang khachhang = khachhangRepository.findById(logindto.getSdt()).get();
            user.setKhachhang(khachhang);
        }
        if(giaypheplaixeRepository.existsBySodienthoai(taikhoan.getSodienthoai())){
            user.setGplx(true);
        }
        else {
            user.setGplx(false);
        }
        Quyen quyen = quyenRepository.findById(taikhoan.getIdquyen()).get();
        user.setQuyen(quyen.getTenquyen());
        String token = jwtService.createToken(new HashMap<>(), taikhoan.getSodienthoai(), quyen.getTenquyen());
        user.setToken(token);
        return new ApiResponse<>(true, "Đăng nhập thành công", user);
    }

    @Transactional
    public ApiResponse<Giaypheplaixe> verifyDrivingCard(Gplxdto gplxdto) {
        if (gplxdto.getSodienthoai() == null || gplxdto.getSodienthoai().isEmpty()) {
            return new ApiResponse<>(false, "Số điện thoại không được để trống", null);
        }
        if (gplxdto.getHovaten() == null || gplxdto.getHovaten().isEmpty()) {
            return new ApiResponse<>(false, "Họ và tên trong GPLX không được để trống", null);
        }
        if (gplxdto.getSogplx() == null || gplxdto.getSogplx().isEmpty()) {
            return new ApiResponse<>(false, "Số GPLX không được để rỗng", null);
        }
        if (gplxdto.getNgaysinh() == null) {
            return new ApiResponse<>(false, "Ngày sinh không được để rỗng", null);
        }
        try {
            Giaypheplaixe giaypheplaixe = new Giaypheplaixe();
            giaypheplaixe.setSogplx(gplxdto.getSogplx());
            giaypheplaixe.setSodienthoai(gplxdto.getSodienthoai());
            giaypheplaixe.setHovaten(gplxdto.getHovaten());
            giaypheplaixe.setNgaysinh(gplxdto.getNgaysinh());
            giaypheplaixe.setTrangthai(Boolean.TRUE);
            giaypheplaixe = giaypheplaixeRepository.save(giaypheplaixe);
            giaypheplaixeRepository.updateTrangThaiGPLX(giaypheplaixe.getSodienthoai(), giaypheplaixe.getIdgiaypheplaixe());
            return new ApiResponse<>(true, "Cập nhật thông tin gplx thành công", giaypheplaixe);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Cập nhật giấy phép lái xe thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<Giaypheplaixe> getThongTinGPLX(String sodienthoai) {
        try {
            System.out.println(sodienthoai);
            Giaypheplaixe giaypheplaixe = giaypheplaixeRepository.findBySodienthoaiAndTrangthai(sodienthoai, true);
            if (giaypheplaixe == null) {
                return new ApiResponse<>(false, "Giấy phép lái xe không tồn tại.", null);
            }
            return new ApiResponse<>(true, "Lấy thông tin giấy phép lái xe thành công", giaypheplaixe);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thông tin giấy phép thất bại", null);
        }
    }
}