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
import org.alfredo.tekmirapedia.adapter.FavoriteKamusAdapter;
import org.alfredo.tekmirapedia.adapter.FavoriteKamusAdapter;
import org.alfredo.tekmirapedia.helper.KamusHelper;
import org.alfredo.tekmirapedia.helper.KamusHelper;
import org.alfredo.tekmirapedia.model.Kamus;
import org.alfredo.tekmirapedia.model.KamusCallback;
import org.alfredo.tekmirapedia.model.Kamus;
import org.alfredo.tekmirapedia.model.KamusCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteKamusFragment extends Fragment implements KamusCallback {
    
    private RecyclerView recyclerView;
    private FavoriteKamusAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<Kamus> list = new ArrayList<>();
    private KamusHelper helper;

    private static final String TAG = FavoriteKamusFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";
    
    public FavoriteKamusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_kamus, container, false);

        progressBar = view.findViewById(R.id.progressBar_favoriteKamus);
        recyclerView = view.findViewById(R.id.list_favoriteKamus);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        helper = helper.getInstance(getContext());
        helper.open();

        adapter = new FavoriteKamusAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (savedInstanceState == null){
            new FavoriteKamusFragment.LoadKamusAsync(helper, this).execute();
        }else {
            ArrayList<Kamus> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapter.setListKamus(list);
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
    public void postExecute(ArrayList<Kamus> kamuses) {
        progressBar.setVisibility(View.GONE);
        if (kamuses.size() != 0) {
            adapter.setListKamus(kamuses);
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

    private static class LoadKamusAsync extends AsyncTask<Void, Void, ArrayList<Kamus>> {
        private final WeakReference<KamusHelper> weakDataHelper;
        private final WeakReference<KamusCallback> weakCallback;

        private LoadKamusAsync(KamusHelper dataHelper, KamusCallback callback) {
            weakDataHelper = new WeakReference<>(dataHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<Kamus> doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return weakDataHelper.get().getFavoriteKamus();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected void onPostExecute(ArrayList<Kamus> kamuses) {
            super.onPostExecute(kamuses);
            weakCallback.get().postExecute(kamuses);
            Log.d(TAG, "onPostExecute: ");
        }
    }
}
