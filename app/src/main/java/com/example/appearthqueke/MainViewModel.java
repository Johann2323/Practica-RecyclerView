package com.example.appearthqueke;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appearthqueke.API.EqApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    public void getEarthquakes(){
        /*ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("001","Carchi - Tulcan",5.0,5082022,1015,154.8));
        eqList.add(new Earthquake("002","Guayas - Guayaquil",7.4,323244,1093,200.9));
        eqList.add(new Earthquake("003","Chimborazo - Alusi",2.9,508232022,1090,143.5));
        eqList.add(new Earthquake("004","Salinas - Santa Elena",6.0,4344232,1011,87.2));
        eqList.add(new Earthquake("005","Macas - Morona Santiago",7.0,5083222,1020,400.1));
        eqList.add(new Earthquake("005","Pedernales - Manabi",3.9,5012123,1005,324.9));
        this.eqList.setValue(eqList);*/

        EqApiClient.EqService service = EqApiClient.getInstance().getService();
        service.getEarthquakes().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.d("MainViewModel",response.body());
                List<Earthquake> earthquakesList = parseEarthquake(response.body());
                eqList.setValue(earthquakesList);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            Log.d("MainViewModel",t.getMessage());
            }
        });
    }
    private List<Earthquake> parseEarthquake (String responsesString){
        ArrayList<Earthquake> eqList = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(responsesString);
            JSONArray featuresJSONArray = jsonResponse.getJSONArray("features");
            for(int i=0;i<featuresJSONArray.length();i++){
                JSONObject jsonFeatures = featuresJSONArray.getJSONObject(i);
                String id = jsonFeatures.getString("id");
                JSONObject jsonProperties = jsonFeatures.getJSONObject("properties");
                double magnitude = jsonProperties.getDouble("mag");
                String place = jsonProperties.getString("place");

                long time = jsonProperties.getLong("time");

                DecimalFormat df = new DecimalFormat("#.00");
                double R = magnitude;
                df.format(R);

                JSONObject jsonGeometry = jsonFeatures.getJSONObject("geometry");
                JSONArray coordinatesJSONArray = jsonGeometry.getJSONArray("coordinates");
                double longitude = coordinatesJSONArray.getDouble(0);
                double latitude = coordinatesJSONArray.getDouble(1);

                Earthquake earthquake = new Earthquake(id,place,R,time,latitude,longitude);
                eqList.add(earthquake);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eqList;
    }
}
