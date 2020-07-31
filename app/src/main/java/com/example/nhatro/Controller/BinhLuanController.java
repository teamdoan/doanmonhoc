package com.example.nhatro.Controller;



import com.example.nhatro.Model.BinhLuanModel;

import java.util.List;

/**
 * Created by Binh on 6/14/17.
 */

public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public  BinhLuanController(){
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String maPhongTro, BinhLuanModel binhLuanModel, List<String> listHinh){
        binhLuanModel.ThemBinhLuan(maPhongTro,binhLuanModel,listHinh);
    }
}
