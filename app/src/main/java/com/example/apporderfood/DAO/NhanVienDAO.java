package com.example.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apporderfood.DTO.NhanVienDTO;
import com.example.apporderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    SQLiteDatabase sqLiteDatabase;

    public NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public long ThemNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN, nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CCCD, nhanVienDTO.getCCCD());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVienDTO.getMAQUYEN());

        long kiemtra = sqLiteDatabase.insert(CreateDatabase.TB_NHANVIEN, null, contentValues);
        return kiemtra;
    }

    public boolean KiemTraNhanVien(){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);

        if(cursor.getCount()!=0){
            return true;
        }else{
            return false;
        }
    }

    public int KiemTraDangNhap(String tendn, String matkhau){
        String truyvan = "SELECT * FROM " +CreateDatabase.TB_NHANVIEN
                + " WHERE " +CreateDatabase.TB_NHANVIEN_TENDN + " = '" +tendn
                + "' AND " +CreateDatabase.TB_NHANVIEN_MATKHAU + " = '" +matkhau + "'";

        int manhanvien = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            manhanvien = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MANV));
            cursor.moveToNext();
        }

        return manhanvien;
    }

    public List<NhanVienDTO> LayDanhSachNhanVien(){
        List<NhanVienDTO> nhanVienDTOs = new ArrayList<NhanVienDTO>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;

        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVienDTO nhanVienDTO = new NhanVienDTO();

            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MATKHAU)));

            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCCCD(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_CCCD)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MANV)));

            nhanVienDTOs.add(nhanVienDTO);
            cursor.moveToNext();
        }

        return nhanVienDTOs;
    }

    public boolean XoaNhanVien(int manv){

        long kiemtra = sqLiteDatabase.delete(CreateDatabase.TB_NHANVIEN,CreateDatabase.TB_NHANVIEN_MANV + " = " + manv,null);
        if(kiemtra !=0 ){
            return true;
        }else{
            return false;
        }
    }

    public NhanVienDTO LayDanhSachNhanVienTheoMa(int manv){

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manv;

        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MATKHAU)));

            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVienDTO.setCCCD(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_CCCD)));
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MANV)));

            cursor.moveToNext();
        }
        return nhanVienDTO;
    }

    public boolean SuaNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CCCD, nhanVienDTO.getCCCD());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVienDTO.getMAQUYEN());

        long check = sqLiteDatabase.update(CreateDatabase.TB_NHANVIEN, contentValues, CreateDatabase.TB_NHANVIEN_MANV + " = '" + nhanVienDTO.getMANV() + "'", null);
        if(check != 0){
            return true;
        }else{
            return false;
        }
    }

    public int LayQuyenNhanVien(int manv){
        int maquyen = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manv;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MAQUYEN));
            cursor.moveToNext();
        }

        return maquyen;
    }
}
