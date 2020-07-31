package com.example.nhatro.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.nhatro.Controller.Interfaces.OdauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binh on 4/17/17.
 */

public class PhongTroModel implements Parcelable{
    boolean giaohang;
    String giodongcua,giomocua,tenphongtro,videogioithieu,maphongtro,chitietdatphong, sdt;
    List<String> tienich;
    List<String> hinhanhphongtro;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhPhongTroModel> chiNhanhPhongTroModelList;
    List<Bitmap> bitmapList;



    long giatoida;
    long giatoithieu;
    long luotthich;


    public String getChitietdatphong() {
        return chitietdatphong;
    }

    public void setChitietdatphong(String chitietdatphong) {
        this.chitietdatphong = chitietdatphong;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }



    protected PhongTroModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        sdt = in.readString();
        tenphongtro = in.readString();
        chitietdatphong = in.readString();
        videogioithieu = in.readString();
        maphongtro = in.readString();
        tienich = in.createStringArrayList();
        hinhanhphongtro = in.createStringArrayList();
        luotthich = in.readLong();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        chiNhanhPhongTroModelList = new ArrayList<ChiNhanhPhongTroModel>();
        in.readTypedList(chiNhanhPhongTroModelList, ChiNhanhPhongTroModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
    }

    public static final Creator<PhongTroModel> CREATOR = new Creator<PhongTroModel>() {
        @Override
        public PhongTroModel createFromParcel(Parcel in) {
            return new PhongTroModel(in);
        }

        @Override
        public PhongTroModel[] newArray(int size) {
            return new PhongTroModel[size];
        }
    };

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }



    public List<ChiNhanhPhongTroModel> getChiNhanhPhongTroModelList() {
        return chiNhanhPhongTroModelList;
    }

    public void setChiNhanhPhongTroModelList(List<ChiNhanhPhongTroModel> chiNhanhPhongTroModelList) {
        this.chiNhanhPhongTroModelList = chiNhanhPhongTroModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }


    private DatabaseReference nodeRoot ;

    public List<String> getHinhanhphongtro() {
        return hinhanhphongtro;
    }

    public void setHinhanhphongtro(List<String> hinhanhphongtro) {
        this.hinhanhphongtro = hinhanhphongtro;
    }


    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public PhongTroModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenphongtro() {
        return tenphongtro;
    }

    public void setTenphongtro(String tenphongtro) {
        this.tenphongtro = tenphongtro;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaphongtro() {
        return maphongtro;
    }

    public void setMaphongtro(String maphongtro) {
        this.maphongtro = maphongtro;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    private DataSnapshot dataRoot;
    public void getDanhSachPhongTro(final OdauInterface odauInterface, final Location vitrihientai, final int itemtieptheo, final int itemdaco){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               dataRoot = dataSnapshot;
                LayDanhSachPhongTro(dataSnapshot,odauInterface,vitrihientai,itemtieptheo,itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            LayDanhSachPhongTro(dataRoot,odauInterface,vitrihientai,itemtieptheo,itemdaco);
        }else{
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }

    private void LayDanhSachPhongTro(DataSnapshot dataSnapshot,OdauInterface odauInterface,Location vitrihientai,int itemtieptheo,int itemdaco){
        DataSnapshot dataSnapshotPhongTro = dataSnapshot.child("phongtros");
        //Lấy danh sách phongtro
        int i = 0;
        for (DataSnapshot valuePhongTro : dataSnapshotPhongTro.getChildren()){

            if(i == itemtieptheo){
                break;
            }
            if(i < itemdaco){
                i++;
                continue;
            }
            i++;
            PhongTroModel phongTroModel = valuePhongTro.getValue(PhongTroModel.class);
            phongTroModel.setMaphongtro(valuePhongTro.getKey());
            //Lấy danh sách hình ảnh của phongtro theo mã
            DataSnapshot dataSnapshotHinhPhongTro = dataSnapshot.child("hinhanhphongtros").child(valuePhongTro.getKey());
            Log.d("sdsa", String.valueOf(dataSnapshotHinhPhongTro));
            List<String> hinhanhlist = new ArrayList<>();

            for (DataSnapshot valueHinhPhongTro : dataSnapshotHinhPhongTro.getChildren()){
                hinhanhlist.add(valueHinhPhongTro.getValue(String.class));
            }
            phongTroModel.setHinhanhphongtro(hinhanhlist);

            //Lấy danh sách bình luân của phongtro
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(phongTroModel.getMaphongtro());
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();

            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);

                binhLuanModels.add(binhLuanModel);
            }
            phongTroModel.setBinhLuanModelList(binhLuanModels);

            //Lấy chi nhánh phongtro
            DataSnapshot snapshotChiNhanhPhongTro = dataSnapshot.child("chinhanhphongtros").child(phongTroModel.getMaphongtro());
            List<ChiNhanhPhongTroModel> chiNhanhPhongTroModels = new ArrayList<>();

            for (DataSnapshot valueChiNhanhPhongTro : snapshotChiNhanhPhongTro.getChildren()){
                ChiNhanhPhongTroModel chiNhanhPhongTroModel = valueChiNhanhPhongTro.getValue(ChiNhanhPhongTroModel.class);
                Location vitriphongtro = new Location("");
                vitriphongtro.setLatitude(chiNhanhPhongTroModel.getLatitude());
                vitriphongtro.setLongitude(chiNhanhPhongTroModel.getLongitude());

                double khoangcach = vitrihientai.distanceTo(vitriphongtro)/1000;
                chiNhanhPhongTroModel.setKhoangcach(khoangcach);

                chiNhanhPhongTroModels.add(chiNhanhPhongTroModel);
            }

            phongTroModel.setChiNhanhPhongTroModelList(chiNhanhPhongTroModels);

            odauInterface.getDanhSachPhongTroModel(phongTroModel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(sdt);
        dest.writeString(tenphongtro);
        dest.writeString(chitietdatphong);
        dest.writeString(videogioithieu);
        dest.writeString(maphongtro);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhphongtro);
        dest.writeLong(luotthich);
        dest.writeLong(giatoida);
        dest.writeLong(giatoithieu);
        dest.writeTypedList(chiNhanhPhongTroModelList);
        dest.writeTypedList(binhLuanModelList);
    }
}
