package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.*;
import com.example.be_car_rental.Controller.MessageController;
import com.example.be_car_rental.Models.*;
import com.example.be_car_rental.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HopDongService {

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private XeRepository xeRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private BienBanGiaoRepository bienBanGiaoRepository;

    @Autowired
    private BienBanNhanRepository bienBanNhanRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private MessageController messageController;

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Autowired
    private DanhMucKiemTraRepository danhMucKiemTraRepository;

    @Autowired
    private ThongTinThanhToanRepository thongTinThanhToanRepository;

    @Autowired
    private ThongTinTuChoiRepository thongTinTuChoiRepository;

    @Autowired
    private ThongTinHuyCocRepository thongTinHuyCocRepository;

    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @Autowired
    private ThongBaoSuCoRepository thongBaoSuCoRepository;

    public ApiResponse<List<Map<?, ?>>> dsTatCaHopDong() {
        List<Map<?, ?>> list = new ArrayList<>();
        list = hopDongRepository.dsTatCaHopDong();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách hợp đồng thành công", list);
    }

    public ApiResponse<List<Map<?, ?>>> dsHopDongTheoSdt(String sdt) {
        List<Map<?, ?>> list = new ArrayList<>();
        list = hopDongRepository.dsHopDongTheoSdt(sdt);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách hợp đồng thành công", list);
    }

    public ApiResponse<Map<String, Object>> ctHopDong(int id) {
        if (!hopDongRepository.existsById(id)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        Map<String, Object> ct = new HashMap<>();
        ct = hopDongRepository.ctHopDong(id);
        if (ct.isEmpty()) {
            return new ApiResponse<>(false, "Lấy chi tiết thất bại", null);
        }
        return new ApiResponse<>(true, "Lấy chi tiết thành công", ct);
    }

    //Thêm biên ban giao
    @Transactional
    public ApiResponse<BienBanGiao> insertBienBanGiao(BienBanGiaoDto bienbangiaodto) {
        if (bienBanGiaoRepository.existsByIdHopDong(bienbangiaodto.getIdHopDong())) {
            return new ApiResponse<>(false, "Biên bản giao xe đã được thêm", null);
        }
        BienBanGiao bienbangiao = new BienBanGiao();
        bienbangiao.setIdHopDong(bienbangiaodto.getIdHopDong());
        bienbangiao.setNgayGiao(Timestamp.valueOf(LocalDateTime.now()));
        bienbangiao.setTinhTrangXe(bienbangiaodto.getTinhTrangXe());
        bienbangiao.setSoKmHienTai(bienbangiaodto.getSoKmHienTai());
        bienbangiao.setDiaDiemGiao(bienbangiaodto.getDiaDiemGiao());
        bienbangiao.setPhanTramXang(bienbangiaodto.getPhanTramXang());
        bienbangiao.setXangTheoLit(bienbangiaodto.getXangTheoLit());
        bienbangiao.setPhuKienKemTheo(bienbangiaodto.getPhuKienKemTheo());
        bienbangiao.setNguoiGiaoXe(bienbangiaodto.getNguoiGiaoXe());

        HopDong hopdong = hopDongRepository.findById(bienbangiaodto.getIdHopDong()).get();
        hopdong.setTrangThai("dagiaoxe");
        try {
            bienbangiao = bienBanGiaoRepository.save(bienbangiao);
            hopdong = hopDongRepository.save(hopdong);

            return new ApiResponse<>(true, "Thêm thành công", bienbangiao);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<BienBanNhan> insertBienBanNhan(BienBanNhanDto bienbannhandto) {
        if (bienBanNhanRepository.existsByIdHopDong(bienbannhandto.getIdHopDong())) {
            return new ApiResponse<>(false, "Biên bản nhận đã được thêm", null);
        }
        BienBanNhan bienbannhan = new BienBanNhan();
        bienbannhan.setIdHopDong(bienbannhandto.getIdHopDong());
        bienbannhan.setNgayNhan(bienbannhandto.getNgayNhan());
        bienbannhan.setSoKmHienTai(bienbannhandto.getSoKmHienTai());
        bienbannhan.setPhanTramXang(bienbannhandto.getPhanTramXang());
        bienbannhan.setXangTheoLit(bienbannhandto.getXangTheoLit());
        bienbannhan.setNguoiNhanXe(bienbannhandto.getNguoiNhanXe());
        bienbannhan.setDiaDiemNhan(bienbannhandto.getDiaDiemNhan());
        HopDong hopdong = hopDongRepository.findById(bienbannhandto.getIdHopDong()).get();
        hopdong.setTrangThai("danhanxe");
        Xe xe = xeRepository.findById(hopdong.getIdXe()).get();
        xe.setTrangThai("cothethue");
        try {
            bienbannhan = bienBanNhanRepository.save(bienbannhan);
            hopdong = hopDongRepository.save(hopdong);
            xe = xeRepository.save(xe);
            if (!bienbannhandto.getDanhMuc().isEmpty()) {
                for (Map<String, Object> item : bienbannhandto.getDanhMuc()) {
                    DanhMucKiemTra danhmuckiemtra = new DanhMucKiemTra();
                    danhmuckiemtra.setMucKiemTra((String) item.get("item"));
                    danhmuckiemtra.setTrangThai((String) item.get("pass"));
                    danhmuckiemtra.setGhiChu((String) item.get("note"));
                    danhmuckiemtra.setChiPhiPhatSinh(BigDecimal.valueOf((Integer) item.get("cost")));
                    danhmuckiemtra.setIdBienBanNhan(bienbannhan.getIdBienBanNhan());
                    danhMucKiemTraRepository.save(danhmuckiemtra);
                }
            }
            return new ApiResponse<>(true, "Thêm biên bản nhận và danh mục thành công", bienbannhan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    public ApiResponse<HoaDon> insertHoaDon(HoaDonDto hoadondto) {
        HoaDon hoadon = new HoaDon();
        hoadon.setIdHopDong(hoadondto.getIdHopDong());
        hoadon.setTienThue(hoadondto.getTienThue());
        hoadon.setTienDaCoc(hoadondto.getTienDatCoc());
        hoadon.setTienConLai(hoadondto.getTienConLai());
        hoadon.setTienPhatSinh(hoadondto.getTienPhatSinh());
        hoadon.setTongTien(hoadondto.getTongTien());
        try {
            hoadon = hoaDonRepository.save(hoadon);
            return new ApiResponse<>(true, "Thêm thành công", hoadon);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    public ApiResponse applyDieuKhoanHopDong(int id) {
        try {
            hopDongRepository.applyDieuKhoanHopDong(id);
            return new ApiResponse<>(true, "Áp dụng điều khoản vào hợp đồng thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Áp dụng điều khoản thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<HopDong> dangKyThueXe(HopDongDto hopdongdto) {
        // Kiểm tra các thông tin đầu vào
        if (hopdongdto.getIdXe() == 0) {
            return new ApiResponse<>(false, "Id xe không được trống", null);
        }
        if (hopdongdto.getTienDatCoc() == null || hopdongdto.getTienDatCoc().compareTo(BigDecimal.ZERO) <= 0) {
            return new ApiResponse<>(false, "Tiền đặt cọc không hợp lệ", null);
        }
        if (hopdongdto.getGiaThueTong() == null || hopdongdto.getGiaThueTong().compareTo(BigDecimal.ZERO) <= 0) {
            return new ApiResponse<>(false, "Giá thuê tổng không hợp lệ", null);
        }
        if (hopdongdto.getSoDienThoai() == null || hopdongdto.getSoDienThoai().isEmpty()) {
            return new ApiResponse<>(false, "Số điện thoại không được trống", null);
        }
        if (hopdongdto.getThoiGianBatDau() == null) {
            return new ApiResponse<>(false, "Thời gian bắt đầu không hợp lệ", null);
        }
        if (hopdongdto.getThoiGianKetThuc() == null || hopdongdto.getThoiGianKetThuc().before(hopdongdto.getThoiGianBatDau())) {
            return new ApiResponse<>(false, "Thời gian kết thúc phải sau thời gian bắt đầu", null);
        }
        if (hopdongdto.getGiaThueMotNgay() == null || hopdongdto.getGiaThueMotNgay().compareTo(BigDecimal.ZERO) <= 0) {
            return new ApiResponse<>(false, "Giá thuê mỗi ngày không hợp lệ", null);
        }
        if (hopdongdto.getSoNgayThue() <= 0) {
            return new ApiResponse<>(false, "Số ngày thuê phải lớn hơn 0", null);
        }
        if (hopdongdto.getNgayTao() == null) {
            return new ApiResponse<>(false, "Ngày tạo không được trống", null);
        }
        if (hopdongdto.getPhuTroiQuangDuong() == null || hopdongdto.getPhuTroiQuangDuong().compareTo(BigDecimal.ZERO) < 0) {
            return new ApiResponse<>(false, "Phụ trội quãng đường không hợp lệ", null);
        }
        if (hopdongdto.getSoKmGioiHan() < 0) {
            return new ApiResponse<>(false, "Số km giới hạn không hợp lệ", null);
        }
        if (hopdongdto.getPhuTroiThoiGian() == null || hopdongdto.getPhuTroiThoiGian().compareTo(BigDecimal.ZERO) < 0) {
            return new ApiResponse<>(false, "Phụ trội thời gian không hợp lệ", null);
        }
        if (hopdongdto.getSoGioThue() < 0) {
            return new ApiResponse<>(false, "Số giờ thuê không hợp lệ", null);
        }

        try {
            // Tạo mới hợp đồng
            HopDong hopdong = new HopDong();
            hopdong.setTienDatCoc(hopdongdto.getTienDatCoc());
            hopdong.setGiaThueTong(hopdongdto.getGiaThueTong());
            hopdong.setSoDienThoai(hopdongdto.getSoDienThoai());
            hopdong.setIdXe(hopdongdto.getIdXe());
            hopdong.setThoiGianBatDau(hopdongdto.getThoiGianBatDau());
            hopdong.setThoiGianKetThuc(hopdongdto.getThoiGianKetThuc());
            hopdong.setGiaThueMotNgay(hopdongdto.getGiaThueMotNgay());
            hopdong.setSoNgayThue(hopdongdto.getSoNgayThue());
            hopdong.setNgayTao(hopdongdto.getNgayTao());
            hopdong.setPhuTroiQuangDuong(hopdongdto.getPhuTroiQuangDuong());
            hopdong.setSoKmGioiHan(hopdongdto.getSoKmGioiHan());
            hopdong.setPhuTroiThoiGian(hopdongdto.getPhuTroiThoiGian());
            hopdong.setGioiHanPhiTheoGio(hopdongdto.getGioiHanPhiTheoGio());
            hopdong.setSoGioThue(hopdongdto.getSoGioThue());
            hopdong.setTrangThai("dangky"); // Trang thái khi tạo mới là "dang ky"
            hopdong.setPhiVeSinh(hopdongdto.getPhiVeSinh());
            hopdong.setPhiKhuMui(hopdongdto.getPhiKhuiMui());
            hopdong.setSoKmGioiHanChuyenDi(hopdongdto.getSoKmGioiHanChuyenDi());
            // Lưu hợp đồng vào cơ sở dữ liệu
            hopdong = hopDongRepository.save(hopdong);

            Xe xe = xeRepository.findById(hopdong.getIdXe()).get();
            xe.setTrangThai("dangduocthue");
            xeRepository.save(xe);

            // Tạo thông báo
            ThongBao thongbao = new ThongBao();
            thongbao.setIdNoiDung(hopdong.getIdHopDong());
            thongbao.setLoaiThongBao("dangky");
            thongbao.setNoiDung("Người dùng " + hopdong.getSoDienThoai() + " vừa đăng ký thuê xe, vui lòng duyệt đăng ký.");
            thongbao.setSoDienThoai("nhanvien");
            thongbao.setTrangThai("chuaxem");
            thongbao.setThoiGian(Timestamp.valueOf(LocalDateTime.now()));
            thongbao = thongBaoRepository.save(thongbao);

            // Gửi thông báo cho quản lý
            messageController.send("manager", thongbao);

            // Trả về kết quả thành công
            return new ApiResponse<>(true, "Đăng ký thành công", hopdong);

        } catch (Exception e) {
            // Ghi lỗi nếu có sự cố và trả về phản hồi thất bại
            return new ApiResponse<>(false, "Xảy ra lỗi trong quá trình thêm hợp đồng: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<String, Object>>> ds_hopdong_trangthai(String trangthai) {
        List<Map<String, Object>> list = new ArrayList<>();
        list = hopDongRepository.ds_hopdong_trangthai(trangthai);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", list);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse<List<Map<String, Object>>> ds_hopdong_sdt_trangthai(String trangthai, String sdt) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (trangthai.equals("dangky")) {
            list = hopDongRepository.ds_hopdong_sdt_trangthai(trangthai, sdt);
        }
        if (!trangthai.equals("dangky")) {
            list = hopDongRepository.ds_hopdong_user_daduyet(sdt);
        }
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", list);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse<HopDong> duyetHopDong(int idhopdong) {
        if (!hopDongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        try {
            HopDong hopdong = hopDongRepository.findById(idhopdong).get();
            hopdong.setTrangThai("duyet");
            hopdong.setThoiGianDuyet(Timestamp.valueOf(LocalDateTime.now()));
            hopdong = hopDongRepository.save(hopdong);
            ThongBao thongbao = new ThongBao();
            thongbao.setLoaiThongBao("yeucauthanhtoan");
            thongbao.setSoDienThoai(hopdong.getSoDienThoai());
            thongbao.setNoiDung("Hợp đồng của bạn đã được duyệt, vui lòng thanh toán tiền cọc để hợp đồng bắt đầu có hiệu lực.");
            thongbao.setIdNoiDung(hopdong.getIdHopDong());
            thongbao.setTrangThai("chuaxem");
            thongbao.setThoiGian(Timestamp.valueOf(LocalDateTime.now()));
            thongBaoRepository.save(thongbao);
            messageController.send(hopdong.getSoDienThoai(), thongbao);
            return new ApiResponse<>(true, "Duyệt hợp đồng thành công", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi trong quá trình duyệt", null);
        }
    }

    @Transactional
    public ApiResponse<HopDong> tuChoiHopDong(int idhopdong, String lido) {
        if (!hopDongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        try {
            HopDong hopdong = hopDongRepository.findById(idhopdong).get();
            hopdong.setTrangThai("tuchoi");
            hopdong = hopDongRepository.save(hopdong);
            Xe xe = xeRepository.findById(hopdong.getIdXe()).get();
            xe.setTrangThai("cothethue");
            xeRepository.save(xe);
            ThongTinTuChoi thongtintuchoi = new ThongTinTuChoi();
            thongtintuchoi.setLiDoTuChoi(lido);
            thongtintuchoi.setThoiGianTuChoi(Timestamp.valueOf(LocalDateTime.now()));
            thongtintuchoi.setIdHopDong(hopdong.getIdHopDong());
            thongTinTuChoiRepository.save(thongtintuchoi);
            ThongBao thongbao = new ThongBao();
            thongbao.setLoaiThongBao("tuchoihopdong");
            thongbao.setSoDienThoai(hopdong.getSoDienThoai());
            thongbao.setNoiDung("Hợp đồng của bạn đã bị từ chối với lí do: " + lido);
            thongbao.setIdNoiDung(hopdong.getIdHopDong());
            thongbao.setTrangThai("chuaxem");
            thongbao.setThoiGian(Timestamp.valueOf(LocalDateTime.now()));
            thongBaoRepository.save(thongbao);
            messageController.send(hopdong.getSoDienThoai(), thongbao);
            return new ApiResponse<>(true, "Từ chối hợp đồng thành công", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi trong quá trình từ chối", null);
        }
    }

    @Transactional
    public void capNhatHopDongHetHan() {
        try {
            List<Map<String, Object>> ds_hopdong_hethan_datcoc = hopDongRepository.ds_hopdong_hethan_datcoc();
            if (ds_hopdong_hethan_datcoc == null || ds_hopdong_hethan_datcoc.isEmpty()) {
                System.out.println("Không có hợp đồng hết hạn.");
                return;
            }
            for (Map<String, Object> item : ds_hopdong_hethan_datcoc) {
                try {
                    ThongBao thongbao = new ThongBao();
                    thongbao.setIdNoiDung((Integer) item.get("idhopdong"));
                    thongbao.setNoiDung("Hợp đồng mã " + item.get("idhopdong") + " của bạn đã bị huỷ do quá thời gian đặt cọc.");
                    thongbao.setLoaiThongBao("hopdong");
                    thongbao.setSoDienThoai((String) item.get("sodienthoai"));
                    thongbao.setTrangThai("chuaxem");
                    thongbao.setThoiGian(Timestamp.valueOf(LocalDateTime.now()));
                    thongbao = thongBaoRepository.save(thongbao);

                    // Gửi thông báo
                    messageController.send(thongbao.getSoDienThoai(), thongbao);
                } catch (Exception e) {
                    e.printStackTrace(); // Ghi log lỗi cho từng thông báo
                }
            }
            hopDongRepository.capNhatHopDongHetHanCoc();
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log để kiểm tra lỗi
            throw new RuntimeException("Đã xảy ra lỗi khi cập nhật hợp đồng hết hạn.", e);
        }
    }

    @Transactional
    public ApiResponse<HopDong> huyHopDongChuaCoc(int idhopdong) {
        if (!hopDongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        try {
            HopDong hopdong = hopDongRepository.findById(idhopdong).get();
            if (hopdong.getTrangThai().equals("duyet")) {
                hopdong.setGhiChu("Hợp đồng huỷ lúc đã duyệt hợp đồng.");
            }
            if (hopdong.getTrangThai().equals("dangky")) {
                hopdong.setGhiChu("Hợp đồng huỷ lúc đã chưa duyệt hợp đồng.");
            }
            hopdong.setTrangThai("huy");
            hopdong.setThoiGianHuy(Timestamp.valueOf(LocalDateTime.now()));
            hopdong = hopDongRepository.save(hopdong);
            Xe xe = xeRepository.findById(hopdong.getIdXe()).get();
            xe.setTrangThai("cothethue");
            xeRepository.save(xe);
            return new ApiResponse<>(true, "Huỷ đăng ký hợp đồng thành công", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi trong quá trình huỷ đăng ký", null);
        }
    }

    @Transactional
    public ApiResponse<HopDong> huyHopDongDaCoc(int idhopdong, String lido, String phuongthuc, String nganhang, String sotaikhoan) {
        if (!hopDongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        HopDong hopdong = hopDongRepository.findById(idhopdong).get();
        ThongTinHuyCoc thongtinhuycoc = new ThongTinHuyCoc();
        thongtinhuycoc.setIdHopDong(hopdong.getIdHopDong());
        thongtinhuycoc.setLiDoHuyCoc(lido);
        thongtinhuycoc.setThoiGianHuyCoc(Timestamp.valueOf(LocalDateTime.now()));
        if(phuongthuc.equals("tienmat")){
            thongtinhuycoc.setPhuongThuc("tienmat");
            thongtinhuycoc.setNganHang("");
            thongtinhuycoc.setSoTaiKhoan("");
        }
        if(phuongthuc.equals("chuyenkhoan")){
            thongtinhuycoc.setPhuongThuc("chuyenkhoan");
            thongtinhuycoc.setNganHang(nganhang);
            thongtinhuycoc.setSoTaiKhoan(sotaikhoan);
        }
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        // Thời gian bắt đầu chuyến đi
        LocalDateTime thoigianbatdau = hopdong.getThoiGianBatDau().toLocalDateTime();
        // Thời gian đặt cọc
        LocalDateTime thoigiandatcoc = hopdong.getNgayTao().toLocalDateTime();
        // Số tiền đặt cọc
        BigDecimal tienDatCoc = hopdong.getTienDatCoc();
        // Biến lưu kết quả
        BigDecimal tienHoanTra = BigDecimal.ZERO;
        if (now.isBefore(thoigiandatcoc.plusHours(1))) {
            // Trong vòng 1 giờ sau khi đặt cọc
            tienHoanTra = tienDatCoc; // 100% tiền cọc
            hopdong.setTrangThai("chuahoantien");
            thongtinhuycoc.setTrangThaiHoanTien("chuahoantien");
        } else if (now.isBefore(thoigianbatdau.minusDays(7))) {
            // Hơn 7 ngày trước khởi hành
            tienHoanTra = tienDatCoc.multiply(BigDecimal.valueOf(0.7)); // 70% tiền cọc
            hopdong.setTrangThai("chuahoantien");
            thongtinhuycoc.setTrangThaiHoanTien("chuahoantien");
        } else {
            // 7 ngày hoặc ít hơn trước chuyến đi
            tienHoanTra = BigDecimal.ZERO; // Không hoàn tiền
            hopdong.setTrangThai("dahoantien");
            thongtinhuycoc.setTrangThaiHoanTien("dahoantien");
        }
        thongtinhuycoc.setSoTienHoanLai(tienHoanTra);
        // Cap nhat trang thai xe
        Xe xe = xeRepository.findById(hopdong.getIdXe()).get();
        xe.setTrangThai("cothethue");
        try {
            xeRepository.save(xe);
            hopDongRepository.save(hopdong);
            thongTinHuyCocRepository.save(thongtinhuycoc);
            return new ApiResponse<>(true, "Huỷ cọc thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Huỷ cọc thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<List<ThongTinThanhToan>> getThongtinthanhtoan(int idhopdong) {
        List<ThongTinThanhToan> list = thongTinThanhToanRepository.findByIdHopDong(idhopdong);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Chưa có thông tin thanh toán", list);
        }
        return new ApiResponse<>(true, "Lấy thông tin thanh toán thành công", list);
    }

    @Transactional
    public ApiResponse capNhatHoanTien(int idhopdong) {
        if (!hopDongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        HopDong hopdong = hopDongRepository.findById(idhopdong).get();
        hopdong.setTrangThai("dahoantien");
        hopdong.setThoiGianHoanTien(Timestamp.valueOf(LocalDateTime.now()));

        ThongTinHuyCoc thongtinhuycoc = thongTinHuyCocRepository.findByIdHopDong(hopdong.getIdHopDong());
        thongtinhuycoc.setTrangThaiHoanTien("dahoantien");
        try {
            hopDongRepository.save(hopdong);
            thongTinHuyCocRepository.save(thongtinhuycoc);
            return new ApiResponse<>(true, "Cập nhật trạng thái hoàn tiền thành công.", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Cập nhật trạng thái hoàn tiền thất bại", null);
        }
    }

    @Transactional
    public ApiResponse xoaHopDongHetHan(String sodienthoai) {
        try {
            hopDongRepository.xoaHopDongHetHan(sodienthoai);
            return new ApiResponse<>(true, "Xoá thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Xoá thất bại", null);
        }
    }

    @Transactional
    public ApiResponse xoaHopDongTuChoi(String sodienthoai) {
        try {
            hopDongRepository.xoaHopDongTuChoi(sodienthoai);
            return new ApiResponse<>(true, "Xoá thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Xoá thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<BienBanGiao> getBienBanGiao(int idhopdong) {
        if (!bienBanGiaoRepository.existsByIdHopDong(idhopdong)) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        BienBanGiao bienbangiao = new BienBanGiao();
        try {
            bienbangiao = bienBanGiaoRepository.findByIdHopDong(idhopdong);
            return new ApiResponse<>(true, "Lấy biên bản giao thành công", bienbangiao);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy thông tin", null);
        }
    }

    @Transactional
    public ApiResponse insertDanhGia(DanhGiaDto danhgiadto) {
        if (!khachHangRepository.existsById(danhgiadto.getSoDienThoai())) {
            return new ApiResponse<>(false, "Khách hàng không tồn tại", null);
        }
        if (!xeRepository.existsById(danhgiadto.getIdXe())) {
            return new ApiResponse<>(false, "Xe không tồn tại", null);
        }
        if (!hopDongRepository.existsById(danhgiadto.getIdHopDong())) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        DanhGia danhgia = new DanhGia();
        danhgia.setIdXe(danhgiadto.getIdXe());
        danhgia.setSoDienThoai(danhgiadto.getSoDienThoai());
        danhgia.setNoiDungDanhGia(danhgiadto.getDanhGia());
        danhgia.setSao(danhgiadto.getSao());
        HopDong hopdong = hopDongRepository.findById(danhgiadto.getIdHopDong()).get();
        hopdong.setThoiGianDanhGia(Timestamp.valueOf(LocalDateTime.now()));
        try {
            danhgia = danhGiaRepository.save(danhgia);
            hopDongRepository.save(hopdong);
            return new ApiResponse<>(true, "Cập nhật đánh giá hợp đồng thành công", danhgia);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lưu đánh giá thất bại", null);
        }
    }

    @Transactional
    public ApiResponse themThongBaoSuCo(ThongBaoSuCoDto thongbaosucodto) {
        if (!hopDongRepository.existsById(thongbaosucodto.getIdhopdong())) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        ThongBaoSuCo thongbaosuco = new ThongBaoSuCo();
        thongbaosuco.setIdHopDong(thongbaosucodto.getIdhopdong());
        thongbaosuco.setViTri(thongbaosucodto.getVitri());
        thongbaosuco.setMoTa(thongbaosucodto.getMota());
        thongbaosuco.setLienLac(thongbaosucodto.getLienlac());
        try {
            thongbaosuco = thongBaoSuCoRepository.save(thongbaosuco);
            ThongBao thongbao = new ThongBao();
            thongbao.setLoaiThongBao("thongbaosuco");
            thongbao.setSoDienThoai("nhanvien");
            thongbao.setNoiDung("Thông báo sự cố hợp đồng " + thongbaosuco.getIdHopDong());
            thongbao.setIdNoiDung(thongbaosuco.getIdThongBaoSuCo());
            thongbao.setTrangThai("chuaxem");
            thongbao.setThoiGian(Timestamp.valueOf(LocalDateTime.now()));
            thongBaoRepository.save(thongbao);
            messageController.send("manager", thongbao);
            return new ApiResponse<>(true, "Gửi thông báo thành công", thongbaosuco);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi gửi thông báo", null);
        }
    }

    @Transactional
    public ApiResponse<ThongBaoSuCo> layThongTinSuCo(int idthongbaosuco) {
        if (!thongBaoSuCoRepository.existsById(idthongbaosuco)) {
            return new ApiResponse<>(false, "Id thông báo sự cố không tồn tại", null);
        }
        try {
            ThongBaoSuCo thongbaosuco = thongBaoSuCoRepository.findById(idthongbaosuco).get();
            return new ApiResponse<>(true, "Lấy thông tin sự cố thành công", thongbaosuco);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy thông tin sự cố", null);
        }
    }

    @Transactional
    public ApiResponse<List<DanhMucKiemTra>> layDsDanhMuc(int idbienbannhan) {
        List<DanhMucKiemTra> list = new ArrayList<>();
        try {
            list = danhMucKiemTraRepository.findByIdBienBanNhan(idbienbannhan);
            return new ApiResponse<>(true, "Lấy danh mục thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy danh sách thất bại", null);
        }
    }

    @Transactional
    public ApiResponse capnhatThanhToanHoanTat(int idhopdong){
        if(!hopDongRepository.existsById(idhopdong)){
            return new ApiResponse<>(false,"Id hợp đồng khong tồn tại", null);
        }
        HopDong hopdong = hopDongRepository.findById(idhopdong).get();
        hopdong.setThoiGianHoanTat(Timestamp.valueOf(LocalDateTime.now()));
        hopdong.setTrangThai("hoantat");
        try{
            hopdong = hopDongRepository.save(hopdong);
            return new ApiResponse<>(true,"Cập nhật hop dong thanh công", hopdong);
        }catch (Exception e){
            return new ApiResponse<>(false,"Lỗi khi cập nhật", null);
        }
    }

    @Transactional
    public ApiResponse<HopDong> getByIdAndSdt(int id, String sodienthoai){
        try{
            HopDong hopdong = hopDongRepository.findByIdHopDongAndSoDienThoai(id, sodienthoai);
            return new ApiResponse<>(true,"Lấy thông tin thành công", hopdong);
        }catch (Exception e){
            return new ApiResponse<>(false, "Lấy thông tin thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<HopDong> getById(int id){
        if(!hopDongRepository.existsById(id)){
            return new ApiResponse<>(false, "Hop dong khong ton tai", null);
        }
        try{
            HopDong hopdong = hopDongRepository.findById(id).get();
            return new ApiResponse<>(true,"Lấy thông tin thành công", hopdong);
        }catch (Exception e){
            return new ApiResponse<>(false, "Lấy thông tin thất bại", null);
        }
    }

}