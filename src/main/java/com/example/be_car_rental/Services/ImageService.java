package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.ImageDto;
import com.example.be_car_rental.Models.HinhAnh;
import com.example.be_car_rental.Repositories.HinhAnhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private HinhAnhRepository hinhAnhRepository;
    private final String FOLDER_PATH = "D:\\THUC TAP TOT NGHIEP\\Project\\be-car-rental\\src\\main\\resources\\img\\";

    @Transactional
    public String uploadImageToFileSystem(MultipartFile file, int id, String loai) {
        String uuid = UUID.randomUUID().toString();
        String filePath = FOLDER_PATH + uuid + ".jpg";
        HinhAnh hinhanh = new HinhAnh();
        hinhanh.setIdForeign(id);
        hinhanh.setTenHinhAnh(file.getOriginalFilename());
        hinhanh.setTenDuyNhat(uuid);
        hinhanh.setPath(filePath);
        hinhanh.setLoai(loai);
        try {
            hinhanh = hinhAnhRepository.save(hinhanh);
            file.transferTo(new File(filePath));
            return "Upload thành công " + hinhanh.getTenHinhAnh();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public ApiResponse<String> updateCarBranchImage(MultipartFile file, int id) {
        hinhAnhRepository.deleteAllByIdForeignAndLoai(id, "hangxe");
        String uuid = UUID.randomUUID().toString();
        String filePath = FOLDER_PATH + uuid + ".jpg";
        HinhAnh hinhanh = new HinhAnh();
        hinhanh.setIdForeign(id);
        hinhanh.setTenHinhAnh(file.getOriginalFilename());
        hinhanh.setTenDuyNhat(uuid);
        hinhanh.setPath(filePath);
        hinhanh.setLoai("hangxe");
        try {
            hinhanh = hinhAnhRepository.save(hinhanh);
            file.transferTo(new File(filePath));
            return new ApiResponse<>(true, "Upload thành công ", hinhanh.getTenHinhAnh());
        } catch (Exception e) {
            return new ApiResponse<>(false, "Upload thất bại", null);
        }
    }

    @Transactional
    public ApiResponse<List<String>> uploadMultipleImagesToFileSystem(List<MultipartFile> files, int id, String loai) {
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            list.add(uploadImageToFileSystem(file, id, loai));
        }
        return new ApiResponse<>(true, "Lưu ảnh thành công", list);
    }

    @Transactional
    public ImageDto downloadImageFromFileSystem(int id) throws IOException {
        HinhAnh hinhanh = new HinhAnh();
        HinhAnh fileData = hinhAnhRepository.findById(id).orElse(null);
        String filePath = fileData.getPath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        ImageDto imagedto = new ImageDto();
        imagedto.setId(fileData.getIdForeign());
        imagedto.setLoai(fileData.getLoai());
        imagedto.setSrc(images);
        return imagedto;
    }

    @Transactional
    public ApiResponse<ImageDto> downloadAImageFromFileSystemById(int id, String loai) throws IOException {
        List<HinhAnh> list = new ArrayList<>();
        list = hinhAnhRepository.findByIdForeignAndLoai(id, loai);
        System.out.println(list);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Không có hình ảnh", null);
        }
        ImageDto image = downloadImageFromFileSystem(list.get(0).getIdHinhAnh());
        return new ApiResponse<>(true, "Lấy danh sách ảnh thành công", image);
    }

    @Transactional
    public ApiResponse<List<ImageDto>> downloadImageFromFileSystemById(int id, String loai) throws IOException {
        List<ImageDto> listByte = new ArrayList<>();
        List<HinhAnh> list = new ArrayList<>();
        list = hinhAnhRepository.findByIdForeignAndLoai(id, loai);
        for (HinhAnh item : list) {
            ImageDto image = downloadImageFromFileSystem(item.getIdHinhAnh());
            listByte.add(image);
        }
        if (listByte.isEmpty()) {
            return new ApiResponse<>(false, "Không có hình ảnh", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách ảnh thành công", listByte);
    }


    @Transactional
    public ApiResponse deleteImage(int id) {
        if (!hinhAnhRepository.existsById(id)) {
            return new ApiResponse(false, "Id ảnh không tồn tại", null);
        }
        try {
            String path = hinhAnhRepository.findById(id).get().getPath();
            hinhAnhRepository.deleteById(id);
            Path fullPath = Paths.get(path);
            Files.delete(fullPath);
            return new ApiResponse<>(true, "Xóa ảnh thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Gặp lỗi trong quá trình xóa ảnh", null);
        }
    }

    @Transactional
    public void deleteImageByIdAndLoai(int id, String loai) {
        List<HinhAnh> list = new ArrayList<>();
        list = hinhAnhRepository.findByIdForeignAndLoai(id, loai);
        System.out.println(list);
        if (list.isEmpty()) {
            return;
        }
        try {
            for (HinhAnh item : list) {
                String path = item.getPath();
                Path fullPath = Paths.get(path);
                Files.delete(fullPath);
            }
            hinhAnhRepository.deleteAllByIdForeignAndLoai(id, loai);
        } catch (Exception e) {
        }
    }

    @Transactional
    public ApiResponse<List<ImageDto>> getImagesDeXuatTrangHome(List<Integer> ids) throws IOException {
        List<ImageDto> images = new ArrayList<>();
        try {
            for (int id : ids) {
                HinhAnh hinhanh = hinhAnhRepository.findFirstByIdForeignAndLoai(id, "xe");
                ImageDto imagedto = downloadImageFromFileSystem(hinhanh.getIdHinhAnh());
                images.add(imagedto);
            }
            return new ApiResponse<>(true, "Lấy hình ảnh thành công", images);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thất bại", null);
        }
    }
}