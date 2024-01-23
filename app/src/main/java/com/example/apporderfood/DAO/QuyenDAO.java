package com.example.apporderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apporderfood.DTO.QuyenDTO;
import com.example.apporderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {

    SQLiteDatabase sqLiteDatabase;

    public QuyenDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public void ThemQuyen(String tenquyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_QUYEN_TENQUYEN,tenquyen);
        sqLiteDatabase.insert(CreateDatabase.TB_QUYEN,null,contentValues);
    }

    public List<QuyenDTO> LayDanhSachQuyen(){
        List<QuyenDTO> quyenDTOs = new ArrayList<QuyenDTO>();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_QUYEN;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            QuyenDTO quyenDTO = new QuyenDTO();
            quyenDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_QUYEN_MAQUYEN)));
            quyenDTO.setTENQUYEN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_QUYEN_TENQUYEN)));

            quyenDTOs.add(quyenDTO);

            cursor.moveToNext();
        }

        return quyenDTOs;
    }

}