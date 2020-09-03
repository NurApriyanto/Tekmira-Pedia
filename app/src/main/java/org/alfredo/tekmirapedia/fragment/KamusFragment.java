package org.alfredo.tekmirapedia.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.alfredo.tekmirapedia.R;
import org.alfredo.tekmirapedia.adapter.KamusAdapter;
import org.alfredo.tekmirapedia.model.Kamus;
import org.alfredo.tekmirapedia.model.KamusModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KamusFragment extends Fragment {

    private RecyclerView list;
    private ArrayList<Kamus> kamuses = new ArrayList<>();
    private KamusModel kamusModel;
    private KamusAdapter kamusAdapter;
    private ProgressBar progressBar;
    private EditText search;

    public KamusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kamus, container, false);
        search = view.findViewById(R.id.search_kamus);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterData(editable.toString());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = view.findViewById(R.id.list_kamus);
        progressBar = view.findViewById(R.id.progressBar_kamus);
        list.setHasFixedSize(true);

        kamusModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(KamusModel.class);

        kamusModel.setKamus();
        loadData(true);

        kamusModel.getKamus().observe(this, new Observer<ArrayList<Kamus>>() {
            @Override
            public void onChanged(ArrayList<Kamus> kamuses) {
                if  (kamuses != null){
                    kamusAdapter.setData(kamuses);
                    loadData(false);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        kamusAdapter = new KamusAdapter(getActivity(), kamuses);
        list.setAdapter(kamusAdapter);
        list.setLayoutManager(linearLayoutManager);
    }

    private void loadData(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void filterData(String text){
        ArrayList<Kamus> filteredList = new ArrayList<>();
        for (Kamus kamus : kamuses){
            if (kamus.getIndo().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(kamus);
            } else if (kamus.getInggris().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(kamus);
            }
        }
        kamusAdapter.filterList(filteredList);
    }

}
