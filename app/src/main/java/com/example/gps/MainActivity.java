package com.example.gps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocClient;
    private static final int CODIGO_REQUISICAO = 123;
    private TextView textViewDistancia;
    private Location minhaLocalizacao = new Location("Minha localização");
    private Location catedralLocalizacao = new Location("Localização Catedral");

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDistancia = findViewById(R.id.text_view_distancia);
        textViewDistancia.setText("");

        //Verificação da permissão do uso de GPS
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, CODIGO_REQUISICAO
            );
        }

            fusedLocClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                //usar a localização
                                minhaLocalizacao.setLatitude(location.getLatitude());
                                minhaLocalizacao.setLongitude(location.getLongitude());
                            }
                        }
                    });
        }

    public void calculardistancia(View v){
        catedralLocalizacao.setLatitude(-22.018229);
        catedralLocalizacao.setLongitude(-47.8934227);

        Float distancia = minhaLocalizacao.distanceTo(catedralLocalizacao)/1000;
        String distanciaString = String.format("%.2f", distancia.doubleValue());
        textViewDistancia.setText("Distância: " + distanciaString.replace(".",",") + " KM");
    }
}
