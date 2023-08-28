package com.example.weather;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity3 extends AppCompatActivity {


    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int request_code = 101;

    String Location_provider = LocationManager.GPS_PROVIDER;


    LocationManager mLocationManager;
    LocationListener mLocationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getWeatherForCurrentLocation();

    }

    //получение местоположения
    public void getWeatherForCurrentLocation () {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());
                String key = "d35e04e8dac5675dc184170cdafc1e68";
                String url2 = "https://api.openweathermap.org/data/2.5/weather?lat=" + Latitude + "&lon="+ Longitude +"&APPID=" + key + "&units=metric&lang=ru\n";
                //Переброс данных и переход на 2 страницу
                Intent intent = new Intent();
                intent.putExtra("gps_city",url2);
                intent.setClass(MainActivity3.this, MainActivity4.class);
                startActivity(intent);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras){

            }

            @Override
            public void onProviderEnabled(String provider){

            }

            @Override
            public void onProviderDisabled(String provider){

                // not able to get location
            }


        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request_code);
            return;
        }
        mLocationManager.requestLocationUpdates(Location_provider, MIN_TIME, MIN_DISTANCE, mLocationListener);

    }

    //проверка на подключение
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == request_code){

            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity3.this,"Locationget Succesffully",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }

        }
        else{

            //User denied the permission
        }

    }

}