package org.alfredo.tekmirapedia.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class KamusModel extends ViewModel {

    private MutableLiveData<ArrayList<Kamus>> listKamus = new MutableLiveData<>();

    public void setKamus(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Kamus> listItems = new ArrayList<>();
        String url = "https://tekmirapedia.000webhostapp.com/kamus.json";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("Kamus");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject kamusJson = list.getJSONObject(i);
                        Kamus kamus = new Kamus();
                        kamus.setIdKamus(kamusJson.getInt("ID"));
                        kamus.setIndo(kamusJson.getString("Indo"));
                        kamus.setInggris(kamusJson.getString("Inggris"));
                        kamus.setUraian(kamusJson.getString("Uraian"));
                        listItems.add(kamus);
                    }
                    listKamus.postValue(listItems);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<Kamus>> getKamus(){
        return listKamus;
    }
}
