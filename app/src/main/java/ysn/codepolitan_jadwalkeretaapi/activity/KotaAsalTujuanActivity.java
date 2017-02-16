package ysn.codepolitan_jadwalkeretaapi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ysn.codepolitan_jadwalkeretaapi.R;
import ysn.codepolitan_jadwalkeretaapi.activity.adapter.AdapterKotaAsalTujuan;

public class KotaAsalTujuanActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "KotaAsalTAG";
    private EditText editTextInputKotaAsal;
    private Button buttonBatal;
    private RecyclerView recyclerViewKotaAsal;
    private AdapterKotaAsalTujuan adapterKotaAsalTujuan;
    private List<String> listNamaKotaStasiun;
    private List<String> listValueNamaKotaStasiun;
    private List<String> listNamaKotaBesar;
    private boolean isActivityKotaAsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kota_asal_tujuan);
        EventBus.getDefault().register(this);
        loadComponent();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void loadComponent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_kota_asal_tujuan);
        setSupportActionBar(toolbar);

        editTextInputKotaAsal = (EditText) findViewById(R.id.edit_text_input_kota_asal_activity_kota_asal_tujuan);
        buttonBatal = (Button) findViewById(R.id.button_batal_activity_kota_asal_tujuan);
        recyclerViewKotaAsal = (RecyclerView) findViewById(R.id.recycler_view_activity_kota_asal_tujuan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewKotaAsal.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewKotaAsal.addItemDecoration(dividerItemDecoration);

        editTextInputKotaAsal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String keywordInputNamaKota = editTextInputKotaAsal.getText().toString();
                Map<String, List<String>> mapListData = new HashMap<String, List<String>>();
                if (TextUtils.isEmpty(keywordInputNamaKota)) {
                    mapListData.put("namaKotaStasiun", listNamaKotaStasiun);
                    mapListData.put("valueNamaKotaStasiun", listValueNamaKotaStasiun);
                    mapListData.put("namaKotaBesar", listNamaKotaBesar);
                    adapterKotaAsalTujuan.refreshData(mapListData);
                    adapterKotaAsalTujuan.notifyDataSetChanged();
                } else {
                    List<String> listNamaKotaStasiunKeyword = new ArrayList<String>();
                    List<String> listValueNamaKotaStasiunKeyword = new ArrayList<String>();
                    List<String> listNamaKotaBesarKeyword = new ArrayList<String>();
                    for(int a = 0; a < listNamaKotaStasiun.size(); a++) {
                        if (listNamaKotaStasiun.get(a).toLowerCase().contains(keywordInputNamaKota.toLowerCase())
                                || listNamaKotaBesar.get(a).toLowerCase().contains(keywordInputNamaKota.toLowerCase())) {
                            listNamaKotaStasiunKeyword.add(listNamaKotaStasiun.get(a));
                            listValueNamaKotaStasiunKeyword.add(listValueNamaKotaStasiun.get(a));
                            listNamaKotaBesarKeyword.add(listNamaKotaBesar.get(a));
                        }
                    }
                    mapListData.put("namaKotaStasiun", listNamaKotaStasiunKeyword);
                    mapListData.put("valueNamaKotaStasiun", listValueNamaKotaStasiunKeyword);
                    mapListData.put("namaKotaBesar", listNamaKotaBesarKeyword);
                    adapterKotaAsalTujuan.refreshData(mapListData);
                    adapterKotaAsalTujuan.notifyDataSetChanged();
                }
            }
        });
        buttonBatal.setOnClickListener(this);
    }

    private void loadData() {
        recyclerViewKotaAsal.setAdapter(adapterKotaAsalTujuan);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonBatal) {
            finish();
        }
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(Map<String, List<String>> mapListData) {
        listNamaKotaStasiun = mapListData.get("namaKotaStasiun");
        listValueNamaKotaStasiun = mapListData.get("valueNamaKotaStasiun");
        listNamaKotaBesar = mapListData.get("namaKotaBesar");
        Bundle bundle = getIntent().getExtras();
        isActivityKotaAsal = bundle.getBoolean("isActivityKotaAsal");
        AdapterKotaAsalTujuan.OnItemClickListener onItemClickListener = new AdapterKotaAsalTujuan.OnItemClickListener() {
            @Override
            public void onClick(String valueKota, String kotaName) {
                Map<String, Object> mapDataKota = new HashMap<>();
                mapDataKota.put("valueKota", valueKota);
                mapDataKota.put("kotaName", kotaName);
                mapDataKota.put("isKotaAsal", isActivityKotaAsal);
                EventBus.getDefault().post(mapDataKota);
                finish();
            }
        };
        adapterKotaAsalTujuan = new AdapterKotaAsalTujuan(listNamaKotaStasiun, listValueNamaKotaStasiun, listNamaKotaBesar, onItemClickListener);
    }
}
