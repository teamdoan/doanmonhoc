package com.example.nhatro.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Binh on 4/26/17.
 */

public class ChiNhanhPhongTroModel implements Parcelable {
    String diachi;
    double latitude,longitude,khoangcach;

    public ChiNhanhPhongTroModel(){

    }

    protected ChiNhanhPhongTroModel(Parcel in) {
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        khoangcach = in.readDouble();
    }

    public static final Creator<ChiNhanhPhongTroModel> CREATOR = new Creator<ChiNhanhPhongTroModel>() {
        @Override
        public ChiNhanhPhongTroModel createFromParcel(Parcel in) {
            return new ChiNhanhPhongTroModel(in);
        }

        @Override
        public ChiNhanhPhongTroModel[] newArray(int size) {
            return new ChiNhanhPhongTroModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diachi);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(khoangcach);
    }
}
