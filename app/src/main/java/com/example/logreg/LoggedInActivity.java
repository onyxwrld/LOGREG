package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView logInfo;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        init();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedInActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void init() {
        btnBack = findViewById(R.id.btnBack);
        logInfo = findViewById(R.id.logInfo);
        sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dbHelper = new DBHelper(this);
        int idID = sharedPreferences.getInt("id", -1);
        if (0 <= idID) {
            Cursor cursor = dbHelper.getTableElementById(idID);
            cursor.moveToFirst();
            logInfo.setText("Üdvözöllek" + cursor.getString(4));
        }

    }
}