package com.example.apporderfood.DTO;

public class NhanVienDTO {
    private int MANV, MAQUYEN;
    private String TENDN, MATKHAU, GIOITINH, NGAYSINH, CCCD;

    public int getMANV() {
        return MANV;
    }

    public void setMANV(int MANV) {
        this.MANV = MANV;
    }

    public int getMAQUYEN() {
        return MAQUYEN;
    }

    public void setMAQUYEN(int MAQUYEN) {
        this.MAQUYEN = MAQUYEN;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getTENDN() {
        return TENDN;
    }

    public void setTENDN(String TENDN) {
        this.TENDN = TENDN;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" +
                "MANV=" + MANV +
                ", TENDN='" + TENDN + '\'' +
                ", MATKHAU='" + MATKHAU + '\'' +
                ", GIOITINH='" + GIOITINH + '\'' +
                ", NGAYSINH='" + NGAYSINH + '\'' +
                ", CCCD='" + CCCD + '\'' +
                '}';
    }
}
