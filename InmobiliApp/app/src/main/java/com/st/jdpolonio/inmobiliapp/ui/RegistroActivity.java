package com.st.jdpolonio.inmobiliapp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.models.User;
import com.st.jdpolonio.inmobiliapp.responses.LoginResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.services.RegistroService;
import com.st.jdpolonio.inmobiliapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText email, name, password;
    private TextView haveAccount;
    private Button btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewsById();

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(goLogin);
                finish();
            }
        });

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
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

    public void doRegister() {
        final ProgressDialog progressDialog = new ProgressDialog(RegistroActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.show();

        String email_txt = email.getText().toString().trim();
        String password_txt = password.getText().toString().trim();
        String name_txt = name.getText().toString().trim();


        RegistroService service = ServiceGenerator.createService(RegistroService.class);
        Call<LoginResponse> call = service.register(new User(email_txt, password_txt, name_txt));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    onRegisterSuccess(call, response);

                } else {
                    progressDialog.dismiss();
                    onRegisterFail();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(RegistroActivity.this, "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void onRegisterSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {

        Util.setData(RegistroActivity.this, response.body().getToken(), response.body().getUser().getId(), response.body().getUser().getEmail(), response.body().getUser().getName(),
                response.body().getUser().getPicture());
        Intent loginSuccess = new Intent(RegistroActivity.this, DashboardActivity.class);
        startActivity(loginSuccess);
        finish();
    }


    //TODO Cambiar esto
    public void onRegisterFail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle("Error");
        builder.setIcon(R.drawable.ic_advertencia);
        builder.setMessage("Correo electrónico o contraseña incorrectos")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
