package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.DieuKhoanDto;
import com.example.be_car_rental.DTO.PhienBanDieuKhoanDto;
import com.example.be_car_rental.Models.DieuKhoan;
import com.example.be_car_rental.Models.PhienBanDieuKhoan;
import com.example.be_car_rental.Repositories.DieuKhoanRepository;
import com.example.be_car_rental.Repositories.PhienBanDieuKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DieuKhoanService {

    @Autowired
    private DieuKhoanRepository dieuKhoanRepository;

    @Autowired
    private PhienBanDieuKhoanRepository phienBanDieuKhoanRepository;

    @Transactional
    public ApiResponse<List<DieuKhoan>> getAll() {
        List<DieuKhoan> list = new ArrayList<>();
        list = dieuKhoanRepository.findAll();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse<DieuKhoan> insert(DieuKhoanDto dieukhoandto) {
        if (dieukhoandto.getTieuDe() == "" || dieukhoandto.getTieuDe() == null) {
            return new ApiResponse<>(false, "Tiêu đề trống", null);
        }
        DieuKhoan dieukhoan = new DieuKhoan();
        dieukhoan.setTieuDe(dieukhoandto.getTieuDe());
        dieukhoan.setNgayTao(dieukhoandto.getNgayTao());
        try {
            dieukhoan = dieuKhoanRepository.save(dieukhoan);
            return new ApiResponse<>(true, "Thêm thành công", dieukhoan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại", null);
        }
    }

    @Transactional
    public ApiResponse delete(int id) {
        if (!dieuKhoanRepository.existsById(id)) {
            return new ApiResponse(false, "Điều khoản không tồn tại", null);
        }
        if (phienBanDieuKhoanRepository.existsByIdDieuKhoan(id)) {
            return new ApiResponse(false, "Danh mục điều khoản đã có các chi tiết trong hệ thống", null);
        }
        try {
            dieuKhoanRepository.deleteById(id);
            return new ApiResponse<>(true, "Xóa điều khoản thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Điều khoản có tham chiếu đến các bảng khác", null);
        }
    }

    @Transactional
    public ApiResponse<List> hasDetailTerm() {
        List<DieuKhoan> list = new ArrayList<>();
        list = dieuKhoanRepository.findAll();
        List<Map<Integer, Boolean>> listState = new ArrayList<>();
        try {
            for (DieuKhoan item : list) {
                boolean check = phienBanDieuKhoanRepository.existsByIdDieuKhoan(item.getIdDieuKhoan());
                Map<Integer, Boolean> state = new HashMap<>();
                state.put(item.getIdDieuKhoan(), check);
                listState.add(state);
            }
            return new ApiResponse<>(true, "Kiểm tra thông tin thành công", listState);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thông tin thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<PhienBanDieuKhoan> insertPhienBanDieuKhoan(PhienBanDieuKhoanDto dto) {
        if (dto.getIdDieuKhoan() == 0) {
            return new ApiResponse<>(false, "Id điều khoản không được để trống", null);
        }
        if (dto.getPhienBan() == null || dto.getPhienBan().isEmpty()) {
            return new ApiResponse<>(false, "Phiên bản điều khoản không được để trống.", null);
        }
        if (dto.getNoiDung() == null || dto.getNoiDung().isEmpty()) {
            return new ApiResponse<>(false, "Nội dung điều khoản trống.", null);
        }
        if (dto.getNgayCapNhat() == null) {
            return new ApiResponse<>(false, "Ngày cập nhật trống", null);
        }
        if (phienBanDieuKhoanRepository.existsByIdDieuKhoanAndPhienBan(dto.getIdDieuKhoan(), dto.getPhienBan())) {
            return new ApiResponse<>(false, "Tên phiên bản trùng với phiên bản cũ.", null);
        }
        try {
            PhienBanDieuKhoan phienbandieukhoan = new PhienBanDieuKhoan();
            phienbandieukhoan.setIdDieuKhoan(dto.getIdDieuKhoan());
            phienbandieukhoan.setNgayCapNhat(dto.getNgayCapNhat());
            phienbandieukhoan.setNoiDung(dto.getNoiDung());
            phienbandieukhoan.setTrangThai(Boolean.TRUE);
            phienbandieukhoan.setPhienBan(dto.getPhienBan());
            phienbandieukhoan = phienBanDieuKhoanRepository.save(phienbandieukhoan);
            phienBanDieuKhoanRepository.updateTrangThaiPhienBanDieuKhoan(phienbandieukhoan.getIdDieuKhoan(), phienbandieukhoan.getIdPhienBanDieuKhoan());
            return new ApiResponse<>(true, "Thêm chi tiết thành công", phienbandieukhoan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Thêm thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<PhienBanDieuKhoan> getDetail(int iddieukhoan, boolean trangthai) {
        try {
            PhienBanDieuKhoan phienbandieukhoan = phienBanDieuKhoanRepository.findByIdDieuKhoanAndTrangThai(iddieukhoan, trangthai);
            return new ApiResponse<>(true, "Lấy thông tin thành công", phienbandieukhoan);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thông tin thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<?, ?>>> getVersions(int iddieukhoan) {
        List<Map<?, ?>> list = new ArrayList<>();
        try {
            list = phienBanDieuKhoanRepository.listVersions(iddieukhoan);
            return new ApiResponse<>(true, "Lấy danh sách thành công", list);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<Map<String, Object>> getTermTitle(int iddieukhoan) {
        try {
            String title = dieuKhoanRepository.getTermTitle(iddieukhoan);
            String version = phienBanDieuKhoanRepository.getVersionActive(iddieukhoan);
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
            String detail = phienBanDieuKhoanRepository.getDetail(iddieukhoan, version);
            return new ApiResponse<>(true, "Lấy nội dung thành công", detail);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy nội dung thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<String, String>>> ds_dieukhoan_dangapdung() {
        List<Map<String, String>> result = new ArrayList<>();
        try {
            result = dieuKhoanRepository.danhSachNoiDungDieuKhoan();
            return new ApiResponse<>(true, "Lấy danh sách thành công", result);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lỗi lấy dữ liệu", null);
        }
    }
}
