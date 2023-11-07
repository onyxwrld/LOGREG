package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText LoginUserName, LoginPassword;
    private Button btnLogin, btnToRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = LoginUserName.getText().toString();
                String b = LoginPassword.getText().toString();

                if (dbHelper.nevKereso(a, b)) {
                    Toast.makeText(MainActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    login(a, b);
                } else {
                    Toast.makeText(MainActivity.this, "Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asd = new Intent(new Intent(MainActivity.this, RegisterActivity.class));
                startActivity(asd);
                finish();
            }
        });
    }

    private void login(String usernameOrEmail, String password) {
        Cursor cursor = dbHelper.getTableElementByUsernameOrEmail(usernameOrEmail, password);
        cursor.moveToFirst();
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", cursor.getInt(0));
        editor.apply();
        startActivity(new Intent(MainActivity.this, LoggedInActivity.class));
        finish();
    }

    private void init() {
        LoginPassword = findViewById(R.id.LoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);
        LoginUserName = findViewById(R.id.LoginUserName);
        dbHelper = new DBHelper(this);
    }

}