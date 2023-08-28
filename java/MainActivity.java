package com.example.weather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "101";
    private EditText user_field;
    private Button main_btn;
    private TextView info;
    private Button home_btn;
    private Button test_btn;

    // Переменная для работы с БД
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_field = findViewById(R.id.user_field);
        main_btn = findViewById(R.id.main_btn);
        info = findViewById(R.id.info);
        home_btn = findViewById(R.id.home_btn);
        test_btn = findViewById(R.id.test_btn);

//-------------------------------------------------------
       /*user_field.addTextChangedListener(new TextWatcher() {

            //После изменения
            public void afterTextChanged(Editable s) {
            }

            //Перед изменением
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                info.setText("Перед");
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });*/


        mDBHelper = new DataBaseHelper(this);

        mDb = mDBHelper.getReadableDatabase();

        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";

                Cursor cursor = mDb.rawQuery("SELECT name FROM city", null);
                cursor.moveToFirst();
                result = cursor.getString(0);
                cursor.close();

                info.setText(result);
            }
        });



        //--------------------------------------------------------
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_field.getText().toString().trim().equals(""))
                    Toast.makeText(MainActivity.this, R.string.data_input,Toast.LENGTH_LONG).show();
                else {
                    String city = user_field.getText().toString();
                    //Переброс данных и переход на 2 страницу
                    Intent intent = new Intent();
                    intent.putExtra("info_city",user_field.getText().toString());
                    intent.setClass(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
            }
        });


        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MainActivity4.class);
                    startActivity(intent);
            }
        });

    }


}