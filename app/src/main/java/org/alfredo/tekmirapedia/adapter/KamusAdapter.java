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
import java.util.List;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusHolder> {

    Context context;
    List<Kamus> kamuses;

    public KamusAdapter(Context context, List<Kamus> kamuses) {
        this.context = context;
        this.kamuses = kamuses;
    }

    @NonNull
    @Override
    public KamusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new KamusAdapter.KamusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KamusHolder holder, final int position) {
//        holder.bind(kamuses.get(position));
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

    public class KamusHolder extends RecyclerView.ViewHolder {
        private TextView indo;
        private TextView inggris;
        private TextView uraian;
        public KamusHolder(@NonNull View itemView) {
            super(itemView);
            indo = itemView.findViewById(R.id.tv_title_indo);
            inggris = itemView.findViewById(R.id.tv_title_inggris);
            uraian = itemView.findViewById(R.id.tv_desc);
        }

//        void bind(Kamus kamus){
//
//        }
    }

    public void setData(ArrayList<Kamus> items) {
        kamuses.clear();
        kamuses.addAll(items);
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Kamus> filterList){
        this.kamuses = filterList;
        notifyDataSetChanged();
    }
}
