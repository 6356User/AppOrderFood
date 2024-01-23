package com.example.apporderfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apporderfood.DAO.GoiMonDAO;
import com.example.apporderfood.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {

    int maban,mamonan;
    EditText edtSoLuongMonAn;
    Button btnDongYThemSoLuong;
    GoiMonDAO goiMonDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);
        setTitle(R.string.soluongmonan);

        edtSoLuongMonAn = findViewById(R.id.edtSoLuongMonAn);
        btnDongYThemSoLuong = findViewById(R.id.btnDongYThemSoLuong);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        mamonan = intent.getIntExtra("mamonan", 0);

        btnDongYThemSoLuong.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        boolean kiemtra = goiMonDAO.KiemTraMonAnDaTonTai(magoimon,mamonan);
        if(kiemtra){
            //tiến hành cập nhật món ăn đã tồn tại
            int soluongcu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon,mamonan);
            int soluongmoi = Integer.parseInt(edtSoLuongMonAn.getText().toString());

            int tongsoluong = soluongcu + soluongmoi;

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(tongsoluong);

            boolean kiemtracapnhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
            if(kiemtracapnhat){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }

        }else{
            //thêm mới món ăn
            int soluonggoi = Integer.parseInt(edtSoLuongMonAn.getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(soluonggoi);

            boolean kiemtracapnhat = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
            if(kiemtracapnhat){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
