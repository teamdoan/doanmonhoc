package com.example.nhatro.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhatro.Controller.DangKyController;
import com.example.nhatro.Model.ThanhVienModel;
import com.example.nhatro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Binh on 3/22/17.
 */

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDangKy;
    EditText edEmailDK,edPasswordDK,edNhapLaiPasswordDK;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DangKyController dangKyController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        edEmailDK = (EditText) findViewById(R.id.edEmailDK);
        edPasswordDK = (EditText) findViewById(R.id.edPasswordDK);
        edNhapLaiPasswordDK = (EditText) findViewById(R.id.edNhapLaiPasswordDK);

        btnDangKy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage(getString(R.string.dangxuly));
        progressDialog.setIndeterminate(true);

        progressDialog.show();

        final String email = edEmailDK.getText().toString();
        String matkhau = edPasswordDK.getText().toString();
        String nhaplaimatkhau = edNhapLaiPasswordDK.getText().toString();
        String thongbaoloi = getString(R.string.thongbaoloidangky);

        if(email.trim().length() == 0 ){
            thongbaoloi += getString(R.string.email);
            Toast.makeText(this,thongbaoloi,Toast.LENGTH_SHORT).show();
        }else if(matkhau.trim().length() == 0){
            thongbaoloi += getString(R.string.matkhau);
            Toast.makeText(this,thongbaoloi,Toast.LENGTH_SHORT).show();
        }else if(!nhaplaimatkhau.equals(matkhau)){
            Toast.makeText(this,getString(R.string.thongbaonhaplaimatkhau),Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        ThanhVienModel thanhVienModel = new ThanhVienModel();
                        thanhVienModel.setHoten(email);
                        thanhVienModel.setHinhanh("user.png");
                        String uid = task.getResult().getUser().getUid();

                        dangKyController = new DangKyController();
                        dangKyController.ThemThongTinThanhVienController(thanhVienModel,uid);
                        Toast.makeText(DangKyActivity.this,getString(R.string.thongbaodangkythanhcong),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
