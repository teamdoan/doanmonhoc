package com.example.nhatro.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nhatro.Model.ChonHinhBinhLuanModel;
import com.example.nhatro.R;

import java.util.List;


public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh> {

    Context context;
    int resource;
    List<ChonHinhBinhLuanModel> listDuongDan;

    public  AdapterChonHinhBinhLuan(Context context,int resource,List<ChonHinhBinhLuanModel> listDuongDan){
        this.context = context;
        this.resource = resource;
        this.listDuongDan = listDuongDan;
    }

    @Override
    public ViewHolderChonHinh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        ViewHolderChonHinh viewHolderChonHinh = new ViewHolderChonHinh(view);
        return viewHolderChonHinh;
    }

    @Override
    public void onBindViewHolder(ViewHolderChonHinh holder, final int position) {
        final ChonHinhBinhLuanModel chonHinhBinhLuanModel = listDuongDan.get(position);
        Uri uri = Uri.parse(chonHinhBinhLuanModel.getDuongdan());
        holder.imageView.setImageURI(uri);
        holder.checkBox.setChecked(chonHinhBinhLuanModel.isCheck());
       holder.checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CheckBox checkBox = (CheckBox) v;
               listDuongDan.get(position).setCheck(checkBox.isChecked());
           }
       });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }

    public class ViewHolderChonHinh extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;

        public ViewHolderChonHinh(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);

        }
    }
}
