package com.example.apporderfood.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    /*Ten cac bang*/
    public static final String TB_NHANVIEN = "NHANVIEN";
    public static final String TB_MONAN = "MONAN";
    public static final String TB_LOAIMONAN = "LOAIMONAN";
    public static final String TB_BANAN = "BANAN";
    public static final String TB_GOIMON = "GOIMON";
    public static final String TB_CHITIETGOIMON = "CHITIETGOIMON";
    public static final String TB_QUYEN = "QUYEN";



    /*Thuoc tinh bang QUYEN*/
    public static String TB_QUYEN_MAQUYEN = "MAQUYEN";
    public static String TB_QUYEN_TENQUYEN = "TENQUYEN";


    /*Thuoc tinh bang NHANVIEN*/
    public static final String TB_NHANVIEN_MANV = "MANV";
    public static String TB_NHANVIEN_MAQUYEN = "MAQUYEN";
    public static final String TB_NHANVIEN_TENDN = "TENDN";
    public static final String TB_NHANVIEN_MATKHAU = "MATKHAU";
    public static final String TB_NHANVIEN_GIOITINH = "GIOITINH";
    public static final String TB_NHANVIEN_NGAYSINH = "NGAYSINH";
    public static final String TB_NHANVIEN_CCCD = "CCCD";



    /*Thuoc tinh bang MONAN*/
    public static final String TB_MONAN_MAMON = "MAMON";
    public static final String TB_MONAN_TENMONAN = "TENMONAN";
    public static final String TB_MONAN_GIATIEN = "GIATIEN";
    public static final String TB_MONAN_MALOAI = "MALOAI";
    public static final String TB_MONAN_HINHANHMONAN = "HINHANHMONAN";



    /*Thuoc tinh bang LOAIMONAN*/
    public static final String TB_LOAIMONAN_MALOAI = "MALOAI";
    public static final String TB_LOAIMONAN_TENLOAI = "TENLOAI";



    /*Thuoc tinh bang BANAN*/
    public static final String TB_BANAN_MABAN = "MABAN";
    public static final String TB_BANAN_TENBAN = "TENBAN";
    public static final String TB_BANAN_TINHTRANG = "TINHTRANG";



    /*Thuoc tinh bang GOIMON*/
    public static final String TB_GOIMON_MAGOIMON = "MAGOIMON";
    public static final String TB_GOIMON_MANV = "MANV";
    public static final String TB_GOIMON_NGAYGOI = "NGAYGOI";
    public static final String TB_GOIMON_TINHTRANG = "TINHTRANG";
    public static final String TB_GOIMON_MABAN = "MABAN";



    /*Thuoc tinh bang CHITIETGOIMON*/
    public static final String TB_CHITIETGOIMON_MAGOIMON = "MAGOIMON";
    public static final String TB_CHITIETGOIMON_MAMONAN = "MAMONAN";
    public static final String TB_CHITIETGOIMON_SOLUONG = "SOLUONG";





    public CreateDatabase(@Nullable Context context) {
        super(context, "OrderFood", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String tbNHANVIEN = "CREATE TABLE " +TB_NHANVIEN + " ( "
                +TB_NHANVIEN_MANV + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TB_NHANVIEN_TENDN + " TEXT, "
                +TB_NHANVIEN_MATKHAU + " TEXT, "
                +TB_NHANVIEN_GIOITINH + " TEXT, "
                +TB_NHANVIEN_NGAYSINH + " TEXT, "
                +TB_NHANVIEN_CCCD + " INTEGER, "
                +TB_NHANVIEN_MAQUYEN + " INTEGER )";


        String tbBANAN = "CREATE TABLE " +TB_BANAN + " ( "
                +TB_BANAN_MABAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TB_BANAN_TENBAN + " TEXT, "
                +TB_BANAN_TINHTRANG + " TEXT )";


        String tbMONAN = "CREATE TABLE " +TB_MONAN + " ( "
                +TB_MONAN_MAMON + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TB_MONAN_TENMONAN + " TEXT, "
                +TB_MONAN_MALOAI + " INTEGER, "
                +TB_MONAN_GIATIEN + " TEXT, "
                +TB_MONAN_HINHANHMONAN + " TEXT )";


        String tbLOAIMONAN = "CREATE TABLE " +TB_LOAIMONAN + " ( "
                +TB_LOAIMONAN_MALOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TB_LOAIMONAN_TENLOAI + " TEXT )";


        String tbGOIMON = "CREATE TABLE " +TB_GOIMON + " ( "
                +TB_GOIMON_MAGOIMON + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TB_GOIMON_MABAN + " INTEGER, "
                +TB_GOIMON_MANV + " INTEGER, "
                +TB_GOIMON_NGAYGOI + " TEXT, "
                +TB_GOIMON_TINHTRANG + " TEXT )";


        String tbCHITIETGOIMON = "CREATE TABLE " +TB_CHITIETGOIMON + " ( "
                +TB_CHITIETGOIMON_MAGOIMON + " INTEGER, "
                +TB_CHITIETGOIMON_MAMONAN + " INTEGER, "
                +TB_CHITIETGOIMON_SOLUONG + " INTEGER, "
                + "PRIMARY KEY ( " +TB_CHITIETGOIMON_MAGOIMON + ", " +TB_CHITIETGOIMON_MAMONAN + " ))";


        String tbQUYEN = "CREATE TABLE " + TB_QUYEN + " ( " + TB_QUYEN_MAQUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TB_QUYEN_TENQUYEN + " TEXT )";



        sqLiteDatabase.execSQL(tbNHANVIEN);
        sqLiteDatabase.execSQL(tbBANAN);
        sqLiteDatabase.execSQL(tbMONAN);
        sqLiteDatabase.execSQL(tbLOAIMONAN);
        sqLiteDatabase.execSQL(tbGOIMON);
        sqLiteDatabase.execSQL(tbCHITIETGOIMON);
        sqLiteDatabase.execSQL(tbQUYEN);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }

}
