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
import java.util.List;

public class EnsiklopediaAdapter extends RecyclerView.Adapter<EnsiklopediaAdapter.EnsiklopediaHolder> {

    Context context;
    List<Ensiklopedia> ensiklopedias;

    public EnsiklopediaAdapter(Context context, List<Ensiklopedia> ensiklopedias) {
        this.context = context;
        this.ensiklopedias = ensiklopedias;
    }

    @NonNull
    @Override
    public EnsiklopediaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new EnsiklopediaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnsiklopediaHolder holder, final int position) {
//        holder.bind(ensiklopedias.get(position));
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

    public class EnsiklopediaHolder extends RecyclerView.ViewHolder {
        private TextView indo;
        private TextView inggris;
        private TextView uraian;
        public EnsiklopediaHolder(@NonNull View itemView) {
            super(itemView);
            indo = itemView.findViewById(R.id.tv_title_indo);
            inggris = itemView.findViewById(R.id.tv_title_inggris);
            uraian = itemView.findViewById(R.id.tv_desc);
        }
//        void bind(Ensiklopedia ensiklopedia){
//
//        }
    }

    public void setData(ArrayList<Ensiklopedia> items) {
        ensiklopedias.clear();
        ensiklopedias.addAll(items);
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Ensiklopedia> filterList){
        this.ensiklopedias = filterList;
        notifyDataSetChanged();
    }
}
