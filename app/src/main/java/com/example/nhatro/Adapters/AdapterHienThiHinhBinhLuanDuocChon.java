package com.example.nhatro.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.nhatro.R;

import java.util.List;

public class AdapterHienThiHinhBinhLuanDuocChon extends RecyclerView.Adapter<AdapterHienThiHinhBinhLuanDuocChon.ViewHolderHinhBinhLuan> {

    Context context;
    int resource;
    List<String> list;

    public AdapterHienThiHinhBinhLuanDuocChon(Context context,int resource, List<String> list){
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public AdapterHienThiHinhBinhLuanDuocChon.ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(AdapterHienThiHinhBinhLuanDuocChon.ViewHolderHinhBinhLuan holder, int position) {

        Uri uri = Uri.parse(list.get(position));
        holder.imageView.setImageURI(uri);
        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageView,imgXoa;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imgChonHinhBinhLuan);
            imgXoa = (ImageView) itemView.findViewById(R.id.imgeDelete);
        }
    }
}
