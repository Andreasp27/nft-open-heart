package com.example.nft;

public class HistoryWallet {
    String sks, jumlah;
    int logo;

    public HistoryWallet(String sks, String jumlah, int logo) {
        this.sks = sks;
        this.jumlah = jumlah;
        this.logo = logo;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
