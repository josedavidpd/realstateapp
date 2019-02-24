package com.st.jdpolonio.inmobiliapp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.responses.LoginResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.services.LoginService;
import com.st.jdpolonio.inmobiliapp.util.Util;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView noAccount;
    private Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        findViewsById();

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRegistro = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(goRegistro);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });


    }


    public void findViewsById() {
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.login_btnSend);
        noAccount = findViewById(R.id.login_goRegistro);
    }

    public void doLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Comprobando...");
        progressDialog.show();

        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();

        String credentials = Credentials.basic(email_txt, password_txt);

        LoginService service = ServiceGenerator.createService(LoginService.class);

        Call<LoginResponse> call = service.login(credentials);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(final Call<LoginResponse> call, final Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    Runnable progressRunnable = new Runnable() {

                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            onLoginSuccess(call, response);
                        }
                    };

                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 2000);

                } else {
                    progressDialog.dismiss();
                    onLoginFail();
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void onLoginSuccess(Call<LoginResponse> call, Response<LoginResponse> response) {

        Util.setData(LoginActivity.this, response.body().getToken(), response.body().getUser().getId(), response.body().getUser().getEmail(), response.body().getUser().getName(),
                response.body().getUser().getPicture());

        Intent loginSuccess = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(loginSuccess);
        finish();


    }

    public void onLoginFail() {
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
