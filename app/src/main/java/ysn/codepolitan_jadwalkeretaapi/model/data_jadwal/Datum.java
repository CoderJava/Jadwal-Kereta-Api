package ysn.codepolitan_jadwalkeretaapi.model.data_jadwal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 15/02/17.
 */

public class Datum {
    @SerializedName("kereta")
    @Expose
    private Kereta kereta;
    @SerializedName("berangkat")
    @Expose
    private Berangkat berangkat;
    @SerializedName("datang")
    @Expose
    private Datang datang;
    @SerializedName("durasi")
    @Expose
    private String durasi;
    @SerializedName("harga")
    @Expose
    private Harga harga;
    @SerializedName("tiket")
    @Expose
    private String tiket;

    public Kereta getKereta() {
        return kereta;
    }

    public void setKereta(Kereta kereta) {
        this.kereta = kereta;
    }

    public Berangkat getBerangkat() {
        return berangkat;
    }

    public void setBerangkat(Berangkat berangkat) {
        this.berangkat = berangkat;
    }

    public Datang getDatang() {
        return datang;
    }

    public void setDatang(Datang datang) {
        this.datang = datang;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public Harga getHarga() {
        return harga;
    }

    public void setHarga(Harga harga) {
        this.harga = harga;
    }

    public String getTiket() {
        return tiket;
    }

    public void setTiket(String tiket) {
        this.tiket = tiket;
    }
}
