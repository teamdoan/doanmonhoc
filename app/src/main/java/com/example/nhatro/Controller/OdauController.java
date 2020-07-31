package com.example.nhatro.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhatro.Adapters.AdapterRecyclerOdau;
import com.example.nhatro.Controller.Interfaces.OdauInterface;
import com.example.nhatro.Model.PhongTroModel;
import com.example.nhatro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binh on 4/17/17.
 */

public class OdauController {
    Context context;
    PhongTroModel phongTroModel;
    AdapterRecyclerOdau adapterRecyclerOdau;
    int itemdaco = 3;

    public OdauController(Context context){
        this.context = context;
        phongTroModel = new PhongTroModel();
    }

    public void getDanhSachPhongTroController(Context context, NestedScrollView nestedScrollView, RecyclerView recyclerOdau, final ProgressBar progressBar, final Location vitrihientai){

        final List<PhongTroModel> phongTroModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(context, phongTroModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);

        progressBar.setVisibility(View.VISIBLE);

        final OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachPhongTroModel(final PhongTroModel phongTroModel) {
                final List<Bitmap> bitmaps = new ArrayList<>();
                for(String linkhinh : phongTroModel.getHinhanhphongtro()){

                    StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            bitmaps.add(bitmap);
                            phongTroModel.setBitmapList(bitmaps);

                            if(phongTroModel.getBitmapList().size() == phongTroModel.getHinhanhphongtro().size()){
                                phongTroModelList.add(phongTroModel);
                                adapterRecyclerOdau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }


            }
        };

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) !=null){
                    if(scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()){
                        itemdaco += 3;
                        phongTroModel.getDanhSachPhongTro(odauInterface,vitrihientai,itemdaco,itemdaco-3);
                    }
                }
            }
        });

        phongTroModel.getDanhSachPhongTro(odauInterface,vitrihientai,itemdaco,0);

    }
}
