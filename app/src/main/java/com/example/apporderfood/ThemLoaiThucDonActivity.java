package com.example.apporderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apporderfood.DAO.LoaiMonAnDAO;

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYThemLoaiThucDon;
    EditText edtThemLoaiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themloaithucdon);
        setTitle(R.string.loaithucdon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);

        btnDongYThemLoaiThucDon = findViewById(R.id.btnDongYThemLoaiThucDon);
        edtThemLoaiThucDon = findViewById(R.id.edtThemLoaiThucDon);

        btnDongYThemLoaiThucDon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String sTenLoaiThucDon = edtThemLoaiThucDon.getText().toString();
        if(sTenLoaiThucDon!=null && !sTenLoaiThucDon.equals("")){
            boolean kiemtra = loaiMonAnDAO.ThemLoaiMonAn(sTenLoaiThucDon);
            Intent intentDuLieu = new Intent();
            intentDuLieu.putExtra("kiemtraloaithucdon", kiemtra);
            setResult(Activity.RESULT_OK, intentDuLieu);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
        }
    }
}
