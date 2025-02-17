package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.*;
import com.example.be_car_rental.Models.GiayPhepLaiXe;
import com.example.be_car_rental.Models.KhachHang;
import com.example.be_car_rental.Models.Quyen;
import com.example.be_car_rental.Models.TaiKhoan;
import com.example.be_car_rental.Repositories.GiayPhepLaiXeRepository;
import com.example.be_car_rental.Repositories.KhachHangRepository;
import com.example.be_car_rental.Repositories.QuyenRepository;
import com.example.be_car_rental.Repositories.TaiKhoanRepository;
import com.example.be_car_rental.Security.Jwt.JwtService;
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
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private QuyenRepository quyenRepository;

    @Autowired
    private GiayPhepLaiXeRepository giayPhepLaiXeRepository;

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

    public ApiResponse register(ThongTinDangKyTaiKhoanDto registerdto) {
        if (registerdto.getSoDienThoai() == null || registerdto.getSoDienThoai() == "") {
            return new ApiResponse(false, "Số điện thoại không hợp lệ", null);
        }
        if (registerdto.getEmail() == null || registerdto.getEmail() == "") {
            return new ApiResponse(false, "Email không hợp lệ", null);
        }
        if (registerdto.getMatKhau() == null || registerdto.getMatKhau() == "") {
            return new ApiResponse<>(false, "Mật khẩu không hợp lệ", null);
        }
        if (registerdto.getHoVaTen() == null) {
            return new ApiResponse<>(false, "Họ và tên không được để trống", null);
        }
        String otp = generateOTP();
        System.out.println(otp);
        TaiKhoan taikhoan = new TaiKhoan();
        KhachHang khachhang = new KhachHang();
        taikhoan.setSoDienThoai(registerdto.getSoDienThoai());
        taikhoan.setMatKhau(registerdto.getMatKhau());
        taikhoan.setOtp(otp);
        taikhoan.setThoiGianTaoMa(Timestamp.valueOf(LocalDateTime.now()));
        taikhoan.setThoiGianHetHan(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
        taikhoan.setTrangThai(false);
        taikhoan.setIdQuyen(3);
        khachhang.setSoDienThoai(registerdto.getSoDienThoai());
        khachhang.setEmail(registerdto.getEmail());
        khachhang.setHoVaTen(registerdto.getHoVaTen());
        khachhang.setNgaySinh(registerdto.getNgaySinh());
        try {
            taiKhoanRepository.save(taikhoan);
            khachHangRepository.save(khachhang);
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

    public ApiResponse verifyOtp(ThongTinDangKyTaiKhoanDto registerdto) {
        // Kiem tra tai khoan co ton tai khong
        if (!taiKhoanRepository.existsById(registerdto.getSoDienThoai())) {
            return new ApiResponse(false, "Tài khoản không đúng", null);
        }
        TaiKhoan taikhoan = taiKhoanRepository.findById(registerdto.getSoDienThoai()).get();
        // Kiem tra thoi gian ma xac nhan
        if (taikhoan.getThoiGianHetHan().toLocalDateTime().isBefore(LocalDateTime.now())) {
            return new ApiResponse<>(false, "Mã xác nhận đã hết hạn", null);
        }
        // So sanh ma otp trong database và ng dung nhap
        if (taikhoan.getOtp().equals(registerdto.getOtp())) {
            taikhoan.setTrangThai(true);
            taiKhoanRepository.save(taikhoan);
            return new ApiResponse<>(true, "Đăng ký thành công", null);
        }
        return new ApiResponse(false, "Đăng ký thất bại", null);
    }

    public ApiResponse login(LoginDto logindto) {
        if (logindto.getSoDienThoai() == null || logindto.getSoDienThoai() == "") {
            return new ApiResponse<>(false, "Tên đăng nhập không được bỏ trống", null);
        }
        if (logindto.getMatKhau() == null || logindto.getMatKhau() == "") {
            return new ApiResponse<>(false, "Mật khẩu không được bỏ trống", null);
        }
        if (!taiKhoanRepository.existsById(logindto.getSoDienThoai())) {
            return new ApiResponse(false, "Tên đăng nhập không tồn tại", null);
        }
        TaiKhoan taikhoan = taiKhoanRepository.findById(logindto.getSoDienThoai()).get();
        if (!taikhoan.getMatKhau().equals(logindto.getMatKhau())) {
            return new ApiResponse<>(false, "Sai mật khẩu", null);
        }

        UserResponse user = new UserResponse();
        if(khachHangRepository.existsById(logindto.getSoDienThoai())){
            KhachHang khachhang = khachHangRepository.findById(logindto.getSoDienThoai()).get();
            user.setKhachHang(khachhang);
        }
        if(giayPhepLaiXeRepository.existsBySoDienThoai(taikhoan.getSoDienThoai())){
            user.setGiayPhepLaiXe(true);
        }
        else {
            user.setGiayPhepLaiXe(false);
        }
        Quyen quyen = quyenRepository.findById(taikhoan.getIdQuyen()).get();
        user.setQuyen(quyen.getTenQuyen());
        String token = jwtService.createToken(new HashMap<>(), taikhoan.getSoDienThoai(), quyen.getTenQuyen());
        user.setToken(token);
        return new ApiResponse<>(true, "Đăng nhập thành công", user);
    }

    @Transactional
    public ApiResponse<GiayPhepLaiXe> verifyDrivingCard(GiayPhepLaiXeDto giayPhepLaiXeDto) {
        if (giayPhepLaiXeDto.getSoDienThoai() == null || giayPhepLaiXeDto.getSoDienThoai().isEmpty()) {
            return new ApiResponse<>(false, "Số điện thoại không được để trống", null);
        }
        if (giayPhepLaiXeDto.getHoVaTen() == null || giayPhepLaiXeDto.getHoVaTen().isEmpty()) {
            return new ApiResponse<>(false, "Họ và tên trong GPLX không được để trống", null);
        }
        if (giayPhepLaiXeDto.getSoGiayPhepLaiXe() == null || giayPhepLaiXeDto.getSoGiayPhepLaiXe().isEmpty()) {
            return new ApiResponse<>(false, "Số GPLX không được để rỗng", null);
        }
        if (giayPhepLaiXeDto.getNgaySinh() == null) {
            return new ApiResponse<>(false, "Ngày sinh không được để rỗng", null);
        }
        try {
            GiayPhepLaiXe giaypheplaixe = new GiayPhepLaiXe();
            giaypheplaixe.setSoGiayPhepLaiXe(giayPhepLaiXeDto.getSoGiayPhepLaiXe());
            giaypheplaixe.setSoDienThoai(giayPhepLaiXeDto.getSoDienThoai());
            giaypheplaixe.setHoVaTen(giayPhepLaiXeDto.getHoVaTen());
            giaypheplaixe.setNgaySinh(giayPhepLaiXeDto.getNgaySinh());
            giaypheplaixe.setTrangThai(Boolean.TRUE);
            giaypheplaixe = giayPhepLaiXeRepository.save(giaypheplaixe);
            giayPhepLaiXeRepository.updateTrangThaiGPLX(giaypheplaixe.getSoDienThoai(), giaypheplaixe.getIdGiayPhepLaiXe());
            return new ApiResponse<>(true, "Cập nhật thông tin gplx thành công", giaypheplaixe);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Cập nhật giấy phép lái xe thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<GiayPhepLaiXe> getThongTinGPLX(String sodienthoai) {
        try {
            System.out.println(sodienthoai);
            GiayPhepLaiXe giaypheplaixe = giayPhepLaiXeRepository.findBySoDienThoaiAndTrangThai(sodienthoai, true);
            if (giaypheplaixe == null) {
                return new ApiResponse<>(false, "Giấy phép lái xe không tồn tại.", null);
            }
            return new ApiResponse<>(true, "Lấy thông tin giấy phép lái xe thành công", giaypheplaixe);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thông tin giấy phép thất bại", null);
        }
    }
}