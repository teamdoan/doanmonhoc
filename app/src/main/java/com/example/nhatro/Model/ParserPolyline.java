package com.example.nhatro.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binh on 5/17/17.
 */

public class ParserPolyline {


    public static List<LatLng> LayDanhSachToaDo(String dataJSON){
        List<LatLng> latLngs = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJSON);
            JSONArray routes = jsonObject.getJSONArray("routes");
            for (int i=0; i<routes.length(); i++){
                JSONObject jsonObjectArray = routes.getJSONObject(i);
                JSONObject overviewPolyline = jsonObjectArray.getJSONObject("overview_polyline");

                String point = overviewPolyline.getString("points");

                latLngs = PolyUtil.decode(point);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return latLngs;
    }

}
