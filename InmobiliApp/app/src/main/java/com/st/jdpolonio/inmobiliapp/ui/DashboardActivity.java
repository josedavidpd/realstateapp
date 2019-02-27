package com.st.jdpolonio.inmobiliapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.fragments_list.MyFavouritesListFragment;
import com.st.jdpolonio.inmobiliapp.fragments_list.MyPropertiesListFragment;
import com.st.jdpolonio.inmobiliapp.fragments_list.PropertiesListFragment;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentMyPropertiesListener;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListFragmentPropertiesListener;
import com.st.jdpolonio.inmobiliapp.interfaces.OnListMyFavouritesListener;
import com.st.jdpolonio.inmobiliapp.responses.MinePropertyResponse;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.responses.UserResponse;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.retrofit.TipoAutenticacion;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;
import com.st.jdpolonio.inmobiliapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnListFragmentPropertiesListener, OnListMyFavouritesListener, OnListFragmentMyPropertiesListener {

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        if(Util.getToken(DashboardActivity.this) == null)
            fab.hide();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        hideItems();
        f = new PropertiesListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, f, "mainF")
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

        if (id == R.id.nav_properties) {
            if(Util.getToken(DashboardActivity.this) == null)
                fab.hide();
            else
                fab.show();

            /*fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddPropertyFragment f = AddPropertyFragment.newInstance();
                    Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("propertiesFragment");
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.detach(currentFragment);
                    fragmentTransaction.attach(currentFragment);
                    fragmentTransaction.commit();
                    FragmentManager fm = getSupportFragmentManager();
                    f.show(fm, "addPropertyFrag");
                }
            });*/

            f = new PropertiesListFragment();

            toolbar.setTitle("Inmuebles");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, f, "propertiesFragment").commit();




        } else if (id == R.id.nav_favorites) {


            f = new MyFavouritesListFragment();
            toolbar.setTitle("Favoritos");

            fab.hide();

            getSupportFragmentManager().beginTransaction().replace(R.id.container, f, "favsPropertiesFragment").commit();



        } else if (id == R.id.nav_myproperties) {
            f = new MyPropertiesListFragment();
            toolbar.setTitle("Mis inmuebles");
            fab.hide();

            getSupportFragmentManager().beginTransaction().replace(R.id.container, f, "myPropertiesFragment").commit();

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(DashboardActivity.this,MyProfileActivity.class));

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
            nav_Menu.findItem(R.id.nav_myproperties).setVisible(false);

        } else {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_register).setVisible(false);
        }

    }

    @Override
    public void OnClickProperty(PropertyResponse property) {
        Intent details = new Intent(DashboardActivity.this, PropertyDetailActivity.class);
        details.putExtra("PROPERTY_ID", property.getId());
        details.putExtra("PROPERTY_NAME", property.getTitle());
        details.putExtra("PROPERTY_ADDRESS", property.getAddress());
        details.putExtra("PROPERTY_PRICE", String.valueOf(property.getPrice()));
        details.putExtra("PROPERTY_ROOMS", String.valueOf(property.getRooms()));
        details.putExtra("PROPERTY_SIZE", String.valueOf(property.getSize()));
        details.putExtra("PROPERTY_DESCRIPTION", property.getDescription());
        details.putExtra("PROPERTY_CREATEDAT", property.getCreatedAt());
        details.putExtra("PROPERTY_CATEGORY", property.getCategoryId().getName());
        startActivity(details);


    }

    @Override
    public void onClickFav(final ImageView ic_fav, final String id_property) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Añadir a favoritos");
        builder.setMessage("¿Seguro que desea añadir este inmueble a sus favoritos?");
        builder.setPositiveButton(R.string.addToFav, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addToFav(ic_fav, id_property);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();



    }

    @Override
    public void onClickDeletFav(final ImageView ic_fav, final String id_property) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Eliminar de favoritos");
        builder.setMessage("¿Está seguro que desea eliminar este inmueble de sus favoritos?");
        builder.setPositiveButton(R.string.deleteFav, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteFav(ic_fav, id_property);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void addToFav(final ImageView ic_fav, String id_property) {
        PropertyService service = ServiceGenerator.createService(PropertyService.class,
                Util.getToken(DashboardActivity.this), TipoAutenticacion.JWT);

        Call<UserResponse> call = service.addToFav(id_property);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    ic_fav.setImageResource(R.drawable.ic_like);
                    Toast.makeText(DashboardActivity.this, "Inmueble añadido a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashboardActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteFav(final ImageView ic_fav, String id_property) {
        PropertyService service = ServiceGenerator.createService(PropertyService.class,
                Util.getToken(DashboardActivity.this), TipoAutenticacion.JWT);

        Call<UserResponse> call = service.deleteFav(id_property);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    ic_fav.setImageResource(R.drawable.ic_corazon);
                    Toast.makeText(DashboardActivity.this, "Inmueble eliminado de favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashboardActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClickPropfav(PropertyResponse property) {
        Intent details = new Intent(DashboardActivity.this, PropertyDetailActivity.class);
        details.putExtra("PROPERTY_ID", property.getId());
        details.putExtra("PROPERTY_NAME", property.getTitle());
        details.putExtra("PROPERTY_ADDRESS", property.getAddress());
        details.putExtra("PROPERTY_PRICE", String.valueOf(property.getPrice()));
        details.putExtra("PROPERTY_ROOMS", String.valueOf(property.getRooms()));
        details.putExtra("PROPERTY_SIZE", String.valueOf(property.getSize()));
        details.putExtra("PROPERTY_DESCRIPTION", property.getDescription());
        details.putExtra("PROPERTY_CREATEDAT", property.getCreatedAt());


        startActivity(details);

    }

    @Override
    public void onClickMyProp(MinePropertyResponse property) {
        Intent details = new Intent(DashboardActivity.this, PropertyDetailActivity.class);
        details.putExtra("PROPERTY_ID", property.getId());
        details.putExtra("PROPERTY_NAME", property.getTitle());
        details.putExtra("PROPERTY_ADDRESS", property.getAddress());
        details.putExtra("PROPERTY_PRICE", String.valueOf(property.getPrice()));
        details.putExtra("PROPERTY_ROOMS", String.valueOf(property.getRooms()));
        details.putExtra("PROPERTY_SIZE", String.valueOf(property.getSize()));
        details.putExtra("PROPERTY_DESCRIPTION", property.getDescription());
        details.putExtra("PROPERTY_CREATEDAT", property.getCreatedAt());
        startActivity(details);
    }
}
