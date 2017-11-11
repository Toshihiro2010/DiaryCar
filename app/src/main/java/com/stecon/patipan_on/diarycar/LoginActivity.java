package com.stecon.patipan_on.diarycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnRegister;
    private Button btnLogin;

    private String strUsername;
    private String strPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindWidGet();
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void bindWidGet() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogIn);
        btnRegister = (Button) findViewById(R.id.btnRegister);

    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            myLogin();
        } else if (v == btnRegister) {
            Toast.makeText(this, "Click Register", Toast.LENGTH_SHORT).show();
        }
    }

    private void myLogin() {
        strUsername = edtUsername.getText().toString().trim();
        strPassword = edtPassword.getText().toString().trim();
        if (strUsername.equals("") || strPassword.equals("")) {
            Toast.makeText(this, "กรุณา กรอกข้อมูลให้ครับ นะครับ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, strUsername + " /=> " + strPassword, Toast.LENGTH_SHORT).show();
        }
    }
}
