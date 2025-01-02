package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Thanhtoanhoantatdto;
import com.example.be_car_rental.models.Hopdong;
import com.example.be_car_rental.models.Khachhang;
import com.example.be_car_rental.models.Thongtinthanhtoan;
import com.example.be_car_rental.models.Xe;
import com.example.be_car_rental.repositories.HopdongRepository;
import com.example.be_car_rental.repositories.KhachhangRepository;
import com.example.be_car_rental.repositories.ThongtinthanhtoanRepository;
import com.example.be_car_rental.repositories.XeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twilio.twiml.voice.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PaymentService {

    @Autowired
    private HopdongRepository hopdongRepository;

    @Autowired
    private ThongtinthanhtoanRepository thongtinthanhtoanRepository;

    @Autowired
    private KhachhangRepository khachhangRepository;

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
        if (!hopdongRepository.existsById(idhopdong)) {
            return new ApiResponse<>(false, "Id hợp đồng không tô tại", null);
        }
        Hopdong hopdong = hopdongRepository.findById(idhopdong).get();
        if (!hopdong.getTrangthai().equals("duyet")) {
            return new ApiResponse<>(false, "Hợp đồng đã đặt cọc hoặc chưa được duyệt", null);
        }
        Khachhang khachhang = khachhangRepository.findById(hopdong.getSodienthoai()).orElseThrow(
                () -> new Exception("Không tìm thấy thông tin khách hàng")
        );
        try {
            Thongtinthanhtoan thongtinthanhtoan = new Thongtinthanhtoan();
            thongtinthanhtoan.setSodienthoai(hopdong.getSodienthoai());
            thongtinthanhtoan.setIdhopdong(hopdong.getIdhopdong());
            thongtinthanhtoan.setSotiencanthanhtoan(hopdong.getTiendatcoc());
            thongtinthanhtoan.setNoidung("Thanh toán tiền cọc");
            thongtinthanhtoan.setLoaithanhtoan("tiencoc");
            thongtinthanhtoan.setTrangthai("chuahoanthanh");
            thongtinthanhtoan.setThoigiantaothongtin(Timestamp.valueOf(LocalDateTime.now()));
            thongtinthanhtoan = thongtinthanhtoanRepository.save(thongtinthanhtoan);
            // Tạo mã đơn hàng
            long orderCode = thongtinthanhtoan.getIdthongtinthanhtoan();// Mã đơn hàng độc nhất

            // Số tiền (dùng BigDecimal)
            BigDecimal amount = hopdong.getTiendatcoc(); // Giá trị tiền cọc từ hợp đồng

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
                    .buyerName(khachhang.getHovaten())
                    .buyerPhone(hopdong.getSodienthoai())
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
    public ApiResponse<CheckoutResponseData> createPaymentLinkHoanTat(Thanhtoanhoantatdto thanhtoanhoantatdto) throws Exception {
        if (!hopdongRepository.existsById(thanhtoanhoantatdto.getIdhopdong())) {
            return new ApiResponse<>(false, "Id hợp đồng không tồn tại", null);
        }
        Hopdong hopdong = hopdongRepository.findById(thanhtoanhoantatdto.getIdhopdong()).get();
        Khachhang khachhang = khachhangRepository.findById(hopdong.getSodienthoai()).orElseThrow(
                () -> new Exception("Không tìm thấy thông tin khách hàng")
        );
        try {
            Thongtinthanhtoan thongtinthanhtoan = new Thongtinthanhtoan();
            thongtinthanhtoan.setSodienthoai(hopdong.getSodienthoai());
            thongtinthanhtoan.setIdhopdong(hopdong.getIdhopdong());
            thongtinthanhtoan.setSotiencanthanhtoan(thanhtoanhoantatdto.getSotienthanhtoan());
            thongtinthanhtoan.setNoidung("Thanh toán tiền còn lại và phụ phí");
            thongtinthanhtoan.setLoaithanhtoan("hoantat");
            thongtinthanhtoan.setTrangthai("chuahoanthanh");
            thongtinthanhtoan.setThoigiantaothongtin(Timestamp.valueOf(LocalDateTime.now()));
            thongtinthanhtoan = thongtinthanhtoanRepository.save(thongtinthanhtoan);
            // Tạo mã đơn hàng
            long orderCode = thongtinthanhtoan.getIdthongtinthanhtoan();// Mã đơn hàng độc nhất

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
                    .buyerName(khachhang.getHovaten())
                    .buyerPhone(hopdong.getSodienthoai())
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

            Thongtinthanhtoan thongtinthanhtoan = thongtinthanhtoanRepository.findById(data.getOrderCode()).get();
            thongtinthanhtoan.setSotienthanhtoan(new BigDecimal(data.getAmount()));
            thongtinthanhtoan.setMotathanhtoan(data.getDesc());
            thongtinthanhtoan.setSotknhantien(data.getAccountNumber());
            thongtinthanhtoan.setTransactionid(data.getReference());
            thongtinthanhtoan.setThoigianthanhtoan(Timestamp.valueOf(data.getTransactionDateTime()));
            thongtinthanhtoan.setManganhangkhachhang(data.getCounterAccountBankId());
            thongtinthanhtoan.setTennganhangkhachhang(data.getCounterAccountBankName());
            thongtinthanhtoan.setTentkkhachhang(data.getCounterAccountName());
            thongtinthanhtoan.setSotkkhachhang(data.getCounterAccountNumber());
            thongtinthanhtoan.setSotkao(data.getVirtualAccountNumber());
            thongtinthanhtoan.setTentkao(data.getVirtualAccountName());
            thongtinthanhtoan.setTrangthai("hoanthanh");
            thongtinthanhtoan = thongtinthanhtoanRepository.save(thongtinthanhtoan);

            Hopdong hopdong = hopdongRepository.findById(thongtinthanhtoan.getIdhopdong()).get();

            if (thongtinthanhtoan.getLoaithanhtoan().equals("tiencoc")) {
                hopdong.setTrangthai("dacoc");
                hopdong.setThoigiandatcoc(Timestamp.valueOf(LocalDateTime.now()));
            }
            if (thongtinthanhtoan.getLoaithanhtoan().equals("hoantat")) {
                hopdong.setTrangthai("hoantat");
                hopdong.setThoigianhoantat(Timestamp.valueOf(LocalDateTime.now()));
                Xe xe = xeRepository.findById(hopdong.getIdxe()).get();
                xe.setTrangthai("cothethue");
                xeRepository.save(xe);
            }
            hopdong = hopdongRepository.save(hopdong);

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