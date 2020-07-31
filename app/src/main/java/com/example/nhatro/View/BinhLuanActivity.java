package com.example.nhatro.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhatro.Adapters.AdapterHienThiHinhBinhLuanDuocChon;
import com.example.nhatro.Controller.BinhLuanController;
import com.example.nhatro.Model.BinhLuanModel;
import com.example.nhatro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binh on 6/5/17.
 */

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTenPhongTro, txtDiaChiPhongTro,txtDangBinhLuan;
    Toolbar toolbar;
    EditText edTieuDeBinhLuan,edNoiDungBinhLuan;
    ImageButton btnChonHinh;
    RecyclerView recyclerViewChonHinhBinhLuan;
    AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;

    final int REQUEST_CHONHINHBINHLUAN = 11;
    String maphongtro;
    SharedPreferences sharedPreferences;

    BinhLuanController binhLuanController;
    List<String> listHinhDuocChon;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        maphongtro = getIntent().getStringExtra("maphongtro");
        String tenphong = getIntent().getStringExtra("tenphong");
        String diachi = getIntent().getStringExtra("diachi");

        sharedPreferences = getSharedPreferences("luudangnhap",MODE_PRIVATE);

        txtDiaChiPhongTro = (TextView) findViewById(R.id.txtDiaChiQuanAn);
        txtTenPhongTro = (TextView) findViewById(R.id.txtTenQuanAn);
        txtDangBinhLuan = (TextView) findViewById(R.id.txtDangBinhLuan);
        edTieuDeBinhLuan = (EditText) findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = (EditText) findViewById(R.id.edNoiDungBinhLuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnChonHinh = (ImageButton) findViewById(R.id.btnChonHinh);
        recyclerViewChonHinhBinhLuan = (RecyclerView) findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewChonHinhBinhLuan.setLayoutManager(layoutManager);

        binhLuanController = new BinhLuanController();
        listHinhDuocChon = new ArrayList<>();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        txtDiaChiPhongTro.setText(diachi);
        txtTenPhongTro.setText(tenphong);

        btnChonHinh.setOnClickListener(this);
        txtDangBinhLuan.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnChonHinh:
                Intent iChonHinhBinhLuan = new Intent(this,ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhBinhLuan,REQUEST_CHONHINHBINHLUAN);

                break;

            case R.id.txtDangBinhLuan:
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieude = edTieuDeBinhLuan.getText().toString();
                String noidung = edNoiDungBinhLuan.getText().toString();
                String mauser = sharedPreferences.getString("mauser","");

                binhLuanModel.setTieude(tieude);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setChamdiem(0);
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setMauser(mauser);

                binhLuanController.ThemBinhLuan(maphongtro,binhLuanModel,listHinhDuocChon);

                Intent intent = new Intent(BinhLuanActivity.this,ChiTietPhongTroActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHONHINHBINHLUAN){
            if(resultCode == RESULT_OK){

                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this,R.layout.custom_layout_hienthibinhluanduocchon,listHinhDuocChon);
               recyclerViewChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
               adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();

            }
        }

    }
}
