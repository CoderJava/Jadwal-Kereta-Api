package ysn.codepolitan_jadwalkeretaapi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ysn.codepolitan_jadwalkeretaapi.R;
import ysn.codepolitan_jadwalkeretaapi.api.KeretaApiService;
import ysn.codepolitan_jadwalkeretaapi.model.data_stasiun.DataStasiun;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivityTAG";
    private static String data;
    private Retrofit retrofit;
    private Button buttonTryAgain;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeRetrofit();
        loadComponent();
        loadData();
    }

    private void loadComponent() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_activity_main);
        buttonTryAgain = (Button) findViewById(R.id.button_try_again_activity_main);
        buttonTryAgain.setOnClickListener(this);
    }

    private void loadData() {
        KeretaApiService keretaApiService = retrofit.create(KeretaApiService.class);
        final Call<DataStasiun> resultCallDataStasiun = keretaApiService.getDataStasiun(keretaApiService.apiKey);
        resultCallDataStasiun.enqueue(new Callback<DataStasiun>() {
            @Override
            public void onResponse(Call<DataStasiun> call, Response<DataStasiun> response) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                EventBus.getDefault().postSticky(response.body());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DataStasiun> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Koneksi timeout", Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.GONE);
                buttonTryAgain.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(KeretaApiService.baseApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Override
    public void onClick(View view) {
        if (view == buttonTryAgain) {
            progressBar.setVisibility(View.VISIBLE);
            buttonTryAgain.setVisibility(View.GONE);
            loadData();
        }
    }

}
