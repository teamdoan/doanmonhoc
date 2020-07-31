package com.example.nhatro.View;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhatro.Model.PhongTroModel;
import com.example.nhatro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChiTietDatPhongActivity extends AppCompatActivity {

    TextView txtChiTietDatPhong;
    private DatabaseReference nodeChiTietDatPhong;
    private DataSnapshot dataRoot;
    int itemtieptheo, itemdaco;
    String maPhongTro;
    private java.lang.Object Object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dat_phong);

        txtChiTietDatPhong = findViewById(R.id.txtChiTietDatPhong);
        nodeChiTietDatPhong = FirebaseDatabase.getInstance().getReference();
//        nodeChiTietDatPhong = FirebaseDatabase.getInstance().getReference("chitietdatphongs");
//        maPhongTro = nodeChiTietDatPhong.push().getKey();
//        DatabaseReference nodeData1 = nodeChiTietDatPhong.child(maPhongTro).child("std");
//        Log.d("kssss", String.valueOf(nodeData1));
//


//        nodeData1.addValueEventListener(this);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                showSDT(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        if(dataRoot != null){
            showSDT(dataRoot);
        }else{
            nodeChiTietDatPhong.addListenerForSingleValueEvent(valueEventListener);
        }
    }

/**
    Hiển thị sđt.
 */
    private void showSDT(final DataSnapshot dataSnapshot){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DataSnapshot dataSnapshotPhongTro = dataSnapshot.child("phongtros");
                for(DataSnapshot snapshot : dataSnapshotPhongTro.getChildren()){
                       DataSnapshot dataSnapshotSDTPhongTro = snapshot.child(dataSnapshotPhongTro.getKey());
                    DataSnapshot dataSnap = dataSnapshotSDTPhongTro.child("sdt");
                    if(dataSnap != null) {
                        Object o = dataSnap.getValue(); // chỉ đúng khi có 1 sđt.
                        if (o != null) {
                            txtChiTietDatPhong.setText("Xin vui lòng liên hệ: " +o.toString());
                            return;
                        }
                    }
                }
            }

        });
    }
}