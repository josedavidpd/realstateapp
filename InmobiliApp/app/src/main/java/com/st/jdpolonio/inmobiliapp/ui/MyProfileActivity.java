package com.st.jdpolonio.inmobiliapp.ui;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.models.User;
import com.st.jdpolonio.inmobiliapp.responses.UserResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.retrofit.TipoAutenticacion;
import com.st.jdpolonio.inmobiliapp.services.UserService;
import com.st.jdpolonio.inmobiliapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {

    private EditText name, password, email;
    private ImageView photo;
    private Button btn_edit;
    private TextView tv_favs, tv_properties, tv_name, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewsById();
        setTitle("Mi perfil");
        getMe();

    }

    private void findViewsById() {

        name = findViewById(R.id.edit_profile_name);
        password = findViewById(R.id.edit_profile_password);
        email = findViewById(R.id.edit_profile_email);
        photo = findViewById(R.id.edit_profile_photo);
        btn_edit = findViewById(R.id.edit_profile_btnsend);
        tv_favs = findViewById(R.id.edit_profile_numfavs);
        tv_properties = findViewById(R.id.edit_profile_numproperties);
        tv_name = findViewById(R.id.edit_profile_tv_name);
        tv_email = findViewById(R.id.edit_profile_tv_email);

    }

    public void getMe() {
        UserService service = ServiceGenerator.createService(UserService.class, Util.getToken(MyProfileActivity.this), TipoAutenticacion.JWT);
        Call<User> call = service.getMe();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    tv_name.setText(response.body().getName());
                    tv_email.setText(response.body().getEmail());
                    Log.i("AAAAAAAAAAA",String.valueOf(response.body().getFavs().size()));
                    tv_favs.setText(String.valueOf(response.body().getFavs().size()));
                    Glide.with(MyProfileActivity.this).load(response.body().getPicture()).into(photo);
                    name.setText(response.body().getName());
                    email.setText(response.body().getEmail());
                } else {
                    Toast.makeText(MyProfileActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(MyProfileActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
