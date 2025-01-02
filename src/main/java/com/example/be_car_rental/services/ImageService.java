package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Imagedto;
import com.example.be_car_rental.models.Hinhanh;
import com.example.be_car_rental.repositories.HinhanhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
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
    private HinhanhRepository hinhanhRepository;
    private final String FOLDER_PATH = "D:\\THUC TAP TOT NGHIEP\\Project\\be-car-rental\\src\\main\\resources\\img\\";

    @Transactional
    public String uploadImageToFileSystem(MultipartFile file, int id, String loai) {
        String uuid = UUID.randomUUID().toString();
        String filePath = FOLDER_PATH + uuid + ".jpg";
        Hinhanh hinhanh = new Hinhanh();
        hinhanh.setIdForeign(id);
        hinhanh.setTenhinhanh(file.getOriginalFilename());
        hinhanh.setTenduynhat(uuid);
        hinhanh.setPath(filePath);
        hinhanh.setLoai(loai);
        try {
            hinhanh = hinhanhRepository.save(hinhanh);
            file.transferTo(new File(filePath));
            return "Upload thành công " + hinhanh.getTenhinhanh();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public ApiResponse<String> updateCarBranchImage(MultipartFile file, int id) {
        hinhanhRepository.deleteAllByIdForeignAndLoai(id, "hangxe");
        String uuid = UUID.randomUUID().toString();
        String filePath = FOLDER_PATH + uuid + ".jpg";
        Hinhanh hinhanh = new Hinhanh();
        hinhanh.setIdForeign(id);
        hinhanh.setTenhinhanh(file.getOriginalFilename());
        hinhanh.setTenduynhat(uuid);
        hinhanh.setPath(filePath);
        hinhanh.setLoai("hangxe");
        try {
            hinhanh = hinhanhRepository.save(hinhanh);
            file.transferTo(new File(filePath));
            return new ApiResponse<>(true, "Upload thành công ", hinhanh.getTenhinhanh());
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
    public Imagedto downloadImageFromFileSystem(int id) throws IOException {
        Hinhanh hinhanh = new Hinhanh();
        Hinhanh fileData = hinhanhRepository.findById(id).orElse(null);
        String filePath = fileData.getPath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        Imagedto imagedto = new Imagedto();
        imagedto.setId(fileData.getIdForeign());
        imagedto.setLoai(fileData.getLoai());
        imagedto.setSrc(images);
        return imagedto;
    }

    @Transactional
    public ApiResponse<Imagedto> downloadAImageFromFileSystemById(int id, String loai) throws IOException {
        List<Hinhanh> list = new ArrayList<>();
        list = hinhanhRepository.findByIdForeignAndLoai(id, loai);
        System.out.println(list);
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Không có hình ảnh", null);
        }
        Imagedto image = downloadImageFromFileSystem(list.get(0).getIdhinhanh());
        return new ApiResponse<>(true, "Lấy danh sách ảnh thành công", image);
    }

    @Transactional
    public ApiResponse<List<Imagedto>> downloadImageFromFileSystemById(int id, String loai) throws IOException {
        List<Imagedto> listByte = new ArrayList<>();
        List<Hinhanh> list = new ArrayList<>();
        list = hinhanhRepository.findByIdForeignAndLoai(id, loai);
        for (Hinhanh item : list) {
            Imagedto image = downloadImageFromFileSystem(item.getIdhinhanh());
            listByte.add(image);
        }
        if (listByte.isEmpty()) {
            return new ApiResponse<>(false, "Không có hình ảnh", null);
        }
        return new ApiResponse<>(true, "Lấy danh sách ảnh thành công", listByte);
    }


    @Transactional
    public ApiResponse deleteImage(int id) {
        if (!hinhanhRepository.existsById(id)) {
            return new ApiResponse(false, "Id ảnh không tồn tại", null);
        }
        try {
            String path = hinhanhRepository.findById(id).get().getPath();
            hinhanhRepository.deleteById(id);
            Path fullPath = Paths.get(path);
            Files.delete(fullPath);
            return new ApiResponse<>(true, "Xóa ảnh thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Gặp lỗi trong quá trình xóa ảnh", null);
        }
    }

    @Transactional
    public void deleteImageByIdAndLoai(int id, String loai) {
        List<Hinhanh> list = new ArrayList<>();
        list = hinhanhRepository.findByIdForeignAndLoai(id, loai);
        System.out.println(list);
        if (list.isEmpty()) {
            return;
        }
        try {
            for (Hinhanh item : list) {
                String path = item.getPath();
                Path fullPath = Paths.get(path);
                Files.delete(fullPath);
            }
            hinhanhRepository.deleteAllByIdForeignAndLoai(id, loai);
        } catch (Exception e) {
        }
    }

    @Transactional
    public ApiResponse<List<Imagedto>> getImagesDeXuatTrangHome(List<Integer> ids) throws IOException {
        List<Imagedto> images = new ArrayList<>();
        try {
            for (int id : ids) {
                Hinhanh hinhanh = hinhanhRepository.findFirstByIdForeignAndLoai(id, "xe");
                Imagedto imagedto = downloadImageFromFileSystem(hinhanh.getIdhinhanh());
                images.add(imagedto);
            }
            return new ApiResponse<>(true, "Lấy hình ảnh thành công", images);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Lấy thất bại", null);
        }
    }
}