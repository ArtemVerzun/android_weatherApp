package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity4 extends AppCompatActivity {

    private TextView info_city;
    private TextView info_temp;
    private TextView info_weather;
    private TextView info_temp2;
    private TextView info_humidity;
    private TextView info_wind;
    private Button search_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        info_city = findViewById(R.id.info_city);
        info_temp = findViewById(R.id.info_temp);
        info_weather = findViewById(R.id.info_weather);
        info_temp2 = findViewById(R.id.info_temp2);
        info_humidity = findViewById(R.id.info_humidity);
        info_wind = findViewById(R.id.info_wind);
        search_btn2 = findViewById(R.id.search_btn2);
        String txtName = getIntent().getStringExtra("gps_city");
        String url = txtName;

        new MainActivity4.GetUrlData().execute(url);
        //info_city.setText(url);


        search_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity4.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public class GetUrlData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                //Создаем объект, на основе которого обращаемся к URL
                URL url = new URL(strings[0]);
                //Открываем соединение
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                //Создаем метод, считывающий данные
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();
                try {
                    if(reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        public void onPostExecute(String result){
            super.onPostExecute(result);

            if (result != null){
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jArray = obj.getJSONArray("weather");
                    int length = jArray.length();

                    for(int i=0; i<length; i++)
                    {
                        JSONObject jObj = jArray.getJSONObject(i);
                        String id = jObj.optString("id");
                        String description = jObj.optString("description");
                        info_weather.setText(description);
                    }
                    info_city.setText(obj.getString("name"));
                    info_temp.setText(obj.getJSONObject("main").getInt("temp")+"°");
                    info_temp2.setText("Ощущается как "+obj.getJSONObject("main").getInt("feels_like")+"°");
                    info_humidity.setText("Влажность "+obj.getJSONObject("main").getInt("humidity")+"%");
                    info_wind.setText("Ветер "+obj.getJSONObject("main").getInt("temp")+"м/с");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                info_weather.setText("города не существует...");
                info_temp.setText("");
                info_temp2.setText("");
                info_humidity.setText("");
                info_wind.setText("");
            }


        }

    }


}