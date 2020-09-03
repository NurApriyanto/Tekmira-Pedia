package org.alfredo.tekmirapedia.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.alfredo.tekmirapedia.R;
import org.alfredo.tekmirapedia.adapter.FavoriteEnsiklopediaAdapter;
import org.alfredo.tekmirapedia.helper.EnsiklopediaHelper;
import org.alfredo.tekmirapedia.model.Ensiklopedia;
import org.alfredo.tekmirapedia.model.EnsiklopediaCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteEnsiklopediaFragment extends Fragment implements EnsiklopediaCallback {

    private RecyclerView recyclerView;
    private FavoriteEnsiklopediaAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<Ensiklopedia> list = new ArrayList<>();
    private EnsiklopediaHelper helper;

    private static final String TAG = FavoriteEnsiklopediaFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteEnsiklopediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_ensiklopedia, container, false);

        progressBar = view.findViewById(R.id.progressBar_favoriteEnsiklopedia);
        recyclerView = view.findViewById(R.id.list_favoriteEnsiklopedia);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        helper = helper.getInstance(getContext());
        helper.open();

        adapter = new FavoriteEnsiklopediaAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (savedInstanceState == null){
            new LoadEnsiklopediaAsync(helper, this).execute();
        }else {
            ArrayList<Ensiklopedia> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapter.setListEnsiklopedia(list);
            }
        }

        return view;
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, "run: PreExecute");
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Ensiklopedia> ensiklopedias) {
        progressBar.setVisibility(View.GONE);
        if (ensiklopedias.size() != 0) {
            adapter.setListEnsiklopedia(ensiklopedias);
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "postExecute: ");
        } else {
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "postExecute: data null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        helper.close();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListData());
    }

    private static class LoadEnsiklopediaAsync extends AsyncTask<Void, Void, ArrayList<Ensiklopedia>> {
        private final WeakReference<EnsiklopediaHelper> weakDataHelper;
        private final WeakReference<EnsiklopediaCallback> weakCallback;

        private LoadEnsiklopediaAsync(EnsiklopediaHelper dataHelper, EnsiklopediaCallback callback) {
            weakDataHelper = new WeakReference<>(dataHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<Ensiklopedia> doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return weakDataHelper.get().getFavoriteEnsiklopedia();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected void onPostExecute(ArrayList<Ensiklopedia> ensiklopedias) {
            super.onPostExecute(ensiklopedias);
            weakCallback.get().postExecute(ensiklopedias);
            Log.d(TAG, "onPostExecute: ");
        }
    }

}
