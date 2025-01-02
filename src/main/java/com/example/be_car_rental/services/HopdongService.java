package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.*;
import com.example.be_car_rental.controller.MessageController;
import com.example.be_car_rental.models.*;
import com.example.be_car_rental.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HopdongService {

    @Autowired
    private HopdongRepository hopdongRepository;

    @Autowired
    private XeRepository xeRepository;

    @Autowired
    private KhachhangRepository khachhangRepository;

    @Autowired
    private BienbangiaoRepository bienbangiaoRepository;

    @Autowired
    private BienbannhanRepository bienbannhanRepository;

    @Autowired
    private HoadonRepository hoadonRepository;

    @Autowired
    private MessageController messageController;

    @Autowired
    private ThongbaoRepository thongbaoRepository;

    @Autowired
    private DanhmuckiemtraRepository danhmuckiemtraRepository;

    @Autowired
    private ThongtinthanhtoanRepository thongtinthanhtoanRepository;

    @Autowired
    private ThongtintuchoiRepository thongtintuchoiRepository;

    @Autowired
    private ThongtinhuycocRepository thongtinhuycocRepository;

    @Autowired
    private DanhgiaRepository danhgiaRepository;

    @Autowired
    private ThongbaosucoRepository thongbaosucoRepository;

    public ApiResponse<List<Map<?, ?>>> dsTatCaHopDong() {
        List<Map<?, ?>> list = new ArrayList<>();
        list = hopdongRepository.dsTatCaHopDong();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách hợp đồng thành công", list);
    }

    public ApiResponse<List<Map<?, ?>>> dsHopDongTheoSdt(String sdt) {
        List<Map<?, ?>> list = new ArrayList<>();
        list = hopdongRepository.dsHopDongTheoSdt(sdt);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách hợp đồng thành công", list);
    }

    public ApiResponse<Map<String, Object>> ctHopDong(int id) {
        if (!hopdongRepository.existsById(id)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        Map<String, Object> ct = new HashMap<>();
        ct = hopdongRepository.ctHopDong(id);
        if (ct.isEmpty()) {
            return new ApiResponse<>(false, "Lấy chi tiết thất bại", null);
        }
        return new ApiResponse<>(true, "Lấy chi tiết thành công", ct);
    }

    //Thêm biên ban giao
    @Transactional
    public ApiResponse<Bienbangiao> insertBienBanGiao(Bienbangiaodto bienbangiaodto) {
        if (bienbangiaoRepository.existsByIdhopdong(bienbangiaodto.getIdhopdong())) {
            return new ApiResponse<>(false, "Biên bản giao xe đã được thêm", null);
        }
        Bienbangiao bienbangiao = new Bienbangiao();
        bienbangiao.setIdhopdong(bienbangiaodto.getIdhopdong());
        bienbangiao.setNgaygiao(Timestamp.valueOf(LocalDateTime.now()));
        bienbangiao.setTinhtrangxe(bienbangiaodto.getTinhtrangxe());
        bienbangiao.setSokmhientai(bienbangiaodto.getSokmhientai());
        bienbangiao.setDiadiemgiao(bienbangiaodto.getDiadiemgiao());
        bienbangiao.setPhantramxang(bienbangiaodto.getPhantramxang());
        bienbangiao.setXangtheolit(bienbangiaodto.getXangtheolit());
        bienbangiao.setPhukienkemtheo(bienbangiaodto.getPhukienkemtheo());
        bienbangiao.setNguoigiaoxe(bienbangiaodto.getNguoigiaoxe());

        Hopdong hopdong = hopdongRepository.findById(bienbangiaodto.getIdhopdong()).get();
        hopdong.setTrangthai("dagiaoxe");
        try {
            bienbangiao = bienbangiaoRepository.save(bienbangiao);
            hopdong = hopdongRepository.save(hopdong);

            return new ApiResponse<>(true, "Thêm thành công", bienbangiao);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<Bienbannhan> insertBienBanNhan(Bienbannhandto bienbannhandto) {
        if (bienbannhanRepository.existsByIdhopdong(bienbannhandto.getIdhopdong())) {
            return new ApiResponse<>(false, "Biên bản nhận đã được thêm", null);
        }
        Bienbannhan bienbannhan = new Bienbannhan();
        bienbannhan.setIdhopdong(bienbannhandto.getIdhopdong());
        bienbannhan.setNgaynhan(bienbannhandto.getNgaynhan());
        bienbannhan.setSokmhientai(bienbannhandto.getSokmhientai());
        bienbannhan.setPhantramxang(bienbannhandto.getPhantramxang());
        bienbannhan.setXangtheolit(bienbannhandto.getXangtheolit());
        bienbannhan.setNguoinhanxe(bienbannhandto.getNguoinhanxe());
        bienbannhan.setDiadiemnhan(bienbannhandto.getDiadiemnhan());
        Hopdong hopdong = hopdongRepository.findById(bienbannhandto.getIdhopdong()).get();
        hopdong.setTrangthai("danhanxe");
        Xe xe = xeRepository.findById(hopdong.getIdxe()).get();
        xe.setTrangthai("cothethue");
        try {
            bienbannhan = bienbannhanRepository.save(bienbannhan);
            hopdong = hopdongRepository.save(hopdong);
            xe = xeRepository.save(xe);
            if (!bienbannhandto.getDanhmuc().isEmpty()) {
                for (Map<String, Object> item : bienbannhandto.getDanhmuc()) {
                    Danhmuckiemtra danhmuckiemtra = new Danhmuckiemtra();
                    danhmuckiemtra.setMuckiemtra((String) item.get("item"));
                    danhmuckiemtra.setTrangthai((String) item.get("pass"));
                    danhmuckiemtra.setGhichu((String) item.get("note"));
                    danhmuckiemtra.setChiphiphatsinh(BigDecimal.valueOf((Integer) item.get("cost")));
                    danhmuckiemtra.setIdbienbannhan(bienbannhan.getIdbienbannhan());
                    danhmuckiemtraRepository.save(danhmuckiemtra);
                }
            }
            return new ApiResponse<>(true, "Thêm biên bản nhận và danh mục thành công", bienbannhan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    public ApiResponse<Hoadon> insertHoaDon(Hoadondto hoadondto) {
        Hoadon hoadon = new Hoadon();
        hoadon.setIdhopdong(hoadondto.getIdhopdong());
        hoadon.setTienthue(hoadondto.getTienthue());
        hoadon.setTiendacoc(hoadondto.getTiendatcoc());
        hoadon.setTienconlai(hoadondto.getTienconlai());
        hoadon.setTienphatsinh(hoadondto.getTienphatsinh());
        hoadon.setTongtien(hoadondto.getTongtien());
        try {
            hoadon = hoadonRepository.save(hoadon);
            return new ApiResponse<>(true, "Thêm thành công", hoadon);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    public ApiResponse applyDieuKhoanHopDong(int id) {
        try {
            hopdongRepository.applyDieuKhoanHopDong(id);
            return new ApiResponse<>(true, "Áp dụng điều khoản vào hợp đồng thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Áp dụng điều khoản thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<Hopdong> dangKyThueXe(Hopdongdto hopdongdto) {
        // Kiểm tra các thông tin đầu vào
        if (hopdongdto.getIdxe() == 0) {
            return new ApiResponse<>(false, "Id xe không được trống", null);
        }
        if (hopdongdto.getTiendatcoc() == null || hopdongdto.getTiendatcoc().compareTo(BigDecimal.ZERO) <= 0) {
            return new ApiResponse<>(false, "Tiền đặt cọc không hợp lệ", null);
        }
        if (hopdongdto.getGiathuetong() == null || hopdongdto.getGiathuetong().compareTo(BigDecimal.ZERO) <= 0) {
            return new ApiResponse<>(false, "Giá thuê tổng không hợp lệ", null);
        }
        if (hopdongdto.getSodienthoai() == null || hopdongdto.getSodienthoai().isEmpty()) {
            return new ApiResponse<>(false, "Số điện thoại không được trống", null);
        }
        if (hopdongdto.getThoigianbatdau() == null) {
            return new ApiResponse<>(false, "Thời gian bắt đầu không hợp lệ", null);
        }
        if (hopdongdto.getThoigianketthuc() == null || hopdongdto.getThoigianketthuc().before(hopdongdto.getThoigianbatdau())) {
            return new ApiResponse<>(false, "Thời gian kết thúc phải sau thời gian bắt đầu", null);
        }
        if (hopdongdto.getGiathuemotngay() == null || hopdongdto.getGiathuemotngay().compareTo(BigDecimal.ZERO) <= 0) {
            return new ApiResponse<>(false, "Giá thuê mỗi ngày không hợp lệ", null);
        }
        if (hopdongdto.getSongaythue() <= 0) {
            return new ApiResponse<>(false, "Số ngày thuê phải lớn hơn 0", null);
        }
        if (hopdongdto.getNgaytao() == null) {
            return new ApiResponse<>(false, "Ngày tạo không được trống", null);
        }
        if (hopdongdto.getPhutroiquangduong() == null || hopdongdto.getPhutroiquangduong().compareTo(BigDecimal.ZERO) < 0) {
            return new ApiResponse<>(false, "Phụ trội quãng đường không hợp lệ", null);
        }
        if (hopdongdto.getSokmgioihan() < 0) {
            return new ApiResponse<>(false, "Số km giới hạn không hợp lệ", null);
        }
        if (hopdongdto.getPhutroithoigian() == null || hopdongdto.getPhutroithoigian().compareTo(BigDecimal.ZERO) < 0) {
            return new ApiResponse<>(false, "Phụ trội thời gian không hợp lệ", null);
        }
        if (hopdongdto.getSogiothue() < 0) {
            return new ApiResponse<>(false, "Số giờ thuê không hợp lệ", null);
        }

        try {
            // Tạo mới hợp đồng
            Hopdong hopdong = new Hopdong();
            hopdong.setTiendatcoc(hopdongdto.getTiendatcoc());
            hopdong.setGiathuetong(hopdongdto.getGiathuetong());
            hopdong.setSodienthoai(hopdongdto.getSodienthoai());
            hopdong.setIdxe(hopdongdto.getIdxe());
            hopdong.setThoigianbatdau(hopdongdto.getThoigianbatdau());
            hopdong.setThoigianketthuc(hopdongdto.getThoigianketthuc());
            hopdong.setGiathuemotngay(hopdongdto.getGiathuemotngay());
            hopdong.setSongaythue(hopdongdto.getSongaythue());
            hopdong.setNgaytao(hopdongdto.getNgaytao());
            hopdong.setPhutroiquangduong(hopdongdto.getPhutroiquangduong());
            hopdong.setSokmgioihan(hopdongdto.getSokmgioihan());
            hopdong.setPhutroithoigian(hopdongdto.getPhutroithoigian());
            hopdong.setGioihanphitheogio(hopdongdto.getGioihanphitheogio());
            hopdong.setSogiothue(hopdongdto.getSogiothue());
            hopdong.setTrangthai("dangky"); // Trang thái khi tạo mới là "dang ky"
            hopdong.setPhivesinh(hopdongdto.getPhivesinh());
            hopdong.setPhikhumui(hopdongdto.getPhikhumui());
            hopdong.setSokmgioihanchuyendi(hopdongdto.getSokmgioihanchuyendi());
            // Lưu hợp đồng vào cơ sở dữ liệu
            hopdong = hopdongRepository.save(hopdong);

            Xe xe = xeRepository.findById(hopdong.getIdxe()).get();
            xe.setTrangthai("dangduocthue");
            xeRepository.save(xe);

            // Tạo thông báo
            Thongbao thongbao = new Thongbao();
            thongbao.setIdnoidung(hopdong.getIdhopdong());
            thongbao.setLoaithongbao("dangky");
            thongbao.setNoidung("Người dùng " + hopdong.getSodienthoai() + " vừa đăng ký thuê xe, vui lòng duyệt đăng ký.");
            thongbao.setSodienthoai("nhanvien");
            thongbao.setTrangthai("chuaxem");
            thongbao.setThoigian(Timestamp.valueOf(LocalDateTime.now()));
            thongbao = thongbaoRepository.save(thongbao);

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
        list = hopdongRepository.ds_hopdong_trangthai(trangthai);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", list);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse<List<Map<String, Object>>> ds_hopdong_sdt_trangthai(String trangthai, String sdt) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (trangthai.equals("dangky")) {
            list = hopdongRepository.ds_hopdong_sdt_trangthai(trangthai, sdt);
        }
        if (!trangthai.equals("dangky")) {
            list = hopdongRepository.ds_hopdong_user_daduyet(sdt);
        }
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", list);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse<Hopdong> duyetHopDong(int idhopdong) {
        if (!hopdongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        try {
            Hopdong hopdong = hopdongRepository.findById(idhopdong).get();
            hopdong.setTrangthai("duyet");
            hopdong.setThoigianduyet(Timestamp.valueOf(LocalDateTime.now()));
            hopdong = hopdongRepository.save(hopdong);
            Thongbao thongbao = new Thongbao();
            thongbao.setLoaithongbao("yeucauthanhtoan");
            thongbao.setSodienthoai(hopdong.getSodienthoai());
            thongbao.setNoidung("Hợp đồng của bạn đã được duyệt, vui lòng thanh toán tiền cọc để hợp đồng bắt đầu có hiệu lực.");
            thongbao.setIdnoidung(hopdong.getIdhopdong());
            thongbao.setTrangthai("chuaxem");
            thongbao.setThoigian(Timestamp.valueOf(LocalDateTime.now()));
            thongbaoRepository.save(thongbao);
            messageController.send(hopdong.getSodienthoai(), thongbao);
            return new ApiResponse<>(true, "Duyệt hợp đồng thành công", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi trong quá trình duyệt", null);
        }
    }

    @Transactional
    public ApiResponse<Hopdong> tuChoiHopDong(int idhopdong, String lido) {
        if (!hopdongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        try {
            Hopdong hopdong = hopdongRepository.findById(idhopdong).get();
            hopdong.setTrangthai("tuchoi");
            hopdong = hopdongRepository.save(hopdong);
            Xe xe = xeRepository.findById(hopdong.getIdxe()).get();
            xe.setTrangthai("cothethue");
            xeRepository.save(xe);
            Thongtintuchoi thongtintuchoi = new Thongtintuchoi();
            thongtintuchoi.setLidotuchoi(lido);
            thongtintuchoi.setThoigiantuchoi(Timestamp.valueOf(LocalDateTime.now()));
            thongtintuchoi.setIdhopdong(hopdong.getIdhopdong());
            thongtintuchoiRepository.save(thongtintuchoi);
            Thongbao thongbao = new Thongbao();
            thongbao.setLoaithongbao("tuchoihopdong");
            thongbao.setSodienthoai(hopdong.getSodienthoai());
            thongbao.setNoidung("Hợp đồng của bạn đã bị từ chối với lí do: " + lido);
            thongbao.setIdnoidung(hopdong.getIdhopdong());
            thongbao.setTrangthai("chuaxem");
            thongbao.setThoigian(Timestamp.valueOf(LocalDateTime.now()));
            thongbaoRepository.save(thongbao);
            messageController.send(hopdong.getSodienthoai(), thongbao);
            return new ApiResponse<>(true, "Từ chối hợp đồng thành công", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi trong quá trình từ chối", null);
        }
    }

    @Transactional
    public void capNhatHopDongHetHan() {
        try {
            List<Map<String, Object>> ds_hopdong_hethan_datcoc = hopdongRepository.ds_hopdong_hethan_datcoc();
            if (ds_hopdong_hethan_datcoc == null || ds_hopdong_hethan_datcoc.isEmpty()) {
                System.out.println("Không có hợp đồng hết hạn.");
                return;
            }
            for (Map<String, Object> item : ds_hopdong_hethan_datcoc) {
                try {
                    Thongbao thongbao = new Thongbao();
                    thongbao.setIdnoidung((Integer) item.get("idhopdong"));
                    thongbao.setNoidung("Hợp đồng mã " + item.get("idhopdong") + " của bạn đã bị huỷ do quá thời gian đặt cọc.");
                    thongbao.setLoaithongbao("hopdong");
                    thongbao.setSodienthoai((String) item.get("sodienthoai"));
                    thongbao.setTrangthai("chuaxem");
                    thongbao.setThoigian(Timestamp.valueOf(LocalDateTime.now()));
                    thongbao = thongbaoRepository.save(thongbao);

                    // Gửi thông báo
                    messageController.send(thongbao.getSodienthoai(), thongbao);
                } catch (Exception e) {
                    e.printStackTrace(); // Ghi log lỗi cho từng thông báo
                }
            }
            hopdongRepository.capNhatHopDongHetHanCoc();
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log để kiểm tra lỗi
            throw new RuntimeException("Đã xảy ra lỗi khi cập nhật hợp đồng hết hạn.", e);
        }
    }

    @Transactional
    public ApiResponse<Hopdong> huyHopDongChuaCoc(int idhopdong) {
        if (!hopdongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        try {
            Hopdong hopdong = hopdongRepository.findById(idhopdong).get();
            if (hopdong.getTrangthai().equals("duyet")) {
                hopdong.setGhichu("Hợp đồng huỷ lúc đã duyệt hợp đồng.");
            }
            if (hopdong.getTrangthai().equals("dangky")) {
                hopdong.setGhichu("Hợp đồng huỷ lúc đã chưa duyệt hợp đồng.");
            }
            hopdong.setTrangthai("huy");
            hopdong.setThoigianhuy(Timestamp.valueOf(LocalDateTime.now()));
            hopdong = hopdongRepository.save(hopdong);
            Xe xe = xeRepository.findById(hopdong.getIdxe()).get();
            xe.setTrangthai("cothethue");
            xeRepository.save(xe);
            return new ApiResponse<>(true, "Huỷ đăng ký hợp đồng thành công", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi trong quá trình huỷ đăng ký", null);
        }
    }

    @Transactional
    public ApiResponse<Hopdong> huyHopDongDaCoc(int idhopdong, String lido, String phuongthuc, String nganhang, String sotaikhoan) {
        if (!hopdongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        Hopdong hopdong = hopdongRepository.findById(idhopdong).get();
        Thongtinhuycoc thongtinhuycoc = new Thongtinhuycoc();
        thongtinhuycoc.setIdhopdong(hopdong.getIdhopdong());
        thongtinhuycoc.setLidohuycoc(lido);
        thongtinhuycoc.setThoigianhuycoc(Timestamp.valueOf(LocalDateTime.now()));
        if(phuongthuc.equals("tienmat")){
            thongtinhuycoc.setPhuongthuc("tienmat");
            thongtinhuycoc.setNganhang("");
            thongtinhuycoc.setSotaikhoan("");
        }
        if(phuongthuc.equals("chuyenkhoan")){
            thongtinhuycoc.setPhuongthuc("chuyenkhoan");
            thongtinhuycoc.setNganhang(nganhang);
            thongtinhuycoc.setSotaikhoan(sotaikhoan);
        }
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        // Thời gian bắt đầu chuyến đi
        LocalDateTime thoigianbatdau = hopdong.getThoigianbatdau().toLocalDateTime();
        // Thời gian đặt cọc
        LocalDateTime thoigiandatcoc = hopdong.getNgaytao().toLocalDateTime();
        // Số tiền đặt cọc
        BigDecimal tienDatCoc = hopdong.getTiendatcoc();
        // Biến lưu kết quả
        BigDecimal tienHoanTra = BigDecimal.ZERO;
        if (now.isBefore(thoigiandatcoc.plusHours(1))) {
            // Trong vòng 1 giờ sau khi đặt cọc
            tienHoanTra = tienDatCoc; // 100% tiền cọc
            hopdong.setTrangthai("chuahoantien");
            thongtinhuycoc.setTrangthaihoantien("chuahoantien");
        } else if (now.isBefore(thoigianbatdau.minusDays(7))) {
            // Hơn 7 ngày trước khởi hành
            tienHoanTra = tienDatCoc.multiply(BigDecimal.valueOf(0.7)); // 70% tiền cọc
            hopdong.setTrangthai("chuahoantien");
            thongtinhuycoc.setTrangthaihoantien("chuahoantien");
        } else {
            // 7 ngày hoặc ít hơn trước chuyến đi
            tienHoanTra = BigDecimal.ZERO; // Không hoàn tiền
            hopdong.setTrangthai("dahoantien");
            thongtinhuycoc.setTrangthaihoantien("dahoantien");
        }
        thongtinhuycoc.setSotienhoanlai(tienHoanTra);
        // Cap nhat trang thai xe
        Xe xe = xeRepository.findById(hopdong.getIdxe()).get();
        xe.setTrangthai("cothethue");
        try {
            xeRepository.save(xe);
            hopdongRepository.save(hopdong);
            thongtinhuycocRepository.save(thongtinhuycoc);
            return new ApiResponse<>(true, "Huỷ cọc thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Huỷ cọc thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<List<Thongtinthanhtoan>> getThongtinthanhtoan(int idhopdong) {
        List<Thongtinthanhtoan> list = thongtinthanhtoanRepository.findByIdhopdong(idhopdong);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Chưa có thông tin thanh toán", list);
        }
        return new ApiResponse<>(true, "Lấy thông tin thanh toán thành công", list);
    }

    @Transactional
    public ApiResponse capNhatHoanTien(int idhopdong) {
        if (!hopdongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        Hopdong hopdong = hopdongRepository.findById(idhopdong).get();
        hopdong.setTrangthai("dahoantien");
        hopdong.setThoigianhoantien(Timestamp.valueOf(LocalDateTime.now()));

        Thongtinhuycoc thongtinhuycoc = thongtinhuycocRepository.findByIdhopdong(hopdong.getIdhopdong());
        thongtinhuycoc.setTrangthaihoantien("dahoantien");
        try {
            hopdongRepository.save(hopdong);
            thongtinhuycocRepository.save(thongtinhuycoc);
            return new ApiResponse<>(true, "Cập nhật trạng thái hoàn tiền thành công.", hopdong);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Cập nhật trạng thái hoàn tiền thất bại", null);
        }
    }

    @Transactional
    public ApiResponse xoaHopDongHetHan(String sodienthoai) {
        try {
            hopdongRepository.xoaHopDongHetHan(sodienthoai);
            return new ApiResponse<>(true, "Xoá thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Xoá thất bại", null);
        }
    }

    @Transactional
    public ApiResponse xoaHopDongTuChoi(String sodienthoai) {
        try {
            hopdongRepository.xoaHopDongTuChoi(sodienthoai);
            return new ApiResponse<>(true, "Xoá thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Xoá thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<Bienbangiao> getBienBanGiao(int idhopdong) {
        if (!bienbangiaoRepository.existsByIdhopdong(idhopdong)) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        Bienbangiao bienbangiao = new Bienbangiao();
        try {
            bienbangiao = bienbangiaoRepository.findByIdhopdong(idhopdong);
            return new ApiResponse<>(true, "Lấy biên bản giao thành công", bienbangiao);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy thông tin", null);
        }
    }

    @Transactional
    public ApiResponse insertDanhGia(Danhgiadto danhgiadto) {
        if (!khachhangRepository.existsById(danhgiadto.getSodienthoai())) {
            return new ApiResponse<>(false, "Khách hàng không tồn tại", null);
        }
        if (!xeRepository.existsById(danhgiadto.getIdxe())) {
            return new ApiResponse<>(false, "Xe không tồn tại", null);
        }
        if (!hopdongRepository.existsById(danhgiadto.getIdhopdong())) {
            return new ApiResponse<>(false, "Hợp đồng không tồn tại", null);
        }
        Danhgia danhgia = new Danhgia();
        danhgia.setIdxe(danhgiadto.getIdxe());
        danhgia.setSodienthoai(danhgiadto.getSodienthoai());
        danhgia.setNoidungdanhgia(danhgiadto.getDanhgia());
        danhgia.setSao(danhgiadto.getSao());
        Hopdong hopdong = hopdongRepository.findById(danhgiadto.getIdhopdong()).get();
        hopdong.setThoigiandanhgia(Timestamp.valueOf(LocalDateTime.now()));
        try {
            danhgia = danhgiaRepository.save(danhgia);
            hopdongRepository.save(hopdong);
            return new ApiResponse<>(true, "Cập nhật đánh giá hợp đồng thành công", danhgia);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lưu đánh giá thất bại", null);
        }
    }

    @Transactional
    public ApiResponse themThongBaoSuCo(Thongbaosucodto thongbaosucodto) {
        if (!hopdongRepository.existsById(thongbaosucodto.getIdhopdong())) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        Thongbaosuco thongbaosuco = new Thongbaosuco();
        thongbaosuco.setIdhopdong(thongbaosucodto.getIdhopdong());
        thongbaosuco.setVitri(thongbaosucodto.getVitri());
        thongbaosuco.setMota(thongbaosucodto.getMota());
        thongbaosuco.setLienlac(thongbaosucodto.getLienlac());
        try {
            thongbaosuco = thongbaosucoRepository.save(thongbaosuco);
            Thongbao thongbao = new Thongbao();
            thongbao.setLoaithongbao("thongbaosuco");
            thongbao.setSodienthoai("nhanvien");
            thongbao.setNoidung("Thông báo sự cố hợp đồng " + thongbaosuco.getIdhopdong());
            thongbao.setIdnoidung(thongbaosuco.getIdthongbaosuco());
            thongbao.setTrangthai("chuaxem");
            thongbao.setThoigian(Timestamp.valueOf(LocalDateTime.now()));
            thongbaoRepository.save(thongbao);
            messageController.send("manager", thongbao);
            return new ApiResponse<>(true, "Gửi thông báo thành công", thongbaosuco);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi gửi thông báo", null);
        }
    }

    @Transactional
    public ApiResponse<Thongbaosuco> layThongTinSuCo(int idthongbaosuco) {
        if (!thongbaosucoRepository.existsById(idthongbaosuco)) {
            return new ApiResponse<>(false, "Id thông báo sự cố không tồn tại", null);
        }
        try {
            Thongbaosuco thongbaosuco = thongbaosucoRepository.findById(idthongbaosuco).get();
            return new ApiResponse<>(true, "Lấy thông tin sự cố thành công", thongbaosuco);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi khi lấy thông tin sự cố", null);
        }
    }

    @Transactional
    public ApiResponse<List<Danhmuckiemtra>> layDsDanhMuc(int idbienbannhan) {
        List<Danhmuckiemtra> list = new ArrayList<>();
        try {
            list = danhmuckiemtraRepository.findByIdbienbannhan(idbienbannhan);
            return new ApiResponse<>(true, "Lấy danh mục thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy danh sách thất bại", null);
        }
    }

    @Transactional
    public ApiResponse capnhatThanhToanHoanTat(int idhopdong){
        if(!hopdongRepository.existsById(idhopdong)){
            return new ApiResponse<>(false,"Id hợp đồng khong tồn tại", null);
        }
        Hopdong hopdong = hopdongRepository.findById(idhopdong).get();
        hopdong.setThoigianhoantat(Timestamp.valueOf(LocalDateTime.now()));
        hopdong.setTrangthai("hoantat");
        try{
            hopdong = hopdongRepository.save(hopdong);
            return new ApiResponse<>(true,"Cập nhật hop dong thanh công", hopdong);
        }catch (Exception e){
            return new ApiResponse<>(false,"Lỗi khi cập nhật", null);
        }
    }

    @Transactional
    public ApiResponse<Hopdong> getByIdAndSdt(int id, String sodienthoai){
        try{
            Hopdong hopdong = hopdongRepository.findByIdhopdongAndSodienthoai(id, sodienthoai);
            return new ApiResponse<>(true,"Lấy thông tin thành công", hopdong);
        }catch (Exception e){
            return new ApiResponse<>(false, "Lấy thông tin thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<Hopdong> getById(int id){
        if(!hopdongRepository.existsById(id)){
            return new ApiResponse<>(false, "Hop dong khong ton tai", null);
        }
        try{
            Hopdong hopdong = hopdongRepository.findById(id).get();
            return new ApiResponse<>(true,"Lấy thông tin thành công", hopdong);
        }catch (Exception e){
            return new ApiResponse<>(false, "Lấy thông tin thất bại", null);
        }
    }

}