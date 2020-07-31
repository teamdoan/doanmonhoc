package com.example.nhatro.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhatro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Binh on 3/23/17.
 */

public class KhoiPhucMatKhauActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtDangKyKP;
    Button btnGuiEmail;
    EditText edEmailKP;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenmatkhau);

        firebaseAuth = FirebaseAuth.getInstance();

        txtDangKyKP = (TextView) findViewById(R.id.txtDangKyKP);
        btnGuiEmail = (Button) findViewById(R.id.btnGuiEmailKP);
        edEmailKP = (EditText) findViewById(R.id.edEmailKP);

        btnGuiEmail.setOnClickListener(this);
        txtDangKyKP.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnGuiEmailKP:
                String email = edEmailKP.getText().toString();
                boolean kiemtraemail = KiemTraEmail(email);

                if(kiemtraemail){
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(KhoiPhucMatKhauActivity.this,getString(R.string.thongbaoguimailthanhcong),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(KhoiPhucMatKhauActivity.this,getString(R.string.thongbaoemailkhonghople),Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txtDangKyKP:
                Intent iDangKy = new Intent(KhoiPhucMatKhauActivity.this,DangKyActivity.class);
                startActivity(iDangKy);
                break;
        }
    }

    private boolean KiemTraEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
