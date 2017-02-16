package ysn.codepolitan_jadwalkeretaapi.model.data_stasiun;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 15/02/17.
 */

public class Stasiun {
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }
}
