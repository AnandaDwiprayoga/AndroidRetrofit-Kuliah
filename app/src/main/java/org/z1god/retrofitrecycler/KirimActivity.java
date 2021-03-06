package org.z1god.retrofitrecycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KirimActivity extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    public static final String KEY_IS_UPDATE = "KEY_IS_UPDATE";
    public static final String KEY_MHS_TO_UPDATE = "KEY_MHS_TO_UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim);

        Button btnAddMahasiswa = findViewById(R.id.btn_tambah);
        EditText etNim = findViewById(R.id.et_nim_add);
        EditText etName = findViewById(R.id.et_nama_add);
        EditText etAddress = findViewById(R.id.et_address_add);
        EditText etPhone = findViewById(R.id.et_notelp_add);
        ProgressBar loading = findViewById(R.id.loading_add);
        spinner = findViewById(R.id.spinner_gender);

        setSpinnerGender();

        boolean isEdit = getIntent().getBooleanExtra(KEY_IS_UPDATE,false);
        if(isEdit){
            MahasiswaModel mhs = getIntent().getParcelableExtra(KEY_MHS_TO_UPDATE);

            etNim.setText(mhs.getNim());
            etName.setText(mhs.getNama());
            etAddress.setText(mhs.getAlamat());
            etPhone.setText(mhs.getNo_telp());
            spinner.setSelection(adapter.getPosition(mhs.getJenis_kelamin()));

            etNim.setEnabled(false);
            btnAddMahasiswa.setText("Update Mahasiswa");
        }

        btnAddMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);

                MahasiswaModel mhs = new MahasiswaModel(
                        etNim.getText().toString(),
                        etName.getText().toString(),
                        etAddress.getText().toString(),
                        spinner.getSelectedItem().toString(),
                        etPhone.getText().toString()
                );


                if(isEdit){
                    Call<MessageModel> request = ApiConfig.getApiService().putMahasiswa(mhs);
                    request.enqueue(new Callback<MessageModel>() {
                        @Override
                        public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(KirimActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }else{
                                Gson gson = new Gson();
                                try {
                                    MessageModel errorBody = gson.fromJson(response.errorBody().string(), MessageModel.class);
                                    Toast.makeText(KirimActivity.this, errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            loading.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<MessageModel> call, Throwable t) {
                            Toast.makeText(KirimActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.INVISIBLE);
                        }
                    });
                }else {
                    Call<MessageModel> request = ApiConfig.getApiService().postMahasiswa(mhs);
                    request.enqueue(new Callback<MessageModel>() {
                        @Override
                        public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(KirimActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Gson gson = new Gson();
                                try {
                                    MessageModel errorBody = gson.fromJson(response.errorBody().string(), MessageModel.class);
                                    Toast.makeText(KirimActivity.this, errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            loading.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<MessageModel> call, Throwable t) {
                            Toast.makeText(KirimActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void setSpinnerGender() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void finishActivity(View view) {
        finish();
    }
}
