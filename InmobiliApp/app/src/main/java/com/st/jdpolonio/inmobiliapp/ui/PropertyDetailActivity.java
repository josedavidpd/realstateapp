package com.st.jdpolonio.inmobiliapp.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.st.jdpolonio.inmobiliapp.R;
import com.st.jdpolonio.inmobiliapp.models.ResponseContainer;
import com.st.jdpolonio.inmobiliapp.models.Rows;
import com.st.jdpolonio.inmobiliapp.responses.PropertyResponse;
import com.st.jdpolonio.inmobiliapp.responses.SingleResponseContainer;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String property_id, title, address, rooms, price, size, description, createdAt, category_name;
    private SliderLayout sliderLayout;
    private TextView title_prop, price_prop, address_prop, rooms_prop, size_prop, createdAt_prop, description_prop, category;
    private ImageView iv_map;
    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyBSkN9zoIB50OmgqxaD0uELcZCyzpo99sc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            property_id = extras.getString("PROPERTY_ID");
            title = extras.getString("PROPERTY_NAME");
            address = extras.getString("PROPERTY_ADDRESS");
            rooms = extras.getString("PROPERTY_ROOMS");
            price = extras.getString("PROPERTY_PRICE");
            size = extras.getString("PROPERTY_SIZE");
            description = extras.getString("PROPERTY_DESCRIPTION");
            createdAt = extras.getString("PROPERTY_CREATEDAT");
            category_name = extras.getString("PROPERTY_CATEGORY");
        }


        setTitle("");
        findViewsById();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(5);

        setTexts();
        setSliderViews();
        onClickMapBtn();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void setTexts() {
        title_prop.setText(title);
        price_prop.setText(price);
        address_prop.setText(address);
        rooms_prop.setText(rooms);
        size_prop.setText(size);
        //  createdAt_prop.setText(createdAt);
        description_prop.setText(description);
        category.setText(category_name);

    }

 /*   public void findOneProperty() {

        PropertyService service = ServiceGenerator.createService(PropertyService.class);
        Call<SingleResponseContainer> call = service.findOne(property_id);
        call.enqueue(new Callback<SingleResponseContainer>() {
            @Override
            public void onResponse(Call<SingleResponseContainer> call, Response<SingleResponseContainer> response) {
                if (response.isSuccessful()) {
                    title_prop.setText(response.body().getRows().getTitle());
                    price_prop.setText(String.valueOf(response.body().getRows().getPrice()));
                    address_prop.setText(response.body().getRows().getAddress());
                    rooms_prop.setText(String.valueOf(response.body().getRows().getRooms()));
                    size_prop.setText(String.valueOf(response.body().getRows().getSize()));
                    createdAt_prop.setText(response.body().getRows().getCreatedAt());
                    description_prop.setText(response.body().getRows().getDescription());

                } else {
                    Toast.makeText(PropertyDetailActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleResponseContainer> call, Throwable t) {
                Toast.makeText(PropertyDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void setSliderViews() {


        final DefaultSliderView sliderView = new DefaultSliderView(PropertyDetailActivity.this);

        PropertyService service = ServiceGenerator.createService(PropertyService.class);
        Call<SingleResponseContainer> call = service.findOne(property_id);
        call.enqueue(new Callback<SingleResponseContainer>() {
            @Override
            public void onResponse(Call<SingleResponseContainer> call, Response<SingleResponseContainer> response) {
                if (response.isSuccessful()) {
                    if (response.body().getRows().getPhotos().size() == 0) {
                        for (int i = 0; i < 4; i++) {
                            sliderView.setImageUrl("https://www.abc.es/Media/201304/22/vallecas-solvia--644x362.JPG");
                            sliderLayout.addSliderView(sliderView);
                        }
                    } else {
                        List<String> photos = new ArrayList<>(response.body().getRows().getPhotos());
                        for (int i = 0; i < photos.size(); i++) {

                            String photos_url = response.body().getRows().getPhotos().get(i);
                            sliderView.setImageUrl(photos_url);
                            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                            sliderLayout.addSliderView(sliderView);
                        }
                    }


                } else {
                    Toast.makeText(PropertyDetailActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SingleResponseContainer> call, Throwable t) {
                Toast.makeText(PropertyDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void findViewsById() {

        sliderLayout = findViewById(R.id.imageSlider);
        title_prop = findViewById(R.id.prop_detail_title);
        price_prop = findViewById(R.id.prop_detail_price);
        address_prop = findViewById(R.id.prop_detail_address);
        rooms_prop = findViewById(R.id.prop_details_rooms);
        size_prop = findViewById(R.id.prop_details_size);
        //createdAt_prop = findViewById(R.id.prop_detail_createdAt);
        category = findViewById(R.id.prop_category_name);
        description_prop = findViewById(R.id.prop_details_description);
        iv_map = findViewById(R.id.iv_map);
        mapView = findViewById(R.id.mapView2);

    }

    public void onClickMapBtn() {
        iv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:0,0?q=" + address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMinZoomPreference(12);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng ny = new LatLng(37.4029000, -5.9894000);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
