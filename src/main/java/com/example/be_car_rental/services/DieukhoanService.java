package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Dieukhoandto;
import com.example.be_car_rental.DTO.Phienbandieukhoandto;
import com.example.be_car_rental.models.Dieukhoan;
import com.example.be_car_rental.models.Phienbandieukhoan;
import com.example.be_car_rental.repositories.DieukhoanRepository;
import com.example.be_car_rental.repositories.PhienbandieukhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DieukhoanService {

    @Autowired
    private DieukhoanRepository dieukhoanRepository;

    @Autowired
    private PhienbandieukhoanRepository phienbandieukhoanRepository;

    @Transactional
    public ApiResponse<List<Dieukhoan>> getAll() {
        List<Dieukhoan> list = new ArrayList<>();
        list = dieukhoanRepository.findAll();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse<Dieukhoan> insert(Dieukhoandto dieukhoandto) {
        if (dieukhoandto.getTieude() == "" || dieukhoandto.getTieude() == null) {
            return new ApiResponse<>(false, "Tiêu đề trống", null);
        }
        Dieukhoan dieukhoan = new Dieukhoan();
        dieukhoan.setTieude(dieukhoandto.getTieude());
        dieukhoan.setNgaytao(dieukhoandto.getNgaytao());
        try {
            dieukhoan = dieukhoanRepository.save(dieukhoan);
            return new ApiResponse<>(true, "Thêm thành công", dieukhoan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    @Transactional
    public ApiResponse delete(int id) {
        if (!dieukhoanRepository.existsById(id)) {
            return new ApiResponse(false, "Điều khoản không tồn tại", null);
        }
        if (phienbandieukhoanRepository.existsByIddieukhoan(id)) {
            return new ApiResponse(false, "Danh mục điều khoản đã có các chi tiết trong hệ thống", null);
        }
        try {
            dieukhoanRepository.deleteById(id);
            return new ApiResponse<>(true, "Xóa điều khoản thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Điều khoản có tham chiếu đến các bảng khác", null);
        }
    }

    @Transactional
    public ApiResponse<List> hasDetailTerm() {
        List<Dieukhoan> list = new ArrayList<>();
        list = dieukhoanRepository.findAll();
        List<Map<Integer, Boolean>> listState = new ArrayList<>();
        try {
            for (Dieukhoan item : list) {
                boolean check = phienbandieukhoanRepository.existsByIddieukhoan(item.getIddieukhoan());
                Map<Integer, Boolean> state = new HashMap<>();
                state.put(item.getIddieukhoan(), check);
                listState.add(state);
            }
            return new ApiResponse<>(true, "Kiểm tra thông tin thành công", listState);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thông tin thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<Phienbandieukhoan> insertPhienBanDieuKhoan(Phienbandieukhoandto dto) {
        if (dto.getIddieukhoan() == 0) {
            return new ApiResponse<>(false, "Id điều khoản không được để trống", null);
        }
        if (dto.getPhienban() == null || dto.getPhienban().isEmpty()) {
            return new ApiResponse<>(false, "Phiên bản điều khoản không được để trống.", null);
        }
        if (dto.getNoidung() == null || dto.getNoidung().isEmpty()) {
            return new ApiResponse<>(false, "Nội dung điều khoản trống.", null);
        }
        if (dto.getNgaycapnhat() == null) {
            return new ApiResponse<>(false, "Ngày cập nhật trống", null);
        }
        if (phienbandieukhoanRepository.existsByIddieukhoanAndPhienban(dto.getIddieukhoan(), dto.getPhienban())) {
            return new ApiResponse<>(false, "Tên phiên bản trùng với phiên bản cũ.", null);
        }
        try {
            Phienbandieukhoan phienbandieukhoan = new Phienbandieukhoan();
            phienbandieukhoan.setIddieukhoan(dto.getIddieukhoan());
            phienbandieukhoan.setNgaycapnhat(dto.getNgaycapnhat());
            phienbandieukhoan.setNoidung(dto.getNoidung());
            phienbandieukhoan.setTrangthai(Boolean.TRUE);
            phienbandieukhoan.setPhienban(dto.getPhienban());
            phienbandieukhoan = phienbandieukhoanRepository.save(phienbandieukhoan);
            phienbandieukhoanRepository.updateTrangThaiPhienBanDieuKhoan(phienbandieukhoan.getIddieukhoan(), phienbandieukhoan.getIdphienbandieukhoan());
            return new ApiResponse<>(true, "Thêm chi tiết thành công", phienbandieukhoan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<Phienbandieukhoan> getDetail(int iddieukhoan, boolean trangthai) {
        try {
            Phienbandieukhoan phienbandieukhoan = phienbandieukhoanRepository.findByIddieukhoanAndTrangthai(iddieukhoan, trangthai);
            return new ApiResponse<>(true, "Lấy thông tin thành công", phienbandieukhoan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thông tin thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<?, ?>>> getVersions(int iddieukhoan) {
        List<Map<?, ?>> list = new ArrayList<>();
        try {
            list = phienbandieukhoanRepository.listVersions(iddieukhoan);
            return new ApiResponse<>(true, "Lấy danh sách thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<Map<String, Object>> getTermTitle(int iddieukhoan) {
        try {
            String title = dieukhoanRepository.getTermTitle(iddieukhoan);
            String version = phienbandieukhoanRepository.getVersionActive(iddieukhoan);
            Map<String, Object> result = new HashMap<>();
            result.put("tieude", title);
            result.put("phienban", version);
            return new ApiResponse<>(true, "Lấy tiêu đề thành công", result);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy tiêu đề thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<String> getDetailByIdAndVersion(int iddieukhoan, String version) {
        try {
            String detail = phienbandieukhoanRepository.getDetail(iddieukhoan, version);
            return new ApiResponse<>(true, "Lấy nội dung thành công", detail);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy nội dung thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<String, String>>> ds_dieukhoan_dangapdung() {
        List<Map<String, String>> result = new ArrayList<>();
        try {
            result = dieukhoanRepository.ds_noidung_dieukhoan();
            return new ApiResponse<>(true, "Lấy danh sách thành công", result);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi lấy dữ liệu", null);
        }
    }
}
