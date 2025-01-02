package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bienbannhandto {
    private Timestamp ngaynhan;
    private int sokmhientai;
    private String tinhtrangxe;
    private int idhopdong;
    private int phantramxang;
    private int xangtheolit;
    private String phukienthemtheo;
    private String nguoinhanxe;
    private String diadiemnhan;
    private List<Map<String, Object>> danhmuc;
}
