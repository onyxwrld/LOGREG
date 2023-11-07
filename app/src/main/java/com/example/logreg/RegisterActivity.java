package com.example.logreg;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegisterActivity extends AppCompatActivity {
    private EditText emailReg, userNameReg, passwordReg, regTeljes;
    private Button btnReg, btnBack;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailReg.getText().toString();
                String username = userNameReg.getText().toString();
                String password = passwordReg.getText().toString();
                String fullname = regTeljes.getText().toString();

                if (dbHelper.addToTable(email, username, password, fullname)) {
                    Toast.makeText(RegisterActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Sikertelen regisztráció!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asd = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(asd);
                finish();
            }
        });
        emailReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = emailReg.getText().toString();
                if (dbHelper.emailELLenoriz(email)) {
                    emailReg.setError("E-mail cim már létezik!");
                    emailReg.setTextColor(Color.RED);
                }
                else {
                    if (email.isEmpty()) {
                        emailReg.setError("E-mail cim megadása kötelező!");
                    } else if (!emailCheck(email)) {
                        emailReg.setError("Nem megfelelő e-mail formátum!");
                    } else {
                        emailReg.setTextColor(Color.GREEN);
                    }
                }
                tudRegistralni();
            }
        });
        userNameReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String username = userNameReg.getText().toString();
                if (dbHelper.felhasznaloNevELL(username)) {
                    userNameReg.setError("Felhasználónév már létezik!");
                    userNameReg.setTextColor(Color.RED);
                }
                else {
                    if (username.isEmpty()) {
                        userNameReg.setError("Felhasználónév megadása kötelező!");
                    }
                    else {
                        userNameReg.setTextColor(Color.GREEN);
                    }
                }
                tudRegistralni();
            }
        });
        regTeljes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String fullname = regTeljes.getText().toString();
                if (fullname.isEmpty()) {
                    regTeljes.setError("Teljes név megadása kötelező!");

                } else if (!isFullname(fullname)) {
                    regTeljes.setError("Nem megfelelő név formátum!");
                }
                tudRegistralni();
            }
        });
        passwordReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String password = passwordReg.getText().toString();
                if (password.isEmpty()) {
                    passwordReg.setError("Jelszó megadása kötelező!");
                }
                tudRegistralni();
            }
        });

    }
    private void tudRegistralni() {
        String email = emailReg.getText().toString();
        String username = userNameReg.getText().toString();
        String password = passwordReg.getText().toString();
        String fullname = regTeljes.getText().toString();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            btnReg.setEnabled(false);
        }
        else {
            btnReg.setEnabled(true);
        }
    }
    private boolean emailCheck(String email) {
        Pattern regexPattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$");
        Matcher matcher = regexPattern.matcher(email);
        return matcher.matches();
    }

    private boolean isFullname(String fullname) {
        return fullname.split(" ").length > 1;
    }

    private void init() {
        emailReg = findViewById(R.id.emailReg);
        userNameReg = findViewById(R.id.userNameReg);
        passwordReg = findViewById(R.id.passwordReg);
        regTeljes = findViewById(R.id.regTeljes);
        btnReg = findViewById(R.id.btnReg);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DBHelper(this);
        btnReg.setEnabled(true);
    }
}