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

public class EnsiklopediaModel extends ViewModel {

    private MutableLiveData<ArrayList<Ensiklopedia>> listEnsiklopedia = new MutableLiveData<>();

    public void setEnsiklopedia(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Ensiklopedia> listItems = new ArrayList<>();
        String url = "https://tekmirapedia.000webhostapp.com/ensiklopedia.json";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("Ensiklopedia");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject ensiklopediaJson = list.getJSONObject(i);
                        Ensiklopedia ensiklopedia = new Ensiklopedia();
                        ensiklopedia.setIdEnsiklopedia(ensiklopediaJson.getInt("ID"));
                        ensiklopedia.setIstilahIndo(ensiklopediaJson.getString("Indo"));
                        ensiklopedia.setIstilahInggris(ensiklopediaJson.getString("Inggris"));
                        ensiklopedia.setUraian(ensiklopediaJson.getString("Uraian"));
                        listItems.add(ensiklopedia);
                    }
                    listEnsiklopedia.postValue(listItems);
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
    public LiveData<ArrayList<Ensiklopedia>> getEnsiklopedia(){
        return listEnsiklopedia;
    }
}
