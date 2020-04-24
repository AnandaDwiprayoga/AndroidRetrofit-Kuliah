package org.z1god.retrofitrecycler;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolder {

    @GET("mahasiswa/")
    Call<List<MahasiswaModel>> getAllMahasiswa();

    @GET("mahasiswa/{nim}")
    Call<MahasiswaModel> getMahasiswaByNim(@Path("nim") String nim);

    @POST("mahasiswa/")
    Call<MessageModel> postMahasiswa(@Body MahasiswaModel mhs);

    @PUT("mahasiswa/")
    Call<MessageModel> putMahasiswa(@Body MahasiswaModel mhs);

//
//    @GET("mahasiswa/")
//    Call<List<MahasiswaModel>> getMahasiswaByNim(@Query("nim") String nim);
}
