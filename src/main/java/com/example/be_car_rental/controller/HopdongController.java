package com.example.be_car_rental.controller;

import com.example.be_car_rental.DTO.*;
import com.example.be_car_rental.services.HopdongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/hopdong")
public class HopdongController {

    @Autowired
    private HopdongService hopdongService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        ApiResponse response = hopdongService.dsTatCaHopDong();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> dsHopDongTheoSdt(@RequestBody Map<?, ?> sdt) {
        ApiResponse response = hopdongService.dsHopDongTheoSdt((String) sdt.get("sdt"));
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/dang-ky")
    public ResponseEntity<ApiResponse> insert(@RequestBody Hopdongdto hopdongdto) {
        ApiResponse response = hopdongService.dangKyThueXe(hopdongdto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse> ctHopDong(@PathVariable int id) {
        ApiResponse response = hopdongService.ctHopDong(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/bienbangiao/add")
    public ResponseEntity<ApiResponse> insertBienBanGiao(@RequestBody Bienbangiaodto bienbangiaodto) {
        ApiResponse response = hopdongService.insertBienBanGiao(bienbangiaodto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/bienbannhan/add")
    public ResponseEntity<ApiResponse> insertBienBanNhan(@RequestBody Bienbannhandto bienbannhandto) {
        ApiResponse response = hopdongService.insertBienBanNhan(bienbannhandto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/hoadon/add")
    public ResponseEntity<ApiResponse> insertHoaDon(@RequestBody Hoadondto hoadondto) {
        ApiResponse response = hopdongService.insertHoaDon(hoadondto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/apply-dieukhoan/{id}")
    public ResponseEntity<ApiResponse> applyDieuKhoan(@PathVariable int id) {
        ApiResponse response = hopdongService.applyDieuKhoanHopDong(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/trangthai/{trangthai}")
    public ResponseEntity<ApiResponse> ds_hopdong_trangthai(@PathVariable String trangthai) {
        ApiResponse response = hopdongService.ds_hopdong_trangthai(trangthai);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/user/{trangthai}/{sodienthoai}")
    public ResponseEntity<ApiResponse> ds_hopdong_trangthai_sdt(@PathVariable String trangthai, @PathVariable String sodienthoai) {
        ApiResponse response = hopdongService.ds_hopdong_sdt_trangthai(trangthai, sodienthoai);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/duyet/{id}")
    public ResponseEntity<ApiResponse> duyetHopdong(@PathVariable int id) {
        ApiResponse response = hopdongService.duyetHopDong(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/tuchoi")
    public ResponseEntity<ApiResponse> tucChoiHopDong(@RequestBody Thongbaotuchoidto thongbaotuchoidto) {
        ApiResponse response = hopdongService.tuChoiHopDong(thongbaotuchoidto.getIdhopdong(), thongbaotuchoidto.getLido());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/huy-hopdong-chuacoc/{id}")
    public ResponseEntity<ApiResponse> huyHopDongChuaDatCoc(@PathVariable int id) {
        ApiResponse response = hopdongService.huyHopDongChuaCoc(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/huy-hopdong-dacoc")
    public ResponseEntity<ApiResponse> huyHopDongDaDatCoc(@RequestBody Thongtinhuycocdto thongtinhuycocdto) {

        ApiResponse response = hopdongService.huyHopDongDaCoc(thongtinhuycocdto.getIdhopdong(),
                thongtinhuycocdto.getLido(),
                thongtinhuycocdto.getPhuongthuc(),
                thongtinhuycocdto.getNganhang(),
                thongtinhuycocdto.getSotaikhoan()
        );
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/thongtinthanhtoan/{id}")
    public ResponseEntity<ApiResponse> getThongtinthanhtoan(@PathVariable int id) {
        ApiResponse response = hopdongService.getThongtinthanhtoan(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/xoa-hopdong-hethan")
    public ResponseEntity<ApiResponse> xoaHopDongHetHan(@RequestBody Map<String, String> object) {
        String sodienthoai = object.get("sodienthoai");
        ApiResponse response = hopdongService.xoaHopDongHetHan(sodienthoai);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/xoa-hopdong-tuchoi")
    public ResponseEntity<ApiResponse> xoaHopDongTuChoi(@RequestBody Map<String, String> object) {
        String sodienthoai = object.get("sodienthoai");
        ApiResponse response = hopdongService.xoaHopDongTuChoi(sodienthoai);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/bienbangiao/{id}")
    public ResponseEntity<ApiResponse> getBienBanGiao(@PathVariable int id) {
        ApiResponse response = hopdongService.getBienBanGiao(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/capnhat-hoantien/{id}")
    public ResponseEntity<ApiResponse> capNhatTrangThaiHoanTien(@PathVariable int id) {
        ApiResponse response = hopdongService.capNhatHoanTien(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/danhgia")
    public ResponseEntity<ApiResponse> danhGiaHopDong(@RequestBody Danhgiadto danhgiadto) {
        ApiResponse response = hopdongService.insertDanhGia(danhgiadto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/thongbao-suco/add")
    public ResponseEntity<ApiResponse> themThongBaoSuCo(@RequestBody Thongbaosucodto thongbaosucodto) {
        ApiResponse response = hopdongService.themThongBaoSuCo(thongbaosucodto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/thongbao-suco/{id}")
    public ResponseEntity<ApiResponse> layThongTinSuCo(@PathVariable int id) {
        ApiResponse response = hopdongService.layThongTinSuCo(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/danhmuc/{id}")
    public ResponseEntity<ApiResponse> layDanhMucKiemTra(@PathVariable int id) {
        ApiResponse response = hopdongService.layDsDanhMuc(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/thanhtoan/{id}")
    public ResponseEntity<ApiResponse> capnhatThanhToan(@PathVariable int id) {
        ApiResponse response = hopdongService.capnhatThanhToanHoanTat(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> layHopDongtheoId(@PathVariable int id) {
        ApiResponse response = hopdongService.getById(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("{id}/{sodienthoai}")
    public ResponseEntity<ApiResponse> layHopDongtheoIdSodienthoai(@PathVariable int id, @PathVariable String sodienthoai) {
        ApiResponse response = hopdongService.getByIdAndSdt(id, sodienthoai);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}