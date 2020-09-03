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
import org.alfredo.tekmirapedia.adapter.EnsiklopediaAdapter;
import org.alfredo.tekmirapedia.model.Ensiklopedia;
import org.alfredo.tekmirapedia.model.EnsiklopediaModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnsiklopediaFragment extends Fragment {

    private RecyclerView list;
    private ArrayList<Ensiklopedia> ensiklopedias = new ArrayList<>();
    private EnsiklopediaModel ensiklopediaModel;
    private EnsiklopediaAdapter ensiklopediaAdapter;
    private ProgressBar progressBar;
    private EditText search;

    public EnsiklopediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ensiklopedia, container, false);
        search = view.findViewById(R.id.search_ensiklopedia);

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
        list = view.findViewById(R.id.list_ensiklopedia);
        progressBar = view.findViewById(R.id.progressBar_ensiklopedia);
        list.setHasFixedSize(true);

        ensiklopediaModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(EnsiklopediaModel.class);

        ensiklopediaModel.setEnsiklopedia();
        loadData(true);

        ensiklopediaModel.getEnsiklopedia().observe(this, new Observer<ArrayList<Ensiklopedia>>() {
            @Override
            public void onChanged(ArrayList<Ensiklopedia> ensiklopedias) {
                if  (ensiklopedias != null){
                    ensiklopediaAdapter.setData(ensiklopedias);
                    loadData(false);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ensiklopediaAdapter = new EnsiklopediaAdapter(getActivity(), ensiklopedias);
        list.setAdapter(ensiklopediaAdapter);
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
        ArrayList<Ensiklopedia> filteredList = new ArrayList<>();
        for (Ensiklopedia ensiklopedia : ensiklopedias){
            if (ensiklopedia.getIstilahIndo().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(ensiklopedia);
            } else if (ensiklopedia.getIstilahInggris().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(ensiklopedia);
            }
        }
        ensiklopediaAdapter.filterList(filteredList);
    }

}
