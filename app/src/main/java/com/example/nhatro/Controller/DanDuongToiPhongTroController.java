package com.example.nhatro.Controller;

import com.example.nhatro.Model.DownloadPolyLine;
import com.example.nhatro.Model.ParserPolyline;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Binh on 5/17/17.
 */

public class DanDuongToiPhongTroController {
    ParserPolyline parserPolyline;
    DownloadPolyLine downloadPolyLine;

    public DanDuongToiPhongTroController(){

    }

    public void HienThiDanDuongToiPhongTro(GoogleMap googleMap,String duongdan){
        parserPolyline = new ParserPolyline();
        downloadPolyLine = new DownloadPolyLine();
        downloadPolyLine.execute(duongdan);

        try {
            String dataJSON = downloadPolyLine.get();
            List<LatLng> latLngList = parserPolyline.LayDanhSachToaDo(dataJSON);

            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng toado : latLngList){
                polylineOptions.add(toado);
            }

            Polyline polyline = googleMap.addPolyline(polylineOptions);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
