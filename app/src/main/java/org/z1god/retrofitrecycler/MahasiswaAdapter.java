package org.z1god.retrofitrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder> {

    List<MahasiswaModel> mahasiswa;
    RecyclerListener myListener;

    public MahasiswaAdapter(List<MahasiswaModel> mahasiswa, RecyclerListener context) {
        this.mahasiswa = mahasiswa;
        myListener = context;
    }

    public MahasiswaAdapter(MahasiswaModel mhs, RecyclerListener context) {
        List<MahasiswaModel> mahasiswa = new ArrayList<>();
        mahasiswa.add(mhs);
        this.mahasiswa = mahasiswa;
        myListener = context;
    }

    @NonNull
    @Override
    public MahasiswaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.MyViewHolder holder, int position) {
        MahasiswaModel mhs = mahasiswa.get(position);

        holder.tvNama.setText(mhs.getNama());
        holder.tvPhone.setText(mhs.getNo_telp());
        holder.tvGender.setText(mhs.getJenis_kelamin());
        holder.tvAddress.setText(mhs.getAlamat());
        holder.tvNim.setText(mhs.getNim());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myListener.onClick(mahasiswa.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mahasiswa.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvAddress,tvPhone,tvGender,tvNim;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvGender = itemView.findViewById(R.id.tv_jk);
            tvNim = itemView.findViewById(R.id.tv_nim);
            tvPhone = itemView.findViewById(R.id.tv_hp);

        }
    }

    public interface RecyclerListener{
        void onClick(MahasiswaModel mhs);
    }
}
