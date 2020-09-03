package org.alfredo.tekmirapedia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.alfredo.tekmirapedia.R;
import org.alfredo.tekmirapedia.activity.DetailKamusActivity;
import org.alfredo.tekmirapedia.model.Kamus;

import java.util.ArrayList;

public class FavoriteKamusAdapter extends RecyclerView.Adapter<FavoriteKamusAdapter.FavoriteKamusHolder> {
    
    Context context;
    private ArrayList<Kamus> kamuses;

    public FavoriteKamusAdapter(Context context, ArrayList<Kamus> Kamuses) {
        this.context = context;
        this.kamuses = Kamuses;
    }

    public ArrayList<Kamus> getListData(){
        return kamuses;
    }

    public void setListKamus(ArrayList<Kamus> listData){
        if (listData.size() > 0){
            this.kamuses.clear();
        }

        this.kamuses.addAll(listData);
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public FavoriteKamusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new FavoriteKamusAdapter.FavoriteKamusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteKamusHolder holder, final int position) {
        holder.indo.setText(kamuses.get(position).getIndo());
        holder.inggris.setText(kamuses.get(position).getInggris());
        holder.uraian.setText(kamuses.get(position).getUraian());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kamus kamus = kamuses.get(position);
                Intent moveData = new Intent(context, DetailKamusActivity.class);
                moveData.putExtra("data_kamus", kamus);
                context.startActivity(moveData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kamuses.size();
    }

    public class FavoriteKamusHolder extends RecyclerView.ViewHolder {
        private TextView indo;
        private TextView inggris;
        private TextView uraian;
        public FavoriteKamusHolder(@NonNull View itemView) {
            super(itemView);
            indo = itemView.findViewById(R.id.tv_title_indo);
            inggris = itemView.findViewById(R.id.tv_title_inggris);
            uraian = itemView.findViewById(R.id.tv_desc);
        }
    }
}
