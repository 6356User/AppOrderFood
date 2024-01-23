package com.example.apporderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apporderfood.DAO.NhanVienDAO;
import com.example.apporderfood.DAO.QuyenDAO;
import com.example.apporderfood.DTO.NhanVienDTO;
import com.example.apporderfood.DTO.QuyenDTO;
import com.example.apporderfood.FragmentApp.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements  View.OnFocusChangeListener, View.OnClickListener {

    TextView txtTieuDeDangKy;
    EditText edtTenDangNhapDK, edtMatKhauDK, edtNgaySinhDK, edtCCCD_DK;
    Button btnDongYDK, btnThoatDK;
    RadioGroup rgGioiTinh;
    RadioButton rdNam, rdNu;
    Spinner spinQuyen;
    String sGioiTinh;
    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;

    int manhanvien = 0;
    int landautien = 0;
    List<QuyenDTO> quyenDTOList;
    List<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        /*Anh xa View*/
        InitialView();
        edtNgaySinhDK.setOnFocusChangeListener(DangKyActivity.this);
        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<>();

        for (int i=0; i<quyenDTOList.size();i++){
            String tenquyen = quyenDTOList.get(i).getTENQUYEN();
            dataAdapter.add(tenquyen);
        }

        manhanvien = getIntent().getIntExtra("manv", 0);
        landautien = getIntent().getIntExtra("landautien",0);


        if(landautien == 0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataAdapter);
            spinQuyen.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            if (quyenDTOList.size() == 0){
                quyenDAO.ThemQuyen("quản lý");
                quyenDAO.ThemQuyen("nhân viên");
            }
            spinQuyen.setVisibility(View.GONE);
        }

        if (manhanvien != 0){
            txtTieuDeDangKy.setText(getResources().getString(R.string.capnhatnhanvien));
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manhanvien);

            edtTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edtMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edtNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edtCCCD_DK.setText(nhanVienDTO.getCCCD());
            String gioitinh =  nhanVienDTO.getGIOITINH();
            if(gioitinh.equals("Nam")){
                rdNam.setChecked(true);
            }else rdNu.setChecked(true);


            String tenquyennhanvien = "";
            for (int i=0; i<quyenDTOList.size(); i++){
                int maquyen = quyenDTOList.get(i).getMAQUYEN();
                String tenquyen = quyenDTOList.get(i).getTENQUYEN();
                int maquyennhanvien = nhanVienDAO.LayQuyenNhanVien(manhanvien);
                if(maquyen == maquyennhanvien){
                    tenquyennhanvien = tenquyen;
                }
            }
            int postition = getPositionOfItemQuyen(spinQuyen, tenquyennhanvien);
            if (postition!=-1){
                spinQuyen.setSelection(postition);
            }
        }

        /*On Click Buttons*/
        btnDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
    }

    private void InitialView() {
        txtTieuDeDangKy = findViewById(R.id.txtTieuDeDangKy);
        /*Anh xa EditText*/
        edtTenDangNhapDK = findViewById(R.id.edtTenDangNhapDK);
        edtMatKhauDK = findViewById(R.id.edtMatKhauDK);
        edtNgaySinhDK = findViewById(R.id.edtNgaySinhDK);
        edtCCCD_DK = findViewById(R.id.edtCCCD_DK);
        /*Anh xa Button*/
        btnDongYDK = findViewById(R.id.btnDongYDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        /*Anh xa Radio Group*/
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        rdNam = findViewById(R.id.rbNam);
        rdNu = findViewById(R.id.rbNu);

        spinQuyen = findViewById(R.id.spinQuyen);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int idv = view.getId();
        if(idv == R.id.edtNgaySinhDK){
            if(b){
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "Ngay Sinh");
                /*Xuat popup ngay sinh*/
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnDongYDK){
            NhanVienDTO nhanVienDTO = new NhanVienDTO();

            String sTenDangNhap = edtTenDangNhapDK.getText().toString();
            String sMatKhau = edtMatKhauDK.getText().toString();
            int rbchecked = rgGioiTinh.getCheckedRadioButtonId();
            if(rbchecked == R.id.rbNam){
                sGioiTinh = "Nam";
            }else if(rbchecked == R.id.rbNu){
                sGioiTinh = "Nu";
            }
            String sNgaySinh = edtNgaySinhDK.getText().toString();
            String sCCCD = edtCCCD_DK.getText().toString();

            if(sTenDangNhap==null || sTenDangNhap.equals("")){
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhaptendangnhap), Toast.LENGTH_SHORT).show();
            }else if(sMatKhau==null || sMatKhau.equals("")){
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhapmatkhau), Toast.LENGTH_SHORT).show();
            } else{
                nhanVienDTO.setTENDN(sTenDangNhap);
                nhanVienDTO.setMATKHAU(sMatKhau);
                nhanVienDTO.setGIOITINH(sGioiTinh);
                nhanVienDTO.setNGAYSINH(sNgaySinh);
                nhanVienDTO.setCCCD(sCCCD);
                if(landautien != 0){
                    //gán mặc định quyền nhân viên là admin
                    nhanVienDTO.setMAQUYEN(1);
                }else{
                    //gán quyền bằng quyền mà admin chọn khi tạo nhân viên
                    int vitri = spinQuyen.getSelectedItemPosition();
                    int maquyen = quyenDTOList.get(vitri).getMAQUYEN();
                    nhanVienDTO.setMAQUYEN(maquyen);
                }

                if(manhanvien!=0) {SuaThongTinNhanVien(nhanVienDTO);}
                else {ThemNhanVienMoi(nhanVienDTO);}
            }
        } else if (id == R.id.btnThoatDK) {
            finish();
        }
    }

    private void SuaThongTinNhanVien(NhanVienDTO nhanVienDTO) {
        NhanVienDTO dto = nhanVienDTO;
        dto.setMANV(manhanvien);
        boolean kiemtra = nhanVienDAO.SuaNhanVien(dto);
        if(kiemtra){
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.suathanhcong),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
        }
    }

    private void ThemNhanVienMoi(NhanVienDTO nhanVienDTO) {
        long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
        if(kiemtra!=0){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
        }
    }

    private int getPositionOfItemQuyen(Spinner spinner, String item) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int position = adapter.getPosition(item);
        return position;
    }
}