package com.example.appearthqueke;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
        MainViewModel viewmodel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.eqRecyclear.setLayoutManager(new LinearLayoutManager(this));
        //modf
        EqAdapter adapter = new EqAdapter();
        adapter.setOnItemClickListener(earthquake -> {
            Toast.makeText(MainActivity.this, earthquake.getPlace(), Toast.LENGTH_SHORT).show();

            String id = earthquake.getId();
            String lugar = earthquake.getPlace();
            double magnitud = earthquake.getMagnitude();
            long tiempo = earthquake.getTime();
            double latitud = earthquake.getLatitude();
            double longitud = earthquake.getLongitude();
            abrirActivityInformacion(id,lugar,magnitud,tiempo,latitud,longitud);

        });
        binding.eqRecyclear.setAdapter(adapter);


        viewmodel.getEqList().observe(this, eqList->{
            adapter.submitList(eqList);
            if (eqList.isEmpty()){
                binding.emptyView.setVisibility(View.VISIBLE);
            }else {
                binding.emptyView.setVisibility(View.GONE);
            }
        });
        viewmodel.getEarthquakes();


    }


    private void abrirActivityInformacion(String id, String place, double magnitude, long time, double latitude,double longitud){
        Intent intent = new Intent(this, Earthquake_Monitor.class);

        Earthquake earthquake = new Earthquake(id, place, magnitude, time,latitude,longitud);

        intent.putExtra(Earthquake_Monitor.INFORMACION_KEY,earthquake);
        intent.putExtra(Earthquake_Monitor.BITMAP_KEY,bitmap);
        startActivity(intent);
    }
}