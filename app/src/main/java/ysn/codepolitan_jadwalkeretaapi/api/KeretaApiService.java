package ysn.codepolitan_jadwalkeretaapi.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.DataJadwal;
import ysn.codepolitan_jadwalkeretaapi.model.data_stasiun.DataStasiun;

/**
 * Created by root on 15/02/17.
 */

public interface KeretaApiService {

    public final String baseApiUrl = "http://ibacor.com/";
    public final String apiKey = "<Your API Key>";

    @GET("api/kereta-api")
    Call<DataStasiun> getDataStasiun(@Query("k") String apiKey);

    @GET("api/kereta-api")
    Call<ResponseBody> getDataJadwal(
            @Query("tanggal") String tanggal,
            @Query("asal") String asal,
            @Query("tujuan") String tujuan,
            @Query("k") String apiKey
    );
}
