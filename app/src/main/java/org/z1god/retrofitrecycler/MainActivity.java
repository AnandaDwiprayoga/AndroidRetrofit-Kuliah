package org.z1god.retrofitrecycler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.z1god.retrofitrecycler.KirimActivity.KEY_IS_UPDATE;
import static org.z1god.retrofitrecycler.KirimActivity.KEY_MHS_TO_UPDATE;

public class MainActivity extends AppCompatActivity implements MahasiswaAdapter.RecyclerListener {
    private RecyclerView recyclerView;
    private MahasiswaAdapter adapter;
    private TextView tvError;
    private ProgressBar loading;
    private Gson gson;

    public static final int RC_GOTO_KIRIM = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.loading);
        tvError  = findViewById(R.id.tv_error);
        recyclerView = findViewById(R.id.rv_mahasiswa);
        Button btnSearch = findViewById(R.id.btn_search);
        EditText inputSearch = findViewById(R.id.et_search);
        SwipeRefreshLayout swipe = findViewById(R.id.swipe_to_refresh);

        gson = new Gson();

        loading.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllMhs();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMhs();

                swipe.setRefreshing(false);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputSearch.getText().toString())){
                    Toast.makeText(MainActivity.this, "masukkan nim terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    loading.setVisibility(View.VISIBLE);
                    getMahasiswaByNim(inputSearch.getText().toString());
                }
            }
        });

    }

    private void getMahasiswaByNim(String nim) {
        Call<MahasiswaModel> call = ApiConfig.getApiService().getMahasiswaByNim(nim);
        call.enqueue(new Callback<MahasiswaModel>() {
            @Override
            public void onResponse(Call<MahasiswaModel> call, Response<MahasiswaModel> response) {
                if (response.isSuccessful()){
                    tvError.setVisibility(View.INVISIBLE);

                    adapter = new MahasiswaAdapter(response.body(), MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }else{
                    try {
                        MessageModel error =gson.fromJson(response.errorBody().string(), MessageModel.class);
                        tvError.setVisibility(View.VISIBLE);

                        tvError.setText(error.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MahasiswaModel> call, Throwable t) {
                tvError.setVisibility(View.VISIBLE);

                tvError.setText(t.getMessage());
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getAllMhs() {
        Call<List<MahasiswaModel>> call = ApiConfig.getApiService().getAllMahasiswa();
        call.enqueue(new Callback<List<MahasiswaModel>>() {
            @Override
            public void onResponse(Call<List<MahasiswaModel>> call, Response<List<MahasiswaModel>> response) {
                if (response.isSuccessful()){
                    tvError.setVisibility(View.INVISIBLE);

                    adapter = new MahasiswaAdapter(response.body(),MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }else{
                    try {
                        MessageModel error =gson.fromJson(response.errorBody().string(), MessageModel.class);
                        tvError.setVisibility(View.VISIBLE);

                        tvError.setText(error.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<MahasiswaModel>> call, Throwable t) {
                tvError.setVisibility(View.VISIBLE);

                tvError.setText(t.getMessage());
                loading.setVisibility(View.INVISIBLE);
            }

        });
    }

    public void addMahasiswa(View view) {
        startActivityForResult(new Intent(this,KirimActivity.class), RC_GOTO_KIRIM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == RC_GOTO_KIRIM){
                getAllMhs();
            }
        }
    }

    @Override
    public void onClick(MahasiswaModel mhs) {
        Intent intent = new Intent(this, KirimActivity.class);
        intent.putExtra(KEY_IS_UPDATE,true);
        intent.putExtra(KEY_MHS_TO_UPDATE,mhs);
        startActivityForResult(intent, RC_GOTO_KIRIM);
    }
}
