package ysn.codepolitan_jadwalkeretaapi.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ysn.codepolitan_jadwalkeretaapi.R;
import ysn.codepolitan_jadwalkeretaapi.api.KeretaApiService;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.DataJadwal;
import ysn.codepolitan_jadwalkeretaapi.model.data_jadwal.Kereta;
import ysn.codepolitan_jadwalkeretaapi.model.data_stasiun.DataStasiun;
import ysn.codepolitan_jadwalkeretaapi.model.data_stasiun.Stasiun;
import ysn.codepolitan_jadwalkeretaapi.model.data_stasiun.Tanggal;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivityTAG";
    private DataStasiun dataStasiun;
    private Retrofit retrofit;
    private EditText editTextAsalKeberangkatan;
    private EditText editTextTujuanKeberangkatan;
    private EditText editTextTanggalKeberangkatan;
    private Button buttonCekJadwalKeberangkatan;

    private List<String> listNamaKotaStasiun;
    private List<String> listValueNamaKotaStasiun;
    private List<String> listNamaKotaBesar;
    private String valueKotaAsal;
    private String valueKotaTujuan;
    private String valueTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventBus.getDefault().register(this);
        initializeRetrofit();
        loadComponent();
        loadData();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(KeretaApiService.baseApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void loadComponent() {
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_home);
        setSupportActionBar(toolbar);*/

        editTextAsalKeberangkatan = (EditText) findViewById(R.id.edit_text_kota_asal_activity_home);
        editTextTujuanKeberangkatan = (EditText) findViewById(R.id.edit_text_kota_tujuan_activity_home);
        editTextTanggalKeberangkatan = (EditText) findViewById(R.id.edit_text_tanggal_keberangkatan_activity_home);
        buttonCekJadwalKeberangkatan = (Button) findViewById(R.id.button_cek_jadwal_activity_home);

        editTextAsalKeberangkatan.setOnClickListener(this);
        editTextTujuanKeberangkatan.setOnClickListener(this);
        editTextTanggalKeberangkatan.setOnClickListener(this);
        buttonCekJadwalKeberangkatan.setOnClickListener(this);
    }

    private void loadData() {
        //  list kota asal
        listNamaKotaStasiun = new ArrayList<>();
        listValueNamaKotaStasiun = new ArrayList<>();
        listNamaKotaBesar = new ArrayList<>();
        List<Stasiun> listStasiun = dataStasiun.getData().getStasiun();
        for (Stasiun stasiun : listStasiun) {
            String kotaBesar = stasiun.getKota();
            kotaBesar = String.valueOf(kotaBesar.charAt(0)).toUpperCase() + "" + kotaBesar.substring(1).toLowerCase();
             java.util.List<ysn.codepolitan_jadwalkeretaapi.model.data_stasiun.List> listNamaKotaDetail = stasiun.getList();
            for (ysn.codepolitan_jadwalkeretaapi.model.data_stasiun.List dataDetail : listNamaKotaDetail) {
                String namaKotaStasiun = dataDetail.getName();
                String valueNamaKotaStasiun = dataDetail.getValue();
                listNamaKotaStasiun.add(namaKotaStasiun);
                listValueNamaKotaStasiun.add(valueNamaKotaStasiun);
                listNamaKotaBesar.add(kotaBesar);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*EventBus.getDefault().unregister(this);*/
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(DataStasiun dataStasiun) {
        if (dataStasiun != null) {
            this.dataStasiun = dataStasiun;
        }
    }

    @Subscribe
    public void onMessageEvent(Map<String, Object> mapDataKota) {
        if (mapDataKota != null && mapDataKota.containsKey("isKotaAsal")) {
            boolean isKotaAsal = (boolean) mapDataKota.get("isKotaAsal");
            String kotaValue = (String) mapDataKota.get("valueKota");
            String kotaName = (String) mapDataKota.get("kotaName");
            if (isKotaAsal) {
                valueKotaAsal = kotaValue;
                editTextAsalKeberangkatan.setText(kotaName);
            } else {
                valueKotaTujuan = kotaValue;
                editTextTujuanKeberangkatan.setText(kotaName);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == editTextAsalKeberangkatan) {
            Map<String, List<String>> mapListDataKotaAsal = new HashMap<>();
            mapListDataKotaAsal.put("namaKotaStasiun", listNamaKotaStasiun);
            mapListDataKotaAsal.put("valueNamaKotaStasiun", listValueNamaKotaStasiun);
            mapListDataKotaAsal.put("namaKotaBesar", listNamaKotaBesar);
            EventBus.getDefault().postSticky(mapListDataKotaAsal);
            Intent intent = new Intent(this, KotaAsalTujuanActivity.class);
            intent.putExtra("isActivityKotaAsal", true);
            startActivity(intent);
        } else if (view == editTextTujuanKeberangkatan) {
            Map<String, List<String>> mapListDataKotaTujuan = new HashMap<>();
            mapListDataKotaTujuan.put("namaKotaStasiun", listNamaKotaStasiun);
            mapListDataKotaTujuan.put("valueNamaKotaStasiun", listValueNamaKotaStasiun);
            mapListDataKotaTujuan.put("namaKotaBesar", listNamaKotaBesar);
            EventBus.getDefault().postSticky(mapListDataKotaTujuan);
            Intent intent = new Intent(this, KotaAsalTujuanActivity.class);
            intent.putExtra("isActivityKotaAsal", false);
            startActivity(intent);
        } else if (view == editTextTanggalKeberangkatan) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    Calendar calendarSet = new GregorianCalendar();
                    calendarSet.set(Calendar.YEAR, year);
                    calendarSet.set(Calendar.MONTH, month);
                    calendarSet.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    editTextTanggalKeberangkatan.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy").format(calendarSet.getTime()));
                    String namaHari = editTextTanggalKeberangkatan.getText().toString().split(",")[0];
                    if (namaHari.equalsIgnoreCase("Sunday")) {
                        namaHari = "Minggu,";
                    } else if (namaHari.equalsIgnoreCase("Monday")) {
                        namaHari = "Senin,";
                    } else if (namaHari.equalsIgnoreCase("Tuesday")) {
                        namaHari = "Selasa,";
                    } else if (namaHari.equalsIgnoreCase("Wednesday")) {
                        namaHari = "Rabu,";
                    } else if (namaHari.equalsIgnoreCase("Thursday")) {
                        namaHari = "Kamis,";
                    } else if (namaHari.equalsIgnoreCase("Friday")) {
                        namaHari = "Jumat,";
                    } else if (namaHari.equalsIgnoreCase("Saturday")) {
                        namaHari = "Sabtu,";
                    }

                    String namaBulan = editTextTanggalKeberangkatan.getText().toString().split(" ")[2];
                    if (namaBulan.equalsIgnoreCase("January")) {
                        namaBulan = "Januari";
                    } else if (namaBulan.equalsIgnoreCase("February")) {
                        namaBulan = "Februari";
                    } else if (namaBulan.equalsIgnoreCase("March")) {
                        namaBulan = "Maret";
                    } else if (namaBulan.equalsIgnoreCase("April")) {
                        namaBulan = "April";
                    } else if (namaBulan.equalsIgnoreCase("May")) {
                        namaBulan = "Mei";
                    } else if (namaBulan.equalsIgnoreCase("June")) {
                        namaBulan = "Juni";
                    } else if (namaBulan.equalsIgnoreCase("July")) {
                        namaBulan = "Juli";
                    } else if (namaBulan.equalsIgnoreCase("August")) {
                        namaBulan = "Agustus";
                    } else if (namaBulan.equalsIgnoreCase("September")) {
                        namaBulan = "September";
                    } else if (namaBulan.equalsIgnoreCase("October")) {
                        namaBulan = "Oktober";
                    } else if (namaBulan.equalsIgnoreCase("November")) {
                        namaBulan = "November";
                    } else if (namaBulan.equalsIgnoreCase("December")) {
                        namaBulan = "Desember";
                    }
                    String[] splitDate = editTextTanggalKeberangkatan.getText().toString().split(" ");
                    String strDate = namaHari + " " + splitDate[1] + " " + namaBulan + " " + splitDate[3];
                    editTextTanggalKeberangkatan.setText(strDate);
                    List<Tanggal> listTanggal = dataStasiun.getData().getTanggal();
                    for (Tanggal tanggal : listTanggal) {
                        String nameTanggal = tanggal.getName();
                        if (editTextTanggalKeberangkatan.getText().toString().equalsIgnoreCase(nameTanggal)) {
                            valueTanggal = tanggal.getValue();
                            break;
                        }
                    }
                    valueTanggal = (valueTanggal.equals("-") || TextUtils.isEmpty(valueTanggal)) ? "-" : valueTanggal;
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else if (view == buttonCekJadwalKeberangkatan) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Harap tunggu");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            Log.d(TAG, "valueTanggal: " + valueTanggal);
            Log.d(TAG, "valueKotaAsal: " + valueKotaAsal);
            Log.d(TAG, "valueKotaTujuan: " + valueKotaTujuan);
            Log.d(TAG, "apiKey: " + KeretaApiService.apiKey);
            KeretaApiService keretaApiService = retrofit.create(KeretaApiService.class);
            Call<ResponseBody> resultGetResponseBody = keretaApiService.getDataJadwal(valueTanggal, valueKotaAsal, valueKotaTujuan, KeretaApiService.apiKey);
            resultGetResponseBody.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressDialog.dismiss();
                    try {
                        String dataJadwaljson = response.body().string();
                        JSONObject jsonObjectDataJadwalJson = new JSONObject(dataJadwaljson);
                        String status = jsonObjectDataJadwalJson.getString("status");
                        if (status.equalsIgnoreCase("Success")) {
                            EventBus.getDefault().postSticky(jsonObjectDataJadwalJson);
                            Intent intent = new Intent(HomeActivity.this, JadwalKeretaApiActivity.class);
                            intent.putExtra("kotaAsal", editTextAsalKeberangkatan.getText().toString());
                            intent.putExtra("kotaTujuan", editTextTujuanKeberangkatan.getText().toString());
                            intent.putExtra("tanggalJadwal", editTextTanggalKeberangkatan.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeActivity.this, "Data jadwal tidak tersedia", Toast.LENGTH_LONG)
                                    .show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                    Toast.makeText(HomeActivity.this, "Koneksi timeout. Silakan coba lagi", Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
    }
}
