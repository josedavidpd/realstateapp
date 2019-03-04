package com.st.jdpolonio.inmobiliapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.fragments_list.PropertiesListFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText ciudad;
    private Button btn_buscar;
    private String id_categoria, ciudad_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        getSupportActionBar().hide();


        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ciudad_txt = ciudad.getText().toString().trim();
                Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                i.putExtra("CITY", ciudad_txt);
                i.putExtra("ID_CATEGORY", getSelectedItem());
                startActivity(i);
                finish();
            }
        });

    }

    public void findViewsById() {
        radioGroup = findViewById(R.id.rg_categories);
        ciudad = findViewById(R.id.et_ciudad);
        btn_buscar = findViewById(R.id.btn_search);
    }

    public String getSelectedItem() {

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_obranueva:
                id_categoria = "5c7c3b4c5d546200174b3b94";
                break;
            case R.id.rb_venta:
                id_categoria = "5c7c3b4c5d546200174b3b96";
                break;
            case R.id.rb_alquiler:
                id_categoria = "5c7c3b4c5d546200174b3b95";
                break;
        }

        return id_categoria;
    }
}
