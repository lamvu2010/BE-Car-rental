package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Xedto;
import com.example.be_car_rental.models.Danhgia;
import com.example.be_car_rental.models.Phuphiphatsinh;
import com.example.be_car_rental.models.Xe;
import com.example.be_car_rental.repositories.*;
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
    private DongxeRepository dongxeRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PhuphiphatsinhRepository phuphiphatsinhRepository;

    @Autowired
    private DanhgiaRepository danhgiaRepository;

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
        if (xedto.getBiensoxe() == null || xedto.getBiensoxe() == "") {
            return new ApiResponse<>(false, "Biển số xe trống", null);
        }
        if (xedto.getDangkiem() == null) {
            return new ApiResponse<>(false, "Thông tin đăng kiểm trống", null);
        }
        if (xedto.getSochongoi() == 0) {
            return new ApiResponse<>(false, "Số chỗ ngồi không hợp lệ", null);
        }
        if (xedto.getIddongxe() == 0) {
            return new ApiResponse<>(false, "Id dòng xe không hợp lệ", null);
        }
        if (xedto.getNamsanxuat() == 0) {
            return new ApiResponse<>(false, "Năm sản xuất không hợp lệ", null);
        }
        try {
            Xe xe = new Xe();
            xe.setBiensoxe(xedto.getBiensoxe());
            xe.setIddongxe(xedto.getIddongxe());
            xe.setDangkiem(xedto.getDangkiem());
            xe.setSochongoi(xedto.getSochongoi());
            xe.setNamsanxuat(xedto.getNamsanxuat());
            xe.setTrangthai(xedto.getTrangthai());
            xe.setXuatxu(xedto.getXuatxu());
            xe.setKieudang(xedto.getKieudang());
            xe.setHopso(xedto.getHopso());
            xe.setNhienlieu(xedto.getNhienlieu());
            xe.setDongco(xedto.getDongco());
            xe.setMaunoithat(xedto.getMaunoithat());
            xe.setMaungoaithat(xedto.getMaungoaithat());
            xe.setDandong(xedto.getDandong());
            xe.setThongtinmota(xedto.getThongtinmota());
            xe.setGiahientai(xedto.getGiahientai());
            xe = xeRepository.save(xe);
            Phuphiphatsinh phuphiphatsinh = new Phuphiphatsinh();
            phuphiphatsinh.setIdxe(xe.getIdxe());
            phuphiphatsinh.setPhiquagio(xedto.getPhiquagio());
            phuphiphatsinh.setPhivuotgioihan(xedto.getPhivuotgioihan());
            phuphiphatsinh.setPhivesinh(xedto.getPhivesinh());
            phuphiphatsinh.setPhikhumui(xedto.getPhikhumui());
            phuphiphatsinh.setKmgioihan(xedto.getKmgioihan());
            phuphiphatsinh.setGioihanphitheogio(xedto.getGioihanphitheogio());
            phuphiphatsinh = phuphiphatsinhRepository.save(phuphiphatsinh);
            return new ApiResponse<>(true, "Thêm thành công", xe);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại. " + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<Xe> update(Xedto xedto) {
        if (!xeRepository.existsById(xedto.getIdxe())) {
            return new ApiResponse<>(false, "Id xe không tồn tại", null);
        }
        if (xedto.getBiensoxe() == null || xedto.getBiensoxe() == "") {
            return new ApiResponse<>(false, "Biển số xe trống", null);
        }
        if (xedto.getDangkiem() == null) {
            return new ApiResponse<>(false, "Thông tin đăng kiểm trống", null);
        }
        if (xedto.getMaunoithat() == null) {
            return new ApiResponse<>(false, "Màu nội thất trống", null);
        }
        if (xedto.getMaungoaithat() == null) {
            return new ApiResponse<>(false, "Màu ngoại thất trống", null);
        }
        if (xedto.getGiahientai() == null) {
            return new ApiResponse<>(false, "Giá thuê trống", null);
        }
        try {
            Xe xe = xeRepository.findById(xedto.getIdxe()).get();
            xe.setDangkiem(xedto.getDangkiem());
            xe.setMaungoaithat(xedto.getMaungoaithat());
            xe.setMaunoithat(xedto.getMaunoithat());
            xe.setGiahientai(xedto.getGiahientai());
            xe.setThongtinmota(xedto.getThongtinmota());
            xe.setTrangthai(xedto.getTrangthai());
            xe = xeRepository.save(xe);
            Phuphiphatsinh phuphiphatsinh = new Phuphiphatsinh();
            if (phuphiphatsinhRepository.existsByIdxe(xedto.getIdxe())) {
                phuphiphatsinh = phuphiphatsinhRepository.findByIdxe(xedto.getIdxe());
            }
            phuphiphatsinh.setIdxe(xe.getIdxe());
            phuphiphatsinh.setPhiquagio(xedto.getPhiquagio());
            phuphiphatsinh.setPhivuotgioihan(xedto.getPhivuotgioihan());
            phuphiphatsinh.setPhivesinh(xedto.getPhivesinh());
            phuphiphatsinh.setPhikhumui(xedto.getPhikhumui());
            phuphiphatsinh.setKmgioihan(xedto.getKmgioihan());
            phuphiphatsinh.setGioihanphitheogio(xedto.getGioihanphitheogio());
            phuphiphatsinh = phuphiphatsinhRepository.save(phuphiphatsinh);
            imageService.deleteImageByIdAndLoai(xedto.getIdxe(), "xe");
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

    @Transactional
    public ApiResponse<List<Map<?, ?>>> dsBienSoTheoIddongxe(int id) {
        if (!dongxeRepository.existsById(id)) {
            return new ApiResponse<>(false, "Id dòng xe không tồn tại", null);
        }
        List<Map<?, ?>> list = new ArrayList<>();
        list = xeRepository.dsBienSoTheoIddongxe(id);
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

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
        List<Map<String, Object>> list = danhgiaRepository.lay_ds_danhgia(idxe, page, pageSize);

        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Không có đánh giá nào cho xe này.", Collections.emptyList());
        }

        return new ApiResponse<>(true, "Lấy danh sách đánh giá thành công.", list);
    }
}