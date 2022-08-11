package com.example.appearthqueke;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.appearthqueke.databinding.ActivityInformacionBinding;
import com.example.appearthqueke.databinding.ActivityMainBinding;

public class Earthquake_Monitor extends AppCompatActivity {
ActivityInformacionBinding binding;
    public static final String INFORMACION_KEY = "informacion";
    public static final String BITMAP_KEY="bitmap";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();

        Earthquake sitio = extras.getParcelable(INFORMACION_KEY);

        binding.setMonitor(sitio);
        Bitmap bitmap = extras.getParcelable(BITMAP_KEY);
        binding.txtMagnitud.setText(String.valueOf(sitio.getMagnitude()));
        binding.txtLugar.setText(sitio.getPlace());
        binding.txtTiempo.setText(String.valueOf(sitio.getTime()));
        binding.txtLong.setText("Longitude: "+String.valueOf(sitio.getLongitude()));
        binding.txtLat.setText("Latitude: "+String.valueOf(sitio.getLatitude()));

    }
}