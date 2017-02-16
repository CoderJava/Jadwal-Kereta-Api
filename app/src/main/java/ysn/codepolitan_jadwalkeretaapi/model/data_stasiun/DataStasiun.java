package ysn.codepolitan_jadwalkeretaapi.model.data_stasiun;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 15/02/17.
 */

public class DataStasiun {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("view")
    @Expose
    private String view;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


}
