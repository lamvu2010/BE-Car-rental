package com.example.be_car_rental.controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadImages(@RequestParam("images") List<MultipartFile> files, @RequestParam("id") int id, @RequestParam("loai") String loai) {
        try {
            ApiResponse response = imageService.uploadMultipleImagesToFileSystem(files, id, loai);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}/{loai}")
    public ResponseEntity<ApiResponse> getImageByIdAndLoai(@PathVariable int id, @PathVariable String loai) throws IOException {
        ApiResponse response = imageService.downloadImageFromFileSystemById(id, loai);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/a/{id}/{loai}")
    public ResponseEntity<ApiResponse> getAImageByIdAndLoai(@PathVariable int id, @PathVariable String loai) throws IOException {
        System.out.println(id);
        System.out.println(loai);
        ApiResponse response = imageService.downloadAImageFromFileSystemById(id, loai);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteImageById(@PathVariable int id) {
        ApiResponse response = imageService.deleteImage(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

//    @DeleteMapping("/{id}/{loai}")
//    public ResponseEntity<ApiResponse> deleteImageById(@PathVariable int id, @PathVariable String loai) {
//        ApiResponse response = imageService.deleteImageByIdAndLoai(id,loai);
//        if (response.isSuccess()) {
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

    @PostMapping("/carbranch")
    public ResponseEntity<ApiResponse> updateImageCarBranch(@RequestParam("images") MultipartFile file, @RequestParam("id") int id) {
        ApiResponse response = imageService.updateCarBranchImage(file, id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/xe/home")
    public ResponseEntity<ApiResponse> downloadImagesForHomePage(@RequestBody List<Integer> ids) throws IOException {
        ApiResponse response = imageService.getImagesDeXuatTrangHome(ids);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
