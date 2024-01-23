package com.example.apporderfood;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.apporderfood.CustomAdapter.AdapterHienThiThanhToan;
import com.example.apporderfood.DAO.BanAnDAO;
import com.example.apporderfood.DAO.GoiMonDAO;
import com.example.apporderfood.DTO.ThanhToanDTO;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gvThanhToan;
    Button btnThanhToan, btnThoatThanhToan;
    TextView txtTongTien;

    int maban = 0;
    long tongtien = 0;
    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    AdapterHienThiThanhToan adapterHienThiThanhToan;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        gvThanhToan = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoatThanhToan = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);

        maban = getIntent().getIntExtra("maban",0);
        if(maban != 0){

            HienThiThanhToan();

            for (int i=0; i < thanhToanDTOList.size() ; i++){
                int soluong = thanhToanDTOList.get(i).getSoLuong();
                int giatien = thanhToanDTOList.get(i).getGiaTien();

                tongtien += (soluong*giatien); // tongtien = tongtien + (soluong*giatien)
            }

            txtTongTien.setText(getResources().getString(R.string.tongtienthanhtoan) + " " + tongtien);
        }

        btnThanhToan.setOnClickListener(this);
        btnThoatThanhToan.setOnClickListener(this);

    }

    private void HienThiThanhToan(){
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        thanhToanDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this,R.layout.custom_layout_thanhtoan,thanhToanDTOList);
        gvThanhToan.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnThanhToan){

            boolean kiemtrabanan = banAnDAO.CapNhatLaiTinhTrangBan(maban,"false");
            boolean kiemtragoimon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban,"true");
            if(kiemtrabanan && kiemtragoimon){
                Toast.makeText(ThanhToanActivity.this,getResources().getString(R.string.thanhtoanthanhcong), Toast.LENGTH_SHORT).show();
                HienThiThanhToan();
            }else{
                Toast.makeText(ThanhToanActivity.this,getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
            }

        }else if(id == R.id.btnThoatThanhToan){
            finish();
        }
    }
}
