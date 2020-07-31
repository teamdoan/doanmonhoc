package com.example.nhatro.Controller;


import com.example.nhatro.Model.ThanhVienModel;

/**
 * Created by Binh on 4/18/17.
 */

public class DangKyController {
    ThanhVienModel thanhVienModel;

    public DangKyController(){
        thanhVienModel = new ThanhVienModel();
    }

    public void ThemThongTinThanhVienController(ThanhVienModel thanhVienModel,String uid){
        thanhVienModel.ThemThongTinThanhVien(thanhVienModel,uid);
    }
}
