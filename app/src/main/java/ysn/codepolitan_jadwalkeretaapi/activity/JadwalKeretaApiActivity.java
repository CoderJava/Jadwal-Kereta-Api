package ysn.codepolitan_jadwalkeretaapi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ysn.codepolitan_jadwalkeretaapi.R;
import ysn.codepolitan_jadwalkeretaapi.activity.adapter.AdapterJadwalKeretaApi;
import ysn.codepolitan_jadwalkeretaapi.api.KeretaApiService;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.Berangkat;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.DataJadwal;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.Datang;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.Datum;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.Harga;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.Kereta;

public class JadwalKeretaApiActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private DataJadwal dataJadwal;
    private TextView textViewKotaAsal;
    private TextView textViewKotaTujuan;
    private TextView textViewTanggalJadwal;
    private RecyclerView recyclerViewJadwal;
    private List<Datum> listDatum;
    private List<Integer> listViewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_kereta_api);
        EventBus.getDefault().register(this);
        initializeRetrofit();
        loadComponent();
        loadAdapter();
    }

    private void loadAdapter() {
        AdapterJadwalKeretaApi adapterJadwalKeretaApi = new AdapterJadwalKeretaApi(listDatum, listViewType, textViewKotaAsal.getText().toString(), textViewKotaTujuan.getText().toString());
        recyclerViewJadwal.setAdapter(adapterJadwalKeretaApi);
    }

    private void loadComponent() {
        textViewKotaAsal = (TextView) findViewById(R.id.text_view_kota_asal_activity_jadwal_kereta_api);
        textViewKotaTujuan = (TextView) findViewById(R.id.text_view_kota_tujuan_activity_jadwal_kereta_api);
        textViewTanggalJadwal = (TextView) findViewById(R.id.text_view_tanggal_jadwal_activity_jadwal_kereta_api);
        recyclerViewJadwal = (RecyclerView) findViewById(R.id.recycler_view_activity_jadwal_kereta_api);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewJadwal.setLayoutManager(layoutManager);

        Bundle bundle = getIntent().getExtras();
        textViewKotaAsal.setText(bundle.getString("kotaAsal"));
        textViewKotaTujuan.setText(bundle.getString("kotaTujuan"));
        textViewTanggalJadwal.setText(bundle.getString("tanggalJadwal"));
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(KeretaApiService.baseApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(JSONObject jsonObjectDataJadwal) {
        try {
            //  data
            JSONArray jsonArrayData = jsonObjectDataJadwal.getJSONArray("data");
            listDatum = new ArrayList<>();
            listViewType = new ArrayList<>();
            for(int a = 0; a < jsonArrayData.length(); a++) {
                JSONObject jsonObjectItemData = jsonArrayData.getJSONObject(a);
                Datum datum = new Datum();

                //  tiket
                String tiket = jsonObjectItemData.getString("tiket");
                if (!tiket.equalsIgnoreCase("Tersedia")) {
                    continue;
                }

                listViewType.add(AdapterJadwalKeretaApi.ITEM_HEADER);
                listViewType.add(AdapterJadwalKeretaApi.ITEM_CONTENT);
                listViewType.add(AdapterJadwalKeretaApi.ITEM_FOOTER);

                //  kereta
                JSONObject jsonObjectKereta = jsonObjectItemData.getJSONObject("kereta");
                Kereta kereta = new Kereta();
                kereta.setName(jsonObjectKereta.getString("name"));
                kereta.setClass_(jsonObjectKereta.getString("class"));

                //  berangkat
                JSONObject jsonObjectBerangkat = jsonObjectItemData.getJSONObject("berangkat");
                Berangkat berangkat = new Berangkat();
                berangkat.setJam(jsonObjectBerangkat.getString("jam"));
                berangkat.setTanggal(jsonObjectBerangkat.getString("tanggal"));

                //  datang
                JSONObject jsonObjectDatang = jsonObjectItemData.getJSONObject("datang");
                Datang datang = new Datang();
                datang.setJam(jsonObjectDatang.getString("jam"));
                datang.setTanggal(jsonObjectDatang.getString("tanggal"));

                //  durasi
                String durasi = jsonObjectItemData.getString("durasi");
                if (durasi.split(" ")[0].equalsIgnoreCase("0j")) {
                    durasi = durasi.split(" ")[1];
                }
                else if (durasi.split(" ")[1].equalsIgnoreCase("0m")) {
                    durasi = durasi.split(" ")[0];
                }

                //  harga
                JSONObject jsonObjectHarga = jsonObjectItemData.getJSONObject("harga");
                Harga harga = new Harga();
                harga.setRp(jsonObjectHarga.getString("rp"));
                harga.setSubclass(jsonObjectHarga.getString("subclass"));

                datum.setKereta(kereta);
                datum.setBerangkat(berangkat);
                datum.setDatang(datang);
                datum.setDurasi(durasi);
                datum.setHarga(harga);
                datum.setTiket(tiket);
                listDatum.add(datum);
            }

        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
    }

}
