package com.st.jdpolonio.inmobiliapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.fragments_list.PropertiesListFragment;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentPropertiesListener;
import com.st.jdpolonio.inmobiliapp.util.Util;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnListFragmentPropertiesListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView userPicture;
    private TextView userName, userEmail;
    private Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViewsById();
        toolbar.setTitle("Inmuebles");
        setSupportActionBar(toolbar);
        getInfoUser();

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        hideItems();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PropertiesListFragment(), "mainF")
                .commit();
    }

    public void findViewsById() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    /** Menú lateral **/
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_properties) {

        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_myproperties) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_logout) {
            Util.clearSharedPreferences(DashboardActivity.this);
            finish();
            startActivity(getIntent());

        } else if (id == R.id.nav_login) {
            Intent goLogin = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(goLogin);

        } else if (id == R.id.nav_register) {
            Intent goRegister = new Intent(DashboardActivity.this, RegistroActivity.class);
            startActivity(goRegister);

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getInfoUser() {
        View headerView = navigationView.getHeaderView(0);

        userPicture = headerView.findViewById(R.id.pictureUser);
        userName = headerView.findViewById(R.id.nameUser);
        userEmail = headerView.findViewById(R.id.emailUser);

        if (Util.getToken(DashboardActivity.this) == null) {

            Glide.with(DashboardActivity.this).load("https://avatars.servers.getgo.com/2205256774854474505_medium.jpg").into(userPicture);
            userName.setText("Inicie sesión o regístrese");
            userEmail.setVisibility(View.INVISIBLE);

        } else {
            Glide.with(DashboardActivity.this).load(Util.getPhotoUser(DashboardActivity.this)).into(userPicture);
            userName.setText(Util.getNombreUser(DashboardActivity.this));
            userEmail.setText(Util.getEmailUser(DashboardActivity.this));

        }


    }

    public void hideItems() {
        Menu nav_Menu = navigationView.getMenu();
        if (Util.getToken(DashboardActivity.this) == null) {

            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_favorites).setVisible(false);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);

        } else {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_register).setVisible(false);
        }

    }

    @Override
    public void OnClickProperty() {

    }
}
