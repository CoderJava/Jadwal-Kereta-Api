package ysn.codepolitan_jadwalkeretaapi.model.data_jadwal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 15/02/17.
 */

public class Harga {
    @SerializedName("rp")
    @Expose
    private String rp;
    @SerializedName("subclass")
    @Expose
    private String subclass;

    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }
}
