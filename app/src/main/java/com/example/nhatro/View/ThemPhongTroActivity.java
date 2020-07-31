package com.example.nhatro.View;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhatro.Model.ChiNhanhPhongTroModel;
import com.example.nhatro.Model.PhongTroModel;
import com.example.nhatro.Model.TienIchModel;
import com.example.nhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Binh on 8/17/17.
 */

public class ThemPhongTroActivity extends AppCompatActivity implements  View.OnClickListener,AdapterView.OnItemSelectedListener{
    final int RESULT_IMG1 = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_IMGTHUCDON = 117;
    final int RESULT_VIDEO = 200;

    Button btnGioMoCua,btnGioDongCua,btnThemPhongTro;
    Spinner spinnerKhuVuc;
    LinearLayout khungTienIch,khungChiNhanh,khungChuaChiNhanh,khungChuaThucDon;
    String gioMoCua,gioDongCua,khuvuc;
    RadioGroup rdgTrangThai;
    EditText edTenPhongTro,edGiaToiDa,edGiaThoiThieu, edSDT;
    Bitmap selectedBitmap;

    List<String> selectedTienIchList;
    List<String> khuVucList,thucDonList;
    List<String> chiNhanhList;

    List<Bitmap> hinhDaChup;
    List<String> hinhPhongTro;
    Uri videoSelected;

    ArrayAdapter<String> adapterKhuVuc;
    ImageView imgTam,imgHinhPhong1,imgHinhPhong2,imgHinhPhong3,imgHinhPhong4,imgHinhPhong5,imgHinhPhong6,imgVideo;
    VideoView videoView;

    String maPhongTro;
    String sdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themphongtro);

        btnGioDongCua = (Button) findViewById(R.id.btnGioDongCua);
        btnGioMoCua = (Button) findViewById(R.id.btnGioMoCua);
        spinnerKhuVuc = (Spinner) findViewById(R.id.spinnerKhuVuc);
        khungTienIch = (LinearLayout) findViewById(R.id.khungTienTich);
        khungChiNhanh = (LinearLayout) findViewById(R.id.khungChiNhanh);
        khungChuaChiNhanh = (LinearLayout) findViewById(R.id.khungChuaChiNhanh);
        imgHinhPhong1 = (ImageView) findViewById(R.id.imgHinhQuan1);
        imgHinhPhong2 = (ImageView) findViewById(R.id.imgHinhQuan2);
        imgHinhPhong3 = (ImageView) findViewById(R.id.imgHinhQuan3);
        imgHinhPhong4 = (ImageView) findViewById(R.id.imgHinhQuan4);
        imgHinhPhong5 = (ImageView) findViewById(R.id.imgHinhQuan5);
        imgHinhPhong6 = (ImageView) findViewById(R.id.imgHinhQuan6);
        imgVideo = (ImageView) findViewById(R.id.imgVideo);
        videoView = (VideoView) findViewById(R.id.videoView);
        btnThemPhongTro = (Button) findViewById(R.id.btnThemQuanAn);
        rdgTrangThai = (RadioGroup) findViewById(R.id.rdgTrangThai);
        edGiaThoiThieu = (EditText) findViewById(R.id.edGiaToiThieu);
        edGiaToiDa = (EditText) findViewById(R.id.edGiaToiDa);
        edTenPhongTro = (EditText) findViewById(R.id.edTenQuan);
        edSDT = (EditText) findViewById(R.id.ed_sdt);


        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        selectedTienIchList = new ArrayList<>();
        chiNhanhList = new ArrayList<>();
        hinhDaChup = new ArrayList<>();
        hinhPhongTro = new ArrayList<>();

        adapterKhuVuc = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();

        CloneChiNhanh();


        LayDanhSachKhuVuc();
        LayDanhSachTienIch();

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);
        imgHinhPhong1.setOnClickListener(this);
        imgHinhPhong2.setOnClickListener(this);
        imgHinhPhong3.setOnClickListener(this);
        imgHinhPhong4.setOnClickListener(this);
        imgHinhPhong5.setOnClickListener(this);
        imgHinhPhong6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        btnThemPhongTro.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_IMG1:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhPhong1.setImageURI(uri);
                    hinhPhongTro.add(String.valueOf(uri));
                }
                break;

            case RESULT_IMG2:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhPhong2.setImageURI(uri);
                    hinhPhongTro.add(String.valueOf(uri));
                }
                break;

            case RESULT_IMG3:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhPhong3.setImageURI(uri);
                    hinhPhongTro.add(String.valueOf(uri));
                }
                break;

            case RESULT_IMG4:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhPhong4.setImageURI(uri);
                    hinhPhongTro.add(String.valueOf(uri));
                }
                break;

            case RESULT_IMG5:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhPhong5.setImageURI(uri);
                    hinhPhongTro.add(String.valueOf(uri));
                }
                break;

            case RESULT_IMG6:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhPhong6.setImageURI(uri);
                    hinhPhongTro.add(String.valueOf(uri));
                }
                break;

            case RESULT_IMGTHUCDON:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgTam.setImageBitmap(bitmap);
                hinhDaChup.add(bitmap);
                break;

            case RESULT_VIDEO:
                if(RESULT_OK == resultCode){
                    imgVideo.setVisibility(View.GONE);
                    Uri uri = data.getData();
                    videoSelected = uri;
                    videoView.setVideoURI(uri);
                    videoView.start();

                }
                break;
        }

    }



    private void CloneChiNhanh(){
        final View view = LayoutInflater.from(ThemPhongTroActivity.this).inflate(R.layout.layout_clone_chinhanh,null);
        ImageButton btnThemChiNhanh = (ImageButton) view.findViewById(R.id.btnThemChiNhanh);
        final ImageButton btnXoaChiNhanh = (ImageButton) view.findViewById(R.id.btnXoaChiNhanh);

        btnThemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edTenChiNhanh = (EditText) view.findViewById(R.id.edTenChiNhanh);
                String tenChiNhanh = edTenChiNhanh.getText().toString();

                v.setVisibility(View.GONE);
                btnXoaChiNhanh.setVisibility(View.VISIBLE);
                btnXoaChiNhanh.setTag(tenChiNhanh);


                chiNhanhList.add(tenChiNhanh);

                CloneChiNhanh();
            }
        });

        btnXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiNhanh = v.getTag().toString();
                chiNhanhList.remove(tenChiNhanh);
                khungChuaChiNhanh.removeView(view);

            }
        });
        khungChuaChiNhanh.addView(view);
    }

    private void LayDanhSachKhuVuc(){
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String tenKhuVuc = snapshot.getKey();
                    khuVucList.add(tenKhuVuc);
                }

                adapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void LayDanhSachTienIch(){
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String maTienIch = snapshot.getKey();
                    TienIchModel tienIchModel = snapshot.getValue(TienIchModel.class);
                    tienIchModel.setMaTienIch(maTienIch);

                    CheckBox checkBox = new CheckBox(ThemPhongTroActivity.this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setTag(maTienIch);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String maTienIch = buttonView.getTag().toString();
                            if(isChecked){
                                selectedTienIchList.add(maTienIch);
                            }else{
                                selectedTienIchList.remove(maTienIch);
                            }
                        }
                    });
                    khungTienIch.addView(checkBox);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(final View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);

        switch (v.getId()){
            case R.id.btnGioDongCua:

                TimePickerDialog timePickerDialog = new TimePickerDialog(ThemPhongTroActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       gioDongCua = hourOfDay +":"+minute;
                        ((Button)v).setText(gioDongCua);
                    }
                },gio,phut,true);

                timePickerDialog.show();
                break;

            case R.id.btnGioMoCua:

                TimePickerDialog moCuaTimePickerDialog = new TimePickerDialog(ThemPhongTroActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua = hourOfDay +":"+minute;
                        ((Button)v).setText(gioMoCua);
                    }
                },gio,phut,true);

                moCuaTimePickerDialog.show();
                break;

            case R.id.imgHinhQuan1:
                ChonHinhTuGallary(RESULT_IMG1);
                break;

            case R.id.imgHinhQuan2:
                ChonHinhTuGallary(RESULT_IMG2);
                break;

            case R.id.imgHinhQuan3:
                ChonHinhTuGallary(RESULT_IMG3);
                break;

            case R.id.imgHinhQuan4:
                ChonHinhTuGallary(RESULT_IMG4);
                break;

            case R.id.imgHinhQuan5:
                ChonHinhTuGallary(RESULT_IMG5);
                break;

            case R.id.imgHinhQuan6:
                ChonHinhTuGallary(RESULT_IMG6);
                break;

            case R.id.imgVideo:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn video..."),RESULT_VIDEO);
                break;

            case R.id.btnThemQuanAn:
                ThemPhongTro();
                Intent intent1 = new Intent(ThemPhongTroActivity.this,TrangChuActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void ThemPhongTro(){
        String tenPhongTro = edTenPhongTro.getText().toString();
        String Sdt = edSDT.getText().toString();
        long giaToiDa = Long.parseLong(edGiaToiDa.getText().toString());
        long giaToiThieu = Long.parseLong(edGiaThoiThieu.getText().toString());
        int idRadioSelected = rdgTrangThai.getCheckedRadioButtonId();
        boolean giaoHang = false;
        if(idRadioSelected == R.id.rdGiaoHang){
            giaoHang = true;
        }else{
            giaoHang = false;
        }

        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference nodePhongTro = nodeRoot.child("phongtros");
        DatabaseReference nodeChiTietDatPhong = FirebaseDatabase.getInstance().getReference("chitietdatphongs");
        maPhongTro = nodeChiTietDatPhong.push().getKey();

        nodeChiTietDatPhong.child(maPhongTro).child("sdt").setValue(Sdt);
        nodeRoot.child("khuvucs").child(khuvuc).push().setValue(maPhongTro);

        for(String chinhanh : chiNhanhList){
            String urlGeoCoding = "https://maps.googleapis.com/maps/api/geocode/json?address="+chinhanh.replace(" ","%20")+"&key=AIzaSyBVd2D3evAh1Ip_f5nuN1P6ad-14G3Ns0g";
            //AIzaSyBVd2D3evAh1Ip_f5nuN1P6ad-14G3Ns0g
            //mamoi: IzaSyBEg9WUb7xVdfOEyiaGhALLBXrl-IFp65U
            DownloadToaDo downloadToaDo = new DownloadToaDo();
            downloadToaDo.execute(urlGeoCoding);

        }

        PhongTroModel phongTroModel = new PhongTroModel();
        phongTroModel.setTenphongtro(tenPhongTro);
        phongTroModel.setGiatoida(giaToiDa);
        phongTroModel.setGiatoithieu(giaToiThieu);
        phongTroModel.setGiaohang(giaoHang);
        phongTroModel.setGiomocua(gioMoCua);
        phongTroModel.setGiodongcua(gioDongCua);
        phongTroModel.setVideogioithieu(videoSelected.getLastPathSegment());
        phongTroModel.setTienich(selectedTienIchList);
        phongTroModel.setLuotthich(0);
        phongTroModel.setSdt(Sdt);

        nodePhongTro.child(maPhongTro).setValue(phongTroModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        FirebaseStorage.getInstance().getReference().child("video/"+videoSelected.getLastPathSegment()).putFile(videoSelected);
//        for(Uri hinhphong : hinhPhongTro) {
//            FirebaseStorage.getInstance().getReference().child("hinhanh/" + hinhphong.getLastPathSegment()).putFile(hinhphong);
//            FirebaseDatabase.getInstance().getReference().child("hinhanhphongtros").child(maPhongTro).push().setValue(hinhphong);
//        }

        for(String valueHinh : hinhPhongTro){
            Uri uri = Uri.fromFile(new File(valueHinh));
            FirebaseStorage.getInstance().getReference().child("hinhanh/" + uri.getLastPathSegment()).putFile(uri);
            FirebaseDatabase.getInstance().getReference().child("hinhanhphongtros").child(maPhongTro).push().setValue(uri.getLastPathSegment());
        }
    }

    class DownloadToaDo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i =0 ;i<results.length();i++){
                    JSONObject object = results.getJSONObject(i);
                    String address = object.getString("formatted_address");
                    JSONObject geometry = object.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    double latitude = (double) location.get("lat");
                    double longitude = (double) location.get("lng");

                    ChiNhanhPhongTroModel chiNhanhPhongTroModel = new ChiNhanhPhongTroModel();
                    chiNhanhPhongTroModel.setDiachi(address);
                    chiNhanhPhongTroModel.setLatitude(latitude);
                    chiNhanhPhongTroModel.setLatitude(longitude);

                    FirebaseDatabase.getInstance().getReference().child("chinhanhphongtros").child(maPhongTro).push().setValue(chiNhanhPhongTroModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChonHinhTuGallary(int requestCode){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn hình..."),requestCode);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spinnerKhuVuc:
               khuvuc = khuVucList.get(position);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
