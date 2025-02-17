package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.ThanhToanHoanTatDto;
import com.example.be_car_rental.Models.HopDong;
import com.example.be_car_rental.Models.KhachHang;
import com.example.be_car_rental.Models.ThongTinThanhToan;
import com.example.be_car_rental.Models.Xe;
import com.example.be_car_rental.Repositories.HopDongRepository;
import com.example.be_car_rental.Repositories.KhachHangRepository;
import com.example.be_car_rental.Repositories.ThongTinThanhToanRepository;
import com.example.be_car_rental.Repositories.XeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    @Autowired
    private HopDongRepository hopDongRepository;

    @Autowired
    private ThongTinThanhToanRepository thongTinThanhToanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private XeRepository xeRepository;

    private final PayOS payOS;

    public PaymentService(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    public int generateTimeBasedId() {
        LocalDateTime now = LocalDateTime.now();
        String timeString = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        return Integer.parseInt(timeString.substring(2)); // Lấy phần đủ ngắn để phù hợp với int
    }

    @Transactional
    public ApiResponse<CheckoutResponseData> createPaymentLinkDatCoc(int idhopdong) throws Exception {
        if (!hopDongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tô tại", null);
        }
        HopDong hopdong = hopDongRepository.findById(idhopdong).get();
        if (!hopdong.getTrangThai().equals("duyet")) {
            return new ApiResponse<>(false, "Hợp đồng đã đặt cọc hoặc chưa được duyệt", null);
        }
        KhachHang khachhang = khachHangRepository.findById(hopdong.getSoDienThoai()).orElseThrow(
                () -> new Exception("Không tìm thấy thông tin khách hàng")
        );
        try {
            ThongTinThanhToan thongtinthanhtoan = new ThongTinThanhToan();
            thongtinthanhtoan.setSoDienThoai(hopdong.getSoDienThoai());
            thongtinthanhtoan.setIdHopDong(hopdong.getIdHopDong());
            thongtinthanhtoan.setSoTienCanThanhToan(hopdong.getTienDatCoc());
            thongtinthanhtoan.setNoiDung("Thanh toán tiền cọc");
            thongtinthanhtoan.setLoaiThanhToan("tiencoc");
            thongtinthanhtoan.setTrangThai("chuahoanthanh");
            thongtinthanhtoan.setThoiGianTaoThongTin(Timestamp.valueOf(LocalDateTime.now()));
            thongtinthanhtoan = thongTinThanhToanRepository.save(thongtinthanhtoan);
            // Tạo mã đơn hàng
            long orderCode = thongtinthanhtoan.getIdThongTinThanhToan();// Mã đơn hàng độc nhất

            // Số tiền (dùng BigDecimal)
            BigDecimal amount = hopdong.getTienDatCoc(); // Giá trị tiền cọc từ hợp đồng

            // URL chuyển hướng
            String returnUrl = "http://localhost:5173/contact/" + idhopdong; // URL khi thanh toán thành công
            String cancelUrl = "http://localhost:5173/home";  // URL khi thanh toán bị hủy

            // Thời gian hiệu lực 5p
//        int expiredAt = (int) (System.currentTimeMillis() / 1000) + (5 * 60);

            // Item data (đã tạo từ trước)
            ItemData datcoc = ItemData.builder()
                    .name("Đặt cọc thuê xe hợp đồng mã: " + idhopdong)
                    .price(amount.intValue()) // Chuyển sang int nếu cần
                    .quantity(1)
                    .build();

            // Xây dựng PaymentData
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)      // Mã đơn hàng
                    .amount(2000)              // Số tiền
                    .description("thanh toan tien coc") // Mô tả thanh toán
                    .returnUrl(returnUrl)        // URL thành công
                    .cancelUrl(cancelUrl)        // URL hủy
                    .item(datcoc)
                    .buyerName(khachhang.getHoVaTen())
                    .buyerPhone(hopdong.getSoDienThoai())
                    .build();
            System.out.println("Lỗi createLink");
            CheckoutResponseData result = payOS.createPaymentLink(paymentData);
            System.out.println("Lỗi createLink2");
            return new ApiResponse<>(true, "Tạo link thanh toán thành công", result);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Tạo link thanh toán thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<CheckoutResponseData> createPaymentLinkHoanTat(ThanhToanHoanTatDto thanhtoanhoantatdto) throws Exception {
        if (!hopDongRepository.existsById(thanhtoanhoantatdto.getIdhopdong())) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        HopDong hopdong = hopDongRepository.findById(thanhtoanhoantatdto.getIdhopdong()).get();
        KhachHang khachhang = khachHangRepository.findById(hopdong.getSoDienThoai()).orElseThrow(
                () -> new Exception("Không tìm thấy thông tin khách hàng")
        );
        try {
            ThongTinThanhToan thongtinthanhtoan = new ThongTinThanhToan();
            thongtinthanhtoan.setSoDienThoai(hopdong.getSoDienThoai());
            thongtinthanhtoan.setIdHopDong(hopdong.getIdHopDong());
            thongtinthanhtoan.setSoTienCanThanhToan(thanhtoanhoantatdto.getSotienthanhtoan());
            thongtinthanhtoan.setNoiDung("Thanh toán tiền còn lại và phụ phí");
            thongtinthanhtoan.setLoaiThanhToan("hoantat");
            thongtinthanhtoan.setTrangThai("chuahoanthanh");
            thongtinthanhtoan.setThoiGianTaoThongTin(Timestamp.valueOf(LocalDateTime.now()));
            thongtinthanhtoan = thongTinThanhToanRepository.save(thongtinthanhtoan);
            // Tạo mã đơn hàng
            long orderCode = thongtinthanhtoan.getIdThongTinThanhToan();// Mã đơn hàng độc nhất

            // Số tiền (dùng BigDecimal)
            BigDecimal amount = thanhtoanhoantatdto.getSotienthanhtoan(); // Giá trị tiền cọc từ hợp đồng

            // URL chuyển hướng
            String returnUrl = "http://localhost:5173/contact/" + thanhtoanhoantatdto.getIdhopdong(); // URL khi thanh toán thành công
            String cancelUrl = "http://localhost:5173/home";  // URL khi thanh toán bị hủy

            // Thời gian hiệu lực 5p
//        int expiredAt = (int) (System.currentTimeMillis() / 1000) + (5 * 60);

            // Item data (đã tạo từ trước)
            ItemData hoantat = ItemData.builder()
                    .name("Hoàn tất thanh toán hợp đồng mã: " + thanhtoanhoantatdto.getIdhopdong())
                    .price(amount.intValue()) // Chuyển sang int nếu cần
                    .quantity(1)
                    .build();
            System.out.println(hoantat);
            // Xây dựng PaymentData
            PaymentData paymentData = PaymentData.builder().orderCode(orderCode)      // Mã đơn hàng
                    .amount(2000)              // Số tiền
                    .description("Hoan tat hop dong.") // Mô tả thanh toán
                    .returnUrl(returnUrl)        // URL thành công
                    .cancelUrl(cancelUrl)        // URL hủy
                    .item(hoantat)
                    .buyerName(khachhang.getHoVaTen())
                    .buyerPhone(hopdong.getSoDienThoai())
                    .build();
            System.out.println("Không tạo được link");
            CheckoutResponseData result = payOS.createPaymentLink(paymentData);
            System.out.println("Tạo được link");
            return new ApiResponse<>(true, "Tạo link thanh toán thành công", result);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Tạo link thanh toán thất bại", null);
        }
    }

    @Transactional
    public ApiResponse handleWebhook(ObjectNode body) throws JsonProcessingException {
        if (!body.has("success")) {
            body.put("success", false); // Thêm trường `success` vào payload
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);
        if (!webhookBody.getSuccess()) {
            return new ApiResponse<>(false, "Trạng thái giao dịch không thành công", null);
        }
        try {
            // Init Response
            response.put("error", 0);
            response.put("message", "Webhook delivered");
            response.set("data", null);

            WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);

            ThongTinThanhToan thongtinthanhtoan = thongTinThanhToanRepository.findById(data.getOrderCode()).get();
            thongtinthanhtoan.setSoTienThanhToan(new BigDecimal(data.getAmount()));
            thongtinthanhtoan.setMoTaThanhToan(data.getDesc());
            thongtinthanhtoan.setSoTkNhanTien(data.getAccountNumber());
            thongtinthanhtoan.setTransactionId(data.getReference());
            thongtinthanhtoan.setThoiGianThanhToan(Timestamp.valueOf(data.getTransactionDateTime()));
            thongtinthanhtoan.setMaNganHangKhachHang(data.getCounterAccountBankId());
            thongtinthanhtoan.setTenNganHangKhachHang(data.getCounterAccountBankName());
            thongtinthanhtoan.setTenTkKhachHang(data.getCounterAccountName());
            thongtinthanhtoan.setSoTkKhachHang(data.getCounterAccountNumber());
            thongtinthanhtoan.setSoTkAo(data.getVirtualAccountNumber());
            thongtinthanhtoan.setTenTkAo(data.getVirtualAccountName());
            thongtinthanhtoan.setTrangThai("hoanthanh");
            thongtinthanhtoan = thongTinThanhToanRepository.save(thongtinthanhtoan);

            HopDong hopdong = hopDongRepository.findById(thongtinthanhtoan.getIdHopDong()).get();

            if (thongtinthanhtoan.getLoaiThanhToan().equals("tiencoc")) {
                hopdong.setTrangThai("dacoc");
                hopdong.setThoiGianDatCoc(Timestamp.valueOf(LocalDateTime.now()));
            }
            if (thongtinthanhtoan.getLoaiThanhToan().equals("hoantat")) {
                hopdong.setTrangThai("hoantat");
                hopdong.setThoiGianHoanTat(Timestamp.valueOf(LocalDateTime.now()));
                Xe xe = xeRepository.findById(hopdong.getIdXe()).get();
                xe.setTrangThai("cothethue");
                xeRepository.save(xe);
            }
            hopdong = hopDongRepository.save(hopdong);

            // Xử lí data vào csdl
            return new ApiResponse<>(true, "Nhận dữ liệu webhook thành công", response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return new ApiResponse<>(false, "Nhận dữ liệu webhook thất bại", response);
        }
    }
}