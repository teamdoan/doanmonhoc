package com.example.nhatro.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhatro.Adapters.AdapterBinhLuan;
import com.example.nhatro.Model.PhongTroModel;
import com.example.nhatro.Model.TienIchModel;
import com.example.nhatro.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Binh on 4/30/17.
 */

public class ChiTietPhongTroActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    TextView txtTenPhongTro,txtDiaChi,txtThoiGianHoatDong,txtTrangThaiHoatDong,txtTongSoHinhAnh,txtTongSoBinhLuan,txtTongSoCheckIn,txtTongSoLuuLai,txtTieuDeToolbar,txtGioiHanGia,txtTenWifi,txtMatKhauWifi,txtNgayDangWifi;
    ImageView imHinhAnhPhongTro,imgPlayTrailer;
    Button btnBinhLuan;
    LinearLayout khungWifi;
    PhongTroModel phongTroModel;
    Toolbar toolbar;
    RecyclerView recyclerViewBinhLuan;
    AdapterBinhLuan adapterBinhLuan;
    GoogleMap googleMap;
    SupportMapFragment mapFragment;
    LinearLayout khungTienIch;
    View khungTinhNang;
    VideoView videoView;
    RecyclerView recyclerThucDon;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietphongtro);

        phongTroModel = getIntent().getParcelableExtra("phongtro");

        txtTenPhongTro = (TextView) findViewById(R.id.txtTenQuanAn);
       // txtDiaChi = (TextView) findViewById(R.id.txtDiaChiQuanAn);
        txtThoiGianHoatDong = (TextView) findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = (TextView) findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoBinhLuan = (TextView) findViewById(R.id.tongSoBinhLuan);
        txtTongSoCheckIn = (TextView) findViewById(R.id.tongSoCheckIn);
        txtTongSoHinhAnh = (TextView) findViewById(R.id.tongSoHinhAnh);
        txtTongSoLuuLai = (TextView) findViewById(R.id.tongSoLuuLai);
        imHinhAnhPhongTro = (ImageView) findViewById(R.id.imHinhQuanAn);
        txtTieuDeToolbar = (TextView) findViewById(R.id.txtTieuDeToolbar);
        txtGioiHanGia = (TextView) findViewById(R.id.txtGioiHanGia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerViewBinhLuan = (RecyclerView) findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        khungTienIch = (LinearLayout) findViewById(R.id.khungTienTich);

        khungTinhNang = (View) findViewById(R.id.khungTinhNang);
        btnBinhLuan = (Button) findViewById(R.id.btnBinhLuan);
        videoView = (VideoView) findViewById(R.id.videoTrailer);
        imgPlayTrailer = (ImageView) findViewById(R.id.imgPLayTrailer);


        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        mapFragment.getMapAsync(this);

        HienThiChiTietPhongTro();

        khungTinhNang.setOnClickListener(this);
        btnBinhLuan.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void HienThiChiTietPhongTro(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");





        Date dateHienTai = null;
        try {
            String giomocua = phongTroModel.getGiomocua();
            String giohientai = dateFormat.format(calendar.getTime());
            String giodongcua = phongTroModel.getGiodongcua();
            dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);



            if(dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)){
                //gio mo cua
                txtTrangThaiHoatDong.setText(getString(R.string.dangmocua));
            }else{
                //dong cua
                txtTrangThaiHoatDong.setText(getString(R.string.dadongcua));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtTieuDeToolbar.setText(phongTroModel.getTenphongtro());

        txtTenPhongTro.setText(phongTroModel.getTenphongtro());
       // txtDiaChi.setText(phongTroModel.getChiNhanhPhongTroModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(phongTroModel.getGiomocua() + " - " + phongTroModel.getGiodongcua());
        txtTongSoHinhAnh.setText(phongTroModel.getHinhanhphongtro().size() + "");
        txtTongSoBinhLuan.setText(phongTroModel.getBinhLuanModelList().size() + "");
        txtThoiGianHoatDong.setText(phongTroModel.getGiomocua() + " - " + phongTroModel.getGiodongcua());

        DownLoadHinhTienIch();


        if(phongTroModel.getGiatoida() != 0 && phongTroModel.getGiatoithieu() != 0){
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieu = numberFormat.format(phongTroModel.getGiatoithieu()) + " đ";
            String giatoida = numberFormat.format(phongTroModel.getGiatoida()) + " đ";
            txtGioiHanGia.setText( giatoithieu + " - " + giatoida );
        }else{
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }

        StorageReference storageHinhPhongTro = FirebaseStorage.getInstance().getReference().child("hinhanh").child(phongTroModel.getHinhanhphongtro().get(0));
        final long ONE_MEGABYTE = 1024 * 1024;
        storageHinhPhongTro.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imHinhAnhPhongTro.setImageBitmap(bitmap);
            }
        });

        if(phongTroModel.getVideogioithieu() != null){
            videoView.setVisibility(View.VISIBLE);
            imgPlayTrailer.setVisibility(View.VISIBLE);
            imHinhAnhPhongTro.setVisibility(View.GONE);
            StorageReference storageVideo = FirebaseStorage.getInstance().getReference().child("video").child(phongTroModel.getVideogioithieu());
            storageVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView.setVideoURI(uri);
                    videoView.seekTo(1);
                }
            });

            imgPlayTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.start();
                    MediaController mediaController = new MediaController(ChiTietPhongTroActivity.this);
                    videoView.setMediaController(mediaController);
                    imgPlayTrailer.setVisibility(View.GONE);
                }
            });

        }else{
            videoView.setVisibility(View.GONE);
            imgPlayTrailer.setVisibility(View.GONE);
            imHinhAnhPhongTro.setVisibility(View.VISIBLE);
        }

        //Load danh sach binh luan cua PhongTro
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(this,R.layout.custom_layout_binhluan, phongTroModel.getBinhLuanModelList());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        NestedScrollView nestedScrollViewChiTiet = (NestedScrollView) findViewById(R.id.nestScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = phongTroModel.getChiNhanhPhongTroModelList().get(0).getLatitude();
        double longitude = phongTroModel.getChiNhanhPhongTroModelList().get(0).getLongitude();

        LatLng latLng = new LatLng(latitude,longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(phongTroModel.getTenphongtro());

        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);
    }

    private void DownLoadHinhTienIch(){

        for (String matienich : phongTroModel.getTienich()){
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);

                    StorageReference storageHinhPhongTro = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    final long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhPhongTro.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietPhongTroActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50,50);
                            layoutParams.setMargins(10,10,10,10);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageTienIch.setPadding(5,5,5,5);


                            imageTienIch.setImageBitmap(bitmap);
                            khungTienIch.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.khungTinhNang:
                Intent iDanDuong = new Intent(this, DanDuongToiPhongTroActivity.class);
                iDanDuong.putExtra("latitude", phongTroModel.getChiNhanhPhongTroModelList().get(0).getLatitude());
                iDanDuong.putExtra("longitude", phongTroModel.getChiNhanhPhongTroModelList().get(0).getLongitude());
                startActivity(iDanDuong);
                break;

            case R.id.btnBinhLuan:
                Intent iBinhLuan = new Intent(this,BinhLuanActivity.class);
                iBinhLuan.putExtra("maphongtro", phongTroModel.getMaphongtro());
                iBinhLuan.putExtra("tenphong", phongTroModel.getTenphongtro());
                iBinhLuan.putExtra("diachi", phongTroModel.getChiNhanhPhongTroModelList().get(0).getDiachi());
                startActivity(iBinhLuan);
        }
    }
}
