package com.example.apporderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apporderfood.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.apporderfood.DAO.LoaiMonAnDAO;
import com.example.apporderfood.DAO.MonAnDAO;
import com.example.apporderfood.DTO.LoaiMonAnDTO;
import com.example.apporderfood.DTO.MonAnDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;

    ImageButton imThemLoaiThucDon;
    ImageView imHinhThucDon;
    Spinner spinLoaiMonAn;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    TextView txtTieuDeThemMonAn;
    EditText edThemTenMonAn, edThemGiaTien;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    String sMonAnURI, suaMonAnUri;

    int mamonan = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        InitialViewID();

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        HienThiSpinnerLoaiMonAn();

        imThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);

        mamonan = getIntent().getIntExtra("mamonan", 0);
        if(mamonan!=0){
            txtTieuDeThemMonAn.setText(getResources().getString(R.string.capnhatmonan));
            MonAnDTO monAnDTO = monAnDAO.LayThongTinMonAnTheoMa(mamonan);

            edThemTenMonAn.setText(monAnDTO.getTenMonAn());
            edThemGiaTien.setText(monAnDTO.getGiaTien());

            suaMonAnUri = monAnDTO.getHinhAnh().toString();
            Picasso.get().load(suaMonAnUri).into(imHinhThucDon);

            int postition = adapterHienThiLoaiMonAn.GetPostitionOfMaLoai(monAnDTO.getMaLoai());
            if (postition!=-1){
                spinLoaiMonAn.setSelection(postition);
            }
        }

    }

    private void InitialViewID() {
        imThemLoaiThucDon = findViewById(R.id.imThemLoaiThucDon);
        spinLoaiMonAn = findViewById(R.id.spinLoaiMonAn);
        imHinhThucDon = findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = findViewById(R.id.btnThoatThemMonAn);
        edThemTenMonAn = findViewById(R.id.edThemTenMonAn);
        edThemGiaTien = findViewById(R.id.edThemGiaTien);
        txtTieuDeThemMonAn = findViewById(R.id.txtTieuDeThemMonAn);
    }

    private void HienThiSpinnerLoaiMonAn() {
        loaiMonAnDTOList = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(ThemThucDonActivity.this, R.layout.custom_layout_spinloaithucdon, loaiMonAnDTOList);
        spinLoaiMonAn.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imThemLoaiThucDon) {
            Intent intentThemLoaiMonAn = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
            startActivityForResult(intentThemLoaiMonAn, REQUEST_CODE_THEMLOAITHUCDON);
        } else if (id == R.id.imHinhThucDon) {
            Intent intentMoHinh = new Intent();
            intentMoHinh.setType("image/*");
            intentMoHinh.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intentMoHinh, getResources().getString(R.string.chonhinhthucdon)), REQUEST_CODE_MOHINH);
        } else if (id == R.id.btnDongYThemMonAn) {
            int vitri = spinLoaiMonAn.getSelectedItemPosition();
            if (vitri != -1) {
                int maloai = loaiMonAnDTOList.get(vitri).getMaLoai();
                String tenmonan = edThemTenMonAn.getText().toString();
                String giatien = edThemGiaTien.getText().toString();

                if (tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("")) {

                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setMaLoai(maloai);
                    monAnDTO.setTenMonAn(tenmonan);
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(sMonAnURI);

                    if(mamonan !=0){
                        SuaMonAn(monAnDTO);
                        finish();
                    }else{
                        ThemBanAn(monAnDTO);
                    }
                } else
                    Toast.makeText(this, getResources().getString(R.string.nhapthongtinmonan), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btnThoatThemMonAn) {
            finish();
        }
    }

    private void SuaMonAn(MonAnDTO monAnDTO) {
        monAnDTO.setMaMonAn(mamonan);
        monAnDTO.setHinhAnh(suaMonAnUri);

        boolean check = monAnDAO.SuaMonAn(monAnDTO);
        if(check) Toast.makeText(this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
    }

    private void ThemBanAn(MonAnDTO monAnDTO) {
        boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
        if (kiemtra) Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtraloaithucdon", false);
                if (kiemtra) {
                    HienThiSpinnerLoaiMonAn();
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_CODE_MOHINH) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                imHinhThucDon.setImageURI(data.getData());
                sMonAnURI = data.getData().toString();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                    imHinhThucDon.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            }
        }
    }
}
