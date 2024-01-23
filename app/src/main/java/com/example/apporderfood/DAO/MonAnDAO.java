package com.example.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apporderfood.DTO.MonAnDTO;
import com.example.apporderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    SQLiteDatabase sqLiteDatabase;
    public MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public boolean ThemMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANHMONAN, monAnDTO.getHinhAnh());

        long kiemtra = sqLiteDatabase.insert(CreateDatabase.TB_MONAN, null, contentValues);
        if(kiemtra!=0){
            return true;
        }else{
            return false;
        }
    }

    public List<MonAnDTO> LayDanhSachMonAnTheoLoai(int maloai){
        List<MonAnDTO> monAnDTOs = new ArrayList<MonAnDTO>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            MonAnDTO monAnDTO = new MonAnDTO();
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_HINHANHMONAN)) + "");
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOs.add(monAnDTO);
            cursor.moveToNext();
        }

        return monAnDTOs;
    }

    public boolean XoaMonAn(int mamonan){
        long check = sqLiteDatabase.delete(CreateDatabase.TB_MONAN, CreateDatabase.TB_MONAN_MAMON + " = "+ mamonan, null);
        if(check!=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean SuaMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANHMONAN, monAnDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAnDTO.getMaLoai());

        long check = sqLiteDatabase.update(CreateDatabase.TB_MONAN, contentValues, CreateDatabase.TB_MONAN_MAMON + " = " + monAnDTO.getMaMonAn(), null);
        if (check!=0){
            return true;
        }else{
            return false;
        }
    }

    public MonAnDTO LayThongTinMonAnTheoMa(int mamonan){
        MonAnDTO monAnDTO = new MonAnDTO();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MAMON + " = " +mamonan;

        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_HINHANHMONAN)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_MALOAI)));

            cursor.moveToNext();
        }

        return monAnDTO;
    }
}
