package com.example.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apporderfood.DTO.BanAnDTO;
import com.example.apporderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {

    SQLiteDatabase sqLiteDatabase;
    public BanAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public boolean ThemBanAn(String tenbanan){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenbanan);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long kiemtra = sqLiteDatabase.insert(CreateDatabase.TB_BANAN, null, contentValues);
        if(kiemtra!=0) return true;
        else return false;
    }

    public List<BanAnDTO> LayDanhSachBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<>();
        String truyvan = "SELECT * FROM " +CreateDatabase.TB_BANAN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMABAN(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTENBAN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_BANAN_TENBAN)));
            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }

        return banAnDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maban){
        String status = "";
        String query = "SELECT * FROM " + CreateDatabase.TB_BANAN
                + " WHERE " + CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            status = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }
        return status;
    }

    public boolean CapNhatLaiTinhTrangBan(int maban,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG,tinhtrang);

        long kiemtra = sqLiteDatabase.update(CreateDatabase.TB_BANAN,contentValues,CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'",null );

        if(kiemtra !=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean XoaBanAnTheoMa(int maban){
        long kiemtra = sqLiteDatabase.delete(CreateDatabase.TB_BANAN,CreateDatabase.TB_BANAN_MABAN + " = " + maban,null);
        if(kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean CapNhatLaiTenBan(int maban,String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN,tenban);

        long kiemtra = sqLiteDatabase.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'", null);

        if(kiemtra !=0){
            return true;
        }else{
            return false;
        }
    }

}
