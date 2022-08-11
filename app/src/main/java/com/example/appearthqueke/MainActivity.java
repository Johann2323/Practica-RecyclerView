package com.example.appearthqueke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appearthqueke.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.eqRecyclear.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("001","Carchi - Tulcan",5.0,5082022,1015,154.8));
        eqList.add(new Earthquake("002","Guayas - Guayaquil",7.4,323244,1093,200.9));
        eqList.add(new Earthquake("003","Chimborazo - Alusi",2.9,508232022,1090,143.5));
        eqList.add(new Earthquake("004","Salinas - Santa Elena",6.0,4344232,1011,87.2));
        eqList.add(new Earthquake("005","Macas - Morona Santiago",7.0,5083222,1020,400.1));
        eqList.add(new Earthquake("005","Pedernales - Manabi",3.9,5012123,1005,324.9));
        //modf
        EqAdapter adapter = new EqAdapter();
        adapter.setOnItemClickListener(earthquake -> {
            Toast.makeText(MainActivity.this, earthquake.getPlace(), Toast.LENGTH_SHORT).show();

            String id = earthquake.getId();
            String lugar = earthquake.getPlace();
            double magnitud = earthquake.getMagnitude();
            int tiempo = earthquake.getTime();
            double latitud = earthquake.getLatitude();
            double longitud = earthquake.getLongitude();
            abrirActivityInformacion(id,lugar,magnitud,tiempo,latitud,longitud);

        });
        binding.eqRecyclear.setAdapter(adapter);
        adapter.submitList(eqList);

        if (eqList.isEmpty()){
            binding.emptyView.setVisibility(View.VISIBLE);
        }else{
            binding.emptyView.setVisibility(View.GONE);
        }
    }


    private void abrirActivityInformacion(String id, String place, double magnitude, int time, double latitude,double longitud){
        Intent intent = new Intent(this, Earthquake_Monitor.class);

        Earthquake earthquake = new Earthquake(id, place, magnitude, time,latitude,longitud);

        intent.putExtra(Earthquake_Monitor.INFORMACION_KEY,earthquake);
        intent.putExtra(Earthquake_Monitor.BITMAP_KEY,bitmap);
        startActivity(intent);
    }
}