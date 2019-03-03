package com.st.jdpolonio.inmobiliapp.ui;

import android.content.Context;

import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.st.jdpolonio.inmobiliapp.R;

import com.st.jdpolonio.inmobiliapp.responses.SingleResponseContainer;
import com.st.jdpolonio.inmobiliapp.retrofit.ServiceGenerator;
import com.st.jdpolonio.inmobiliapp.services.PropertyService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String property_id, title, address, rooms, price, size, description, createdAt, category_name,loc;
    private SliderLayout sliderLayout;
    private TextView title_prop, price_prop, address_prop, rooms_prop, size_prop, createdAt_prop, description_prop, category;
    private MapView mapView;

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
            loc = extras.getString("PROPERTY_LOC");
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

    }

    /**
     * Método para convertir una dirección a LatLng
     **/
    /* public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }*/

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
        description_prop.setText(description);
        category.setText(category_name);

    }

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
        category = findViewById(R.id.prop_category_name);
        description_prop = findViewById(R.id.prop_details_description);
        mapView = findViewById(R.id.mapView);

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

        String[] latlong = loc.split(",");

        double lat = Double.parseDouble(latlong[0]);
        Double lon = Double.parseDouble(latlong[1]);

        LatLng latLng = new LatLng(lat,lon);
        googleMap.setMinZoomPreference(12);
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(price + " €/mes")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_house))
        );
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
    }
}
