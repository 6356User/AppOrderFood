package com.example.apporderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apporderfood.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTaiKhoanDN, edtMatKhauDN;
    Button btnDongYDN, btnDangKyDN;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_nhap);
        /*Anh xa View*/
        InitialView();

        nhanVienDAO = new NhanVienDAO(DangNhapActivity.this);

        btnDangKyDN.setOnClickListener(this);
        btnDongYDN.setOnClickListener(this);

        HienThiButtonDangKyVaDongY();

    }

    private void HienThiButtonDangKyVaDongY(){
        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
        if(kiemtra){
            btnDangKyDN.setVisibility(View.GONE);
            btnDongYDN.setVisibility(View.VISIBLE);
        }else{
            btnDangKyDN.setVisibility(View.VISIBLE);
            btnDongYDN.setVisibility(View.GONE);
        }
    }

    private void InitialView() {
        /*Anh xa EditText*/
        edtTaiKhoanDN = findViewById(R.id.edtTaiKhoanDN);
        edtMatKhauDN = findViewById(R.id.edtMatKhauDN);
        /*Anh xa Button*/
        btnDongYDN = findViewById(R.id.btnDongYDN);
        btnDangKyDN = findViewById(R.id.btnDangKyDN);
    }

    private void btnDongY(){
        String sTenDangNhap = edtTaiKhoanDN.getText().toString();
        String sMatKhau = edtMatKhauDN.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDangNhap, sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);

        if(kiemtra != 0){
            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen",maquyen);
            editor.commit();

            Toast.makeText(DangNhapActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
            Intent intentTrangChu = new Intent(DangNhapActivity.this, TrangChuActivity.class);
            intentTrangChu.putExtra("tendn", edtTaiKhoanDN.getText().toString());
            intentTrangChu.putExtra("manv", kiemtra);
            startActivity(intentTrangChu);
            //1overridePendingTransition(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
        }else{
            Toast.makeText(DangNhapActivity.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnDangKy(){
        Intent intentDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
        intentDangKy.putExtra("landautien",1);
        startActivity(intentDangKy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiButtonDangKyVaDongY();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnDongYDN){
            btnDongY();
        }else if(id == R.id.btnDangKyDN){
            btnDangKy();
        }
    }
}