package com.example.appearthqueke;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Toast;

import com.example.appearthqueke.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    Bitmap bitmap;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MainViewModel viewmodel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.eqRecyclear.setLayoutManager(new LinearLayoutManager(this));



        int orient = getResources().getConfiguration().orientation;
        System.out.println(orient);



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

            Earthquake eqq=eqList.stream().max(Comparator.comparingDouble(Earthquake::getMagnitude)).get();


            binding.setEarthquakelandscape(eqq);
            adapter.submitList(eqList);
            if (eqList.isEmpty()){
                binding.emptyView.setVisibility(View.VISIBLE);
            }else {
                binding.emptyView.setVisibility(View.GONE);
            }
        });
        viewmodel.getEarthquakes();

        viewmodel.getEqList().observe(this, eqList->{
            double magni;
            double mayor=0;
            double lat=0;
            double longi=0;
            double tiemp=0;
            String plac="";

            for (int i = 0; i < eqList.size(); i++){
                System.out.println(eqList.get(i).getMagnitude());
                magni=eqList.get(i).getMagnitude();
                if (magni>mayor){
                    mayor = magni;
                    lat = eqList.get(i).getLatitude();
                    longi= eqList.get(i).getLongitude();
                    tiemp= eqList.get(i).getTime();
                    plac= eqList.get(i).getPlace();


                }
            }

            System.out.println("el numero mayor es "+ mayor);
            System.out.println("el numero mayor es "+ longi);
            System.out.println("el numero mayor es "+ lat);
            System.out.println("el numero mayor es "+ tiemp);
            System.out.println("el numero mayor es "+ plac);
            //binding.txtMag.setText(String.valueOf(mayor));

        });


    }


    private void abrirActivityInformacion(String id, String place, double magnitude, long time, double latitude,double longitud){
        Intent intent = new Intent(this, Earthquake_Monitor.class);

        Earthquake earthquake = new Earthquake(id, place, magnitude, time,latitude,longitud);

        intent.putExtra(Earthquake_Monitor.INFORMACION_KEY,earthquake);
        intent.putExtra(Earthquake_Monitor.BITMAP_KEY,bitmap);
        startActivity(intent);
    }
}