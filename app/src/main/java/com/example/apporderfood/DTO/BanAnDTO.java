package com.example.apporderfood.DTO;

public class BanAnDTO {

    private int MABAN;
    private String TENBAN;
    boolean DuocChon;

    public boolean isDuocChon() {
        return DuocChon;
    }

    public void setDuocChon(boolean duocChon) {
        DuocChon = duocChon;
    }

    public int getMABAN() {
        return MABAN;
    }

    public void setMABAN(int MABAN) {
        this.MABAN = MABAN;
    }

    public String getTENBAN() {
        return TENBAN;
    }

    public void setTENBAN(String TENBAN) {
        this.TENBAN = TENBAN;
    }
}
