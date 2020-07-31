package com.example.nhatro.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.nhatro.Adapters.AdapterViewPagerTrangChu;
import com.example.nhatro.R;


/**
 * Created by Binh on 3/21/17.
 */

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager viewPagerTrangChu;
    RadioButton rdOdau,rdAngi;
    RadioGroup groupOdauAngi;
    ImageView imgThemPhongTro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);
        viewPagerTrangChu = (ViewPager) findViewById(R.id.viewpager_trangchu);
        rdAngi = (RadioButton) findViewById(R.id.rd_angi);
        groupOdauAngi = (RadioGroup) findViewById(R.id.group_odau_angi);
        imgThemPhongTro = (ImageView) findViewById(R.id.imgThemPhongTro);

        imgThemPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iThemPhongTro = new Intent(TrangChuActivity.this, ThemPhongTroActivity.class);
                startActivity(iThemPhongTro);
            }
        });
        AdapterViewPagerTrangChu adapterViewPagerTrangChu = new AdapterViewPagerTrangChu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPagerTrangChu);

        viewPagerTrangChu.addOnPageChangeListener(this);
        groupOdauAngi.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                    rdOdau.setChecked(true);
                break;


        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rd_angi:
                viewPagerTrangChu.setCurrentItem(1);
                break;
        }
    }

}
