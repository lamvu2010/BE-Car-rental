package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Xedto;
import com.example.be_car_rental.Models.PhuPhiPhatSinh;
import com.example.be_car_rental.Models.Xe;
import com.example.be_car_rental.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class XeService {
    @Autowired
    private XeRepository xeRepository;

    @Autowired
    private DongXeRepository dongXeRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PhuPhiPhatSinhRepository phuPhiPhatSinhRepository;

    @Autowired
    private DanhGiaRepository danhGiaRepository;

    // Xử lý giá trị năm sản xuất từ database đổ lên
    private Map<?, ?> processYearField(Map<?, ?> map) {
        // Tạo một bản sao mutable của map
        Map<Object, Object> mutableMap = new HashMap<>(map);

        if (mutableMap.containsKey("namsanxuat") && mutableMap.get("namsanxuat") != null) {
            Object yearValue = mutableMap.get("namsanxuat");
            if (yearValue instanceof Date) {
                Date date = (Date) yearValue;
                // Chỉ lấy năm từ giá trị Date
                int year = date.toLocalDate().getYear();
                mutableMap.put("namsanxuat", String.valueOf(year));
            } else if (yearValue instanceof String) {
                // Nếu là chuỗi, cắt chuỗi để lấy năm
                String yearString = (String) yearValue;
                if (yearString.length() >= 4) {
                    mutableMap.put("namsanxuat", yearString.substring(0, 4));
                }
            }
        }
        return mutableMap;
    }

    @Transactional
    public ApiResponse<Xe> insert(Xedto xedto) {
        if (xedto.getBienSoXe() == null || xedto.getBienSoXe() == "") {
            return new ApiResponse<>(false, "Biển số xe trống", null);
        }
        if (xedto.getDangKiem() == null) {
            return new ApiResponse<>(false, "Thông tin đăng kiểm trống", null);
        }
        if (xedto.getSoChoNgoi() == 0) {
            return new ApiResponse<>(false, "Số chỗ ngồi không hợp lệ", null);
        }
        if (xedto.getIdDongXe() == 0) {
            return new ApiResponse<>(false, "Id dòng xe không hợp lệ", null);
        }
        if (xedto.getNamSanXuat() == 0) {
            return new ApiResponse<>(false, "Năm sản xuất không hợp lệ", null);
        }
        try {
            Xe xe = new Xe();
            xe.setBienSoXe(xedto.getBienSoXe());
            xe.setIdDongXe(xedto.getIdDongXe());
            xe.setDangKiem(xedto.getDangKiem());
            xe.setSoChoNgoi(xedto.getSoChoNgoi());
            xe.setNamSanXuat(xedto.getNamSanXuat());
            xe.setTrangThai(xedto.getTrangThai());
            xe.setXuatXu(xedto.getXuatXu());
            xe.setKieuDang(xedto.getKieuDang());
            xe.setHopSo(xedto.getHopSo());
            xe.setNhienLieu(xedto.getNhienLieu());
            xe.setDongCo(xedto.getDongCo());
            xe.setMauNoiThat(xedto.getMauNoiThat());
            xe.setMauNgoaiThat(xedto.getMauNgoaiThat());
            xe.setDanDong(xedto.getDanDong());
            xe.setThongTinMoTa(xedto.getThongTinMoTa());
            xe.setGiaHienTai(xedto.getGiaHienTai());
            xe = xeRepository.save(xe);
            PhuPhiPhatSinh phuphiphatsinh = new PhuPhiPhatSinh();
            phuphiphatsinh.setIdXe(xe.getIdXe());
            phuphiphatsinh.setPhiQuaGio(xedto.getPhiQuaGio());
            phuphiphatsinh.setPhiVuotGioiHan(xedto.getPhiVuotGioiHan());
            phuphiphatsinh.setPhiVeSinh(xedto.getPhiVeSinh());
            phuphiphatsinh.setPhiKhuMui(xedto.getPhiKhuMui());
            phuphiphatsinh.setKmGioiHan(xedto.getKmGioiHan());
            phuphiphatsinh.setGioiHanPhiTheoGio(xedto.getGioiHanPhiTheoGio());
            phuphiphatsinh = phuPhiPhatSinhRepository.save(phuphiphatsinh);
            return new ApiResponse<>(true, "Thêm thành công", xe);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại. " + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<Xe> update(Xedto xedto) {
        if (!xeRepository.existsById(xedto.getIdXe())) {
            return new ApiResponse<>(false, "Id xe không tồn tại", null);
        }
        if (xedto.getBienSoXe() == null || xedto.getBienSoXe() == "") {
            return new ApiResponse<>(false, "Biển số xe trống", null);
        }
        if (xedto.getDangKiem() == null) {
            return new ApiResponse<>(false, "Thông tin đăng kiểm trống", null);
        }
        if (xedto.getMauNoiThat() == null) {
            return new ApiResponse<>(false, "Màu nội thất trống", null);
        }
        if (xedto.getMauNgoaiThat() == null) {
            return new ApiResponse<>(false, "Màu ngoại thất trống", null);
        }
        if (xedto.getGiaHienTai() == null) {
            return new ApiResponse<>(false, "Giá thuê trống", null);
        }
        try {
            Xe xe = xeRepository.findById(xedto.getIdXe()).get();
            xe.setDangKiem(xedto.getDangKiem());
            xe.setMauNgoaiThat(xedto.getMauNgoaiThat());
            xe.setMauNoiThat(xedto.getMauNoiThat());
            xe.setGiaHienTai(xedto.getGiaHienTai());
            xe.setThongTinMoTa(xedto.getThongTinMoTa());
            xe.setTrangThai(xedto.getTrangThai());
            xe = xeRepository.save(xe);
            PhuPhiPhatSinh phuphiphatsinh = new PhuPhiPhatSinh();
            if (phuPhiPhatSinhRepository.existsByIdXe(xedto.getIdXe())) {
                phuphiphatsinh = phuPhiPhatSinhRepository.findByIdXe(xedto.getIdXe());
            }
            phuphiphatsinh.setIdXe(xe.getIdXe());
            phuphiphatsinh.setPhiQuaGio(xedto.getPhiQuaGio());
            phuphiphatsinh.setPhiVuotGioiHan(xedto.getPhiVuotGioiHan());
            phuphiphatsinh.setPhiVeSinh(xedto.getPhiVeSinh());
            phuphiphatsinh.setPhiKhuMui(xedto.getPhiKhuMui());
            phuphiphatsinh.setKmGioiHan(xedto.getKmGioiHan());
            phuphiphatsinh.setGioiHanPhiTheoGio(xedto.getGioiHanPhiTheoGio());
            phuphiphatsinh = phuPhiPhatSinhRepository.save(phuphiphatsinh);
            imageService.deleteImageByIdAndLoai(xedto.getIdXe(), "xe");
            return new ApiResponse<>(true, "Sửa thành công", xe);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Cập nhật thất bại", null);
        }
    }

    @Transactional
    public ApiResponse delete(int id) {
        if (!xeRepository.existsById(id)) {
            return new ApiResponse<>(false, "Id không tồn tại", null);
        }
        try {
            xeRepository.deleteById(id);
            return new ApiResponse<>(true, "Xóa thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Xóa thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<?, ?>>> getAll() {
        List<Map<?, ?>> list = xeRepository.getAll();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", list);
        }
        List<Map<?, ?>> processedList = list.stream().map(this::processYearField).collect(Collectors.toList());
        return new ApiResponse<>(true, "Lấy thành công", processedList);
    }

    @Transactional
    public ApiResponse<List<Map<?, ?>>> getPage(int currentPage, int itemsPerPage) {
        List<Map<?, ?>> list = xeRepository.getPage(currentPage, itemsPerPage);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", list);
        }
        List<Map<?, ?>> processedList = list.stream().map(this::processYearField).collect(Collectors.toList());
        return new ApiResponse<>(true, "Lấy thành công", processedList);
    }

    @Transactional
    public ApiResponse<Map<?, ?>> findById(int id) {
        if (!xeRepository.existsById(id)) {
            return new ApiResponse<>(false, "Xe không tồn tại", null);
        }
        Map<?, ?> xe = xeRepository.getXeById(id);
        return new ApiResponse<>(true, "Lấy thông tin xe thành công", xe);
    }

    @Transactional
    public ApiResponse<List<Map<?, ?>>> dsXeChoThue() {
        List<Map<?, ?>> list = new ArrayList<>();
        list = xeRepository.getDsXeChoThue();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

//    @Transactional
//    public ApiResponse<List<Map<?, ?>>> dsBienSoTheoIddongxe(int id) {
//        if (!dongXeRepository.existsById(id)) {
//            return new ApiResponse<>(false, "Id dòng xe không tồn tại", null);
//        }
//        List<Map<?, ?>> list = new ArrayList<>();
//        list = xeRepository.dsBienSoTheoIddongxe(id);
//        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
//    }

    @Transactional
    public ApiResponse<Long> count() {
        long total = xeRepository.count();
        if (total == 0) {
            return new ApiResponse<>(true, "Không có xe trong csdl", total);
        }
        return new ApiResponse<>(true, "Lấy số xe thành công", total);
    }

    @Transactional
    public ApiResponse<List<Map<String, Object>>> getDsXeDeXuatTrangHome() {
        List<Map<String, Object>> list = new ArrayList<>();
        list = xeRepository.dsXeDeXuatTrangHome();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách trống", list);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse<List<Map<String, Object>>> layDanhsachdanhgia(int idxe, int page, int pageSize) {
        List<Map<String, Object>> list = danhGiaRepository.lay_ds_danhgia(idxe, page, pageSize);

        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Không có đánh giá nào cho xe này.", Collections.emptyList());
        }

        return new ApiResponse<>(true, "Lấy danh sách đánh giá thành công.", list);
    }
}