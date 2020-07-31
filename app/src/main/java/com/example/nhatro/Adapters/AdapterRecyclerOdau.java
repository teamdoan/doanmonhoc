package com.example.nhatro.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhatro.Model.BinhLuanModel;
import com.example.nhatro.Model.ChiNhanhPhongTroModel;
import com.example.nhatro.Model.PhongTroModel;
import com.example.nhatro.R;
import com.example.nhatro.View.ChiTietDatPhongActivity;
import com.example.nhatro.View.ChiTietPhongTroActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Binh on 4/17/17.
 */

public class AdapterRecyclerOdau extends RecyclerView.Adapter<AdapterRecyclerOdau.ViewHolder> {

    List<PhongTroModel> phongTroModelList;
    int resource;
    Context context;

    public AdapterRecyclerOdau(Context context, List<PhongTroModel> phongTroModelList, int resource){
        this.phongTroModelList = phongTroModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenPhongTroOdau,txtTieudebinhluan2,txtTieudebinhluan,txtNodungbinhluan2,txtNodungbinhluan,txtChamDiemBinhLuan,txtChamDiemBinhLuan2,txtTongBinhLuan,txtTongHinhBinhLuan,txtDiaChiPhongTroODau,txtKhoanCachPhongTroODau;
        Button btnDatMonOdau;
        ImageView imageHinhPhongTroODau;
        CircleImageView cicleImageUser2,cicleImageUser;
        LinearLayout containerBinhLuan,containerBinhLuan2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenPhongTroOdau = (TextView) itemView.findViewById(R.id.txtTenQuanQuanOdau);
            btnDatMonOdau = (Button) itemView.findViewById(R.id.btnDatMonOdau);
            imageHinhPhongTroODau = (ImageView) itemView.findViewById(R.id.imageHinhQuanAnOdau);
            txtNodungbinhluan = (TextView) itemView.findViewById(R.id.txtNodungbinhluan);
            txtNodungbinhluan2 = (TextView) itemView.findViewById(R.id.txtNodungbinhluan2);
            txtTieudebinhluan = (TextView) itemView.findViewById(R.id.txtTieudebinhluan);
            txtTieudebinhluan2 = (TextView) itemView.findViewById(R.id.txtTieudebinhluan2);
            cicleImageUser = (CircleImageView) itemView.findViewById(R.id.cicleImageUser);
            cicleImageUser2 = (CircleImageView) itemView.findViewById(R.id.cicleImageUser2);
            containerBinhLuan = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2 = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan2);
            txtTongBinhLuan = (TextView) itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhBinhLuan = (TextView) itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiaChiPhongTroODau = (TextView) itemView.findViewById(R.id.txtDiaChiQuanAnODau);
            txtKhoanCachPhongTroODau = (TextView) itemView.findViewById(R.id.txtKhoangCachQuanAnODau);
            cardView = (CardView) itemView.findViewById(R.id.cardViewOdau);
        }
    }

    @Override
    public AdapterRecyclerOdau.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerOdau.ViewHolder holder, int position) {
        final PhongTroModel phongTroModel = phongTroModelList.get(position);
        holder.txtTenPhongTroOdau.setText(phongTroModel.getTenphongtro());
        if(phongTroModel.isGiaohang()){
            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
            holder.btnDatMonOdau.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Intent id = new Intent(context, ChiTietDatPhongActivity.class);
                     context.startActivity(id);
                }
            });
        }

        if(phongTroModel.getBitmapList().size() > 0){
            holder.imageHinhPhongTroODau.setImageBitmap(phongTroModel.getBitmapList().get(0));
        }
        //Lấy danh sách bình luận của quán ăn
        if(phongTroModel.getBinhLuanModelList().size() > 0){

            BinhLuanModel binhLuanModel = phongTroModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
            holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
//            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("thanhvien").child("user.png");
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.cicleImageUser.setImageBitmap(bitmap);
                }
            });
            if(phongTroModel.getBinhLuanModelList().size() > 2){
                BinhLuanModel binhLuanModel2 = phongTroModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
                holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung());
//                holder.txtChamDiemBinhLuan2.setText(binhLuanModel2.getChamdiem() + "");
                StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("thanhvien").child("user.png");
                long ONE_MEGABYTE1 = 1024 * 1024;
                storageReference1.getBytes(ONE_MEGABYTE1).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        holder.cicleImageUser2.setImageBitmap(bitmap);
                    }
                });
            }
            holder.txtTongBinhLuan.setText(phongTroModel.getBinhLuanModelList().size() + "");

            int tongsohinhbinhluan = 0;
            double tongdiem = 0;
            //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
            for (BinhLuanModel binhLuanModel1 : phongTroModel.getBinhLuanModelList()){
                tongsohinhbinhluan += binhLuanModel1.getHinhanhBinhLuanList().size();
                tongdiem += binhLuanModel1.getChamdiem();
            }

            //double diemtrungbinh = tongdiem/ phongTroModel.getBinhLuanModelList().size();
//            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinh));

            if(tongsohinhbinhluan > 0){
                holder.txtTongHinhBinhLuan.setText(tongsohinhbinhluan + "");
            }

        }else{
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
        }

        //Lấy chi nhánh quán ăn và hiển thị địa chỉ và km
        if(phongTroModel.getChiNhanhPhongTroModelList().size() > 0){
            ChiNhanhPhongTroModel chiNhanhPhongTroModelTam = phongTroModel.getChiNhanhPhongTroModelList().get(0);
            for (ChiNhanhPhongTroModel chiNhanhPhongTroModel : phongTroModel.getChiNhanhPhongTroModelList()){
                if(chiNhanhPhongTroModelTam.getKhoangcach() > chiNhanhPhongTroModel.getKhoangcach()){
                    chiNhanhPhongTroModelTam = chiNhanhPhongTroModel;
                }
            }

            holder.txtDiaChiPhongTroODau.setText(chiNhanhPhongTroModelTam.getDiachi());
            holder.txtKhoanCachPhongTroODau.setText(String.format("%.1f", chiNhanhPhongTroModelTam.getKhoangcach()) + " km");
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietPhongTro = new Intent(context, ChiTietPhongTroActivity.class);
                iChiTietPhongTro.putExtra("phongtro", phongTroModel);
                context.startActivity(iChiTietPhongTro);
            }
        });
    }

//    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
//        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
//        long ONE_MEGABYTE = 1024 * 1024;
//        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                circleImageView.setImageBitmap(bitmap);
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return phongTroModelList.size();
    }


}
