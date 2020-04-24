package org.z1god.retrofitrecycler;

import android.os.Parcel;
import android.os.Parcelable;

public class MahasiswaModel implements Parcelable {
    private String nim,nama,alamat,jenis_kelamin,no_telp;

    public MahasiswaModel(String nim, String nama, String alamat, String jenis_kelamin, String no_telp) {
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.no_telp = no_telp;
    }

    protected MahasiswaModel(Parcel in) {
        nim = in.readString();
        nama = in.readString();
        alamat = in.readString();
        jenis_kelamin = in.readString();
        no_telp = in.readString();
    }

    public static final Creator<MahasiswaModel> CREATOR = new Creator<MahasiswaModel>() {
        @Override
        public MahasiswaModel createFromParcel(Parcel in) {
            return new MahasiswaModel(in);
        }

        @Override
        public MahasiswaModel[] newArray(int size) {
            return new MahasiswaModel[size];
        }
    };

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nim);
        dest.writeString(nama);
        dest.writeString(alamat);
        dest.writeString(jenis_kelamin);
        dest.writeString(no_telp);
    }
}
