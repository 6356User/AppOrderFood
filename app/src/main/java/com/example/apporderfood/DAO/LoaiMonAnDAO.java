package com.example.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apporderfood.DTO.BanAnDTO;
import com.example.apporderfood.DTO.LoaiMonAnDTO;
import com.example.apporderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {

    SQLiteDatabase sqLiteDatabase;
    public LoaiMonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public boolean ThemLoaiMonAn(String tenloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);

        long kiemtra = sqLiteDatabase.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);
        if(kiemtra!=0) return true;
        else return false;
    }

    public List<LoaiMonAnDTO> LayDanhSachLoaiMonAn(){
        List<LoaiMonAnDTO> loaiMonAnDTOList = new ArrayList<>();

        String truyvan = "SELECT * FROM " +CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAnDTOList.add(loaiMonAnDTO);

            cursor.moveToNext();
        }

        return loaiMonAnDTOList;
    }

    public String LayHinhLoaiMonAn(int maloai){
        String hinhanh = "";

        String truyvan = "SELECT * FROM " +CreateDatabase.TB_MONAN
                + " WHERE " +CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai
                + "' AND " +CreateDatabase.TB_MONAN_HINHANHMONAN
                + " != '' ORDER BY " + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            hinhanh = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_HINHANHMONAN));
            cursor.moveToNext();
        }

        return hinhanh;
    }

}
