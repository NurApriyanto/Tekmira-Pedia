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
import org.alfredo.tekmirapedia.activity.DetailEnsiklopediaActivity;
import org.alfredo.tekmirapedia.model.Ensiklopedia;

import java.util.ArrayList;

public class FavoriteEnsiklopediaAdapter extends RecyclerView.Adapter<FavoriteEnsiklopediaAdapter.FavoriteEnsiklopediaHolder> {

    Context context;
    private ArrayList<Ensiklopedia> ensiklopedias;

    public FavoriteEnsiklopediaAdapter(Context context, ArrayList<Ensiklopedia> ensiklopedias) {
        this.context = context;
        this.ensiklopedias = ensiklopedias;
    }

    public ArrayList<Ensiklopedia> getListData(){
        return ensiklopedias;
    }

    public void setListEnsiklopedia(ArrayList<Ensiklopedia> listData){
        if (listData.size() > 0){
            this.ensiklopedias.clear();
        }

        this.ensiklopedias.addAll(listData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteEnsiklopediaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new FavoriteEnsiklopediaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteEnsiklopediaHolder holder, final int position) {
        holder.indo.setText(ensiklopedias.get(position).getIstilahIndo());
        holder.inggris.setText(ensiklopedias.get(position).getIstilahInggris());
        holder.uraian.setText(ensiklopedias.get(position).getUraian());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ensiklopedia ensiklopedia = ensiklopedias.get(position);
                Intent moveData = new Intent(context, DetailEnsiklopediaActivity.class);
                moveData.putExtra("data_ensiklopedia", ensiklopedia);
                context.startActivity(moveData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ensiklopedias.size();
    }

    public class FavoriteEnsiklopediaHolder extends RecyclerView.ViewHolder {
        private TextView indo;
        private TextView inggris;
        private TextView uraian;
        public FavoriteEnsiklopediaHolder(@NonNull View itemView) {
            super(itemView);
            indo = itemView.findViewById(R.id.tv_title_indo);
            inggris = itemView.findViewById(R.id.tv_title_inggris);
            uraian = itemView.findViewById(R.id.tv_desc);
        }
    }
}
