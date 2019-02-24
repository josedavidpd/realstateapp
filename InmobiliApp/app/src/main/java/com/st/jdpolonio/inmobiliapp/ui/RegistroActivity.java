package com.st.jdpolonio.inmobiliapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.st.jdpolonio.inmobiliapp.R;

public class RegistroActivity extends AppCompatActivity {

    private EditText email, name, password;
    private TextView haveAccount;
    private Button btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        findViewsById();

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(goLogin);
            }
        });


    }


    public void findViewsById() {

        email = findViewById(R.id.registro_email);
        name = findViewById(R.id.registro_name);
        password = findViewById(R.id.registro_password);
        haveAccount = findViewById(R.id.registro_goLogin);
        btn_registro = findViewById(R.id.registro_btnSend);


    }
}
