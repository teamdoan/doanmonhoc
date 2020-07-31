package com.example.nhatro.Model;

/**
 * Created by Binh on 6/7/17.
 */

public class ChonHinhBinhLuanModel {

    String duongdan;
    boolean isCheck;

    public ChonHinhBinhLuanModel(String duongdan, boolean isCheck){
        this.duongdan = duongdan;
        this.isCheck = isCheck;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public void setDuongdan(String duongdan) {
        this.duongdan = duongdan;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


}
